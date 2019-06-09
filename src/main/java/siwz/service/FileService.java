package siwz.service;

import siwz.model.AppFile;
import siwz.model.AppUser;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    Long save(MultipartFile file, String username);

    Resource loadFileById(Long id);

    List<AppFile> getTop10Files();

    List<AppFile> getFilesByUser(AppUser user);

    List<Long> getDownloadsFrom7Days();
}
