package io;

import model.Bet;
import model.SystemUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public int getInt() {
        int number = sc.nextInt();
        sc.nextLine();
        return number;
    }

    public void close() {
        sc.close();
    }

    public SystemUser register() {
        printer.printLine("Login:");
        String login = sc.nextLine();
        printer.printLine("Password:");
        String password = sc.nextLine();
        printer.printLine("Budget");
        BigDecimal budget = sc.nextBigDecimal();
        sc.nextLine();

        return new SystemUser(login,password, budget);
    }

    public SystemUser login() {
        printer.printLine("Login:");
        String login = sc.nextLine();
        printer.printLine("Password:");
        String password = sc.nextLine();

        return new SystemUser(login,password);
    }

    public Bet addBet() {
        printer.printLine("Enter bet information");
        printer.printLine("Sport type:");
        String sportType = sc.nextLine();
        printer.printLine("League name:");
        String leagueName = sc.nextLine();
        printer.printLine("Match name (ex. Liverpool - Chelsea:");
        String matchName = sc.nextLine();
        printer.printLine("Match date (ex. 24-04-2018:");
        String date = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate matchDate = LocalDate.parse(date, formatter);
        printer.printLine("Bet name (ex. Chelsea WIN");
        String betName = sc.nextLine();
        printer.printLine("Bet Odd: ");
        BigDecimal betOdd = sc.nextBigDecimal();
        sc.nextLine();
        printer.printLine("Probability of bet: ");
        BigDecimal probability = sc.nextBigDecimal();
        sc.nextLine();

        return new Bet(sportType, leagueName, matchName, matchDate, betName, betOdd, probability);
    }
}
