package com.simo.web.region;

import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.model.RegionMapper;
import com.simo.web.region.model.RegionServiceDTO;
import com.simo.web.region.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<RegionEntity> findAllRegions(){
        return this.regionRepository.findAll();
    }

    public RegionEntity findRegionByName(String name){

        return this.regionRepository.findByName(name).orElse(null);

    }
}
