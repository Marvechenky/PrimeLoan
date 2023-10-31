package com.marvis.primeloan.data.model;

import com.marvis.primeloan.enums.Gender;
import com.marvis.primeloan.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate DateOfBirth;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String phoneNumber;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    @Email(message = "This field requires a valid email address")
    private String email;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

}
