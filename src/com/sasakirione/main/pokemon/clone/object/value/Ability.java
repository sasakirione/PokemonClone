package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.AbilityConst;
import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;

/**
 * ポケモンの特性を担当するクラス
 */
public class Ability {
    /** 特性の名前 */
    private final String abilityName;
    /** 特性が発動条件を満たしてるか、もしかくは一度だけの特性なら使用済みか */
    private boolean abilityBool = false;

    /**
     *  特性クラスのコンストラクタ
     *  特性を作ります
     *  @param name 特性の名前
     */
    public Ability(String name) {
        this.abilityName = name;
    }

    /**
     *  技の攻撃力をあげる特性か判定
     *  技の攻撃力をあげる特性かを判断し、その場合は倍率を返します
     *  @param move 出そうとしてる技のインスタンス
     *  @return 攻撃力に対する倍率(通常は1)
     */
    public double powerBoost(PokemonMove move) {
        if (checkTypeBoost(move.getMoveType())) {
            return CalculationConst.ONE_POINT_FIVE;
        }
        if(isTorrent(move.getMoveType())) {
            return CalculationConst.ONE_POINT_FIVE;
        }
        return CalculationConst.ONE;
    }

    /**
     *  特定のタイプの攻撃力をあげる特性か判定
     *  トランジスタやりゅうのあぎとなどの特定のタイプの攻撃力をあげる特性かを判定します
     *  @param type 出そうとしてる技のタイプ
     *  @return 対象の特性と技の組み合わせだったらtrue
     */
    private boolean checkTypeBoost(String type) {
        return (abilityName.equals(AbilityConst.TRANSISTOR) && type.equals("でんき")) || (abilityName.equals(AbilityConst.DRAGONS_MAW) && type.equals("ドラゴン")) ;
    }

    /**
     *  サイコメイカー判定
     *  特性がサイコフィールを展開する特性かどうか判定します
     *  ex) サイコメイカー
     *  @return 合致する特性だったらtrue
     */
    public boolean isPsychoMaker() {
        return abilityName.equals(AbilityConst.PSYCHO_MAKER);
    }

    /**
     *  リベロ判定
     *  特性が自分のタイプを技のタイプに合わせて変化させる特性かどうかを判定します
     *  ex) リベロ、へんげんじざい
     *  @return 合致する特性だったらtrue
     */
    public boolean isLibero() {
        return (abilityName.equals(AbilityConst.LIBERO) || abilityName.equals(AbilityConst.PROTEAN));
    }

    /**
     *  げきりゅう判定
     *  特性がピンチ(HPが1/3以下)の時に対応するタイプの技の攻撃力が上がる特性かどうかとHPが適用条件を満たしてるかどうかを判定します
     *  ex) げきりゅう、もうか、しんりょく、むしのしらせ
     *  @param moveType 技のタイプ
     *  @return 合致する特性だったらtrue
     */
    private boolean isTorrent(String moveType) {
        if (!abilityBool) {
            return false;
        }
        return (abilityName.equals(AbilityConst.TORRENT) && moveType.equals("みず")) || (abilityName.equals(AbilityConst.BLAZE) && moveType.equals("ほのお")) ||
                (abilityName.equals(AbilityConst.OVER_GROW) && moveType.equals("くさ")) || (abilityName.equals(AbilityConst.SWARM) && moveType.equals("むし"));
    }

    /**
     *  HP起動
     *  HPが1/3以下になった時に呼ばれて、対応する特性だった場合にはabilityBoolをtrueにします
     *  ex) げきりゅう、もうか、しんりょく、むしのしらせ
     */
    public void doOneThird() {
        if ((abilityName.equals(AbilityConst.TORRENT)) || (abilityName.equals(AbilityConst.BLAZE)) ||
                (abilityName.equals(AbilityConst.OVER_GROW)) || (abilityName.equals(AbilityConst.SWARM))) {
            abilityOn();
        }
    }

    /**
     *  特性起動判定
     *  特性が使える状態かどうか、特性が使用済みかを操作します
     */
    public void abilityOn() {
        this.abilityBool = true;
    }

    /**
     *  特性名を返す
     *  特性の名前を返します
     *  @return 特性名
     */
    public String getName() {
        return this.abilityName;
    }

    /**
     *  特性のtoString
     *  特性の名前を返します
     *  @return 特性名
     */
    public String toString() {
        return getName();
    }

    /**
     *  ばかかわ判定
     *  相手の攻撃を無効化する特性かどうか、それが使える状態かどうかを判定します
     *  @return 条件に合致した場合にtrue
     */
    public boolean isBakekawa() {
        return (this.abilityName.equals(AbilityConst.DISGUISE) && !this.abilityBool);
    }
}
