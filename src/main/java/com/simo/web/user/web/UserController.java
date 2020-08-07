package com.simo.web.user.web;

import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.service.RegionService;
import com.simo.web.user.model.UserRegisterDTO;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final RegionService regionService;

    public UserController(UserServiceImpl userService, RegionService regionService) {
        this.userService = userService;
        this.regionService = regionService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());

        List<RegionEntity> allRegions = this.regionService.findAllRegions();
        model.addAttribute("regions", allRegions);

        return "users/register";
    }

    @PostMapping("/register")
    public String registerConfirm(
            @Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
//            @ModelAttribute("regions") List<RegionEntity> allRegions,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            return "users/register";
        }

        if (userService.existUser(userRegisterDTO.getEmail())) {
            bindingResult.rejectValue(
                    "email",
                    "error.email",
                    "An account with this email already exists.");
            return "users/register";
        }

        userService.createAndLoginUser(userRegisterDTO);

        return "redirect:/home";

    }

}
