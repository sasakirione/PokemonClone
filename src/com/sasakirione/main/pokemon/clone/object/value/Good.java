package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.constant.GoodConst;

/**
 * ポケモンの道具を担当するクラス
 */
public class Good {
    /** 道具の名前 */
    private final String goodName;

    /**
     * 道具クラスのコンストラクタ
     * 道具を設定します。
     * @param goodName 道具の名前
     */
    public Good(String goodName) {
        this.goodName = goodName;
    }

    /**
     * こだわりアイテム判定
     * 技にこだわりが生じるアイテムだった場合にtrueを返します。
     * @return 道具がこだわりアイテムだった場合にtrue
     */
    public boolean isChoice() {
        return (this.goodName.equals(GoodConst.CHOICE_SPECS) || this.goodName.equals(GoodConst.CHOICE_BAND) || this.goodName.equals(GoodConst.CHOICE_SCARF));
    }

    /**
     * S強化アイテム判定
     * Sが1.5倍になるアイテムだった場合はtrueを返します。
     * @return 道具がSを1.5倍にするアイテムだった場合にtrue
     */
    public boolean isSpeedBoost() {
        return (this.goodName.equals(GoodConst.CHOICE_SCARF) || this.goodName.equals(GoodConst.NOT_CHOICE_SCARF));
    }

    /**
     * 攻撃力強化アイテム判定
     * 攻撃力を増やすアイテムだった場合にその倍率を返します。
     * @param moveClass 技のタイプ(特殊攻撃技か物理攻撃技かそれ以外か)
     * @return 攻撃力の倍率(デフォルトは1)
     */
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

    /**
     * 1/8の反動がくる道具か判定
     * アイテムがいのちのたまだった場合にtrueを返します。
     * @return 道具がいのちのたまだった場合にtrue
     */
    public boolean isDamageOneEighth() {
        return isLeftOvers();
    }

    /**
     * いのちのたま判定
     * アイテムがいのちのたまだった場合にtrueを返します。
     * @return 道具がいのちのたまだった場合にtrue
     */
    public boolean isLeftOvers() {
        return this.goodName.equals(GoodConst.LEFT_OVERS);
    }
}
