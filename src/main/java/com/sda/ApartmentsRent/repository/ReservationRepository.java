package com.sda.ApartmentsRent.repository;

import com.sda.ApartmentsRent.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
