package org.xm.judger.features.chinese;


import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.util.DoubleUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 句子数、平均句长、句长是大于7个词的句数、句长是大于8个词的句数、句长是大于9个词的句数及占比
 *
 * @author xuming
 */
public class CNSentenceLengthFeature implements CNFeatures {

    //todo 句长特征权重/范围/各特征项权重 待分析
    public static final double SENTENCE_LENGTH_WEIGHT=0;
    //特征项范围
    private static final double NUM_SENTENCE_MIN=0;
    private static final double NUM_SENTENCE_MAX=0;
    private static final double AVERAGE_LENGTH_MIN=0;
    private static final double AVERAGE_LENGTH_MAX=0;
    private static final double SIX_LENGTH_MIN=0;
    private static final double SIX_LENGTH_MAX=0;
    private static final double EIGHT_LENGTH_MIN=0;
    private static final double EIGHT_LENGHT_MAX=0;
    private static final double TEN_LENGHT_MIN=0;
    private static final double TEN_LENGHT_MAX=0;
    private static final double TWELVE_LENGTH_MIN=0;
    private static final double TWELVE_LENGHT_MAX=0;

    //特征项权重
    private static final double NUM_SENTENCE_WEIGHT=0;
    private static final double AVERAGE_LENGTH_WEIGHT=0;
    private static final double SIX_LENGTH_WEIGHT=0;
    private static final double EIGHT_LENGTH_WEIGHT=0;
    private static final double TEN_LENGTH_WEIGHT=0;
    private static final double TWELVE_LENGTH_WEIGHT=0;


    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        int numSentences = 0;//句子数
        int sumCount = 0;//总句长
        int moreThanSixWordSentenceCount = 0;//句长是大于6个词的句数
        int moreThanEightWordSentenceCount = 0;//句长是大于8个词的句数
        int moreThanTenWordSentenceCount = 0;//句长是大于10个词的句数
        int moreThanTwelveWordSentenceCount=0;
        // compute the word length of longest essay
        ArrayList<ArrayList<ArrayList<String>>> pargraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> pargraph : pargraphs) {
            for (ArrayList<String> sentence : pargraph) {
                numSentences++;
                int size = sentence.size();
                sumCount += size;
                if (size > 6) moreThanSixWordSentenceCount++;
                if (size > 8) moreThanEightWordSentenceCount++;
                if (size > 10) moreThanTenWordSentenceCount++;
                if (size>12) moreThanTwelveWordSentenceCount++;
            }
        }

        HashMap<String, Double> values = new HashMap<>();
        values.put("Num_Sentences", new Double(numSentences));
        values.put("AverageSentenceLength",DoubleUtil.stayTwoDec( new Double(sumCount / (double) numSentences)));
        values.put("MoreThanSixWordSentenceCount", new Double(moreThanSixWordSentenceCount));
        values.put("MoreThanSixWordSentenceRatio", DoubleUtil.stayTwoDec(new Double(moreThanSixWordSentenceCount / (double) numSentences)));
        values.put("MoreThanEightWordSentenceCount", new Double(moreThanEightWordSentenceCount));
        values.put("MoreThanEightWordSentenceRatio", DoubleUtil.stayTwoDec(new Double(moreThanEightWordSentenceCount / (double) numSentences)));
        values.put("MoreThanTenWordSentenceCount", new Double(moreThanTenWordSentenceCount));
        values.put("MoreThanTenWordSentenceRatio", DoubleUtil.stayTwoDec(new Double(moreThanTenWordSentenceCount / (double) numSentences)));
        values.put("MoreThanTenWordSentenceCount", new Double(moreThanTwelveWordSentenceCount));
        values.put("MoreThanTenWordSentenceRatio", DoubleUtil.stayTwoDec(new Double(moreThanTwelveWordSentenceCount / (double) numSentences)));

        if (Config.DEBUG) {
            System.out.println("句子数  numSentences for ID(" + instance.id + "): " + values.get("Num_Sentences"));
            System.out.println("平均句长  AverageSentenceLength for ID(" + instance.id + "): " + values.get("AverageSentenceLength"));
            System.out.println("句长是大于6个词的句数  MoreThanSixWordSentenceCount for ID(" + instance.id + "): " + values.get("MoreThanSevenWordSentenceCount"));
            System.out.println("句长是大于8个词的句数  MoreThanEightWordSentenceCount for ID(" + instance.id + "): " + values.get("MoreThanEightWordSentenceCount"));
            System.out.println("句长是大于10个词的句数  MoreThanTenWordSentenceCount for ID(" + instance.id + "): " + values.get("MoreThanNineWordSentenceCount"));
            System.out.println("句长是大于6个词的比例  MoreThanSixWordSentenceRatio for ID(" + instance.id + "): " + values.get("MoreThanSevenWordSentenceRatio")*100+"%");
            System.out.println("句长是大于8个词的比例  MoreThanEightWordSentenceRatio for ID(" + instance.id + "): " + values.get("MoreThanEightWordSentenceRatio")*100+"%");
            System.out.println("句长是大于10个词的比例  MoreThanTenWordSentenceRatio for ID(" + instance.id + "): " + values.get("MoreThanNineWordSentenceRatio")*100+"%");
            System.out.println("句长是大于12个词的比例 MoreThanTwelveWordSentenceCount for ID("+instance.id+"):"+values.get("moreThanTwelveWordSentenceCount"));
            System.out.println("句长是大于10个词的比例  MoreThanTwelveWordSentenceCount for ID(" + instance.id + "): " + values.get("MoreThanTwelveWordSentenceCount")*100+"%");
        }
        return values;
    }

    /**
     * 句长评分
     * sentenceLengthScore
     * @param instance
     * @return
     */
    @Override
    public HashMap<String, Double> normalizeScore(CNEssayInstance instance) {
        HashMap<String,Double> scores=new HashMap<>();
        HashMap<String,Double> results=getFeatureScores(instance);
        //原始数据
        double numSentence=results.get("Num_Sentences");
        double averageSentenceLength=results.get("AverageSentenceLength");
        double sixWordRatio=results.get("MoreThanSixWordSentenceRatio");
        double eightWordRatio=results.get("MoreThanEightWordSentenceRatio");
        double tenWordRatio=results.get("MoreThanTenWordSentenceRatio");
        double twelveWordRatio=results.get("MoreThanTenWordSentenceRatio");

        //得分-百分制
        double numSentenceScore=((numSentence-NUM_SENTENCE_MIN)/(NUM_SENTENCE_MAX-NUM_SENTENCE_MIN))*100;
        double averageSentenceLengthScore=((averageSentenceLength-AVERAGE_LENGTH_MIN)/(AVERAGE_LENGTH_MAX-AVERAGE_LENGTH_MIN))*100;
        double sixWordRatioScore=((sixWordRatio-SIX_LENGTH_MIN)/(SIX_LENGTH_MAX-SIX_LENGTH_MIN))*100;
        double eightWordRatioScore=((eightWordRatio-EIGHT_LENGTH_MIN)/(EIGHT_LENGHT_MAX-EIGHT_LENGTH_MIN))*100;
        double tenWordRatioScore=((tenWordRatio-TEN_LENGHT_MIN)/(TEN_LENGHT_MAX-TEN_LENGHT_MIN))*100;
        double twelveWordRatioScore=((twelveWordRatio-TWELVE_LENGTH_MIN)/(TWELVE_LENGHT_MAX-TWELVE_LENGTH_MIN))*100;

        //句长综合得分
        double sentenceLenghtScore=numSentenceScore*NUM_SENTENCE_WEIGHT+averageSentenceLengthScore*AVERAGE_LENGTH_WEIGHT+
                                    sixWordRatioScore*SIX_LENGTH_WEIGHT+eightWordRatioScore*EIGHT_LENGTH_WEIGHT+
                                    tenWordRatioScore*TEN_LENGTH_WEIGHT+twelveWordRatioScore*TWELVE_LENGTH_WEIGHT;
        scores.put("sentenceLengthScore",sentenceLenghtScore);
        return scores;
    }
}
