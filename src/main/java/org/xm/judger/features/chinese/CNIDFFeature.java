package org.xm.judger.features.chinese;


import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.domain.EssayInstance;
import org.xm.judger.util.DoubleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * 文本相似度计算 TF-IDF：关键词--词向量--计算相似度
 *  单词权重
 * @author xuming
 */
public class CNIDFFeature implements CNFeatures {
    //todo  TF_IDF特征权重/特征项范围的分析

    public static final double IDF_WEIGHT=0;
    private static final double IDF_MIN=0;
    private static final double IDF_MAX=0;

    HashMap<String, double[]> idf;

    /**
     * init and compute IDF
     *
     * @param instances
     */
    public CNIDFFeature(ArrayList<CNEssayInstance> instances) {
        idf = new HashMap<>();
        // 1.doc frequency
        for (EssayInstance instance : instances) {
            //找出关键词---set过滤掉重复的
            HashSet<String> words = new HashSet<>();
            ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
            for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
                for (ArrayList<String> sentence : paragraph) {
                    for (String token : sentence) {
                        words.add(token.toLowerCase());
                    }
                }
            }
            // merge
            //计算词频 --每个单词的词频
            for (String word : words) {
                if (idf.containsKey(word))
                    idf.get(word)[0]++;
                else idf.put(word, new double[]{1});
            }
        }
        // 2.invert it
        //计算IDF-反文档词频
        for (String word : idf.keySet())
            idf.get(word)[0] = Math.log(instances.size() / (double) idf.get(word)[0]);
    }

    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        int numWords = 0;
        double sumIdf = 0;
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    sumIdf += idf.get(token.toLowerCase())[0];
                    numWords++;
                }
            }
        }
        HashMap<String, Double> values = new HashMap<>();
        values.put("AverageIDF", DoubleUtil.stayTwoDec(new Double(sumIdf / (double) numWords)));
        if (Config.DEBUG)
            System.out.println("单词权重  AverageIDF for ID(" + instance.id + "): " + values.get("AverageIDF")*100+"%");
        return values;
    }

    /**
     * IDF得分
     * idfScore
     * @param cnEssayInstance
     * @return
     */
    @Override
    public HashMap<String, Double> normalizeScore(CNEssayInstance cnEssayInstance) {
        HashMap<String,Double> scores=new HashMap<>();
        double idf=cnEssayInstance.getFeature("AverageIDF");
        double idfScore=((idf-IDF_MIN)/(IDF_MAX-IDF_MIN))*100;
        scores.put("idfScore",idfScore);
        System.out.println("idfScore:"+idfScore);
        return scores;
    }
}
