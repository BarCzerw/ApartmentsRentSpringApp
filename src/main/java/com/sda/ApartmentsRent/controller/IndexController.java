package com.sda.ApartmentsRent.controller;

import com.sda.ApartmentsRent.model.Apartment;
import com.sda.ApartmentsRent.model.Reservation;
import com.sda.ApartmentsRent.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final ApartmentService apartmentService;

    @GetMapping
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/authorized")
    public String getAuthenticatedPage() {
        return "apart_list";
    }

    @GetMapping("/apartments")
    public String getApartmentForUserPage(Model model) {
        model.addAttribute("apartmentList", apartmentService.getAllApartments());
        return "new-apart-list";
    }

    @GetMapping("/apartments/{id}")
    public String getApartmentDetailForUserPage(Model model, @PathVariable long id) {
        Optional<Apartment> apartmentOptional = apartmentService.getApartmentById(id);
        if (apartmentOptional.isPresent()) {
            model.addAttribute("apartDetails", apartmentOptional.get());
            model.addAttribute("newReservation", new Reservation());
            model.addAttribute("apartmentToReserveId", id);
            return "new-apart-details";
        } else {
            return "redirect:/apartments";
        }
    }

}
