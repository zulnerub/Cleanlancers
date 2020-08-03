package com.simo.web.comment.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResponseMapper {

    ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

    ResponseServiceDTO mapResponseEntityToResponseServiceDto(ResponseEntity responseEntity);

    ResponseServiceDTO mapResponseAddDtoToResponseServiceDto(ResponseAddDTO responseAddDTO);

    ResponseEntity mapResponseServiceDtoToResponseEntity(ResponseServiceDTO responseServiceDto);
}
