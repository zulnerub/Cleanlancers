package com.simo.web.user.web;

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

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerConfirm(
            @Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            ModelAndView modelAndView,
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

//        boolean passMatch = userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());
//
//        if (!passMatch || userRegisterDTO.getRole() == null) {
//            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
//            redirectAttributes.addFlashAttribute("matching", passMatch);
//            redirectAttributes.addFlashAttribute("incorrectRole", userRegisterDTO.getRole() == null);
//            modelAndView.setViewName("redirect:/users/register");
//        }else {
//            UserServiceModel userServiceModel = this.userService.registerUser(userRegisterDTO);
//            modelAndView.setViewName("redirect:/users/login");
//        }

        userService.createAndLoginUser(userRegisterDTO);

        return "redirect:/home";

    }

}
