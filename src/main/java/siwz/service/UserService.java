package siwz.service;

import siwz.model.AppUser;

public interface UserService {

    AppUser getUserByUsername(String name);

    void saveUser(AppUser user);

    AppUser getUserByUsernameOrEmail(String name, String email);
}