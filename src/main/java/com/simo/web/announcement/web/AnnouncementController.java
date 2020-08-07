package com.simo.web.announcement.web;

import com.simo.web.announcement.model.AnnouncementRegisterDTO;
import com.simo.web.announcement.service.AnnouncementServiceImpl;
import com.simo.web.user.model.RoleEntity;
import com.simo.web.user.model.UserServiceDTO;
import com.simo.web.user.model.mapper.UserMapper;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementServiceImpl announcementService;
    private final UserServiceImpl userService;

    public AnnouncementController(AnnouncementServiceImpl announcementService, UserServiceImpl userService) {
        this.announcementService = announcementService;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CLEANER') || hasRole('ROLE_CLIENT')")
    @GetMapping
    public String userAnnouncement(Model model, Principal principal){
        UserServiceDTO currentUser =
                UserMapper.INSTANCE.mapUserEntityToUserServiceModel(
                        this.userService.findUserByUsername(principal.getName()));

        List<String> userRoles = currentUser.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        if (userRoles.contains("ROLE_ADMIN")){
            model.addAttribute("announcements",
                    announcementService.findAll());
        }else{
            model.addAttribute("announcements",
                    announcementService.findAllByUserEmail(principal.getName()));
        }

        return "announcement/announcements";
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newTask(Model model){

        AnnouncementRegisterDTO formData;

        if (model.containsAttribute("formData")) {
            formData = (AnnouncementRegisterDTO) model.getAttribute("formData");
        }else{
            formData = new AnnouncementRegisterDTO();
        }

        model.addAttribute("formData", formData);

        return "announcement/new";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("formData") AnnouncementRegisterDTO announcementRegisterDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("formData", announcementRegisterDTO);
            redirectAttributes
                    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formData",
                            bindingResult);

            return "redirect:/announcements/new";
        }

        announcementService.createOrUpdateAnnouncement(announcementRegisterDTO);

        return "redirect:/announcements";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public String delete(@ModelAttribute(name = "deleteId") Long deleteId){
        announcementService.delete(deleteId);

        return "redirect:/announcements";
    }
}
