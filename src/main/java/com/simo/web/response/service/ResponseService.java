package com.simo.web.response.service;

import com.simo.web.response.model.ResponseEntity;
import com.simo.web.response.model.ResponseServiceDTO;

public interface ResponseService {

    ResponseEntity save(ResponseServiceDTO responseServiceDTO);

    void delete(Long deleteId);
}
