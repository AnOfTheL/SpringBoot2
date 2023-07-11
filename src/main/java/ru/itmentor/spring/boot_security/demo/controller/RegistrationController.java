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
        //System.out.println(userFromDb.get().toString());

        if (userFromDb.isPresent()) {
            model.addAttribute("message", "User exists!");
            return registration(model);
        }

        User user = new User(name, lastname, age);
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);

        /*Optional<Role> roleFromDb = roleService.getByParam("USER");
        //System.out.println(roleFromDb.get().toString());

        if (!roleFromDb.isEmpty()) {
            //Set<Role> roleSet = new HashSet<>();
            //roleSet.add(roleFromDb.get());
            user.setRoles(Collections.singleton(roleFromDb.get()));
        }

        user.setRoles(Collections.singleton(new Role("USER")));*/

        userService.save(user);

        return "redirect:/login";
    }
}
