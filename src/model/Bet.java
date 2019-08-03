package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Bet implements Serializable {
    private String sportType;
    private String leagueName;
    private String matchName;
    private LocalDate matchDate;
    private String betName;
    private BigDecimal odd;
    private BigDecimal probability;

    public Bet(String sportType, String leagueName, String matchName, LocalDate matchDate, String betName, BigDecimal odd, BigDecimal probability) {
        this.sportType = sportType;
        this.leagueName = leagueName;
        this.matchName = matchName;
        this.matchDate = matchDate;
        this.betName = betName;
        this.odd = odd;
        this.probability = probability;
    }

    public BigDecimal getOdd() {
        return odd;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(sportType, bet.sportType) &&
                Objects.equals(leagueName, bet.leagueName) &&
                Objects.equals(matchName, bet.matchName) &&
                Objects.equals(matchDate, bet.matchDate) &&
                Objects.equals(betName, bet.betName) &&
                Objects.equals(odd, bet.odd) &&
                Objects.equals(probability, bet.probability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sportType, leagueName, matchName, matchDate, betName, odd, probability);
    }

    @Override
    public String toString() {
        return "Bet " + sportType + " " +
                ", League: " + leagueName + " " +
                ", Match: " + matchName + " " +
                ", Date: " + matchDate + " " +
                ", Bet: " + betName + " " +
                ", Odd: " + odd + " " +
                ", Probability: " + probability.multiply(BigDecimal.valueOf(100)) + "%";
    }
}
