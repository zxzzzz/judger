package org.xm.judger.openApi.impl;

import org.xm.judger.Judger;
import org.xm.judger.analyzer.FeatureHandler;
import org.xm.judger.analyzer.Impl.TextTendencyAnalyzerImpl;
import org.xm.judger.analyzer.Impl.ThemeAnalyzerImpl;
import org.xm.judger.analyzer.TextTendencyAnalyzer;
import org.xm.judger.analyzer.ThemeAnalyzer;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.openApi.ComprehensiveScore;
import org.xm.judger.parser.CNEssayInstanceParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 综合得分实现
 *
 * @author zhouxuan
 * @create 2018-04-30 上午10:19
 **/
public class ComprehensiveScoreImpl implements ComprehensiveScore {
    /**
     * 关键词集大小
     */
    private static final int KEY_WORD_SIZE=10;
    /**
     * 摘要句子数
     */
    private static final int SUMMARY_SIZE=4;
    //todo 只有文章需要实例化

    /**
     * 文章与主题转换为模型
     * @param themePath 主题路径
     * @param textPath  文章路径
     * @return
     */
    public ArrayList<CNEssayInstance> convertToCN(String themePath, String textPath) {

        CNEssayInstanceParser parser=new CNEssayInstanceParser();
        ArrayList<CNEssayInstance> allInstances =new ArrayList<>();
        try {
            ArrayList<CNEssayInstance> themeInstances =parser.load(themePath);
            ArrayList<CNEssayInstance> textInstances =parser.load(textPath);
            allInstances.add(themeInstances.get(0));
            allInstances.add(textInstances.get(0));

        }catch (IOException e){
            System.out.println("加载文章数据失败！"+e);
            System.exit(1);
        }
        return allInstances;
    }

    //        CNEssayInstanceParser parser = new CNEssayInstanceParser();
//        // Parse the input training file
//        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);
//        Judger.setCninstances(instances);
//
//        // Get feature Scores for each instance
//        ArrayList<CNEssayInstance> instancesFeatures = FeatureHandler.getFeatures(instances);
//
//        // Now we have all the instances and features
//        // use any Machine Learning Tools (such as Weka)
//        FeatureHandler.saveFeatures(instancesFeatures, saveTrainFeaturesPath);


    @Override
    public HashMap<String, Double> getComprehensiveScore(ArrayList<CNEssayInstance> cnEssayInstances) {




        return null;
    }

    @Override
    public HashMap<String, Double> getDetailScore(ArrayList<CNEssayInstance> cnEssayInstances) {


        return null;
    }

    @Override
    public CNEssayInstance automaticScoring(String themePath, String textPath) {
        //1.转换
        ArrayList<CNEssayInstance> instances = convertToCN(themePath,textPath);


        //待评分文本
        ArrayList<CNEssayInstance> text=new ArrayList<>();

        text.add( instances.get(1));


        //篇章特征评分

        ArrayList<CNEssayInstance> processText =FeatureHandler.getFeatures(text);

        //文章
        CNEssayInstance cnText =processText.get(0);

        //主题评分
        ThemeAnalyzer themeAnalyzer =new ThemeAnalyzerImpl();

        //关键词
        List<String > keyWords=themeAnalyzer.getKeyWords(processText.get(0),KEY_WORD_SIZE);

        cnText.setKeyWords(keyWords);

        //摘要

        List<String > summarys =themeAnalyzer.getSummary(cnText,SUMMARY_SIZE);
        cnText.setSummarys(summarys);
        //主题评分
        //主题相似度
        double similarity =themeAnalyzer.themeSimilarity(instances);

        cnText.setSimilary(similarity);

        //文本倾向性
        TextTendencyAnalyzer textTendencyAnalyzer=new TextTendencyAnalyzerImpl();
        //文章分类

        String category = textTendencyAnalyzer.themeAnalyzer(cnText.essay);
        String sentimentPos=textTendencyAnalyzer.sentimentPos(cnText.essay);
        cnText.setSentimentPos(sentimentPos);
        cnText.setThemeClassified(category);


        return cnText;
    }
}
