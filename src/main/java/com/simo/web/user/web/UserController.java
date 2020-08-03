package com.simo.web.user.web;

import com.simo.web.user.model.UserMapper;
import com.simo.web.user.model.UserRegisterDTO;
import com.simo.web.user.model.UserServiceModel;
import com.simo.web.user.service.UserServiceImpl;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public String register(@Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult) {

        return "users/register";
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(
            @Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            ModelAndView modelAndView,
            RedirectAttributes redirectAttributes) {



        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            modelAndView.setViewName("redirect:/users/register");
        }

        boolean passMatch = userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());

        if (!passMatch || userRegisterDTO.getRole() == null) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("matching", passMatch);
            redirectAttributes.addFlashAttribute("incorrectRole", userRegisterDTO.getRole() == null);
            modelAndView.setViewName("redirect:/users/register");
        }else {
            UserServiceModel userServiceModel = this.userService.registerUser(userRegisterDTO);
            modelAndView.setViewName("redirect:/users/login");
        }

        return modelAndView;

    }

}
