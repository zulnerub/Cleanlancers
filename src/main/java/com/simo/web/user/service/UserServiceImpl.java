package com.simo.web.user.service;

import com.simo.web.user.model.*;
import com.simo.web.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity findUserByUsername(String username){
        return this.userRepository.findOneByEmail(username).orElse(null);

    }


    @Override
    public UserServiceModel registerUser(UserRegisterDTO userRegisterDTO) {

        UserServiceModel userServiceModel = UserMapper.INSTANCE.mapUserRegisterDtoToUserServiceModel(userRegisterDTO);

        if (this.userRepository.count() == 0){
            userServiceModel.setRoles(List.of(new RoleEntity("ROLE_ADMIN")));
        }else {
            userServiceModel.setRoles(List.of(new RoleEntity(userRegisterDTO.getRole())));
        }

        userServiceModel.setPasswordHash(this.passwordEncoder.encode(userRegisterDTO.getPassword()));
        userServiceModel.setCleanerTasks(new ArrayList<>());
        userServiceModel.setClientTasks(new ArrayList<>());
        userServiceModel.setComments(new ArrayList<>());
        userServiceModel.setResponses(new ArrayList<>());

        UserEntity userEntity =
                UserMapper.INSTANCE.mapUserServiceModelToUserEntity(userServiceModel);

        return UserMapper.INSTANCE.mapUserEntityToUserServiceModel(this.userRepository.saveAndFlush(userEntity));

    }
}
