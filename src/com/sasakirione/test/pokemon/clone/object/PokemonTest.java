package com.sasakirione.test.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
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
    Pokemon regieleki_megane;
    Pokemon zapdos;
    Pokemon greninja;
    Pokemon polteageist;

    int[] cs = new int[]{0, 0, 0, 252, 0, 252};

    @BeforeEach
    public void before() {
        regieleki = new Pokemon("レジエレキ", this.cs, "なし" , "おくびょう");
        regieleki_megane = new Pokemon("レジエレキ", this.cs, "こだわりメガネ" , "おくびょう");
        zapdos = new Pokemon("サンダー", this.cs, "なし" , "おくびょう");
        greninja = new Pokemon("ゲッコウガ", new int[]{252, 0, 0, 0, 252 , 0}, "なし" , "おくびょう");
        polteageist = new Pokemon("ポットデス", this.cs, "こだわってないスカーフ", "おくびょう");
    }

    @DisplayName("レジエレキでサンダーをなぐる、サンダープリズンで")
    @RepeatedTest(100)
    public void test005() {
        PokemonMove a = regieleki.getDamage("サンダープリズン");
        zapdos.takeDamage(a);
        System.out.println(zapdos.getCurrentHP2());
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

    @DisplayName("タイプ相性を実装する、レジエレキでゲッコウガを")
    @RepeatedTest(100)
    public void test009() {
        PokemonMove a = regieleki.getDamage("サンダープリズン");
        greninja.takeDamage(a);
        System.out.println(greninja.getCurrentHP2());
        Assertions.assertTrue(greninja.getCurrentHP() < 12);
        Assertions.assertFalse(greninja.getCurrentHP() < 0);
    }

    @Test
    @DisplayName("タイプを設定")
    public void test010() {
        assertThrows(IllegalArgumentException.class, () -> {
            Type type = new Type("げんしりょく");
        });
        Type type = new Type("みず");
    }

    @DisplayName("バトル場を使う")
    @RepeatedTest(100)
    public void test011() {
        PokemonStadium stadium = new PokemonStadium(regieleki, zapdos);
        PokemonMove a = regieleki.getDamage("サンダープリズン");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        System.out.println(stadium.forwardTurn(a,b));
        System.out.println(regieleki.getCurrentHP2());
        System.out.println(zapdos.getCurrentHP2());
        stadium.forwardTurn(a, b);
        Assertions.assertEquals("おわりだよ", stadium.forwardTurn(a, b));
    }

    @Test
    @DisplayName("メッセージ機能")
    public void test012() {
        PokemonStadium stadium = new PokemonStadium(regieleki, greninja);
        PokemonMove a = regieleki.getDamage("サンダープリズン");
        PokemonMove b = greninja.getDamage("ハイドロポンプ");
        System.out.println(stadium.forwardTurn(a,b));
        BattleLog.getLogAll();
        Assertions.assertEquals("こうかばつぐんだ！", BattleLog.getLog(3));
    }

    @DisplayName("こだわりメガネを実装_ダメージ量を増やす")
    @RepeatedTest(100)
    public void test013() {
        PokemonStadium stadium = new PokemonStadium(regieleki, zapdos);
        PokemonMove a = regieleki_megane.getDamage("サンダープリズン");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
        Assertions.assertTrue(zapdos.getCurrentHP() < 25);
    }

    @Test
    @DisplayName("こだわりメガネを実装_こだわり")
    public void test014() {
        PokemonMove a1 = regieleki_megane.getDamage("サンダープリズン");
        assertThrows(IllegalArgumentException.class, () -> {
            PokemonMove a2 = regieleki_megane.getDamage("10まんボルト");
        });
    }

    @DisplayName("タイプ不一致技を設定")
    @RepeatedTest(100)
    public void test015() {
        PokemonStadium stadium = new PokemonStadium(regieleki, zapdos);
        PokemonMove a = regieleki.getDamage("げんしのちから");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
        Assertions.assertTrue(68 < zapdos.getCurrentHP());
    }

    @Test
    @DisplayName("デモ用")
    public void test016() {
        PokemonStadium stadium = new PokemonStadium(regieleki, zapdos);
        PokemonMove a = regieleki_megane.getDamage("サンダープリズン");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a, b);
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
    }

    @Test
    @DisplayName("七尾百合子を使う")
    public void test017() {
        Pokemon nanao = new Pokemon("七尾百合子", this.cs, new int[]{60,50,70,130,90,95}, "こだわりメガネ" , "おくびょう",
                "みず", "フェアリー", "うるおいボディ");
        PokemonStadium stadium = new PokemonStadium(nanao, regieleki);
        PokemonMove a = nanao.getDamage("ハイドロポンプ");
        PokemonMove b = regieleki.getDamage("サンダープリズン");
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
        Assertions.assertEquals("Aは 七尾百合子 をくりだした！", BattleLog.getLog(0));
    }

    @Test
    @DisplayName("ポットデス　からをやぶる")
    public void test018() {
        PokemonStadium stadium = new PokemonStadium(polteageist, regieleki);
        PokemonMove a = polteageist.getDamage("からをやぶる");
        PokemonMove b = regieleki.getDamage("サンダープリズン");
        PokemonMove a2 = polteageist.getDamage("シャドーボール");
        stadium.forwardTurn(a, b);
        stadium.forwardTurn(a2, b);
        BattleLog.getLogAll();
        Assertions.assertEquals("ポットデス の からをやぶる のこうげきだ！", BattleLog.getLog(4));
        Assertions.assertEquals("ポットデス の シャドーボール のこうげきだ！", BattleLog.getLog(5));
    }
}
