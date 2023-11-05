package com.marvis.primeloan.service;

import com.marvis.primeloan.data.model.AppUser;

public interface AppUserService {

    AppUser findUserByEmailIgnoreCase(String email);

    AppUser saveUser(AppUser appUser);
}
