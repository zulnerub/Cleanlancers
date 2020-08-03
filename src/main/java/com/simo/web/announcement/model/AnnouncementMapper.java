package com.simo.web.announcement.model;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnnouncementMapper {

    AnnouncementMapper INSTANCE = Mappers.getMapper(AnnouncementMapper.class);

    AnnouncementEntity mapAnnouncementDtoToEntity(AnnouncementDTO dto);

    AnnouncementDTO mapAnnouncementEntityToDto(AnnouncementEntity entity);

}