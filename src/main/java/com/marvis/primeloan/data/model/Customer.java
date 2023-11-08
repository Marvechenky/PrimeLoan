package com.marvis.primeloan.data.model;
import com.marvis.primeloan.data.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

    @OneToOne(cascade = ALL)
    private AppUser appUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Gender gender;

    private String birthDay;
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    private Loan loan;

}
