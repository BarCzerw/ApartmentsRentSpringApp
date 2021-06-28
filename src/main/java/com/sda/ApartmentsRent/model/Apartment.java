package com.sda.ApartmentsRent.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sda.ApartmentsRent.model.enums.ApartmentPurpose;
import com.sda.ApartmentsRent.model.enums.ApartmentStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private double area;
    private String yearBuild;
    @Enumerated(value = EnumType.STRING)
    private ApartmentStyle apartmentStyle;
    @ManyToOne
    @ToString.Exclude
    @JsonBackReference
    private ApartmentOwner apartmentOwner;
    @OneToMany(mappedBy = "apartment")
    private List<Reservation> reservations;
    private double rentCost;
    private double utilitiesCost;
    @Enumerated(value = EnumType.STRING)
    private ApartmentPurpose apartmentPurpose;

    private boolean rented;

    public boolean isRentable() {
        return !rented;
    }

    public List<Reservation> getReservationsSorted() {
        return reservations.stream().sorted(Comparator.comparing(Reservation::getReservationStart)).collect(Collectors.toList());
    }

}
