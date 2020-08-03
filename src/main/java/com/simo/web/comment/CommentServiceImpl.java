package com.simo.web.comment;

import com.simo.web.comment.model.CommentAddDTO;
import com.simo.web.comment.model.CommentEntity;
import com.simo.web.comment.model.CommentMapper;
import com.simo.web.comment.model.CommentServiceDTO;
import com.simo.web.comment.repository.CommentRepository;
import com.simo.web.task.TaskService;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final UserServiceImpl userService;

    public CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userService ) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public CommentEntity findById(Long id){
        return this.commentRepository.findById(id).orElse(null);
    }

    public CommentEntity save(CommentServiceDTO commentServiceDTO){
        CommentEntity commentEntity = CommentMapper.INSTANCE.mapCommentServiceDtoToCommentEntity(commentServiceDTO);

        return this.commentRepository.save(commentEntity);
    }

    public void deleteById(Long id){
        this.commentRepository.deleteById(id);
    }
}
