package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.ServiceApplication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private ServiceApplication<User> userService;

    @GetMapping()
    public String selectAll(ModelMap model) {
        model.addAttribute("users", userService.getAll());
        return "show";
    }

    @GetMapping("/{id}")
    public String selectById(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id).get());
        return "userCrud";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        //model.addAttribute("roles", Role.values());
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

        User user = new User(name, lastname, age);
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);

        Set<Role> sRoles = new HashSet<>();
        Collections.addAll(sRoles, roles);
        user.setRoles(sRoles);

        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getById(id).get());
        return "edit";
    }

    @PostMapping("/{id}/update")
    public String update(@RequestParam("name") String name,
                         @RequestParam("lastname") String lastname,
                         @RequestParam("age") byte age,
                         @PathVariable("id") long id) {
        userService.update(id, new User(name, lastname, age));
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
