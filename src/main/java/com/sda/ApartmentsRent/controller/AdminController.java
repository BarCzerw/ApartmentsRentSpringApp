package com.sda.ApartmentsRent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
//@PreAuthorize(value = "hasRole('ADMIN')") dokladnie admin
@PreAuthorize(value = "hasAnyRole('ADMIN','SUPERVISOR')") //admin albo supervisor
@RequiredArgsConstructor
public class AdminController {

    @GetMapping
    public String getIndex() {
        return "admin-index";
    }

}
