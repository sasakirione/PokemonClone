package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.GoodConst;

public class Good {
    private final String goodName;

    public Good(String goodName) {
        this.goodName = goodName;
    }

    public boolean isChoice() {
        return (this.goodName.equals(GoodConst.CHOICE_SPECS) || this.goodName.equals(GoodConst.CHOICE_BAND) || this.goodName.equals(GoodConst.CHOICE_SCARF));
    }

    public boolean isSpeedBoost() {
        return (this.goodName.equals(GoodConst.CHOICE_SCARF) || this.goodName.equals(GoodConst.NOT_CHOICE_SCARF));
    }

}
