package com.sda.ApartmentsRent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    @Formula(value="(SELECT COUNT(*) FROM apartment a WHERE a.apartment_owner_owner_id=owner_id)")
    private Integer apartmentsCount;

    @OneToMany(mappedBy = "apartmentOwner")
    List<Apartment> apartments;

}
