package org.xm.judger.features.chinese;


import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.util.DoubleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Feature for sentence-transition coherence.  More or less average keyword overlap.
 * 句子连贯性特征，关键字重复率指数
 * @author xuming
 */
public class CNSentenceCoherenceFeature implements CNFeatures {

    // todo 句子连贯性特征权重/范围
    public static final  double OVERLAP_COHERENCE_WEIGHT =0.1;
    //特征项范围
    private static final double OVERLAP_COHERENCE_MIN=0;
    private static final double OVERLAP_COHERENCE_MAX=0.3;


    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        HashMap<String, Double> result = new HashMap<>();
        int numWords = 0;
        int overlap = 0;
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            HashSet<String> partParagraph = new HashSet<>();
            for (ArrayList<String> sentence : paragraph) {
                HashSet<String> words = new HashSet<>();
                for (String token : sentence) {
                    token = token.toLowerCase();
                    //重复词计算
                    if (partParagraph.contains(token))
                        overlap++;
                    numWords++;
                    words.add(token);
                }
                // merge
                partParagraph.addAll(words);
            }
        }
        result.put("overlap_coherence", new Double(overlap / (double) numWords));
        if (Config.DEBUG)
            System.out.println("单词重复率  Overlap coherence for ID(" + instance.id + ") @ score("
                    + instance.domain1_score + "): " + result.get("overlap_coherence")*100+"%");

        return result;
    }

    /**
     * 句子连贯性评分
     * overlapCoherenceScore
     * @param instance
     * @return
     */
    @Override
    public HashMap<String, Double> normalizeScore(CNEssayInstance instance) {
        HashMap<String,Double> scores=new HashMap<>();
        double overlapCoherence=instance.getFeature("overlap_coherence");
        double overlapCoherenceScore=((overlapCoherence-OVERLAP_COHERENCE_MIN)/(OVERLAP_COHERENCE_MAX-OVERLAP_COHERENCE_MIN))*100;
        overlapCoherenceScore=DoubleUtil.processScore(overlapCoherenceScore);
        scores.put("overlapCoherenceScore",overlapCoherenceScore);
        System.out.println("overlapCoherenceScore:"+overlapCoherenceScore);
        return scores;
    }
}
