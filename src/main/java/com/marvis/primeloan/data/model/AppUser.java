package com.marvis.primeloan.data.model;

import com.marvis.primeloan.enums.Gender;
import com.marvis.primeloan.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

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

    private LocalDate birthDay;

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
