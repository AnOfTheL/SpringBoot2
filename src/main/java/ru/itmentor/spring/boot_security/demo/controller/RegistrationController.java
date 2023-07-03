package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public String registration(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping()
    public String addUser(
            ModelMap model,
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("age") byte age,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("roles") Role[] roles) {

        User userFromDb = userService.getUserByUsername(username);

        if (userFromDb != null) {
            model.addAttribute("message", "User exists!");
            return registration(model);
        }

        User user = new User(name, lastname, age);
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);

        Set<Role> sRoles = new HashSet<>();
        Collections.addAll(sRoles, roles);
        user.setRoles(sRoles);

        userService.saveUser(user);

        return "redirect:/login";
    }
}
