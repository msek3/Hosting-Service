package siwz;

import siwz.dao.FileDao;
import siwz.dao.RoleDao;
import siwz.dao.UserDao;
import siwz.model.AppFile;
import siwz.model.AppRole;
import siwz.model.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = siwz.App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppUserTest {
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    FileDao fileDao;

    @Test
    public void saveUser(){
        AppUser appUser = new AppUser();
        appUser.setName("name4");
        appUser.setPassword("passwd");
        appUser.setEmail("example@example.com");
        AppRole appRole = roleDao.findRoleByName("ROLE_ADMIN");
        appUser.getRoles().add(appRole);
        userDao.save(appUser);
    }

    @Test
    public void saveFileToUser(){
        AppUser appUser = userDao.findUserByName("name4");
        AppFile file = new AppFile();
        file.setName("file2");
        file.setOwner(appUser);
        file.setDownloads(4L);
        fileDao.save(file);

        List<AppFile> filesToSet = fileDao.findFilesByUser(appUser);
        if(filesToSet.isEmpty() || filesToSet.get(0) == null ) {
            appUser.setFiles(new ArrayList<>());
        } else {
            appUser.setFiles(filesToSet);
        }
        appUser.getFiles().add(file);

        List<AppFile> files = fileDao.findFilesByUser(appUser);
        assertEquals(file.getId(), files.get(0).getId());

        fileDao.remove(file);
    }

    @Test
    public void findUser(){
        AppUser appUser = userDao.findUserByName("name4");
        assertNotNull(appUser);
        assertEquals(appUser.getRoles().iterator().next().getName(), "ROLE_ADMIN");
    }

    @Test
    public void deleteUser(){
        AppUser appUser = userDao.findUserByName("name4");
        userDao.remove(appUser);
        assertNull(userDao.findUserByName("name4"));
    }
}