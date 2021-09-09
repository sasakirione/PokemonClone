package com.sasakirione.main.pokemon.clone.object.value;

public class Good {
    private final String goodName;

    public Good(String goodName) {
        this.goodName = goodName;
    }

    public boolean isChoice() {
        return (this.goodName.equals("こだわりメガネ") || this.goodName.equals("こだわりハチマキ") || this.goodName.equals("こだわりスカーフ"));
    }

    public boolean isSpeedBoost() {
        return (this.goodName.equals("こだわりスカーフ") || this.goodName.equals("こだわってないスカーフ"));
    }

}
