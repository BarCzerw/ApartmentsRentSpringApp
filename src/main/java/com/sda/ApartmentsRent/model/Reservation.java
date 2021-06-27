package com.sda.ApartmentsRent.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationEnd;

    @ManyToOne()
    @ToString.Exclude
    @JsonBackReference
    private Apartment apartment;

}
