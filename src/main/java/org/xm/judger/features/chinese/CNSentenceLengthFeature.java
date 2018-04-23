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

    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        int numSentences = 0;//句子数
        int sumCount = 0;//总句长
        int moreThanSevenWordSentenceCount = 0;//句长是大于7个词的句数
        int moreThanEightWordSentenceCount = 0;//句长是大于8个词的句数
        int moreThanNineWordSentenceCount = 0;//句长是大于9个词的句数
        // compute the word length of longest essay
        ArrayList<ArrayList<ArrayList<String>>> pargraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> pargraph : pargraphs) {
            for (ArrayList<String> sentence : pargraph) {
                numSentences++;
                int size = sentence.size();
                sumCount += size;
                if (size > 7) moreThanSevenWordSentenceCount++;
                if (size > 8) moreThanEightWordSentenceCount++;
                if (size > 9) moreThanNineWordSentenceCount++;
            }
        }

        HashMap<String, Double> values = new HashMap<>();
        values.put("Num_Sentences", new Double(numSentences));
        values.put("AverageSentenceLength",DoubleUtil.stayTwoDec( new Double(sumCount / (double) numSentences)));
        values.put("MoreThanSevenWordSentenceCount", new Double(moreThanSevenWordSentenceCount));
        values.put("MoreThanSevenWordSentenceRatio", DoubleUtil.stayTwoDec(new Double(moreThanSevenWordSentenceCount / (double) numSentences)));
        values.put("MoreThanEightWordSentenceCount", new Double(moreThanEightWordSentenceCount));
        values.put("MoreThanEightWordSentenceRatio", DoubleUtil.stayTwoDec(new Double(moreThanEightWordSentenceCount / (double) numSentences)));
        values.put("MoreThanNineWordSentenceCount", new Double(moreThanNineWordSentenceCount));
        values.put("MoreThanNineWordSentenceRatio", DoubleUtil.stayTwoDec(new Double(moreThanNineWordSentenceCount / (double) numSentences)));

        if (Config.DEBUG) {
            System.out.println("句子数  numSentences for ID(" + instance.id + "): " + values.get("Num_Sentences"));
            System.out.println("平均句长  AverageSentenceLength for ID(" + instance.id + "): " + values.get("AverageSentenceLength"));
            System.out.println("句长是大于7个词的句数  MoreThanSevenWordSentenceCount for ID(" + instance.id + "): " + values.get("MoreThanSevenWordSentenceCount"));
            System.out.println("句长是大于8个词的句数  MoreThanEightWordSentenceCount for ID(" + instance.id + "): " + values.get("MoreThanEightWordSentenceCount"));
            System.out.println("句长是大于9个词的句数  MoreThanNineWordSentenceCount for ID(" + instance.id + "): " + values.get("MoreThanNineWordSentenceCount"));
            System.out.println("句长是大于7个词的比例  MoreThanSevenWordSentenceRatio for ID(" + instance.id + "): " + values.get("MoreThanSevenWordSentenceRatio")*100+"%");
            System.out.println("句长是大于8个词的比例  MoreThanEightWordSentenceRatio for ID(" + instance.id + "): " + values.get("MoreThanEightWordSentenceRatio")*100+"%");
            System.out.println("句长是大于9个词的比例  MoreThanNineWordSentenceRatio for ID(" + instance.id + "): " + values.get("MoreThanNineWordSentenceRatio")*100+"%");
        }
        return values;
    }
}
