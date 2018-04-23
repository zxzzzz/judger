package org.xm.judger.features.chinese;


import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.util.DoubleUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 词长特征
 * 包括：平均词长、词长是1的单词数、词长是2的单词数、词长是3的单词数、词长是4的单词数及占比
 *
 * @author xuming
 */
public class CNWordLengthFeature implements CNFeatures {

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
        return values;
    }
}
