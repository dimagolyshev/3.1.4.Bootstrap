package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface AdminViewFormatter {
    public String formatUserRoles(Set<Role> roles);
}