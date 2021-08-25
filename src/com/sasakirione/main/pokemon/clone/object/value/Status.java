package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.loggin.BattleLog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Status {
    private HP currentHP;
    private int[] real;
    private int[] rank;
    private final int[] realSource;
    private int[] base;
    private final Effort effort;
    private String good;
    private Nature nature;
    private boolean parCheck = false;
    private boolean brnCheck = false;

    public Status(int[] base, Effort effort, String good, Nature nature) {
        this.rank = new int[] {0, 0, 0, 0, 0, 0};
        this.good = good;
        this.base = base;
        this.effort = effort;
        this.nature = nature;
        pokemonRealSet();
        this.realSource = real;
        setGood();
    }

    private void setGood() {
        if (this.good.equals("こだわりスカーフ")) {
            this.real[5] = (int) Math.round(real[5] * 1.5);
        }
        if (this.good.equals("こだわりメガネ")) {
            this.real[3] = (int) Math.round(real[3] * 1.5);
        }
        if (this.good.equals("こだわりハチマキ")) {
            this.real[1] = (int) Math.round(real[1] * 1.5);
        }
        if (this.good.equals("こだわらないスカーフ")) {
            this.real[5] = (int) Math.round(real[5] * 1.5);
        }
    }

    public int getHP() {
        return real[0];
    }

    public int getA() {
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
        return real[5];
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
        goodCalculation(item);
        ailmentCalculation(item);
    }

    private void ailmentCalculation(int item) {
        if (item == 5 && parCheck) {
            real[5] = (int) Math.round(this.real[5] * 0.5);
        }
        if (item == 1 && brnCheck) {
            real[1] = (int) Math.round(this.real[1] * 0.5);
        }
    }

    private void goodCalculation(int i) {
        if (this.good.equals("こだわりスカーフ") && i == 5) {
            this.real[5] = (int) Math.round(real[5] * 1.5);
        }
        if (this.good.equals("こだわりメガネ") && i == 3) {
            this.real[3] = (int) Math.round(real[3] * 1.5);
        }
        if (this.good.equals("こだわりハチマキ") && i==1) {
            this.real[1] = (int) Math.round(real[1] * 1.5);
        }
        if (this.good.equals("こだわらないスカーフ") && i == 5) {
            this.real[5] = (int) Math.round(real[5] * 1.5);
        }
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
        this.real[i] = (int) Math.round(this.realSource[i] * magnification);
    }

    private int realCalculationHP(int base, int effort) {
        return (int) Math.floor((((base * 2.0) + 31 + Math.floor(effort / 4.0)) * (50.0 / 100.0) + 50 + 10));
    }

    private int realCalculationEtc(int base,int effort){
        return (int) Math.floor((((base * 2.0) + 31 + Math.floor(effort / 4.0)) * (50.0 / 100.0) + 5));
    }

    private void pokemonRealSet() {
        int hp = realCalculationHP(this.base[0],this.effort.getH());
        int a = realCalculationEtc(this.base[1],this.effort.getA());
        int b = realCalculationEtc(this.base[2],this.effort.getB());
        int c = realCalculationEtc(this.base[3],this.effort.getC());
        int d = realCalculationEtc(this.base[4],this.effort.getD());
        int s = realCalculationEtc(this.base[5],this.effort.getS());

        int[] tempReal = new int[] {hp, a, b, c, d, s};
        this.real = tempReal;
        pokemonNatureCalculation(tempReal);
        this.currentHP = new HP(hp);
    }

    private void pokemonNatureCalculation(int[] tempReal) {
        if (!nature.isMajime()) {
            int plus = this.nature.plusNature();
            int minus = this.nature.minusNature();
            real[minus] = (int) Math.floor(real[minus] * 0.9);
            real[plus] = (int) Math.floor(real[plus] * 1.1);
        }
    }

    public void damageCalculation(double realAttack, int defense, int damage, double magnification, String type) {
        int realDefense = real[defense];
        double vitals = 1.0;
        if (isVitals()) {
            vitals = 1.5;
            realDefense = realSource[defense];
            BattleLog.vitals();
        }
        double a = Math.floor(50 * 0.4 + 2);
        double b = Math.floor(a * damage * realAttack / realDefense);
        double c = Math.floor((b / 50.0) + 2);
        double d = fiveOutOverFiveIn(c * vitals);
        int finalDamage = (int) Math.floor(d * randomNumber() * magnification);
        this.currentHP.pruneHP(finalDamage);
    }

    private double randomNumber() {
        Random random = new Random();
        int randomNumberRaw = random.nextInt(16);
        return 0.85 + (0.01 * randomNumberRaw);
    }

    private boolean isVitals() {
        Random random = new Random();
        int randomNumber = random.nextInt(16);
        return randomNumber == 0;
    }

    private int fiveOutOverFiveIn(double i) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(i));
        BigDecimal resBD = bigDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return (int) resBD.doubleValue();
    }

    public void constantDamage(int i) {
        this.currentHP.pruneHP(i);
    }

    public void getPAR() {
        parCheck = true;
        real[5] = (int) Math.round(this.real[5] * 0.5);
    }

    public int getCurrentHP() {
        return currentHP.getCurrentHP();
    }

    public String getCurrentHP2() {
        return currentHP.toString();
    }
}
