package io.file;

import exception.DataExportException;
import exception.DataImportException;
import model.BettingSystem;
import model.SystemUser;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "UsersDB.o";

    @Override
    public void exportUserData(SystemUser systemUser, String login) {
        try(FileOutputStream fos = new FileOutputStream(login+".o");
            ObjectOutputStream oos = new ObjectOutputStream(fos)
        ){
            oos.writeObject(systemUser);
        } catch (FileNotFoundException e){
            throw new DataExportException("File no founded " + login+".o");
        } catch (IOException e){
            throw new DataExportException("Error writing data to the file" + login+".o");
        }
    }

    @Override
    public void exportBettingSystemData(BettingSystem bettingSystem) {
            try(FileOutputStream fos = new FileOutputStream(FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
            ){
                oos.writeObject(bettingSystem);
            } catch (FileNotFoundException e){
                throw new DataExportException("File no founded " + FILE_NAME);
            } catch (IOException e){
                throw new DataExportException("Error writing data to the file" + FILE_NAME);
            }
    }

    @Override
    public SystemUser importUserData(String login) {
       try(FileInputStream fis = new FileInputStream(login+".o");
           ObjectInputStream ois = new ObjectInputStream(fis)
       ) {
           return (SystemUser) ois.readObject();
       } catch (FileNotFoundException e){
           throw new DataImportException("There's no such file " + login+".o");
       } catch (IOException e){
           throw new DataImportException("Reading data error" + login+".o");
       } catch (ClassNotFoundException e){
           throw new DataImportException("Wrong file format " + login+".o");
       }
    }

    @Override
    public BettingSystem importBettingSystemData() {
        try(FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (BettingSystem) ois.readObject();
        } catch (FileNotFoundException e){
            throw new DataImportException("There's no such file " + FILE_NAME);
        } catch (IOException e){
            throw new DataImportException("Reading data error" + FILE_NAME);
        } catch (ClassNotFoundException e){
            throw new DataImportException("Wrong file format " + FILE_NAME);
        }
    }
}
