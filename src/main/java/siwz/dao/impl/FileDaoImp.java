package siwz.dao.impl;

import siwz.dao.FileDao;
import siwz.exception.FileNotExistingException;
import siwz.model.AppFile;
import siwz.model.AppUser;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import siwz.model.DownloadsPerDay;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileDaoImp extends FileDao {

    @Autowired
    public FileDaoImp(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(AppFile file) {
        doWithSession(factory, session -> session.save(file));
    }

    @Override
    public List<AppFile> findFilesByUser(AppUser appUser) {
        List<AppFile> files = new ArrayList<>();
        doWithSession(factory, session -> {
            Query<AppFile> query = session.createQuery("select f from file f , user_files uf where uf.file_id = f.id AND uf.user_id =:id", AppFile.class);
            query.setParameter("id", appUser.getId());
            files.addAll(query.list());
        });
        return files;
    }

    @Override
    public void remove(AppFile file) {
        doWithSession(factory, session -> session.delete(file));
    }

    @Override
    public List<AppFile> getTop10Files() {
        List<AppFile> files = new ArrayList<>();
        doWithSession(factory, session -> {
            Query<AppFile> query = session.createQuery("from file f order by f.downloads desc ", AppFile.class);
            query.setMaxResults(10);
            files.addAll(query.list());
        });
        return files;
    }

    @Override
    public AppFile getFileById(Long id) {
        List<AppFile> files = new ArrayList<>();
        doWithSession(factory, session -> {
            Query<AppFile> query = session.createQuery("from file f where f.id=:id", AppFile.class);
            query.setParameter("id", id);
            files.addAll(query.list());
        });
        if(!files.isEmpty()){
            return files.get(0);
        }
        throw new FileNotExistingException();
    }

    @Override
    public void updateDownloads(Long id) {
        doWithSession(factory, session -> {
            Query query = session.createQuery("UPDATE file f set f.downloads = f.downloads + 1 WHERE f.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            Query query2 = session.createQuery("update downloads_per_day set amount = amount + 1 where day < :toDate and day > :fromDate");
            query2.setParameter("toDate", LocalDateTime.now());
            query2.setParameter("fromDate", LocalDateTime.now().minusDays(1));
            query2.executeUpdate();
        });

    }

    @Override
    public List<DownloadsPerDay> getFilesFrom7Days() {
        List<DownloadsPerDay> results = new ArrayList<>();
        doWithSession(factory, session -> {
            Query<DownloadsPerDay> query = session.createQuery("from downloads_per_day d where d.day < :toDate and d.day > :fromDate order by d.day desc", DownloadsPerDay.class);
            query.setParameter("toDate", LocalDateTime.now());
            query.setParameter("fromDate", LocalDateTime.now().minusDays(7));
            results.addAll(query.getResultList());
        });
        return results;
    }
}
