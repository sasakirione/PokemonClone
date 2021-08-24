package com.sasakirione.main.pokemon.clone.object.value;

public class Real {
    private int[] real;
    private int[] rank;
    private final int[] realSource;
    private String good;

    public Real(int[] real, String good) {
        this.real = real;
        this.realSource = real;
        this.rank = new int[] {0, 0, 0, 0, 0, 0};
        this.good = good;

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

    private void rankUp(int item, int i) {
        int rank = this.rank[item] + i;
        if (rank > 6) {
            rank = 6;
        }
        this.rank[item] = rank;
        rankCalculation(item);
        goodCalculation(item);
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

}
