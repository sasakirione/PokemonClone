package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.utility.CalculationUtility;

import java.util.Random;

public class Status {
    private HP currentHP;
    private int[] real;
    private int[] rank;
    private final int[] realSource;
    private int[] base;
    private final Effort effort;
    private final Nature nature;
    private boolean parCheck = false;
    private boolean brnCheck = false;

    public Status(int[] base, Effort effort, Nature nature) {
        this.rank = new int[] {0, 0, 0, 0, 0, 0};
        this.base = base;
        this.effort = effort;
        this.nature = nature;
        pokemonRealSet();
        this.realSource = real.clone();
    }

    public int getHP() {
        return real[0];
    }

    public int getA() {
        if (brnCheck) {
            return (int) Math.floor(real[1] * 0.5);
        }
        return real[1];
    }

    public int getB() {
        return real[2];
    }

    public int getC() {
        return real[3];
    }

    public int getD() {
        return real[4];
    }

    public int getS() {
        double realSpeed = this.real[5];
        if (this.isParCheck()) {
            realSpeed = realSpeed * CalculationConst.HALF;
        }
        return (int) Math.round(realSpeed);
    }

    public int[] getRank() {
        return this.rank.clone();
    }

    public void rankUpA(int i) {
        rankUp(1,i);
    }

    public void rankUpB(int i) {
        rankUp(2,i);
    }

    public void rankUpC(int i) {
        rankUp(3,i);
    }

    public void rankUpD(int i) {
        rankUp(4,i);
    }

    public void rankUpS(int i) {
        rankUp(5,i);
    }

    public void rankUp(int item, int i) {
        int rank = this.rank[item] + i;
        if (rank > 6) {
            rank = 6;
        }
        this.rank[item] = rank;
        rankCalculation(item);
    }

    private void rankCalculation(int i) {
        int rank = this.rank[i];
        double magnification = 1.0;
        if (rank < 0) {
            magnification = 2.0/((rank*(-1)) + 2.0);
        }
        if (0 < rank) {
            magnification = (rank + 2.0) / 2.0;
        }
        int realSourceValue = this.realSource[i];
        this.real[i] = (int) Math.round(realSourceValue * magnification);
    }

    private int realCalculationHP(int base, int effort) {
        return (int) Math.floor((((base * 2.0) + 31 + Math.floor(effort / 4.0)) * (50.0 / 100.0) + 50 + 10));
    }

    private int realCalculationEtc(int base,int effort){
        return (int) Math.floor((((base * 2.0) + 31 + Math.floor(effort / 4.0)) * (50.0 / 100.0) + 5));
    }

    private void pokemonRealSet() {
        this.real = effort.realCalculation(this.base);
        pokemonNatureCalculation();
        this.currentHP = new HP(this.real[0]);
    }

    private void pokemonNatureCalculation() {
        if (!nature.isMajime()) {
            int plus = this.nature.plusNature();
            int minus = this.nature.minusNature();
            real[minus] = (int) Math.floor(real[minus] * 0.9);
            real[plus] = (int) Math.floor(real[plus] * 1.1);
        }
    }

    public void damageCalculation(double power, int defense, double magnification, int vitalRank) {
        int realDefense = real[defense];
        double vitals = 1.0;
        if (isVitals(vitalRank)) {
            vitals = 1.5;
            realDefense = realSource[defense];
            BattleLog.vitals();
        }
        double a = Math.floor(power / realDefense);
        double c = Math.floor((a / 50.0) + 2);
        double d = CalculationUtility.fiveOutOverFiveIn(c * vitals);
        int finalDamage = (int) Math.floor(d * randomNumber() * magnification);
        this.currentHP.pruneHP(finalDamage);
    }


    private double randomNumber() {
        Random random = new Random();
        int randomNumberRaw = random.nextInt(16);
        return 0.85 + (0.01 * randomNumberRaw);
    }

    private boolean isVitals(int vitalRank) {
        int vital = switch (vitalRank) {
          case 1 -> 8;
          case 2 -> 2;
          case 3,4,5,6 -> 1;
          default -> 24;
        };
        Random random = new Random();
        int randomNumber = random.nextInt(vital);
        return randomNumber == 0;
    }

    public void constantDamage(int i) {
        this.currentHP.pruneHP(i);
    }

    public void getPAR() {
        parCheck = true;
    }

    public boolean isParCheck() {
        return parCheck;
    }

    public int getCurrentHP() {
        return currentHP.getCurrentHP();
    }

    public String getCurrentHP2() {
        return currentHP.toString();
    }

    public boolean isDead() {
        return currentHP.getCurrentHP() == 0;
    }

    public boolean isOneThird() {
        return this.currentHP.isOneThird();
    }

    public void rankReset() {
        this.rank = new int[] {0, 0, 0, 0, 0, 0};
        pokemonRealSet();
    }

    public void rankReset(Integer i) {
        this.rank[i] = 0;
    }

    public void damageOneEighth() {
        this.currentHP.damageOneEighth();
        BattleLog.hp(this);
    }

    public void recoveryOnePointSixteen() {
        this.currentHP.recoveryOnePointSixteen();
        BattleLog.hp(this);
    }

    public double getA2() {
        return this.realSource[1];
    }

    public double getC2() {
        return this.realSource[3];
    }

    public void getBAR() {
        brnCheck = true;
    }
}
