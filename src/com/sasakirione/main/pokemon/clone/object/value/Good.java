package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
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

    public double powerBoost(MoveClass moveClass) {
        if ((this.goodName.equals(GoodConst.CHOICE_BAND) && moveClass.equals(MoveClass.PHYSICS)) ||
                (this.goodName.equals(GoodConst.CHOICE_SPECS) && moveClass.equals(MoveClass.SPECIAL))) {
            return CalculationConst.ONE_POINT_FIVE;
        }
        if (this.goodName.equals(GoodConst.LIFE_ORB) && (moveClass.equals(MoveClass.SPECIAL) || moveClass.equals(MoveClass.PHYSICS))) {
            return CalculationConst.ONE_POINT_THREE_ORB;
        }
        return CalculationConst.ONE;
    }

    public boolean isDamageOneEighth() {
        return this.goodName.equals(GoodConst.LIFE_ORB);
    }
}
