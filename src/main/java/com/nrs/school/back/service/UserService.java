package com.nrs.school.back.service;

import com.nrs.school.back.entities.UserEntity;
import com.nrs.school.back.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> allUsers() {

        return new ArrayList<>(userRepository.findAll());
    }
}
