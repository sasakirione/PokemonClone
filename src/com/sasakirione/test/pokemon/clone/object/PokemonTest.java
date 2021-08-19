package com.sasakirione.test.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;
import com.sasakirione.main.pokemon.clone.object.PokemonStadium;
import com.sasakirione.main.pokemon.clone.object.value.Effort;
import com.sasakirione.main.pokemon.clone.object.value.Type;
import org.junit.jupiter.api.*;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PokemonTest {
    Pokemon regieleki;
    Pokemon zapdos;
    Pokemon greninja;

    @BeforeEach
    public void before() {
        regieleki = new Pokemon(894);
        zapdos = new Pokemon(145);
        greninja = new Pokemon(658);
    }

    @Test
    @DisplayName("レジエレキでサンダーをなぐる、サンダープリズンで")
    @RepeatedTest(100)
    public void test005() {
        PokemonMove a = regieleki.getDamage("0002");
        zapdos.takeDamage(a);
        System.out.println(zapdos.getCurrentHP2().toString());
        Assertions.assertTrue(zapdos.getCurrentHP() < 73);
    }

    @Test
    @DisplayName("努力値クラスのテスト、252以上ふる")
    public void test006() {
        assertThrows(IllegalArgumentException.class, () -> {
            Effort effort = new Effort(new int[]{200, 300, 0, 0, 0, 0});
        });
    }

    @Test
    @DisplayName("努力値を合計510以上ふる")
    public void test007() {
        assertThrows(IllegalArgumentException.class, () -> {
            Effort effort = new Effort(new int[]{252, 252, 0, 0, 0, 252});
        });
    }

    @Test
    @DisplayName("努力値正常")
    public void test008() {
        Effort effort = new Effort(new int[]{252, 252, 0, 0, 0, 0});
    }

    @Test
    @DisplayName("タイプ相性を実装する、レジエレキでゲッコウガを")
    @RepeatedTest(100)
    public void test009() {
        PokemonMove a = regieleki.getDamage("0002");
        greninja.takeDamage(a);
        System.out.println(greninja.getCurrentHP2().toString());
        Assertions.assertTrue(greninja.getCurrentHP() < 12);
        Assertions.assertTrue(!(greninja.getCurrentHP() < 0));
    }

    @Test
    @DisplayName("タイプを設定")
    public void test010() {
        assertThrows(IllegalArgumentException.class, () -> {
            Type type = new Type("げんしりょく");
        });
        Type type = new Type("みず");
    }

    @Test
    @DisplayName("バトル場を使う")
    public void test011() {
        PokemonStadium stadiume = new PokemonStadium(regieleki, zapdos);
        PokemonMove a = regieleki.getDamage("サンダープリズン");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        System.out.println(stadiume.forwardTurn(a,b));
        System.out.println(regieleki.getCurrentHP2());
        System.out.println(zapdos.getCurrentHP2());
        Assertions.assertTrue(stadiume.forwardTurn(a,b) == "Bの負け");
    }

}
