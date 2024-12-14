package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViewFormatterImpl implements ViewFormatter {

    @Override
    public String formatUserRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
    }
}
