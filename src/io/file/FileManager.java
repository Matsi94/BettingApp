package io.file;

import model.BettingSystem;
import model.SystemUser;

public interface FileManager {
    void exportUserData(SystemUser systemUser, String login);
    void exportBettingSystemData(BettingSystem bettingSystem);
    SystemUser importUserData(String login);
    BettingSystem importBettingSystemData();
}
