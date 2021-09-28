package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.constant.MoveConst;
import com.sasakirione.main.pokemon.clone.exception.UnsupportedMoveException;
import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.code.MoveClass;
import com.sasakirione.main.pokemon.clone.object.code.MoveCombo;
import com.sasakirione.main.pokemon.clone.object.value.*;
import com.sasakirione.main.pokemon.clone.utility.CalculationUtility;

import java.util.Random;

/**
 * ポケモンのわざを担当するクラス
 */
public class PokemonMove {
    /** 技の名前 */
    private final String moveName;
    /** ポケモン */
    private final Pokemon pokemon;
    /** 技の種類 */
    private final MoveClass moveClass;
    /** 技の威力 */
    private int moveDamage;
    /** 技のタイプ */
    private final String moveType;
    /** 技の優先度 */
    private final int priority;
    /** 技の命中率 */
    private final int accuracy;
    /** 技の連続仕様 */
    private final MoveCombo moveCombo;
    /** 技の急所ランク */
    private final int vitalRank;
    /** 技の連続回数 */
    private int multipleCount;

    /**
     * コンストラクタ(通常用)
     * 技クラスのコンストラクタです。
     * @param name わざの名前
     * @param pokemon ポケモンのインスタンス
     * @param moveClass 技の種類
     * @param moveDamage 技の威力
     * @param moveType 技のタイプ
     * @param priority 技の優先度
     * @param accuracy 技の命中率
     * @param vitalRank 技の急所ランク
     * @param comb 技の連続仕様
     */
    public PokemonMove(String name, Pokemon pokemon, MoveClass moveClass, int moveDamage, String moveType,
                       int priority, int accuracy, int vitalRank, int comb) {
        this.moveName = name;
        this.moveClass = moveClass;
        this.priority = priority;
        this.moveDamage = moveDamage;
        this.moveType = moveType;
        this.accuracy = accuracy;
        this.pokemon = pokemon;
        this.moveCombo = setMoveCombo(comb);
        this.vitalRank = vitalRank;
        this.multipleCount = 0;
    }

    /**
     * コンストラクタ
     * 技クラスのコンストラクタです。
     * @param name わざの名前
     */
    public PokemonMove(String name, Pokemon pokemon) {
        this.moveName = name;
        this.priority = 0;
        this.accuracy = 100;
        this.pokemon = pokemon;
        this.moveCombo = MoveCombo.NORMAL;
        this.vitalRank = 0;

        if (name.equals("サンダープリズン")) {
            this.moveClass = MoveClass.SPECIAL;
            this.moveDamage = 80;
            this.moveType = "でんき";
            return;
        }
        if (name.equals(MoveConst.SHELL_SMASH)) {
            this.moveClass = MoveClass.SELF_CHANGE;
            this.moveType = "ノーマル";
            return;
        }
        if (name.equals(MoveConst.SEISMIC_TOSS)) {
            this.moveClass = MoveClass.PHYSICS;
            this.moveType = "ノーマル";
            return;
        }
        if (name.equals(MoveConst.EERIE_IMPULSE)) {
            this.moveClass = MoveClass.ENEMY_CHANGE;
            this.moveType = "でんき";
            return;
        }
        if (name.equals(MoveConst.THUNDER_WAVE)) {
            this.moveClass = MoveClass.ENEMY_CHANGE;
            this.moveType = "でんき";
            return;
        }
        if (name.equals(MoveConst.CALM_MIND)) {
            this.moveClass = MoveClass.SELF_CHANGE;
            this.moveType = "エスパー";
            return;
        }
        if (name.equals(MoveConst.HARDEN)) {
            this.moveClass = MoveClass.SELF_CHANGE;
            this.moveType = "ノーマル";
            return;
        }
        if (name.equals(MoveConst.SOAK)) {
            this.moveClass = MoveClass.ENEMY_CHANGE;
            this.moveType = "みず";
            return;
        }
        throw new UnsupportedMoveException();
    }

    /**
     * 連続仕様変換
     * わざの連続仕様のコードをEnum型に変換します
     * @return わざの連続仕様
     */
    private MoveCombo setMoveCombo(int code) {
        return switch (code) {
            case 3 -> MoveCombo.FIXED_THREE;
            case 2 -> MoveCombo.FIXED_TWO;
            case 30 -> MoveCombo.MAX_THREE;
            case 50 -> MoveCombo.MAX_FIVE;
            default -> MoveCombo.NORMAL;
        };
    }

    /**
     * わざの名前を返す
     * わざの名前を返します。
     * @return わざの名前
     */
    public String getMoveName() {
        return moveName;
    }

    /**
     * 攻撃実数値を返す
     * 攻撃または特防の実数値を返します
     * @return 物理技の場合は攻撃実数値、特殊技の場合は特攻実数値
     */
    public int getRealAttack() {
        if (moveClass.equals(MoveClass.PHYSICS)) {
            return this.pokemon.getStatus().getA();
        } else {
            return this.pokemon.getStatus().getC();
        }
    }

    /**
     * タイプ一致倍率を返す
     * タイプ一致わざの場合にタイプ一致の倍率を返します
     * @return タイプ一致わざの場合は1.5
     */
    public double getMagnification() {
        if (pokemon.getType().isTypeMatch(moveType)) {
            return (6144.0/4096.0);
        }
        return (1.0);
    }

    /**
     * わざのタイプを返す
     * わざのタイプを返します。
     * @return わざのタイプ
     */
    public String getMoveType() {
        return this.moveType;
    }

    /**
     * わざの優先度を返す
     * わざの優先度を返します。
     * @return わざの優先度
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * 自分にかかる変化技判定
     * 自分にかかる変化技かを判定します
     * @return 自分にかかる変化技だった場合はtrue
     */
    public boolean isSelfChangeMove() {
        return this.moveClass.equals(MoveClass.SELF_CHANGE);
    }

    /**
     * 相手にかかる変化技判定
     * 相手にかかる変化技かを判定します
     * @return 相手にかかる変化技だった場合はtrue
     */
    public boolean isEnemyChangeMove() { return this.moveClass.equals(MoveClass.ENEMY_CHANGE); }

    /**
     * 物理技判定
     * 物理技かを判定します
     * @return 物理技だった場合はtrue
     */
    public boolean isPhysicsMove() {return this.moveClass.equals(MoveClass.PHYSICS); }

    /**
     * 技の名前判定
     * 入力された技の名前か判定します
     * @param name わざの名前
     * @return 入力された技の名前と同じ技だった場合はtrue
     */
    public boolean isMoveNameCheck(String name) {
        return this.moveName.equals(name);
    }

    /**
     * サイコフィールド処理
     * 対象の技に倍率をかけます
     */
    public void psychoBoost() {
        if (this.moveType.equals("エスパー")) {
            fieldBoost();
        }
    }

    /**
     * エレキフィールド処理
     * 対象の技に倍率をかけます
     */
    public void electricBoost() {
        if (this.moveType.equals("でんき")) {
            fieldBoost();
        }
    }

    /**
     * フィールドバフ処理
     * フィールドにより技の威力にバフをかけます
     */
    private void fieldBoost() {
        if (this.moveClass.equals(MoveClass.PHYSICS) || this.moveClass.equals(MoveClass.SPECIAL)) {
            this.moveDamage = (int) Math.round(this.moveDamage * CalculationConst.ONE_POINT_THREE);
        }
    }

    /**
     * 攻撃力を返す
     * 防御補正前の段階まで計算を進めた攻撃力を返します
     * @return 攻撃力
     */
    public double getPower() {
        double a = Math.floor(50 * 0.4 + 2);
        double b = a * moveDamage * getRealAttack();
        return CalculationUtility.fiveOutOverFiveIn(b * this.pokemon.getAbility().powerBoost(this) * this.pokemon.getGood().powerBoost(moveClass));
    }

    /**
     * リベロ処理
     * 攻撃する側のタイプを技のタイプにします
     */
    public void libero() {
        pokemon.libero(new Type(moveType));
    }

    /**
     * 技の命中判定
     * 技の命中判定をします
     * @return 技が命中した場合はtrue
     */
    public boolean isMoveHit() {
        return  randomForAccuracy() < this.accuracy;
    }

    /**
     * 命中判定用乱数
     * 技の命中判定をするのに必要な乱数を返します
     * @return 乱数(0-99)
     */
    public int randomForAccuracy() {
        Random random = new Random();
        return random.nextInt(100);
    }

    /**
     * 攻撃終了後の処理
     * 技が命中した後に行う処理を行います
     * ex) いのちのたま
     */
    public void endDecision() {
        if (this.pokemon.getGood().isDamageOneEighth()) {
            this.pokemon.getStatus().damageOneEighth();
            BattleLog.tama(this.pokemon.getName());
        }
    }

    /**
     * てんねん用攻撃力を返す
     *　てんねん用に能力変化を無視した攻撃力を返します
     *  @return てんねん用攻撃力
     */
    public double getPower2() {
        double a = Math.floor(50 * 0.4 + 2);
        double b = a * moveDamage * getRealAttack2();
        return CalculationUtility.fiveOutOverFiveIn(b * this.pokemon.getAbility().powerBoost(this) * this.pokemon.getGood().powerBoost(moveClass));
    }

    /**
     * てんねん用攻撃実数値を返す
     * 補正がかかってない攻撃または特防の実数値を返します
     * @return 物理技の場合は攻撃実数値(補正なし)、特殊技の場合は特攻実数値(補正なし)
     */
    private double getRealAttack2() {
        if (moveClass.equals(MoveClass.PHYSICS)) {
            return this.pokemon.getStatus().getA2();
        } else {
            return this.pokemon.getStatus().getC2();
        }
    }

    /**
     * 技の連続回数を返す
     * 技を何回連続で行うかを返します
     * @return 技の連続回数
     */
    public int getCombCount() {
        if (this.moveCombo.equals(MoveCombo.NORMAL)) {
            return 1;
        }
        if (this.moveCombo.equals(MoveCombo.FIXED_TWO)) {
            return 2;
        }
        if (this.moveCombo.equals(MoveCombo.FIXED_THREE)) {
            return 3;
        }
        if (this.moveCombo.equals(MoveCombo.MAX_THREE)) {
            return setCombCount(3);
        }
        if (this.moveCombo.equals(MoveCombo.MAX_FIVE)) {
            return setCombCount(5);
        }
        return 1;
    }

    /**
     * 技の連続回数を算出する
     * 連続回数がランダムで決まる技の連続回数を決定する
     * @return 技の連続回数
     */
    private int setCombCount(int i) {
        if (i == 3) {
            return 3;
        }
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber < 35) {
            return 2;
        }
        if (randomNumber < 70) {
            return 3;
        }
        if (randomNumber < 85) {
            return 4;
        }
        return 5;
    }

    /**
     * 連続技判定
     * 連続技かどうかを判定します
     * @return 連続技の場合はtrueを返す
     */
    public boolean isCombAttack() {
        return !this.moveCombo.equals(MoveCombo.NORMAL);
    }

    /**
     * 急所ランクを返す
     * 技の急所ランクと技を出すポケモンの急所ランクを合わせた急所ランクを返します
     * @return 急所ランク
     */
    public int getVitalRank() {
        return this.vitalRank;
    }

    public boolean isMultipleTurnMove() {
        return this.moveName.equals(MoveConst.PETAL_DANCE);
    }

    public boolean isMultipleTurnMoveEnd() {
        if (isMultipleTurnMove() && this.multipleCount < 3) {
            return false;
        } else if (isMultipleTurnMove() && this.multipleCount == 3) {
            return randomForAccuracy() < 50;
        } else {
            return true;
        }
    }

    public void forwardTurn() {
        this.multipleCount++;
    }
}