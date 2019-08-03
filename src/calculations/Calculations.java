package calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculations {

    public static BigDecimal kellysAlgorithm(BigDecimal budget, BigDecimal probability, BigDecimal odd){
        BigDecimal tmp1 = odd.subtract(BigDecimal.valueOf(1));
        BigDecimal tmp2 = probability.multiply(tmp1);
        return budget.multiply(tmp1).divide(tmp2, RoundingMode.HALF_UP);
    }
}
