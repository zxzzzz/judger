package org.xm.judger.features.chinese;


import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.util.DoubleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Compute the percentage of tokens match the input word or regex.
 * token匹配输入词或正则表达式的百分率
 * @author xuming
 */
public class CNPercentMatchesFeature implements CNFeatures {
    private Pattern pattern = null;

    public CNPercentMatchesFeature(String word) {
        this(word, false);
    }

    public CNPercentMatchesFeature(String regex, boolean isRegex) {
        if (isRegex)
            this.pattern = Pattern.compile(regex);
        this.pattern = Pattern.compile(Pattern.quote(regex));
    }

    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        HashMap<String, Double> result = new HashMap<>();
        int numWords = 0;
        int matches = 0;
        //todo 优化点：分词可以在评分之前统一进行
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    if (pattern.matcher(token).matches()) {
                        //匹配输入的词
                        matches++;
                    }
                    numWords++;
                }
            }
        }
        result.put("PercentMatches_" + pattern, DoubleUtil.stayTwoDec(new Double(matches / (double) numWords)));
        if (Config.DEBUG)
            System.out.println("单词匹配率  Percent matches(匹配模式：" + pattern + ") for ID(" + instance.id + "): 匹配率："
                    +  DoubleUtil.stayTwoDec(new Double(matches / (double) numWords))*100+"%");
        return result;
    }
}
