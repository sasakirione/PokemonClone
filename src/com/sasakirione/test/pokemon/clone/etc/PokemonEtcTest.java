package com.sasakirione.test.pokemon.clone.etc;

import com.sasakirione.main.pokemon.clone.data.PokemonDataGetInterface;
import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.utility.CalculationUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PokemonEtcTest {
    @Test
    @DisplayName("汎用計算クラス五捨五超入のテスト")
    public void test025() {
        double a = 3.5;
        double b = 3.500001;
        Assertions.assertEquals(3,CalculationUtility.fiveOutOverFiveIn(a));
        Assertions.assertEquals(4,CalculationUtility.fiveOutOverFiveIn(b));
    }

    @Test
    @DisplayName("propertiesファイルの使用")
    public void test002() {
        PokemonDataGetInterface pokemonDataGet = new PokemonDataGetTest();
        Pokemon test = pokemonDataGet.getObjectByID(100, 0, new int[] {252, 252, 0, 0, 0, 0}, 1, "なし", "まじめ");
        System.out.println(test.getName());
    }
}
