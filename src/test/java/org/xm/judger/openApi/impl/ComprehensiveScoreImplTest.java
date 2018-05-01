package org.xm.judger.openApi.impl;

import org.junit.jupiter.api.Test;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.openApi.ComprehensiveScore;

import static org.junit.jupiter.api.Assertions.*;

class ComprehensiveScoreImplTest {

    ComprehensiveScore comprehensiveScore=new ComprehensiveScoreImpl();
    private static final String themePath ="data/law/law01.txt";
    private static final String textPath = "data/law/law02.txt";

    @Test
    void convertToCN() {
    }

    @Test
    void getComprehensiveScore() {

    }

    @Test
    void getDetailScore() {
    }

    @Test
    void automaticScoring(){
       CNEssayInstance cnEssayInstance= comprehensiveScore.automaticScoring(themePath,textPath);
       System.out.println("文本相似度："+cnEssayInstance.getSimilary());
       System.out.println("文本分类："+cnEssayInstance.getThemeClassified());
       System.out.println("情感极性:"+cnEssayInstance.getSentimentPos());
       System.out.println("摘要："+cnEssayInstance.getSummarys());
       System.out.println("关键词："+cnEssayInstance.getKeyWords());
       System.out.println("篇章结构评分："+cnEssayInstance.getNomalizeScores().get("comprehensiveScore"));
    }
}