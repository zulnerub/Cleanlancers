package com.simo.web.user.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleServiceDTO mapRoleEntityToRoleServiceDto(RoleEntity roleEntity);
}
