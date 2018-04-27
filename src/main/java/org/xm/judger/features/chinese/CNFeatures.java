package org.xm.judger.features.chinese;

import org.xm.judger.domain.CNEssayInstance;

import java.util.HashMap;

/**
 * @author xuming
 */
public interface CNFeatures {
    /**
     * get feature score for each chinese essay instance
     * 特征分数
     * @param instance
     * @return
     */
    HashMap<String, Double> getFeatureScores(CNEssayInstance instance);

    /**
     * 特征项得分
     */
    HashMap<String,Double> normalizeScore(CNEssayInstance instance);

}
