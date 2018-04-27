package org.xm.judger.features.chinese;

import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.util.DoubleUtil;
import org.xm.xmnlp.dic.DicReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Stop word num / all word num
 * 停用词占比
 * @author xuming
 */
public class CNStopWordRatioFeature implements CNFeatures {
    HashSet<String> stopwords;
    private static final String PATH = Config.STOP_WORDS_PATH;
    private static final Double STOP_MAX_GRADE= 0.0;
    private static final Double STOP_MIN_GRADE=0.0;
    public CNStopWordRatioFeature() throws IOException {
        this(PATH);
    }

    public CNStopWordRatioFeature(String stopwordPath) throws IOException {
        stopwords = new HashSet<>();
        BufferedReader br = DicReader.getReader(stopwordPath);
        String line;
        while ((line = br.readLine()) != null) {
            line.trim();
            if (line.length() == 0 || line.charAt(0) == '#')
                continue;
            stopwords.add(line.toLowerCase());
        }
        br.close();
    }

    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        HashMap<String, Double> result = new HashMap<>();
        int numStopwords = 0;
        int numWords = 0;
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    numWords++;
                    if (stopwords.contains(token.toLowerCase()))
                        numStopwords++;
                }
            }
        }
        result.put("stopword_ratio", DoubleUtil.stayTwoDec(numStopwords / (double) numWords));
        System.out.println("停用词比例 stopword ratio :"+result.get("stopword_ratio")*100+"%");
        return result;
    }

    @Override
    public HashMap<String, Double> normalizeScore(CNEssayInstance instance) {
        //todo 停用词得分
        HashMap<String,Double> scores=new HashMap<>();
        HashMap<String, Double> results=getFeatureScores(instance);
        double value=results.get("stopword_ratio");
        double score=((value-STOP_MIN_GRADE)/(STOP_MAX_GRADE-STOP_MIN_GRADE))*100;
        scores.put("stopword_ratio",score);
        return scores;
    }
}
