package siwz.dao.impl;

import siwz.dao.UserDao;
import siwz.model.AppUser;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public class UserDaoImp extends UserDao {

    @Autowired
    public UserDaoImp(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(AppUser appUser){
        doWithSession(factory,
                session -> session.save(appUser));
    }

    @Override
    public AppUser findUserByName(String name) {
        LinkedList<AppUser> appUser = new LinkedList<>();
        doWithSession(factory, session -> {
            Query<AppUser> query = session.createQuery("from user u where u.name=:name", AppUser.class);
            query.setParameter("name", name);
            appUser.addAll(query.list());
        });
        if(appUser.isEmpty())
            return null;
        return appUser.getFirst();
    }

    @Override
    public void remove(AppUser appUser){
        doWithSession(factory, session -> session.delete(appUser));
    }

    @Override
    public void merge(AppUser appUser) {
        doWithSession(factory, session -> session.merge(appUser));
    }

    @Override
    public AppUser findUserByUserNameOrEmail(String name, String email) {
        LinkedList<AppUser> appUser = new LinkedList<>();
        doWithSession(factory, session -> {
            Query<AppUser> query = session.createQuery("from user u where u.name=:name or u.email=:email", AppUser.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            appUser.addAll(query.list());
        });
        if(appUser.isEmpty())
            return null;
        return appUser.getFirst();
    }

    @Override
    public void updateUser(AppUser user) {
        doWithSession(factory, session -> session.update(user));
    }
}
