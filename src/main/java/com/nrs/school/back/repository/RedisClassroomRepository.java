package com.nrs.school.back.repository;

import com.nrs.school.back.entities.RedisClassroomEntity;
import org.springframework.data.repository.CrudRepository;


public interface RedisClassroomRepository extends CrudRepository<RedisClassroomEntity, String> {
}
