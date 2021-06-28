package com.sda.ApartmentsRent.service;

import com.sda.ApartmentsRent.model.Apartment;
import com.sda.ApartmentsRent.model.Reservation;
import com.sda.ApartmentsRent.repository.ReservationRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
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
        return date.isAfter(reservation.getReservationStart()) || date.isBefore(reservation.getReservationEnd());
    }

    public void addReservation(Reservation reservation, long apartmentId) {
        log.info("ReservationService: adding reservation to apartmentId=" + apartmentId);
        Optional<Apartment> apartmentOptional = apartmentService.getApartmentById(apartmentId);
        if (apartmentOptional.isPresent() &&
                isReservationDateAvailable(apartmentOptional.get().getId(), reservation.getReservationStart(), reservation.getReservationEnd())) {
            reservation.setApartment(apartmentOptional.get());
            reservationRepository.save(reservation);
        }
    }

    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public List<Reservation> getAllReservationsSorted() {
        return getAllReservations().stream()
                .sorted(Comparator.comparing(Reservation::getReservationStart))
                .collect(Collectors.toList());
    }
}
