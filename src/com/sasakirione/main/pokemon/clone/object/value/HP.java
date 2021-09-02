package com.sasakirione.main.pokemon.clone.object.value;

public class HP {
    private final int maxHP;
    private int currentHP;

    public HP (int hp) {
        this.maxHP = hp;
        this.currentHP = hp;
    }

    public void pruneHP(int damage) {
        int hp = currentHP - damage;
        this.currentHP = Math.max(hp, 0);
    }

    public boolean isDeath() {
        return this.currentHP == 0;
    }

    public String toString() {
        int half = maxHP / 2;
        int quarter = maxHP / 4;
        String now = "緑";
        if (currentHP <= half) {
            now = "黄色";
            if (currentHP <= quarter) {
                now = "赤色";
            }
        }
        return this.currentHP+"/"+this.maxHP + " " + now;
    }

    public int getCurrentHP() {
        return currentHP;
    }
}
