package com.marvis.primeloan.service;

import com.marvis.primeloan.data.model.AppUser;
import com.marvis.primeloan.data.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService{

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser findUserByEmailIgnoreCase(String email) {
        return  appUserRepository.findByEmailIgnoreCase(email)
                .orElse(null);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }
}
