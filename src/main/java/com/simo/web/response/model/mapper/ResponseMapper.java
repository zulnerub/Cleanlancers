package com.simo.web.response.model.mapper;

import com.simo.web.response.model.ResponseAddDTO;
import com.simo.web.response.model.ResponseEntity;
import com.simo.web.response.model.ResponseServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResponseMapper {

    ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

    ResponseServiceDTO mapResponseAddDtoToResponseServiceDto(ResponseAddDTO responseAddDTO);

    ResponseEntity mapResponseServiceDtoToResponseEntity(ResponseServiceDTO responseServiceDTO);
}
