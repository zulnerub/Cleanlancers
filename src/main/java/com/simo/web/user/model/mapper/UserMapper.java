package com.simo.web.user.model.mapper;


import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserRegisterDTO;
import com.simo.web.user.model.UserServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserServiceModel mapUserRegisterDtoToUserServiceModel(UserRegisterDTO userRegisterDTO);

    UserEntity mapUserServiceModelToUserEntity(UserServiceModel userServiceModel);

    UserServiceModel mapUserEntityToUserServiceModel(UserEntity saveAndFlush);
}
