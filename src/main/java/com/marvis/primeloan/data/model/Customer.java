package com.marvis.primeloan.data.model;

import com.marvis.primeloan.data.model.enums.Gender;
import com.marvis.primeloan.data.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

    private LocalDateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String firstName;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String lastName;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String otherName;

    private Gender gender;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String phoneNumber;

    @NaturalId(mutable = true)
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    @Email(message = "This field requires a valid email address")
    private String email;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String password;

    private String birthDay;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    private Loan loan;

    @ElementCollection
    private Set<Role> role;


}
