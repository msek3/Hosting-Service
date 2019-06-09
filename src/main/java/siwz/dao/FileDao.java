package siwz.dao;

import siwz.model.AppFile;
import siwz.model.AppUser;
import siwz.model.DownloadsPerDay;

import java.util.List;

public abstract class FileDao extends BasicDao {

    public abstract void save(AppFile file);

    public abstract List<AppFile> findFilesByUser(AppUser appUser);

    public abstract void remove(AppFile file);

    public abstract List<AppFile> getTop10Files();

    public abstract AppFile getFileById(Long id);

    public abstract void updateDownloads(Long id);

    public abstract List<DownloadsPerDay> getFilesFrom7Days();
}
