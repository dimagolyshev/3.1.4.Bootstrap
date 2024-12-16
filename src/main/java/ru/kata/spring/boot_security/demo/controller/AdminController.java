package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.service.ViewFormatter;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final ViewFormatter viewFormatter;

    public AdminController(UserService userService, RoleService roleService, ViewFormatter viewFormatter) {
        this.userService = userService;
        this.roleService = roleService;
        this.viewFormatter = viewFormatter;
    }

    @GetMapping
    public ModelAndView printUsers(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("userDetails",
                userService.getUserDetails(
                        userService.findByUsername(
                                authentication.getName())));
        modelAndView.addObject("users", userService.list());
        modelAndView.addObject("availableRoles", roleService.list());
        modelAndView.addObject("viewFormatter", viewFormatter);
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String password,
                          @RequestParam byte age,
                          @RequestParam String email,
                          @RequestParam List<String> roles) {
        userService.add(firstName, lastName, password, age, email, roles);
        return new ModelAndView("redirect:.");
    }

    @PostMapping(value = "/edit")
    public ModelAndView updateUser(@RequestParam Long id,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String password,
                             @RequestParam byte age,
                             @RequestParam String email,
                             @RequestParam List<String> roles) {
        userService.edit(id, firstName, lastName, password, age, email, roles);
        return new ModelAndView("redirect:.");
    }

    @PostMapping(value = "/delete")
    public ModelAndView deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return new ModelAndView("redirect:.");
    }

}