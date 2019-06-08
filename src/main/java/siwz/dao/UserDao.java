package siwz.dao;

import siwz.model.AppUser;

public abstract class UserDao extends BasicDao {
    public abstract void save(AppUser appUser);
    public abstract AppUser findUserByName(String name);
    public abstract void remove(AppUser appUser);
    public abstract void merge(AppUser appUser);

    public abstract AppUser findUserByUserNameOrEmail(String name, String email);

    public abstract void updateUser(AppUser user);
}
