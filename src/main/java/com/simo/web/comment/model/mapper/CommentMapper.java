package com.simo.web.comment.model.mapper;

import com.simo.web.comment.model.CommentAddDTO;
import com.simo.web.comment.model.CommentEntity;
import com.simo.web.comment.model.CommentServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentServiceDTO mapCommentEntityTCommentServiceDto(CommentEntity commentEntity);

     CommentServiceDTO mapCommentAddDtoToCommentServiceDto(CommentAddDTO commentAddDTO);

     CommentEntity mapCommentServiceDtoToCommentEntity(CommentServiceDTO commentServiceDTO);
}
