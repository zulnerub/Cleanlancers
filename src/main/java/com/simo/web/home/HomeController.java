package com.simo.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@Preauthorize("hasRole('USER')")
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        return "home/home";
    }

    @GetMapping("/home")
    public String homeAbsolute(Model model) {
        return home(model);
    }

    @PostMapping("/home")
    public String homePost() {
        return "redirect:/home";
    }
}
