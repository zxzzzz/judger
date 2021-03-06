package org.xm.judger.openApi.impl;

import org.xm.judger.analyzer.FeatureHandler;
import org.xm.judger.analyzer.TextTendencyAnalyzer;
import org.xm.judger.analyzer.ThemeAnalyzer;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.features.chinese.CNFeatures;
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

        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        ArrayList<CNEssayInstance> allInstances = new ArrayList<>();
        try {
            CNEssayInstance themeInstances = parser.loadText(themePath);
            CNEssayInstance textInstances = parser.loadText(textPath);
            allInstances.add(themeInstances);
            allInstances.add(textInstances);

        } catch (IOException e) {
            System.out.println("加载文章数据失败！" + e);
            System.exit(1);
        }
        return allInstances;
    }

    @Override
    public HashMap<String, Double> getComprehensiveScore(ArrayList<CNEssayInstance> cnEssayInstances) {




        return null;
    }

    @Override
    public HashMap<String, Double> getDetailScore(ArrayList<CNEssayInstance> cnEssayInstances) {


        return null;
    }

    /**
     * 计算最终分数
     * @param essayInstance
     * @return
     */
    private CNEssayInstance getAllScore(CNEssayInstance essayInstance){
        HashMap<String,Double> scores=essayInstance.getNomalizeScores();
        double structure =scores.get("comprehensiveScore");
        double textTendency =scores.get("textTendencyScore");
        double themeScore =scores.get("themeScore");
        double allScore =structure*Config.STRUCTURE_WEIGHT+textTendency*Config.TEXT_TENDENCY_WEIGHT+themeScore*Config.THEME_WIGHT;
        essayInstance.getNomalizeScores().put("finalScore",allScore);
        return essayInstance;

    }

    @Override
    public CNEssayInstance automaticScoring(String themePath, String textPath) {
        //1.转换
        CNEssayInstanceParser parser=new CNEssayInstanceParser();
        CNEssayInstance theme =null;
        CNEssayInstance text =null;
        try {


            theme= parser.loadText(themePath);
            text =parser.loadText(textPath);

        }catch (IOException e){
            System.out.println(e);

        }

        ArrayList<CNEssayInstance> cnEssayInstances =new ArrayList<>();
        cnEssayInstances.add(text);

        //篇章特征评分

        ArrayList<CNEssayInstance> processText =FeatureHandler.getFeatures(cnEssayInstances);

        //文章
        CNEssayInstance cnText =processText.get(0);

        //主题评分
        ThemeAnalyzer themeAnalyzer =new ThemeAnalyzer();

        //关键词
        List<String > keyWords=themeAnalyzer.getKeyWords(cnText,KEY_WORD_SIZE);

        cnText.setKeyWords(keyWords);

        //摘要

        List<String > summarys =themeAnalyzer.getSummary(cnText,SUMMARY_SIZE);

        cnText.setSummarys(summarys);

        //主题评分

        //主题相似度
        double similarity =themeAnalyzer.themeSimilarity(theme,cnText);

        cnText.setSimilary(similarity);

        //文本倾向性
        TextTendencyAnalyzer textTendencyAnalyzer=new TextTendencyAnalyzer();
        //文章分类

        String category = textTendencyAnalyzer.themeAnalyzer(cnText);
        String sentimentPos=textTendencyAnalyzer.sentimentPos(cnText);
        cnText.setSentimentPos(sentimentPos);
        cnText.setThemeClassified(category);
        textTendencyAnalyzer.getTextTendencyScore(cnText);
        themeAnalyzer.getThemeScore(cnText);
        getAllScore(cnText);
        System.out.println("篇章结构得分："+cnText.getNomalizeScores().get("comprehensiveScore"));
        System.out.println("文本倾向性得分："+cnText.getNomalizeScores().get("textTendencyScore"));
        System.out.println("主题得分："+cnText.getNomalizeScores().get("themeScore"));
        System.out.println("最终得分:"+cnText.getNomalizeScores().get("finalScore"));

        return cnText;
    }
}
