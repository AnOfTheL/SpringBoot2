package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.implementation.RoleService;
import ru.itmentor.spring.boot_security.demo.service.implementation.UserService;

import java.util.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String selectAll(ModelMap model) {
        model.addAttribute("users", userService.getAll());
        return "show";
    }

    @GetMapping("/{id}")
    public String selectById(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id).orElseThrow(() -> new RuntimeException("User not found")));
        return "userCrud";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        return "new";
    }

    @PostMapping("/new")
    public String create(ModelMap model,
                         @RequestParam("name") String name,
                         @RequestParam("lastname") String lastname,
                         @RequestParam("age") byte age,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("roles") Role[] roles)
    {
        Optional<User> userFromDb = userService.getByParam(username);

        if (userFromDb.isPresent()) {
            model.addAttribute("message", "User exists!");
            return newUser(model);
        }

        User user = new User(name, lastname, age, username, password);

        if (roles != null) {
            Arrays.stream(roles).forEach(role->userService.addRoleByService(user, role));
        }

        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model,
                       @PathVariable("id") long id) {
        model.addAttribute("user", userService.getById(id).orElseThrow(() -> new RuntimeException("User not found")));
        model.addAttribute("roles", roleService.getAll());
        return "edit";
    }

    @PostMapping("/{id}/update")
    public String update(@RequestParam("name") String name,
                         @RequestParam("lastname") String lastname,
                         @RequestParam("age") byte age,
                         @RequestParam(value ="roles", required = false) Role[] roles,
                         @PathVariable("id") long id) {
        User user = userService.getById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(name);
        user.setLastname(lastname);
        user.setAge(age);

        if (roles != null) {
            Arrays.stream(roles).forEach(role->userService.addRoleByService(user, role));
        }

        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
