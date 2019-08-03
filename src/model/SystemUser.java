package model;

import exception.BetAlreadyExistsException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SystemUser extends User implements Serializable {
    private Set<Bet> bettingHistory = new HashSet<>();
    private BigDecimal budget;

    public SystemUser(String login, String password) {
        super(login, password);
    }

    public SystemUser(String login, String password, BigDecimal budget) {
        super(login, password);
        this.budget = budget;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void addBetToHistory(Bet bet){
        for (Bet value : bettingHistory) {
            if (value.equals(bet))
                throw new BetAlreadyExistsException("This bet is already added." + bet);

        }
        this.bettingHistory.add(bet);
    }

    public Set<Bet> getBettingHistory() {
        return bettingHistory;
    }


}
