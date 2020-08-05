package com.simo.web.comment.service;

import com.simo.web.comment.model.CommentEntity;
import com.simo.web.comment.model.mapper.CommentMapper;
import com.simo.web.comment.model.CommentServiceDTO;
import com.simo.web.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public CommentEntity findById(Long id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    @Override
    public CommentEntity save(CommentServiceDTO commentServiceDTO) {
        CommentEntity commentEntity = CommentMapper.INSTANCE.mapCommentServiceDtoToCommentEntity(commentServiceDTO);

        return this.commentRepository.save(commentEntity);
    }

    @Override
    public void deleteById(Long id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    public void delete(Long deleteId) {
        this.commentRepository.deleteById(deleteId);
    }


}
