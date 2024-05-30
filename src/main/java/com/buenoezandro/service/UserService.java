package com.buenoezandro.service;

import com.buenoezandro.entity.UserEntity;
import com.buenoezandro.exception.UserNotFoundException;
import com.buenoezandro.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAll(Integer page, Integer pageSize) {
        return this.userRepository.findAll().page(page, pageSize).list();
    }

    public UserEntity findById(UUID userId) {
        return (UserEntity) this.userRepository.findByIdOptional(userId).orElseThrow(UserNotFoundException::new);
    }

    public UserEntity createUser(UserEntity userEntity) {
        this.userRepository.persist(userEntity);
        return userEntity;
    }

    public UserEntity updateUser(UUID userId, UserEntity userEntity) {
        var user = this.findById(userId);
        user.setUsername(userEntity.getUsername());
        this.userRepository.persist(user);
        return user;
    }

    public void deleteById(UUID userId) {
        var user = this.findById(userId);
        this.userRepository.deleteById(user.getUserId());
    }
}