package com.marvis.primeloan.service;

import com.marvis.primeloan.config.JwtService;
import com.marvis.primeloan.data.dto.request.LoginRequest;
import com.marvis.primeloan.data.dto.request.RegistrationRequest;
import com.marvis.primeloan.data.model.AppUser;
import com.marvis.primeloan.data.model.Customer;
import com.marvis.primeloan.data.model.Token;
import com.marvis.primeloan.data.repositories.CustomerRepository;
import com.marvis.primeloan.data.repositories.TokenRepository;
import com.marvis.primeloan.data.model.enums.Role;
import com.marvis.primeloan.exception.AppUserManagementException;
import com.marvis.primeloan.utils.ApiResponse;
import com.marvis.primeloan.utils.GenerateApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserService appUserService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public ApiResponse register(RegistrationRequest registrationRequest) {
        if (appUserService.findUserByEmailIgnoreCase(registrationRequest.getEmail()) != null) {
            throw new AppUserManagementException("User with " + registrationRequest.getEmail() + " already exist");
        }
        AppUser savedUser = saveAppUser(registrationRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String jwt = jwtService.generateToken(userDetails);
        return GenerateApiResponse.createdResponse(jwt);
    }

    private AppUser saveAppUser(RegistrationRequest registrationRequest) {
        Customer customer = new Customer();
        Set<Role> appUserRoles = new HashSet<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate birthDay = LocalDate.parse(registrationRequest.getBirthDay(), dateTimeFormatter);
        appUserRoles.add(Role.CUSTOMER);
        AppUser appUser = AppUser.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(appUserRoles)
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .otherName(registrationRequest.getOtherName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .build();
        customer.setAppUser(appUser);
        customerRepository.save(customer);
        return appUserService.saveUser(appUser);
    }

    public ApiResponse login(LoginRequest loginRequest) {
        authenticateUser(loginRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        if (userDetails == null) throw new AppUserManagementException("Unknown user");
        String jwt = jwtService.generateToken(userDetails);
        revokeAllUserToken(loginRequest.getEmail());
        saveToken(jwt, loginRequest.getEmail());
        return GenerateApiResponse.okResponse("Bearer " + jwt);

    }

    private void saveToken(String jwt, String email) {
        Token token = Token.builder()
                .jwt(jwt)
                .isExpired(false)
                .isRevoked(false)
                .email(email)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserToken(String email) {
        var allUserToken = tokenRepository.findTokenByEmailIgnoreCase(email);
        if (allUserToken.isEmpty()) return;
        allUserToken
                .ifPresent(token -> {
                    token.setIsRevoked(true);
                    token.setIsExpired(true);
                    tokenRepository.save(token);
                });
    }

    private void authenticateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
    }


}
