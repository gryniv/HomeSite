package com.home.site.controller;


import com.home.site.domain.User;
import com.home.site.domain.dto.*;
import com.home.site.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.util.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.*;
import java.util.*;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;


    private final RestTemplate restTemplate;

    public RegistrationController(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirmation,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        assert response != null;
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirmation);

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}