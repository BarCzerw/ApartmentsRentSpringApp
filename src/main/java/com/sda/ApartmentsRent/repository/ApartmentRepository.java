package com.sda.ApartmentsRent.repository;

import com.sda.ApartmentsRent.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment,Long> {
}
