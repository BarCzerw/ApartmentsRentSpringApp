package com.sda.ApartmentsRent.service;

import com.sda.ApartmentsRent.model.Apartment;
import com.sda.ApartmentsRent.model.Reservation;
import com.sda.ApartmentsRent.repository.ReservationRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ApartmentService apartmentService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getApartmentReservations(long apartmentId) {
        Optional<Apartment> optionalApartment = apartmentService.getApartmentById(apartmentId);
        if (optionalApartment.isPresent()) {
            return optionalApartment.get().getReservations();
        } else {
            throw new IllegalArgumentException("Apartment id=" + apartmentId + " not found");
        }
    }

    public boolean isReservationDateAvailable(long apartmentId, LocalDate reservationStart, LocalDate reservationEnd) {
        List<Reservation> apartmentReservations = getApartmentReservations(apartmentId);
        return apartmentReservations.stream().noneMatch(reservation -> isDateCollidingWithReservation(reservation, reservationStart) || isDateCollidingWithReservation(reservation, reservationEnd));
    }

    private boolean isDateCollidingWithReservation(Reservation reservation, LocalDate date) {
        return reservation.getReservationStart().isBefore(date) && reservation.getReservationEnd().isAfter(date);
    }

    public void addReservation(Reservation reservation) {
        if (isReservationDateAvailable(reservation.getApartment().getId(), reservation.getReservationStart(), reservation.getReservationEnd())) {
            reservationRepository.save(reservation);
        }
    }

    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

}
