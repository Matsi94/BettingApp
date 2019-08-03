package app;

import exception.*;
import io.ConsolePrinter;
import io.DataReader;
import io.file.FileManager;
import io.file.SerializableFileManager;
import model.BettingSystem;
import model.SystemUser;

import java.util.InputMismatchException;

class LoggingControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager serialization;

    private BettingSystem bettingSystem;
    private SystemUser systemUser;

    LoggingControl(){
        serialization = new SerializableFileManager();
            try {
            bettingSystem = serialization.importBettingSystemData();
            printer.printLine("User database imported");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("New database initialized.");
            bettingSystem = new BettingSystem();
        }
    }

    SystemUser loggingLoop(){
        LoggingOption loggingOption;

        do {
            printOptions();
            loggingOption = getOption();
            switch (loggingOption){
                case LOGGING_OPTION:
                    systemUser = loginToAnAccount();
                    break;
                case MAKE_AN_ACCOUNT:
                    systemUser = registerNewAccount();
                    break;
                case ABOUT_APP:
                    break;
                case EXIT:
                    exit();
                    break;
                    default:
                        printer.printLine("There is no such an option!");
                        break;
            }
        }while (loggingOption != LoggingOption.EXIT && systemUser == null);
        exportingUserDataBase(bettingSystem);
        exportingUserBetHistory(systemUser);

        return systemUser;
    }

    private SystemUser loginToAnAccount() {
        boolean loginOk = false;
        SystemUser abc = null;
        while (!loginOk){
            try {
                abc = bettingSystem.findUser(dataReader.login());
                loginOk = true;
                printer.printLine("Logged in successfully");
            }catch (WrongLoginPasswordException e){
                printer.printLine(e.getMessage());
            }
        }
        return abc;
    }

    private SystemUser registerNewAccount() {
        SystemUser systemUser = dataReader.register();
        try {
            bettingSystem.addUser(systemUser);
            printer.printLine("New user added successfully");
        } catch (UserAlreadyExistsException e){
            printer.printLine(e.getMessage());
        }

        return systemUser;
    }

    private void exit() {
        exportingUserDataBase(bettingSystem);
        dataReader.close();
        printer.printLine("Goodbye!");
    }

    private LoggingOption getOption() {
        boolean optionOk = false;
        LoggingOption loggingOption = null;
        while (!optionOk){
            try{
                loggingOption = LoggingOption.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e){
                printer.printLine(e.getMessage() + ", enter option number again: ");
            } catch (InputMismatchException err){
                printer.printLine(err.getMessage() + ", wrong value entered, please try again.");
            }
        }
        return loggingOption;
    }

    private void exportingUserDataBase(BettingSystem bettingSystem){
        try{
            serialization.exportBettingSystemData(bettingSystem);
            printer.printLine("Data exported successfully!");
        } catch (DataExportException e){
            printer.printLine(e.getMessage());
        }
    }

    private void exportingUserBetHistory(SystemUser systemUser){
        try{
            serialization.exportUserData(systemUser, systemUser.getLogin());
            printer.printLine("Data exported successfully!");
        } catch (DataExportException e){
            printer.printLine(e.getMessage());
        }
    }

    private void printOptions() {
        printer.printLine("Choose an option: ");
        for (LoggingOption option: LoggingOption.values()) {
            printer.printLine(option.toString());
        }
    }

    private enum LoggingOption {
        LOGGING_OPTION(1, "Login"),
        MAKE_AN_ACCOUNT(2, "New Account"),
        ABOUT_APP(3, "About app"),
        EXIT(4, "Exit");

        private int value;
        private String description;

        LoggingOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + ". " + description;
        }

        static LoggingOption createFromInt(int option) throws NoSuchOptionException {
            try {
                return LoggingOption.values()[option-1];
            } catch (IndexOutOfBoundsException e){
                throw new NoSuchOptionException("There is no such option!");
            }
        }
    }

}
