package com.simo.web.comment.service;

import com.simo.web.comment.model.CommentEntity;
import com.simo.web.comment.model.CommentServiceDTO;

public interface CommentService {

    CommentEntity findById(Long id);

    CommentEntity save(CommentServiceDTO commentServiceDTO);

    void deleteById(Long id);

    void delete(Long deleteId);
}
