package com.simo.web.user.service;


import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserRegisterDTO;

public interface UserService {

    boolean existUser(String email);

    UserEntity findUserByUsername(String username);

    UserEntity registerUser(UserRegisterDTO userRegisterDTO);

    void createAndLoginUser(UserRegisterDTO userRegisterDTO);

}
