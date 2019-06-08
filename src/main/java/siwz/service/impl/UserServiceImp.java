package siwz.service.impl;

import siwz.dao.RoleDao;
import siwz.dao.UserDao;
import siwz.model.AppUser;
import siwz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImp(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public AppUser getUserByUsername(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public void saveUser(AppUser user) {
        user.getRoles().add(roleDao.findRoleByName("ROLE_USER"));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public AppUser getUserByUsernameOrEmail(String name, String email) {
        return userDao.findUserByUserNameOrEmail(name, email);
    }
}
