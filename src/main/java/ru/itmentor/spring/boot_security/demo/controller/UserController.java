package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.ServiceApplication;

import javax.sql.DataSource;

@Controller
@RequestMapping("/")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    @Autowired
    private ServiceApplication<User> userService;

    @Autowired
    DataSource dataSource;

    @GetMapping()
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public String selectUser(ModelMap model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByParam(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "userRead";
    }
}