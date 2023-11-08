package com.marvis.primeloan.data.model;
import com.marvis.primeloan.data.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;


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


    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String firstName;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String lastName;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String otherName;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String phoneNumber;

   // @NaturalId(mutable = true)
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    @Email(message = "This field requires a valid email address")
    private String email;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String password;


    @ElementCollection
    private Set<Role> role;

}
