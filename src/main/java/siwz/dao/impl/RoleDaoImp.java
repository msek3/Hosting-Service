package siwz.dao.impl;

import siwz.dao.RoleDao;
import siwz.model.AppRole;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public class RoleDaoImp extends RoleDao {

    @Autowired
    public RoleDaoImp(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public AppRole findRoleByName(String name){
        LinkedList<AppRole> appRoles = new LinkedList<>();
        doWithSession(factory, session -> {
            Query<AppRole> query = session.createQuery("from role r where r.name=:name", AppRole.class);
            query.setParameter("name", name);
            appRoles.add(query.uniqueResult());
        });
        return appRoles.getFirst();
    }

    @Override
    public void save(AppRole appRole) {
        doWithSession(factory, session -> session.save(appRole));
    }

}
