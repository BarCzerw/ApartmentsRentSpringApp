package com.sda.ApartmentsRent.controller;

import com.sda.ApartmentsRent.model.ApartmentOwner;
import com.sda.ApartmentsRent.service.ApartmentOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owner")
public class ApartmentOwnerController {
    private final ApartmentOwnerService apartmentOwnerService;

    @GetMapping
    public String getAllOwners(Model model) {
        model.addAttribute("ownerList", apartmentOwnerService.getAllOwners());
        return "owner-list";
    }

    @GetMapping("/{ownerId}")
    public String getOwnerDetails(Model model, @PathVariable long ownerId) {
        Optional<ApartmentOwner> ownerOptional = apartmentOwnerService.getOwnerById(ownerId);
        if (ownerOptional.isPresent()) {
            model.addAttribute("ownerDetails", ownerOptional.get());
            return "owner-details";
        } else {
            return "redirect:/owner";
        }
    }

    @GetMapping("/add")
    public String addOwnerForm(Model model) {
        model.addAttribute("newOwner", new ApartmentOwner());
        return "owner-addForm";
    }

    @PostMapping("/add")
    public String addOwner(ApartmentOwner apartmentOwner) {
        apartmentOwnerService.addOwner(apartmentOwner);
        return "redirect:/owner";
    }

    @GetMapping("/delete")
    public String deleteOwner(long ownerIdToDelete) {
        apartmentOwnerService.deleteOwner(ownerIdToDelete);
        return "redirect:/owner";
    }

    @GetMapping("/edit")
    public String editOwner(Model model, long ownerIdToEdit) {
        Optional<ApartmentOwner> ownerOptional = apartmentOwnerService.getOwnerById(ownerIdToEdit);
        if (ownerOptional.isPresent()) {
            model.addAttribute("newOwner", ownerOptional.get());
            return "owner-addForm";
        } else {
            return "redirect:/owner";
        }
    }

}
