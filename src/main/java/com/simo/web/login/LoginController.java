package com.simo.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/users/login")
    public String login(){
        return "users/login";
    }

}
