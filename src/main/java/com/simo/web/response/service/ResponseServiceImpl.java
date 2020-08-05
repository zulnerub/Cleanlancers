package com.simo.web.response.service;

import com.simo.web.response.model.ResponseEntity;
import com.simo.web.response.model.mapper.ResponseMapper;
import com.simo.web.response.model.ResponseServiceDTO;
import com.simo.web.response.repository.ResponseRepository;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService {
    private final ResponseRepository responseRepository;

    public ResponseServiceImpl(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public ResponseEntity save(ResponseServiceDTO responseServiceDTO){
        ResponseEntity response = ResponseMapper.INSTANCE.mapResponseServiceDtoToResponseEntity(responseServiceDTO);

        return this.responseRepository.save(response);
    }

    @Override
    public void delete(Long deleteId) {
        responseRepository.deleteById(deleteId);
    }
}
