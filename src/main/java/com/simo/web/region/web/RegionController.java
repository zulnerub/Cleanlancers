package com.simo.web.region.web;

import com.simo.web.region.RegionService;
import com.simo.web.region.model.RegionEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/regions")
//@PreAuthorize("hasRole('ADMIN')")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public String regions(Model model){

        List<RegionEntity> allRegions = this.regionService.findAllRegions();

        model.addAttribute("regions", !allRegions.isEmpty() ? allRegions : new ArrayList<>());

        return "region/regions";
    }
}
