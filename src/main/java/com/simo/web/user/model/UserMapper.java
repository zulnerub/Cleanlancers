package com.simo.web.user.model;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserServiceModel mapUserRegisterDtoToUserServiceModel(UserRegisterDTO userRegisterDTO);

    UserEntity mapUserServiceModelToUserEntity(UserServiceModel userServiceModel);

    UserServiceModel mapUserEntityToUserServiceModel(UserEntity saveAndFlush);
}
