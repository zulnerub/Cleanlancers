package com.simo.web.region.service;

import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<RegionEntity> findAllRegions(){
        return this.regionRepository.findAll();
    }

    @Override
    public RegionEntity findRegionByName(String name){

        return this.regionRepository.findByName(name).orElse(null);

    }
}
