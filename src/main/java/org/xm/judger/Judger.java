package org.xm.judger;

import lombok.Data;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.ENEssayInstance;

import java.util.ArrayList;

/**
 * @author xuming
 */
@Data
public class Judger {
    private static ArrayList<ENEssayInstance> eninstances;

    public static ArrayList<ENEssayInstance> getEninstances() {
        return eninstances;
    }

    public static void setEninstances(ArrayList<ENEssayInstance> instances) {
        Judger.eninstances = instances;
    }

    private static ArrayList<CNEssayInstance> cninstances;

    public static ArrayList<CNEssayInstance> getCninstances() {
        return cninstances;
    }

    public static void setCninstances(ArrayList<CNEssayInstance> instances) {
        Judger.cninstances = instances;
    }

    /**
     * 作文得分
     */
    public static double cilinSimilarity(String word1, String word2) {
        return 0.0;
    }

}
