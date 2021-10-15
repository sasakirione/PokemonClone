package com.sasakirione.main.pokemon.clone.`object`

/**
 * ポケモンのタイプ相性を担当するクラス
 */
object PokemonTypeCompatibility {
    /**
     * タイプ相性を判定する
     * 攻撃側のタイプと防御側のタイプをいれた時にタイプ相性の倍率を返します。
     * @param attack 攻撃側のタイプ
     * @param defense 防御側のタイプ
     * @return タイプ倍率
     */
    @JvmStatic
    fun typeCompatibility(attack: String, defense: String): Double {
        var magnification = 1.0
        if (attack == "ノーマル") {
            if (defense == "いわ" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
            if (defense == "ゴースト") {
                magnification = 0.0
                return magnification
            }
            return magnification
        }
        if (attack == "ほのお") {
            if (defense == "くさ" || defense == "こおり" || defense == "むし" || defense == "はがね") {
                magnification = 2.0
                return magnification
            }
            if (defense == "ほのお" || defense == "みず" || defense == "いわ" || defense == "ドラゴン") {
                magnification = 0.5
                return magnification
            }
            return magnification
        }
        if (attack == "みず") {
            if (defense == "ほのお" || defense == "じめん" || defense == "いわ") {
                magnification = 2.0
                return magnification
            }
            if (defense == "みず" || defense == "くさ" || defense == "ドラゴン") {
                magnification = 0.5
                return magnification
            }
            return magnification
        }
        if (attack == "でんき") {
            if (defense == "みず" || defense == "ひこう") {
                magnification = 2.0
                return magnification
            }
            if (defense == "でんき" || defense == "くさ" || defense == "ドラゴン") {
                magnification = 0.5
                return magnification
            }
            if (defense == "じめん") {
                magnification = 0.0
                return magnification
            }
            return magnification
        }
        if (attack == "くさ") {
            if (defense == "みず" || defense == "じめん" || defense == "いわ") {
                magnification = 2.0
                return magnification
            }
            if (defense == "ほのお" || defense == "くさ" || defense == "どく" || defense == "ひこう" || defense == "むし" || defense == "ドラゴン" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
            return magnification
        }
        if (attack == "こおり") {
            if (defense == "くさ" || defense == "じめん" || defense == "ひこう" || defense == "ドラゴン") {
                magnification = 2.0
                return magnification
            }
            if (defense == "ほのお" || defense == "みず" || defense == "こおり" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
            return magnification
        }
        if (attack == "かくとう") {
            if (defense == "ノーマル" || defense == "こおり" || defense == "いわ" || defense == "あく" || defense == "はがね") {
                magnification = 2.0
                return magnification
            }
            if (defense == "どく" || defense == "ひこう" || defense == "エスパー" || defense == "むし" || defense == "フェアリー") {
                magnification = 0.5
                return magnification
            }
        }
        if (attack == "どく") {
            if (defense == "くさ" || defense == "フェアリー") {
                magnification = 2.0
                return magnification
            }
            if (defense == "どく" || defense == "じめん" || defense == "いわ" || defense == "ゴースト") {
                magnification = 0.5
                return magnification
            }
            if (defense == "はがね") {
                magnification = 0.0
                return magnification
            }
        }
        if (attack == "じめん") {
            if (defense == "ほのお" || defense == "でんき" || defense == "どく" || defense == "いわ" || defense == "はがね") {
                magnification = 2.0
                return magnification
            }
            if (defense == "くさ" || defense == "むし") {
                magnification = 0.5
                return magnification
            }
            if (defense == "ひこう") {
                magnification = 0.0
                return magnification
            }
        }
        if (attack == "ひこう") {
            if (defense == "くさ" || defense == "かくとう" || defense == "むし") {
                magnification = 2.0
                return magnification
            }
            if (defense == "でんき" || defense == "いわ" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
        }
        if (attack == "エスパー") {
            if (defense == "かくとう" || defense == "どく") {
                magnification = 2.0
                return magnification
            }
            if (defense == "エスパー" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
            if (defense == "あく") {
                magnification = 0.0
                return magnification
            }
        }
        if (attack == "むし") {
            if (defense == "くさ" || defense == "エスパー" || defense == "あく") {
                magnification = 2.0
                return magnification
            }
            if (defense == "ほのお" || defense == "かくとう" || defense == "どく" || defense == "ひこう" || defense == "ゴースト" || defense == "はがね" || defense == "フェアリー") {
                magnification = 0.5
                return magnification
            }
        }
        if (attack == "いわ") {
            if (defense == "ほのお" || defense == "ひこう" || defense == "こおり" || defense == "むし") {
                magnification = 2.0
                return magnification
            }
            if (defense == "かくとう" || defense == "じめん" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
        }
        if (attack == "ゴースト") {
            if (defense == "エスパー" || defense == "ゴースト") {
                magnification = 2.0
                return magnification
            }
            if (defense == "あく") {
                magnification = 0.5
                return magnification
            }
            if (defense == "ノーマル") {
                magnification = 0.0
                return magnification
            }
        }
        if (attack == "ドラゴン") {
            if (defense == "ドラゴン") {
                magnification = 2.0
                return magnification
            }
            if (defense == "はがね") {
                magnification = 0.5
                return magnification
            }
            if (defense == "フェアリー") {
                magnification = 0.0
                return magnification
            }
        }
        if (attack == "あく") {
            if (defense == "エスパー" || defense == "ゴースト") {
                magnification = 2.0
                return magnification
            }
            if (defense == "かくとう" || defense == "あく" || defense == "フェアリー") {
                magnification = 0.5
                return magnification
            }
        }
        if (attack == "はがね") {
            if (defense == "こおり" || defense == "いわ" || defense == "フェアリー") {
                magnification = 2.0
                return magnification
            }
            if (defense == "ほのお" || defense == "みず" || defense == "でんき" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
        }
        if (attack == "フェアリー") {
            if (defense == "かくとう" || defense == "あく" || defense == "ドラゴン") {
                magnification = 2.0
                return magnification
            }
            if (defense == "ほのお" || defense == "どく" || defense == "はがね") {
                magnification = 0.5
                return magnification
            }
        }
        return magnification
    }
}