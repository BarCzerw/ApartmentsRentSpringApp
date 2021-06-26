package com.sda.ApartmentsRent.controller;

import com.sda.ApartmentsRent.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public String getAllReservations(Model model) {
        model.addAttribute("reservationsList", reservationService.getAllReservations());
        return "reservation-list";
    }

}
