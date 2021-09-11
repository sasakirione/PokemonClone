package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.GoodConst;

public class Good {
    private final String goodName;

    public Good(String goodName) {
        this.goodName = goodName;
    }

    public boolean isChoice() {
        return (this.goodName.equals(GoodConst.choiceSpecs) || this.goodName.equals(GoodConst.choiceBand) || this.goodName.equals(GoodConst.choiceScarf));
    }

    public boolean isSpeedBoost() {
        return (this.goodName.equals(GoodConst.choiceScarf) || this.goodName.equals(GoodConst.notChoiceScarf));
    }

}
