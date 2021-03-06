package com.simo.web.user.service;


import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserRegisterDTO;
import com.simo.web.user.model.UserServiceDTO;

import java.util.List;

public interface UserService {

    List<UserEntity> findAllCleaners();

    boolean existUser(String email);

    UserEntity findUserByUsername(String username);

    UserEntity registerUser(UserRegisterDTO userRegisterDTO);

    void createAndLoginUser(UserRegisterDTO userRegisterDTO);

    List<UserServiceDTO> searchCleaners(String email, String firstName, String lastName, String regionName);

    List<String> filterByUserEmail(String userEmail);

    List<String> filterByUserFirstName(String firstName);

    List<String> filterByUserLastName(String lastname);

    List<String> filterByUserRegionName(String regionName);

    List<String> filterCleanersByEmailLikeAndTaskRegion(String emailLike, String taskRegion);

    List<UserServiceDTO> findAllUsersBySearchParams(String username, String firstName, String lastName);

    List<UserServiceDTO> findAllUsers();

    UserServiceDTO findById(Long id);

    void updateUser(UserServiceDTO user);
}
