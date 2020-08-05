package com.simo.web.user.service;

import com.simo.web.user.model.*;
import com.simo.web.user.model.mapper.UserMapper;
import com.simo.web.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean existUser(String email){
        Objects.requireNonNull(email);
        return userRepository.findOneByEmail(email).isPresent();
    }

    @Override
    public UserEntity findUserByUsername(String username){
        return this.userRepository.findOneByEmail(username).orElse(null);
    }

    @Override
    public UserEntity registerUser(UserRegisterDTO userRegisterDTO) {

        UserServiceModel userServiceModel =
                UserMapper.INSTANCE.mapUserRegisterDtoToUserServiceModel(userRegisterDTO);

        if (userRegisterDTO.getPassword() != null){
            userServiceModel.setPasswordHash(
                    this.passwordEncoder.encode(userRegisterDTO.getPassword()));
        }

        if (this.userRepository.count() == 0){
            userServiceModel.setRoles(List.of(new RoleEntity("ROLE_ADMIN")));
        }else {
            userServiceModel.setRoles(List.of(new RoleEntity(userRegisterDTO.getRole())));
        }


        userServiceModel.setCleanerTasks(new ArrayList<>());
        userServiceModel.setClientTasks(new ArrayList<>());
        userServiceModel.setComments(new ArrayList<>());
        userServiceModel.setResponses(new ArrayList<>());

        UserEntity userEntity =
                UserMapper.INSTANCE.mapUserServiceModelToUserEntity(userServiceModel);

        return this.userRepository.saveAndFlush(userEntity);

    }

    @Override
    public void createAndLoginUser(UserRegisterDTO userRegisterDTO){
        UserEntity newUser = registerUser(userRegisterDTO);
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userRegisterDTO.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
