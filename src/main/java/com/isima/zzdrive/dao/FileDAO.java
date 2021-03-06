/**
 * ZZDrive - 2014
 *
 * @author Arnaud CHALIEZ
 * @author Jérémy BOUNY
 */
package com.isima.zzdrive.dao;

import com.isima.zzdrive.model.File;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO {

    @Getter
    @Setter
    @Autowired
    SessionFactory sessionFactory;

    public List getFilesByUserId(int idOwner) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from File as f where f.idowner = :idowner order by f.type, f.name")
                .setParameter("idowner", idOwner).list();
        return list;
    }

    public List getSharedFilesByUserId(int idUser) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select f from File as f, Access a where a.id.idfileaccess = f.idfile AND a.id.iduseraccess = :iduser order by f.type, f.name")
                .setParameter("iduser", idUser).list();
        return list;
    }

    public List getFilesDirectoryByUserId(int idOwner, int idDirectory) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from File as f where f.idowner = :idowner and f.iddirectory = :directory order by f.type, f.name")
                .setParameter("idowner", idOwner)
                .setParameter("directory", idDirectory).list();
        return list;
    }

    public List getFilesDirectory(int idDirectory) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from File as f where f.iddirectory = :directory order by f.type, f.name")
                .setParameter("directory", idDirectory).list();
        return list;
    }

    public List getFilesByDirectory(int idDirectory) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from File as f where f.iddirectory = :iddirectory order by f.type, f.name")
                .setParameter("iddirectory", idDirectory).list();
        return list;
    }

    public void addFile(File file) {
        getSessionFactory().getCurrentSession().save(file);
    }

    public void deleteFile(File file) {
        getSessionFactory().getCurrentSession().delete(file);
    }

    public void updateFile(File file) {
        getSessionFactory().getCurrentSession().update(file);
    }
}
