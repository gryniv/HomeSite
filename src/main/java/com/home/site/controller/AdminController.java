package com.home.site.controller;

import com.home.site.domain.*;
import com.home.site.service.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/adminpanel")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping
    public String admin(Model model) {
    return userList(model);
    }

    @GetMapping("/adminpanel")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("useredit/{user}")
    public String userEditForm(@PathVariable User user,
                               Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }


    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam (required=false, name="active") Boolean active,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.saveUser(user, username, email, password, form, active);
        return "redirect:/adminpanel";
    }

}
