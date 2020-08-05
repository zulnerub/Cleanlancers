package com.simo.web.user.model.mapper;

import com.simo.web.user.model.RoleEntity;
import com.simo.web.user.model.RoleServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleServiceDTO mapRoleEntityToRoleServiceDto(RoleEntity roleEntity);
}
