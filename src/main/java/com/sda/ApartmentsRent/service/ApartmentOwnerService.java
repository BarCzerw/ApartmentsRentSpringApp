package com.sda.ApartmentsRent.service;

import com.sda.ApartmentsRent.model.ApartmentOwner;
import com.sda.ApartmentsRent.repository.ApartmentOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApartmentOwnerService {
    private final ApartmentOwnerRepository apartmentOwnerRepository;

    public List<ApartmentOwner> getAllOwners() {
        return apartmentOwnerRepository.findAll();
    }

    public Optional<ApartmentOwner> getOwnerById(long id) {
        return apartmentOwnerRepository.findById(id);
    }

    public void addOwner(ApartmentOwner apartmentOwner) {
        apartmentOwnerRepository.save(apartmentOwner);
    }

    public void deleteOwner(long id) {
        getOwnerById(id).ifPresent(apartmentOwnerRepository::delete);
    }

}
