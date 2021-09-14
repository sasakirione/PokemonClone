package com.sasakirione.test.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.data.PokemonDataGet;
import com.sasakirione.main.pokemon.clone.data.PokemonDataGetInterface;
import com.sasakirione.main.pokemon.clone.exception.EvArgumentException;
import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;
import com.sasakirione.main.pokemon.clone.object.PokemonStadium;
import com.sasakirione.main.pokemon.clone.object.value.Effort;
import com.sasakirione.main.pokemon.clone.object.value.Type;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PokemonTest {
    Pokemon regieleki;
    Pokemon regieleki_megane;
    Pokemon zapdos;
    Pokemon greninja;
    Pokemon polteageist;
    Pokemon decidueye;
    Pokemon tapuLele;
    Pokemon primarina;
    PokemonDataGetInterface pokemonDataGet;

    int[] cs = new int[]{0, 0, 0, 252, 0, 252};
    int[] as = new int[]{0, 252, 0, 0, 0, 252};

    @BeforeEach
    public void before() {
        regieleki = new Pokemon("レジエレキ", this.cs, "なし" , "おくびょう", "トランジスタ");
        regieleki_megane = new Pokemon("レジエレキ", this.cs, "こだわりメガネ" , "おくびょう", "トランジスタ");
        polteageist = new Pokemon("ポットデス", this.cs, "こだわってないスカーフ", "おくびょう", "のろわれボディ");
        pokemonDataGet = new PokemonDataGet();
        zapdos = pokemonDataGet.getObjectByID(145, this.cs, 1, "なし", "おくびょう");
        greninja = pokemonDataGet.getObjectByID(658, new int[]{252, 0, 0, 0, 252 , 0}, 3, "なし", "おくびょう");
        decidueye = pokemonDataGet.getObjectByID(724, this.as, 1, "なし", "ようき");
        tapuLele = pokemonDataGet.getObjectByID(786, this.cs, 1, "なし", "おくびょう");
        primarina = pokemonDataGet.getObjectByID(730, this.cs, 1, "なし", "ひかえめ");
    }

    @DisplayName("レジエレキでサンダーをなぐる、サンダープリズンで")
    @RepeatedTest(100)
    public void test005() {
        PokemonMove a = regieleki.getDamage2("サンダープリズン");
        zapdos.takeDamage(a, true);
        Assertions.assertTrue(zapdos.getCurrentHP() < 73);
    }

    @Test
    @DisplayName("努力値クラスのテスト、252以上ふる")
    public void test006() {
        assertThrows(EvArgumentException.class, () -> {
            Effort effort = new Effort(new int[]{200, 300, 0, 0, 0, 0});
        });
    }

    @Test
    @DisplayName("努力値を合計510以上ふる")
    public void test007() {
        assertThrows(EvArgumentException.class, () -> {
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
        PokemonMove a = regieleki.getDamage2("サンダープリズン");
        greninja.takeDamage(a, true);
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
        stadium.setTestMode(true);
        PokemonMove a = regieleki.getDamage2("サンダープリズン");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a,b);
        stadium.forwardTurn(a, b);
        stadium.forwardTurn(a, b);
        Assertions.assertTrue(stadium.getEndFlag());
    }

    @Test
    @DisplayName("メッセージ機能")
    public void test012() {
        PokemonStadium stadium = new PokemonStadium(regieleki, greninja);
        stadium.setTestMode(true);
        PokemonMove a = regieleki.getDamage2("サンダープリズン");
        PokemonMove b = greninja.getDamage("ハイドロポンプ");
        stadium.forwardTurn(a,b);
        BattleLog.getLogAll();
        Assertions.assertEquals("こうかばつぐんだ！", BattleLog.getLog(3));
    }

    @DisplayName("こだわりメガネを実装_ダメージ量を増やす")
    @RepeatedTest(100)
    public void test013() {
        PokemonStadium stadium = new PokemonStadium(regieleki, zapdos);
        stadium.setTestMode(true);
        PokemonMove a = regieleki_megane.getDamage2("サンダープリズン");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
        Assertions.assertTrue(zapdos.getCurrentHP() < 25);
    }

    @Test
    @DisplayName("こだわりメガネを実装_こだわり")
    public void test014() {
        PokemonMove a1 = regieleki_megane.getDamage2("サンダープリズン");
        assertThrows(IllegalArgumentException.class, () -> {
            PokemonMove a2 = regieleki_megane.getDamage("10まんボルト");
        });
    }

    @DisplayName("タイプ不一致技を設定")
    @RepeatedTest(100)
    public void test015() {
        PokemonStadium stadium = new PokemonStadium(regieleki, zapdos);
        stadium.setTestMode(true);
        PokemonMove a = regieleki.getDamage("げんしのちから");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
        Assertions.assertTrue((68 < zapdos.getCurrentHP()) || BattleLog.getLog(3).equals("きゅうしょにあたった！"));
    }

    @Test
    @DisplayName("デモ用")
    public void test016() {
        PokemonStadium stadium = new PokemonStadium(regieleki, zapdos);
        stadium.setTestMode(true);
        PokemonMove a = regieleki_megane.getDamage2("サンダープリズン");
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
        stadium.setTestMode(true);
        PokemonMove a = nanao.getDamage("ハイドロポンプ");
        PokemonMove b = regieleki.getDamage2("サンダープリズン");
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
        Assertions.assertEquals("Aは 七尾百合子 を繰り出した！", BattleLog.getLog(0));
    }

    @Test
    @DisplayName("ポットデス　からをやぶる")
    public void test018() {
        PokemonStadium stadium = new PokemonStadium(polteageist, regieleki);
        stadium.setTestMode(true);
        PokemonMove a = polteageist.getDamage2("からをやぶる");
        PokemonMove b = regieleki.getDamage2("サンダープリズン");
        PokemonMove a2 = polteageist.getDamage("シャドーボール");
        stadium.forwardTurn(a, b);
        stadium.forwardTurn(a2, b);
        BattleLog.getLogAll();
        Assertions.assertEquals("ポットデス の からをやぶる のこうげきだ！", BattleLog.getLog(4));
        Assertions.assertEquals("ポットデス の シャドーボール のこうげきだ！", BattleLog.getLog(8));
    }

    @Test
    @DisplayName("状態異常を実装する、マヒ、素早さだけ")
    public void test019() {
        PokemonStadium stadium = new PokemonStadium(zapdos, greninja);
        stadium.setTestMode(true);
        PokemonMove a = zapdos.getDamage2("でんじは");
        PokemonMove b = greninja.getDamage("ハイドロポンプ");
        stadium.forwardTurn(a, b);
        PokemonMove a2 = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a2, b);
        BattleLog.getLogAll();
        Assertions.assertEquals("サンダー の ぼうふう のこうげきだ！", BattleLog.getLog(7));
    }

    @Test
    @DisplayName("先制技を実装")
    public void test020() {
        PokemonStadium stadium = new PokemonStadium(decidueye, zapdos);
        stadium.setTestMode(true);
        PokemonMove a = decidueye.getDamage("かげうち");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        stadium.forwardTurn(a, b);
        BattleLog.getLogAll();
        Assertions.assertEquals("ジュナイパー の かげうち のこうげきだ！", BattleLog.getLog(2));
    }

    @Test
    @DisplayName("テテフちゃんと特性とフィールドを実装")
    public void test021() {
        PokemonStadium stadium = new PokemonStadium(decidueye, tapuLele);
        stadium.setTestMode(true);
        PokemonMove a = decidueye.getDamage("かげうち");
        PokemonMove b = tapuLele.getDamage("サイコキネシス");
        PokemonMove c = tapuLele.getDamage2("めいそう");
        stadium.forwardTurn(a,b);
        BattleLog.getLogAll();
        Assertions.assertEquals("カプ・テテフは サイコフィールドに 守られている！", BattleLog.getLog(5));
    }

    @Test
    @DisplayName("フィールドおわり")
    public void test022() {
        PokemonStadium stadium = new PokemonStadium(decidueye, tapuLele);
        stadium.setTestMode(true);
        PokemonMove a = decidueye.getDamage("かげうち");
        PokemonMove c = tapuLele.getDamage2("めいそう");
        stadium.forwardTurn(a,c);
        stadium.forwardTurn(a,c);
        stadium.forwardTurn(a,c);
        stadium.forwardTurn(a,c);
        stadium.forwardTurn(a,c);
        PokemonMove b = tapuLele.getDamage("サイコキネシス");
        stadium.forwardTurn(a,b);
        BattleLog.getLogAll();
    }

    @Test
    @DisplayName("ポケモンのデータを取得してポケモンインスタンスを作る")
    public void test024() {
        Pokemon katana;
        katana = pokemonDataGet.getObjectByID(798, as, 1, "こだわりハチマキ", "いじっぱり");
        Assertions.assertEquals("カミツルギ", katana.getName());
    }

    @Test
    @DisplayName("技データを取得して技を出す")
    public void test025() {
        Pokemon katana = pokemonDataGet.getObjectByID(798, as, 1, "こだわりハチマキ", "いじっぱり");
        PokemonMove sword = katana.getDamage("せいなるつるぎ");
        Assertions.assertEquals("かくとう", sword.getMoveType());
    }

    @Test
    @DisplayName("リベロ系の実装")
    public void test026() {
        PokemonMove coolingbeam = greninja.getDamage("れいとうビーム");
        PokemonMove b = zapdos.getDamage("ぼうふう");
        PokemonStadium stadium = new PokemonStadium(greninja, zapdos);
        stadium.forwardTurn(coolingbeam, b);
        BattleLog.getLogAll();
        Assertions.assertTrue(greninja.getType().isTypeMatch("こおり"));
    }

    @DisplayName("げきりゅう系の実装")
    @RepeatedTest(100)
    public void test027() {
        PokemonMove a = primarina.getDamage("なみのり");
        Pokemon tapuLele2 = pokemonDataGet.getObjectByID(786, this.cs, 1, "こだわりメガネ", "おくびょう");
        PokemonMove b = tapuLele2.getDamage("サイコキネシス");
        PokemonStadium stadium = new PokemonStadium(primarina, tapuLele2);
        stadium.forwardTurn(a,b);
        BattleLog.getLogAll();
        Assertions.assertTrue((tapuLele2.getCurrentHP() < 47) || primarina.isDead());
    }

    @Test
    @DisplayName("技の命中率の実装")
    public void test028() {
        PokemonStadium stadium = new PokemonStadium(zapdos, primarina);
        PokemonMove a = zapdos.getDamage("ぼうふう");
        PokemonMove b = primarina.getDamage("ハイドロポンプ");
        stadium.forwardTurn(a,b);
        BattleLog.getLogAll();
    }

    @Test
    @DisplayName("ポケモンの交代")
    public void test029() {
        PokemonStadium stadium = new PokemonStadium(decidueye, tapuLele);
        PokemonMove a = decidueye.getDamage("かげうち");
        PokemonMove c = tapuLele.getDamage2("めいそう");
        stadium.forwardTurn(a, c);
        stadium.getChangeB(zapdos, a);
        PokemonMove b = tapuLele.getDamage("サイコキネシス");
        BattleLog.getLogAll();
        Assertions.assertEquals("サンダー", stadium.getPokemonName(1));
        int power = 540540;
        Assertions.assertNotEquals(power, ((int) b.getPower()));
    }

}
