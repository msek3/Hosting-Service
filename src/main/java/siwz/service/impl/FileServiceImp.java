package siwz.service.impl;

import siwz.dao.FileDao;
import siwz.dao.UserDao;
import siwz.exception.FileNotExistingException;
import siwz.files.FileDispatcher;
import siwz.files.FileIdValidator;
import siwz.model.AppFile;
import siwz.model.AppUser;
import siwz.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileServiceImp implements FileService {

    private FileDao fileDao;
    private UserDao userDao;
    private FileDispatcher dispatcher = new FileDispatcher();

    @Autowired
    public FileServiceImp(FileDao fileDao, UserDao userDao) {
        this.fileDao = fileDao;
        this.userDao = userDao;
    }

    @Override
    public List<AppFile> getTop10Files() {
        List<AppFile> files = fileDao.getTop10Files();
        files.forEach(file -> file.setOwner(null));
        return files;
    }

    @Override
    public List<AppFile> getFilesByUser(AppUser user) {
        List<AppFile> files = fileDao.findFilesByUser(user);
        files.forEach(file -> file.setOwner(null));
        return files;
    }

    @Override
    public Resource loadFileById(Long id) {
        if(!FileIdValidator.validate(id.toString())){
            throw new FileNotExistingException();
        }
        AppFile file = fileDao.getFileById(id);
        fileDao.updateDownloads(id);
        return dispatcher.loadFile(file.getName());
    }

    @Override
    public Long save(MultipartFile file, String username) {
        AppFile appFile = new AppFile();
        AppUser user = userDao.findUserByName(username);
        appFile.setOwner(user);
        appFile.setName(file.getOriginalFilename());
        appFile.setDownloads(0L);
        dispatcher.store(file);
        fileDao.save(appFile);
        user.setFiles(fileDao.findFilesByUser(user));
        user.getFiles().add(appFile);
        userDao.updateUser(user);
        return appFile.getId();
    }
}