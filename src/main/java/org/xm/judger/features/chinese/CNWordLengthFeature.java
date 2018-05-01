package org.xm.judger.features.chinese;


import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.util.DoubleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.DeflaterOutputStream;

/**
 * 词长特征
 * 包括：平均词长、词长是1的单词数、词长是2的单词数、词长是3的单词数、词长是4的单词数及占比
 *
 * @author xuming
 */
public class CNWordLengthFeature implements CNFeatures {

    //todo 词长权重/特征项范围待分析
    //词长特征权重
    public static final double WORDLENGTH_WEIGHT=0.2;
    // 特征的范围
    private static final double AVERAGE_MIN=1;
    private static final double AVERAGE_MAX=3;
    private static final double ONE_MIN=0.1;
    private static final double ONE_MAX=0.6;
    private static final double TWO_MIN=0.1;
    private static final double TWO_MAX=0.5;
    private static final double THREE_MIN=0.01;
    private static final double THREE_MAX=0.2;
    private static final double FOUR_MIN=0;
    private static final double FOUR_MAX=0.1;
    private static final double NUM_MIN=50;
    private static final double NUM_MAX=400;
    //各特征项的权重
    private static final double AVERAGE_WEIGHT=0.3;
    private static final double ONE_WEIGHT=0.1;
    private static final double TWO_WEIGHT=0.1;
    private static final double THREE_WEIGHT=0.1;
    private static final double FOUR_WEIGHT=0.1;
    private static final double NUM_WEIGHT=0.3;

    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        int numWords = 0;
        int sumLength = 0;
        int oneLengthWordCount = 0;
        int twoLengthWordCount = 0;
        int threeLengthWordCount = 0;
        int fourLengthWordCount = 0;
        ArrayList<ArrayList<ArrayList<String>>> pargraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> pargraph : pargraphs) {
            for (ArrayList<String> sentence : pargraph) {
                for (String token : sentence) {
                    numWords++;
                    int count = token.length();
                    sumLength += count;
                    if (count == 1) oneLengthWordCount++;
                    if (count == 2) twoLengthWordCount++;
                    if (count == 3) threeLengthWordCount++;
                    if (count == 4) fourLengthWordCount++;
                }
            }
        }
        HashMap<String, Double> values = new HashMap<>();
        double averageWordLength =DoubleUtil.stayTwoDec(new Double(sumLength / (double) numWords));
        double oneLengthWordRatio=DoubleUtil.stayTwoDec(new Double(oneLengthWordCount / (double) numWords));
        double twoLengthWordRatio =DoubleUtil.stayTwoDec(new Double(twoLengthWordCount / (double) numWords));
        double threeLengthWordRatio=DoubleUtil.stayTwoDec(new Double(threeLengthWordCount / (double) numWords));
        double fourLengthWordRatio=DoubleUtil.stayTwoDec(new Double(fourLengthWordCount / (double) numWords));




        values.put("AverageWordLength", averageWordLength);
        values.put("OneLengthWordCount", new Double(oneLengthWordCount));
        values.put("OneLengthWordRatio", oneLengthWordRatio);
        values.put("TwoLengthWordCount", new Double(twoLengthWordCount));
        values.put("TwoLengthWordRatio", twoLengthWordRatio );
        values.put("ThreeLengthWordCount", new Double(threeLengthWordCount));
        values.put("ThreeLengthWordRatio",threeLengthWordRatio);
        values.put("FourLengthWordCount", new Double(fourLengthWordCount));
        values.put("FourLengthWordRatio", fourLengthWordRatio);
        values.put("WordsNum",new Double(numWords));

        if (Config.DEBUG)
            System.out.println("平均词长            Average word length for ID(" + instance.id + "): " +averageWordLength);
            System.out.println("词长为1的单词数      OneLengthWordCount for ID(" + instance.id + "): " + oneLengthWordCount );
            System.out.println("词长为2的单词数      TwoLengthWordCount for ID(" + instance.id + "): " + twoLengthWordCount );
            System.out.println("词长为3的单词数      ThreeLengthWordCount for ID(" + instance.id + "): " + threeLengthWordCount );
            System.out.println("词长为4的单词数      FourLengthWordCount for ID(" + instance.id + "): " + fourLengthWordCount );
            System.out.println("词长为1的单词数占比  OneLengthWordRatio for ID(" + instance.id + "): " + values.get("OneLengthWordRatio")*100+"%");
            System.out.println("词长为2的单词数占比  TwoLengthWordRatio for ID(" + instance.id + "): " + values.get("TwoLengthWordRatio")*100+"%");
            System.out.println("词长为3的单词数占比  ThreeLengthWordRatio for ID(" + instance.id + "): " + values.get("ThreeLengthWordRatio")*100+"%");
            System.out.println("词长为4的单词数占比  FourLengthWordRatio for ID(" + instance.id + "): " + values.get("FourLengthWordRatio")*100+"%");
            System.out.println("单词总数 wordNum" +instance.id+"):"+values.get("WordsNum"));
            return values;
    }

    /**
     *
     * 评分维度：平均词长/词长为1的单词数/词长为2的单词数/词长为3的单词数
     * 评分细则：各特征的得分公式：((value-min)/(max-min))*100;
     * 综合得分公式：sum(featureScore*权重)
     * @param result 中文实例
     * @return
     */
    @Override
    public HashMap<String, Double> normalizeScore(CNEssayInstance result) {
        //todo 词长的评分标准
        HashMap<String,Double> scores=new HashMap<>();
        double averageLength=result.getFeature("AverageWordLength");
        double oneRatio=result.getFeature("OneLengthWordRatio");
        double twoRatio=result.getFeature("TwoLengthWordRatio");
        double threeRatio=result.getFeature("ThreeLengthWordRatio");
        double fourRatio=result.getFeature("FourLengthWordRatio");
        double num=result.getFeature("WordsNum");
        double averageScore =((averageLength-AVERAGE_MIN)/(AVERAGE_MAX-AVERAGE_MIN))*100;
        double oneScore=((oneRatio-ONE_MIN)/(ONE_MAX-ONE_MIN))*100;
        double twoScore=((twoRatio-TWO_MIN)/(TWO_MAX-TWO_MIN))*100;
        double threeScore=((threeRatio-THREE_MIN)/(THREE_MAX-THREE_MIN))*100;
        double fourScore=((fourRatio-FOUR_MIN)/(FOUR_MAX-FOUR_MIN))*100;
        double numScore=((num-NUM_MIN)/(NUM_MAX-NUM_MIN))*100;
        double lengthScore=averageScore*AVERAGE_WEIGHT+oneScore*ONE_WEIGHT+twoScore*TWO_WEIGHT+threeScore*THREE_WEIGHT+fourScore*FOUR_WEIGHT+numScore*NUM_WEIGHT;
        lengthScore=DoubleUtil.processScore(lengthScore);
        scores.put("wordLengthScore",lengthScore);
        System.out.println("wordLengthScore:"+lengthScore);
        return scores;
    }
}
