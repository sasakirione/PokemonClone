package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.AbilityConst;
import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;

public class Ability {
    private final String abilityName;
    private boolean abilityBool = false;

    public Ability(String name) {
        this.abilityName = name;
    }

    public double powerBoost(PokemonMove move) {
        if (checkTypeBoost(move.getMoveType())) {
            return CalculationConst.ONE_POINT_FIVE;
        }
        if(isTorrent(move.getMoveType())) {
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

    public boolean isLibero() {
        return (abilityName.equals(AbilityConst.LIBERO) || abilityName.equals(AbilityConst.PROTEAN));
    }

    public boolean isTorrent(String moveType) {
        return (abilityName.equals(AbilityConst.TORRENT) && moveType.equals("みず")) || (abilityName.equals(AbilityConst.BLAZE) && moveType.equals("ほのお")) ||
                (abilityName.equals(AbilityConst.OVER_GROW) && moveType.equals("くさ")) || (abilityName.equals(AbilityConst.SWARM) && moveType.equals("むし"));
    }

    public boolean isTorrent() {
        return (abilityName.equals(AbilityConst.TORRENT)) || (abilityName.equals(AbilityConst.BLAZE)) ||
                (abilityName.equals(AbilityConst.OVER_GROW)) || (abilityName.equals(AbilityConst.SWARM));
    }

    public void torrent() {
        this.abilityBool = true;
    }

    public String getName() {
        return this.abilityName;
    }
}
