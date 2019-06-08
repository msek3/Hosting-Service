package siwz.dao;

import siwz.model.AppRole;

public abstract class RoleDao extends BasicDao {
    public abstract AppRole findRoleByName(String name);
    public abstract void save(AppRole appRole);
}
