package com.simo.web.region.service;

import com.simo.web.region.model.RegionEntity;

import java.util.List;

public interface RegionService {

    List<RegionEntity> findAllRegions();

    RegionEntity findRegionByName(String name);
}
