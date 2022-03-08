package com.MSK.GSS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Homecontroller {
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }
}
