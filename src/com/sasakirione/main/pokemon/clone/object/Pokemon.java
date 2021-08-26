package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.value.*;

/**
 * ポケモン自体を表すクラス
 */
public class Pokemon {
    /** ポケモンの名前 */
    private String name;
    /** ポケモンのステータス */
    private Status status;
    /** ポケモンのタイプ */
    private Type type;
    /** ポケモンが道具でこだわってるか */
    private boolean goodChoice = false;
    /** ポケモンがこだわってる場合のわざ */
    private String choiceMove = null;
    /** ポケモンの状態異常(状態異常がなしの場合は空文字) */
    private String statusAilment = "";

    /**
     * コンストラクタ(登録されてるポケモン用)
     * 登録されてるポケモンを使うためのコンストラクタ
     * @param name ポケモンの名前
     * @param effort ポケモンの努力値
     * @param good ポケモンの道具
     * @param nature ポケモンの性格
     */
    public Pokemon(String name, int[] effort, String good, String nature) {
        setPokemon(name, effort, new Nature(nature), good);
    }

    /**
     * コンストラクタ(登録されてるポケモン以外用)
     * 登録されてるポケモン以外のポケモンやポケモンじゃないものを使うためのコンストラクタ
     * @param name ポケモンの名前
     * @param effort ポケモンの努力値
     * @param base ポケモンの種族値
     * @param good ポケモンの道具
     * @param nature ポケモンの性格
     * @param type1 ポケモンのタイプ1
     * @param type2 ポケモンのタイプ2
     * @param ability ポケモンの特性
     */
    public Pokemon(String name, int[] effort, int[] base, String good, String nature, String type1, String type2, String ability) {
        this.name = name;
        this.type = new Type(type1, type2);
        this.status = new Status(base, new Effort(effort), good, new Nature(nature));
    }

    /**
     * ポケモンの名前を返す
     * ポケモンの名前を返します。
     * @return ポケモンの名前
     */
    public String getName() {
        return this.name;
    }

    /**
     * ポケモンのわざを出す
     * ポケモンのわざを担当するクラスをインスタンス化して返します。
     * @param name わざの名前
     * @return ポケモンのわざクラスのインスタンス
     */
    public PokemonMove getDamage(String name) {
        if (goodChoice) {
            choiceCheck(name);
        }
        return new PokemonMove(name, this.status, this.type);
    }

    /**
     * こだわりチェック
     * こだわりアイテムを持ってる場合にこだわっているわざ以外のわざを使えないようにチェックします
     * @param name わざの名前
     * @throws IllegalAccessException こだわってるわざ以外を使おうとすると投げます
     */
    private void choiceCheck(String name) {
        if (choiceMove != null && !name.equals(this.choiceMove)) {
            throw new IllegalArgumentException("こだわっています！");
        }
        if (choiceMove == null) {
            this.choiceMove = name;
        }
    }

    /**
     * 登録されてるポケモンの設定
     * 登録されてるポケモンの情報を呼び出してポケモンを登録します。
     * @param name ポケモンの名前
     * @param effortInt ポケモンの努力値
     * @param nature ポケモンの性格
     * @param good ポケモンの道具
     */
    private void setPokemon(String name, int[] effortInt, Nature nature, String good){
        this.name = name;
        int[] base = null;
        Effort effort = null;
        if (name.equals("サンダー")) {
            base = new int[] {90, 90, 85, 125, 90, 100};
            effort = new Effort(effortInt);
            this.type = new Type("でんき","ひこう");
        }
        if (name.equals("ゲッコウガ")) {
            base = new int[] {72, 95, 67, 103, 71, 122};
            effort = new Effort(effortInt);
            this.type = new Type("みず","あく");
        }
        if (name.equals("レジエレキ")) {
            base = new int[] {80, 100, 50, 100, 50, 200};
            effort = new Effort(effortInt);
            this.type = new Type("でんき");
        }
        if (name.equals("ポットデス")) {
            base = new int[] {60, 65, 65, 134, 114, 70};
            effort = new Effort(effortInt);
            this.type = new Type("ゴースト");
        }
        this.status = new Status(base, effort, good, nature);
    }

    /**
     * ポケモンのわざを受ける
     * ポケモンのわざクラスのインスタンスを受け取ってダメージ処理を行います。
     * @param a 受ける技のインスタンス
     */
    public void takeDamage(PokemonMove a) {
        if (a.getMoveClass() == 3) {
            takeChange(a);
            return;
        }
        if (a.getMoveName().equals("ちきゅうなげ")) {
            this.status.constantDamage(50);
            return;
        }
        int damage = a.getMoveDamage();
        int realAttack = a.getRealAttack();
        int defenseChoice;
        String type = a.getMoveType();
        double typeMagnification = this.type.getTypeMagnification(type);
        double magnification = a.getMagnification() * typeMagnification;

        if (a.getMoveClass() == 0) {
            defenseChoice = 2;
        } else {
            defenseChoice = 4;
        }
        this.status.damageCalculation(realAttack, defenseChoice, damage, magnification, type);
        BattleLog.typeMagnification(typeMagnification);
    }

    /**
     * 変化技の処理をする
     * 自分にかかった変化技の処理を行います。
     * @param a 自分に向けられた変化技のインスタンス
     */
    public void takeChange(PokemonMove a) {
        if (a.getMoveName().equals("からをやぶる")) {
            rankUp(1,2);
            rankUp(3,2);
            rankUp(5,2);
        }
        if (a.getMoveName().equals("かいでんぱ")) {
            rankUp(3,-2);
        }
        if (a.getMoveName().equals("でんじは")) {
            getPAR();
        }
    }

    /**
     * まひ処理を行う
     * まひになった時にその処理を行います。
     */
    private void getPAR() {
        if (!statusAilment.equals("")) {
            BattleLog.statusAilmentError();
            return;
        }
        if (this.type.isPARCheck()) {
            BattleLog.parError();
            return;
        }
        statusAilment = "まひ";
        status.getPAR();
        BattleLog.par(this.name);
    }

    /**
     * 残りHP実数値を返す
     * ポケモンの残りHP実数値の数字だけを返します。
     * @return 残りHP実数値
     */
    public int getCurrentHP() {
        return status.getCurrentHP();
    }

    /**
     * 現在のHPを返す
     * ポケモンの現在のHP・最大HP・ステータスバーの色を返します。
     * @return 現在のHP
     */
    public String getCurrentHP2() {
        return status.getCurrentHP2();
    }

    /**
     * ランク変化を行う
     * 技等でランク変化が起こった時にその処理を行います。
     * @param item 変化するステータス(1:攻撃、2:防御、3:特攻、4:特防、5:素早さ)
     * @param i プラスマイナス何段階変化するか
     */
    public void rankUp(int item, int i) {
        status.rankUp(item,i);
        BattleLog.rankUp(this.name, item, i);
    }

    /**
     * 素早さを返す
     * ポケモンの素早さ実数値を返します。
     * @return ポケモンの素早さ実数値
     */
    public int getS() {
        return this.status.getS();
    }
}
