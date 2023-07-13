package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.implementation.RoleService;
import ru.itmentor.spring.boot_security.demo.service.implementation.UserService;

import java.util.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private static final String USER = "USER";

    @GetMapping()
    public String registration(ModelMap model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping()
    public String addUser(ModelMap model,
                          @RequestParam("name") String name,
                          @RequestParam("lastname") String lastname,
                          @RequestParam("age") byte age,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password) {

        Optional<User> userFromDb = userService.getByParam(username);

        if (userFromDb.isPresent()) {
            model.addAttribute("message", "User exists!");
            return registration(model);
        }

        User user = new User(name, lastname, age, username, password);

        userService.addRoleByService(user, roleService.getByParam(USER).orElseThrow(()->new RuntimeException("Role not found")));
        userService.save(user);

        return "redirect:/login";
    }
}
