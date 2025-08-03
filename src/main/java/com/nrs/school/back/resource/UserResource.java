package com.nrs.school.back.resource;

import com.nrs.school.back.entities.UserEntity;
import com.nrs.school.back.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserEntity> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUserEntity = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(currentUserEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> allUsers() {
        List<UserEntity> userEntities = userService.allUsers();

        return ResponseEntity.ok(userEntities);
    }

}
