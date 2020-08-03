package com.simo.web.task.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskServiceDTO mapTaskEntityToTaskServiceDto(TaskEntity taskEntity);

}
