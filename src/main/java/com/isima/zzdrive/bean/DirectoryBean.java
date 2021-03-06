/**
 * ZZDrive - 2014
 *
 * @author Arnaud CHALIEZ
 * @author Jérémy BOUNY
 */
package com.isima.zzdrive.bean;

import com.isima.zzdrive.service.DirectoryService;
import java.io.Serializable;
import java.util.AbstractMap;
import com.isima.zzdrive.model.Directory;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "directoryBean")
@ViewScoped
public class DirectoryBean implements Serializable {

    private int currentDirectory;

    private int sharedDirectory;

    @Getter
    @Setter
    @ManagedProperty("#{DirectoryService}")
    transient DirectoryService directoryService;

    @Getter
    @Setter
    @ManagedProperty("#{userBean}")
    transient private UserBean userBean;

    @PostConstruct
    private void init() {
        setCurrentIdDirectory(userBean.getIdHome());
        setSharedDirectory(-1);
    }

    @Getter
    private ArrayList<AbstractMap.SimpleEntry<Integer, String>> parents = null;

    @Getter
    private ArrayList<AbstractMap.SimpleEntry<Integer, String>> sharedParents = null;

    public int getCurrentIdDirectory() {
        return this.currentDirectory;
    }

    public void setCurrentIdDirectory(Integer currentDirectory) {

        if (null == currentDirectory) {
            return;
        }
        this.currentDirectory = currentDirectory;

        Directory directory = getDirectoryService().getDirectoryById(currentDirectory);
        Directory parent = directory;

        if (null != directory) {
            if (null == parents) {
                parents = new ArrayList<>();
            } else {
                parents.clear();
            }

            do {
                parents.add(0, new AbstractMap.SimpleEntry(parent.getIdfile(), parent.getName()));
            } while (parent.getIdfile() != parent.getIddirectory()
                    && (parent = getDirectoryService().getDirectoryById(parent.getIddirectory())) != null);
        }
    }

    public int getDirectory() {
        return currentDirectory;
    }

    public void setDirectory(int directory) {
        setCurrentIdDirectory(directory);
    }

    public int getSharedDirectory() {
        return sharedDirectory;
    }

    public void setSharedDirectory(int idDirectory) {
        this.sharedDirectory = idDirectory;

        Directory directory = getDirectoryService().getDirectoryById(idDirectory);

        if (null == sharedParents) {
            sharedParents = new ArrayList<>();
        } else {
            sharedParents.clear();
        }

        if (null != directory) {
            sharedParents.add(0, new AbstractMap.SimpleEntry(directory.getIdfile(), directory.getName()));
        }

        sharedParents.add(0, new AbstractMap.SimpleEntry(-1, "Shared home"));
    }
}
