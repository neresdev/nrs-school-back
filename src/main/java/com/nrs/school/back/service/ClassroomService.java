package com.nrs.school.back.service;

import com.nrs.school.back.entities.ClassroomEntity;
import com.nrs.school.back.entities.RedisClassroomEntity;
import com.nrs.school.back.entities.dto.ClassroomDTO;
import com.nrs.school.back.exceptions.DataIntegrityViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.repository.RedisClassroomRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.StreamSupport;

@Component
public class ClassroomService {

    private static final String JSON_INVALID_MESSAGE = "JSON invalid: ";
    private static final String EXISTING_CLASSROOM_MESSAGE = "Classroom with name %s already exist";
    private static final String CLASSROOM_NOT_FOUND_MESSAGE = "Classroom with id %s not found";

    private final ClassroomRepository repository;
    private final RedisClassroomRepository redisRepository;
    private final ModelMapper mapper;

    public ClassroomService(ClassroomRepository repository, RedisClassroomRepository redisRepository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.redisRepository = redisRepository;
    }

    public List<ClassroomDTO> findAll() {
        final var classroomsDto = this.redisClassrooms();
        if (!classroomsDto.isEmpty()) {
            return classroomsDto;
        }

        final var classRoomEntities = repository.findAll();

        saveRedisClassrooms(classRoomEntities);

        return classRoomEntities.stream().map(c -> mapper.map(c, ClassroomDTO.class)).toList();
    }

    private List<ClassroomDTO> redisClassrooms() {
        final var classroomsDto = new ArrayList<ClassroomDTO>();
        final var classroomEntities = redisRepository.findAll();

        for (RedisClassroomEntity classroomEntity : classroomEntities) {
            if(Objects.nonNull(classroomEntity)) {
                classroomsDto.add(mapper.map(classroomEntity, ClassroomDTO.class));
            }
        }

        return classroomsDto;
    }

    private void saveRedisClassrooms(List<ClassroomEntity> classroomEntities) {
        redisRepository.saveAll(classroomEntities.stream().map(c -> mapper.map(c, RedisClassroomEntity.class)).toList());
    }

    public ClassroomDTO create(ClassroomDTO classroomDTO) {
        String messageValidator = entityValidator(classroomDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegrityViolationException(JSON_INVALID_MESSAGE + messageValidator);

        Optional<ClassroomEntity> classroom = findClassroomByClassroomName(classroomDTO.getClassroomName());

        if(classroom.isPresent()) throw new DataIntegrityViolationException(EXISTING_CLASSROOM_MESSAGE.formatted(classroom.get().getClassroomName()));

        var classroomEntity = mapper.map(classroomDTO, ClassroomEntity.class);

        return mapper.map(repository.save(classroomEntity), ClassroomDTO.class);
    }

    public ClassroomEntity findById(Long classroomId) {
        return repository.findById(classroomId).orElseThrow(() -> new ObjectNotFoundException(CLASSROOM_NOT_FOUND_MESSAGE.formatted(classroomId)));
    }

    private String entityValidator(ClassroomDTO classroomDTO){
        String message = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClassroomDTO>> violations = validator.validate(classroomDTO);
        if(!violations.isEmpty()){
            for(ConstraintViolation<ClassroomDTO> violation: violations) message += violation.getMessage() + "; ";
        }
        return message;

    }

    public ClassroomDTO findByClassroomId(String classroomId) {
        var classroom = repository.findByClassroomReferenceCode(classroomId);
        if (classroom.isEmpty()) {
            throw new ObjectNotFoundException(CLASSROOM_NOT_FOUND_MESSAGE.formatted(classroomId));
        }
        return mapper.map(classroom, ClassroomDTO.class);
    }

    public Optional<ClassroomEntity> findClassroomByClassroomName(String classroomName){
        return repository.findByClassroomName(classroomName);
    }
}
