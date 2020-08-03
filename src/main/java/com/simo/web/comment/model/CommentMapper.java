package com.simo.web.comment.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentServiceDTO mapCommentEntityTCommentServiceDto(CommentEntity commentEntity);

     CommentServiceDTO mapCommentAddDtoToCommentServiceDto(CommentAddDTO commentAddDTO);

     CommentEntity mapCommentServiceDtoToCommentEntity(CommentServiceDTO commentServiceDTO);
}
