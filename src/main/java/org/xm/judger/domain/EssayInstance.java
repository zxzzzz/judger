package org.xm.judger.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 文章
 *
 * @author xuming
 */
public abstract class EssayInstance {
    //存储所有在训练集中可用的字段
    public int id;
    public int set;
    public String essay;
    public String title;
    public String filename;
    public int rater1_domain1 = -1;
    public int rater2_domain1 = -1;
    public int rater3_domain1 = -1;
    public int domain1_score = -1;
    public int rater1_domain2 = -1;
    public int rater2_domain2 = -1;
    public int domain2_score = -1;

    public int rater1_trait1 = -1;
    public int rater1_trait2 = -1;
    public int rater1_trait3 = -1;
    public int rater1_trait4 = -1;
    public int rater1_trait5 = -1;
    public int rater1_trait6 = -1;

    public int rater2_trait1 = -1;
    public int rater2_trait2 = -1;
    public int rater2_trait3 = -1;
    public int rater2_trait4 = -1;
    public int rater2_trait5 = -1;
    public int rater2_trait6 = -1;

    public int rater3_trait1 = -1;
    public int rater3_trait2 = -1;
    public int rater3_trait3 = -1;
    public int rater3_trait4 = -1;
    public int rater3_trait5 = -1;
    public int rater3_trait6 = -1;

    ArrayList<ArrayList<ArrayList<String>>> cachedParse = null;

    HashMap<String, Double> features ;
    /**
     * 正规化评分结果
     */
    HashMap<String,Double> nomalizeScores =new HashMap<>();

    //关键词

    List<String > keyWords =new ArrayList<>();

    //摘要
    List<String> summarys =new ArrayList<>();

    //文本相似度
    double similary =0;

    //情极性

    String sentimentPos ="";
    //文本分类
    String themeClassified ="";


    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public List<String> getSummarys() {
        return summarys;
    }

    public void setSummarys(List<String> summarys) {
        this.summarys = summarys;
    }

    public double getSimilary() {
        return similary;
    }

    public void setSimilary(double similary) {
        this.similary = similary;
    }

    public String getSentimentPos() {
        return sentimentPos;
    }

    public void setSentimentPos(String sentimentPos) {
        this.sentimentPos = sentimentPos;
    }

    public String getThemeClassified() {
        return themeClassified;
    }

    public void setThemeClassified(String themeClassified) {
        this.themeClassified = themeClassified;
    }

    public EssayInstance() {
        this.features = new HashMap<>();
    }

    public void setFeature(HashMap<String, Double> featureScores) {
        for (String key : featureScores.keySet()) {
            if (features.containsKey(key))
                features.put(key.concat("1"), featureScores.get(key));
            else
                features.put(key, featureScores.get(key));
        }
    }

    public void setScore(HashMap<String ,Double> nomalize){
        for (String key:nomalize.keySet()) {
                nomalizeScores.put(key, nomalize.get(key));
        }
    }

    public HashMap<String, Double> getNomalizeScores() {
        return this.nomalizeScores;
    }

    public void setFeature(String feature, double value) {
        features.put(feature, value);
    }

    public HashMap<String, Double> getFeatures() {
        return this.features;
    }

    /**
     * 得到特征分数
     * @param feature
     * @return
     */
    public double getFeature(String feature) {
        if (feature.equals("grade"))
            return domain1_score;
        return features.get(feature);
    }

    /**
     * 罗列特证名称
     * @return
     */
    public List<String> listFeatures() {
        ArrayList<String> fList = new ArrayList<>(features.keySet());
        Collections.sort(fList);
        return fList;
    }

    /**
     * 解析到段落层级
     *
     * @return the parsed structure of the text at the paragraph, sentence, word levels
     */
    public abstract ArrayList<ArrayList<ArrayList<String>>> getParagraphs();

    /**
     * 解析到词语层级
     *
     * @return the parsed the paragraph to word levels
     */
    public static List<String> getTokens(EssayInstance instance) {
        List<String> tokens = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) paragraph.forEach(tokens::addAll);
        return tokens;
    }

    /**
     * toString
     *
     * @return shows ID and parsed contents
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ID(" + id + "): ");
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    sb.append(token);
                    sb.append(" ");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

