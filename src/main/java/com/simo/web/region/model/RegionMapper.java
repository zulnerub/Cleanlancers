package com.simo.web.region.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegionMapper {

    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

    RegionServiceDTO mapRegionEntityToRegionServiceDto(RegionEntity regionEntity);
}
