package ru.levelp.model.api;

/**
 * Created by Мария on 07.10.2016.
 */
import ru.levelp.model.entities.BackupInfo;

import java.util.ArrayList;

public class ResponseContainer {
    private int id;
    private String method;
    private long ts;
    private int backupId;
    private ArrayList<BackupInfo> history;

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public long getTs() {
        return ts;
    }

    public int getBackupId() {
        return backupId;
    }

    public ArrayList<BackupInfo> getHistory() {
        return history;
    }
}