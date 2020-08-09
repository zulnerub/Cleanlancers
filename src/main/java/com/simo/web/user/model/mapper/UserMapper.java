package com.simo.web.user.model.mapper;


import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity mapUserServiceModelToUserEntity(UserServiceDTO userServiceDTO);

    UserServiceDTO mapUserEntityToUserServiceModel(UserEntity saveAndFlush);


}
