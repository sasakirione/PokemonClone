package com.sasakirione.main.pokemon.clone.object.value;

import java.util.Arrays;

public class Effort {
    int h;
    int a;
    int b;
    int c;
    int d;
    int s;

    public Effort(int[] effort) {
        Arrays.stream(effort).filter(i -> i > 252).forEach(i -> {throw new IllegalArgumentException("努力値が不正です");});
        int sum = Arrays.stream(effort).sum();
        if (sum > 510) {
            throw new IllegalArgumentException("努力値の合計が510を超えています");
        }
        this.h = effort[0];
        this.a = effort[1];
        this.b = effort[2];
        this.c = effort[3];
        this.d = effort[4];
        this.s = effort[5];
    }

    public int getH() {
        return h;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public int getS() {
        return s;
    }
}
