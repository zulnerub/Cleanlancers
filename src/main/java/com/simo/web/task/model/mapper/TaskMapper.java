package com.simo.web.task.model.mapper;

import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.model.TaskServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskServiceDTO mapTaskEntityToTaskServiceDto(TaskEntity taskEntity);

}
