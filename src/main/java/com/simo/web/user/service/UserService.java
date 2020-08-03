package com.simo.web.user.service;

import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserRegisterDTO;
import com.simo.web.user.model.UserServiceModel;

public interface UserService {
    UserServiceModel registerUser(UserRegisterDTO userRegisterDTO);
    UserEntity findUserByUsername(String username);
}
