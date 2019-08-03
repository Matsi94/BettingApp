package app;

import calculations.Calculations;
import exception.*;
import io.ConsolePrinter;
import io.DataReader;
import io.file.FileManager;
import io.file.SerializableFileManager;
import model.Bet;
import model.SystemUser;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Set;

class AppControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager serialization = new SerializableFileManager();
    private SystemUser appUser;


    void appLoop(SystemUser systemUser){
        Options options;
        appUser = importUser(systemUser.getLogin());

        do {
            printOptions();
            options = getOption();
            switch (options){
                case ADD_BET:
                    addBet(appUser);
                    break;
                case CHECK_HISTORY:
                    checkHistory(appUser);
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("There is no such an option!");
                    break;
            }
        }while (options != Options.EXIT);
    }

    private void checkHistory(SystemUser appUser) {
        Set<Bet> bettingHistory = appUser.getBettingHistory();
        for (Bet bet : bettingHistory) {
            printer.printLine(bet.toString());
        }
    }

    private void addBet(SystemUser user) {
        try{
            Bet bet = dataReader.addBet();
            BigDecimal betValue = Calculations.kellysAlgorithm(user.getBudget(), bet.getProbability(), bet.getOdd());
            printer.printLine("Calculated bet value: " + betValue);
            user.addBetToHistory(bet);
        } catch (BetAlreadyExistsException e){
            printer.printLine(e.getMessage());
        }
    }

    private void printOptions() {
        printer.printLine("Choose options:");
        for (Options options : Options.values()) {
            printer.printLine(options.toString());
        }
    }

    private Options getOption() {
        boolean optionOk = false;
        Options options = null;
        while (!optionOk){
            try{
                options = Options.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e){
                printer.printLine(e.getMessage() + ", enter option number again: ");
            } catch (InputMismatchException err){
                printer.printLine(err.getMessage() + ", wrong value entered, please try again.");
            }
        }
        return options;
    }

    private SystemUser importUser(String login){
        SystemUser user = null;
        try {
            user = serialization.importUserData(login);
            printer.printLine("User database imported");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
        }
        return user;
    }


    private void exit() {
        try{
            serialization.exportUserData(appUser, appUser.getLogin());
            printer.printLine("Data exported successfully!");
        } catch (DataExportException e){
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("Goodbye!");
    }

    private enum Options {
        ADD_BET(1, "Add bet"),
        CHECK_HISTORY(2, "Check your betting history"),
        EXIT(3, "Exit");

        private int value;
        private String description;

        Options(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + ". " + description;
        }

        static Options createFromInt(int option) throws NoSuchOptionException {
            try {
                return Options.values()[option-1];
            } catch (IndexOutOfBoundsException e){
                throw new NoSuchOptionException("There is no such option!");
            }
        }
    }

}
