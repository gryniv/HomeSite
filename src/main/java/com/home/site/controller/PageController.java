package com.home.site.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
