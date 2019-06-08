package siwz;

import siwz.dao.RoleDao;
import siwz.dao.UserDao;
import siwz.model.AppRole;
import siwz.model.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {siwz.App.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class RolesTest {
    @Autowired
    RoleDao rolesDao;

    @Autowired
    UserDao userDao;

    @Test
    public void testTwoRoles(){
        AppUser appUser = new AppUser();
        appUser.setName("testUser");
        appUser.setPassword("passwd");
        appUser.setEmail("example@example.com");
        AppRole appRole = rolesDao.findRoleByName("ROLE_USER");
        appUser.getRoles().add(appRole);
        appRole = rolesDao.findRoleByName("ROLE_ADMIN");
        appUser.getRoles().add(appRole);
        userDao.save(appUser);

        assertEquals(appUser.getRoles().size(), 2);
    }

    @Test
    public void roleIsSavedCorrectly(){
        AppUser appUser = userDao.findUserByName("testUser");
        int adminRolesSize = appUser.getRoles()
                .stream()
                .filter(role -> role.getName()
                                    .equals("ROLE_ADMIN"))
                .collect(Collectors.toList())
                .size();

        int userRolesSize = appUser.getRoles()
                .stream()
                .filter(role -> role.getName()
                                    .equals("ROLE_USER"))
                .collect(Collectors.toList())
                .size();

        assertEquals(1, adminRolesSize);
        assertEquals(1, userRolesSize);
        userDao.remove(appUser);
    }
}