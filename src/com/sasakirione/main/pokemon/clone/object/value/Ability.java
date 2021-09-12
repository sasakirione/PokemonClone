package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.AbilityConst;
import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;

public class Ability {
    private final String abilityName;

    public Ability(String name) {
        this.abilityName = name;
    }

    public double powerBoost(PokemonMove move) {
        if (checkTypeBoost(move.getMoveType())) {
            return CalculationConst.ONE_POINT_FIVE;
        }
        return CalculationConst.ONE;
    }

    private boolean checkTypeBoost(String type) {
        return abilityName.equals(AbilityConst.TRANSISTOR) && type.equals("でんき");
    }

    public boolean isPsychoMaker() {
        return abilityName.equals(AbilityConst.PSYCHO_MAKER);
    }
}
