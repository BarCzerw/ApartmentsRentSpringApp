package com.sda.ApartmentsRent.service;

import com.sda.ApartmentsRent.model.Apartment;
import com.sda.ApartmentsRent.model.ApartmentOwner;
import com.sda.ApartmentsRent.repository.ApartmentOwnerRepository;
import com.sda.ApartmentsRent.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentOwnerRepository apartmentOwnerRepository;

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public List<Apartment> getAllRentableApartments() {
        return getAllApartments().stream()
                .filter(Apartment::isRentable)
                .collect(Collectors.toList());
    }

    public Optional<Apartment> getApartmentById(long id) {
        return apartmentRepository.findById(id);
    }

    public void addApartment(Apartment apartment, long ownerId) {
        Optional<ApartmentOwner> ownerOptional = apartmentOwnerRepository.findById(ownerId);
        if (ownerOptional.isPresent()) {
            apartment.setApartmentOwner(ownerOptional.get());
            apartmentRepository.save(apartment);
        }
    }

    public void deleteApartment(long apartmentId) {
        apartmentRepository.deleteById(apartmentId);
    }
}
