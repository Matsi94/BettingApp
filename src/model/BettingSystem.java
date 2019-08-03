package model;

import exception.NoDataBaseException;
import exception.UserAlreadyExistsException;
import exception.WrongLoginPasswordException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BettingSystem implements Serializable {
    private Set<SystemUser> users = new HashSet<>();

    public void addUser(SystemUser systemUser) {
        for (SystemUser user : users) {
            if (!user.getLogin().equals(systemUser.getLogin()))
                throw new UserAlreadyExistsException(
                        "User with login " + systemUser.getLogin() + " already exists, please try again");
        }
        users.add(systemUser);
    }

    public SystemUser findUser(SystemUser systemUser) {
        SystemUser sysUser = null;
        if(users.size()!=0){
            for (SystemUser user : users){
                if (user.getLogin().equals(systemUser.getLogin()) && user.getPassword().equals(systemUser.getPassword())){
                    sysUser = user;
                } else
                    throw new WrongLoginPasswordException("Wrong login or password");
            }
        } else
            throw new NoDataBaseException("No database founded.");

        return sysUser;
    }
}
