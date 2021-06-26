package com.sda.ApartmentsRent.controller;

import com.sda.ApartmentsRent.model.Apartment;
import com.sda.ApartmentsRent.model.ApartmentOwner;
import com.sda.ApartmentsRent.model.enums.ApartmentPurpose;
import com.sda.ApartmentsRent.model.enums.ApartmentStyle;
import com.sda.ApartmentsRent.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/apart")
public class ApartmentController {
    private final ApartmentService apartmentService;

    @GetMapping
    public String getAllApartments(Model model) {
        model.addAttribute("apartmentList", apartmentService.getAllApartments());
        return "apart-list";
    }

    @GetMapping("/rentable")
    public String getAllRentableApartments(Model model) {
        model.addAttribute("apartmentList", apartmentService.getAllRentableApartments());
        return "apart-list";
    }

    @GetMapping("/{apartmentId}")
    public String getApartmentDetails(Model model, @PathVariable long apartmentId) {
        Optional<Apartment> apartmentOptional = apartmentService.getApartmentById(apartmentId);
        if (apartmentOptional.isPresent()) {
            model.addAttribute("apartDetails", apartmentOptional.get());
            return "apart-details";
        } else {
            return "redirect:/apart";
        }
    }

    @GetMapping("/add")
    public String addApartmentForm(Model model, long ownerId) {
        model.addAttribute("newApart", new Apartment());
        model.addAttribute("apartmentOwnerId", ownerId);
        model.addAttribute("stylesList", ApartmentStyle.values());
        model.addAttribute("purposeList", ApartmentPurpose.values());
        return "apart-addForm";
    }

    @PostMapping("/add")
    public String addApartment(Apartment apartment, long ownerId) {
        apartmentService.addApartment(apartment, ownerId);
        return "redirect:/owner/"+ownerId;
    }

    @GetMapping("/delete")
    public String deleteApartment(long apartIdToDelete) {
        Optional<Apartment> apartmentOptional = apartmentService.getApartmentById(apartIdToDelete);
        if (apartmentOptional.isPresent()) {
            long ownerId = apartmentOptional.get().getApartmentOwner().getOwnerId();
            apartmentService.deleteApartment(apartIdToDelete);
            return "redirect:/owner/" + ownerId;
        } else {
            return "redirect:/owner";
        }
    }

    @GetMapping("/edit")
    public String editApartment(Model model, long apartIdToEdit) {
        Optional<Apartment> apartmentOptional = apartmentService.getApartmentById(apartIdToEdit);
        if (apartmentOptional.isPresent()) {
            model.addAttribute("newApart", apartmentOptional.get());
            model.addAttribute("apartmentOwnerId", apartmentOptional.get().getApartmentOwner().getOwnerId());
            model.addAttribute("stylesList", ApartmentStyle.values());
            model.addAttribute("purposeList", ApartmentPurpose.values());
            return "apart-addForm";
        } else {
            return "redirect:/apart";
        }
    }

}
