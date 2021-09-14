package com.sasakirione.main.pokemon.clone.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtility {
    public static int fiveOutOverFiveIn(double i) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(i));
        BigDecimal resBD = bigDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return (int) resBD.doubleValue();
    }
}
