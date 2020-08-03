package com.simo.web.comment;

import com.simo.web.comment.model.ResponseEntity;
import com.simo.web.comment.model.ResponseMapper;
import com.simo.web.comment.model.ResponseServiceDTO;
import com.simo.web.comment.repository.ResponseRepository;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl {
    private final ResponseRepository responseRepository;

    public ResponseServiceImpl(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public ResponseEntity save(ResponseServiceDTO responseServiceDTO){
        ResponseEntity response = ResponseMapper.INSTANCE.mapResponseServiceDtoToResponseEntity(responseServiceDTO);

        return this.responseRepository.save(response);
    }
}
