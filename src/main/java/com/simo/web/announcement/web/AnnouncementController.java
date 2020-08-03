package com.simo.web.announcement.web;

import com.simo.web.announcement.AnnouncementService;
import com.simo.web.announcement.model.AnnouncementDTO;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.task.model.RegisterTaskDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN') || hasRole('ROLE_CLEANER')")
    @GetMapping
    public String announcement(Model model){
        model.addAttribute("announcements",
                announcementService.findAll());

        return "announcement/announcements";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newTask(Model model, AnnouncementDTO announcementDTO ){

        AnnouncementDTO formData;

        if (model.containsAttribute("formData")) {
            formData = (AnnouncementDTO) model.getAttribute("formData");
        }else{
            formData = new AnnouncementDTO();
        }

        model.addAttribute("formData", formData);

        return "announcement/new";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("formData") AnnouncementDTO announcementDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("formData", announcementDTO);
            redirectAttributes
                    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formData",
                            bindingResult);

            return "redirect:/announcements/new";
        }

        announcementService.createOrUpdateAnnouncement(announcementDTO);

        return "redirect:/announcements";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public String delete(@ModelAttribute(name = "deleteId") Long deleteId){
        announcementService.delete(deleteId);

        return "redirect:/announcements";
    }
}
