package com.sda.ApartmentsRent.controller;

import com.sda.ApartmentsRent.model.Reservation;
import com.sda.ApartmentsRent.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/add")
    public String addReservationForm(Model model, long apartmentId) {
        Reservation reservation = new Reservation();
        model.addAttribute("newReservation", reservation);
        model.addAttribute("apartmentToReserveId", apartmentId);
        return "reservation-addForm";
    }

    @PostMapping("/add")
    public String addReservation(Reservation reservation, long apartmentId) {
        reservationService.addReservation(reservation,apartmentId);
        return "apart-list";
    }

}
