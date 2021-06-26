package com.sda.ApartmentsRent.repository;

import com.sda.ApartmentsRent.model.ApartmentOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentOwnerRepository extends JpaRepository<ApartmentOwner,Long> {

}
