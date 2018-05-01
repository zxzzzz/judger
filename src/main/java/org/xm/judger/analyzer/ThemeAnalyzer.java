package org.xm.judger.analyzer;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.openapi.Operation;
import com.hankcs.hanlp.openapi.impl.OperationImpl;
import lombok.Data;
import org.jcp.xml.dsig.internal.dom.DOMUtils;
import org.xm.judger.domain.CNEssayInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * 主题分析，包括关键字抽取/生成摘要/主题相似度计算
 * @author zhouxuan
 *
 * */
public class ThemeAnalyzer {
    /**
     * 主题相似性得分权重
     */
    public static final double THEME_WEIGHT =0.4;
    private static double THEME_SIMI_MIN =0.05;
    private static double THEME_SIMI_MAX=0.6;
    /**
     * 分词器的基础服务
     */
    private Operation operation=new OperationImpl();

    /**
     * 是否开启主题相似度检测
     * 默认不开启
     */
     public static boolean useTheme=false;

    /**
     * 解析文本关键字
     *
     * @param essayInstance 中文实例
     * @param size 关键词词数
     * @return
     */
    public List<String> getKeyWords(CNEssayInstance essayInstance, int size){
        String content=essayInstance.essay;
        List<String> keywords= HanLP.extractKeyword(content,size);
        return keywords;
    }

    /**
     * 解析文本摘要
     *
     * @param essayInstance  中文文章
     * @param size 摘要句数
     * @return
     */
    public List<String> getSummary(CNEssayInstance essayInstance, int size){
        List<String>summarys=HanLP.extractSummary(essayInstance.essay,size);
        return summarys;
    }

    /**
     * 主题相似度分析
     * @return
     */
    public double themeSimilarity(CNEssayInstance theme,CNEssayInstance text){
        return operation.textSimilarity(theme.essay,text.essay);
    }

    /**
     * 主题相似度得分
     */
    public CNEssayInstance getThemeScore(CNEssayInstance essay){
        double themeSimilarity =essay.getSimilary();

        double themeScore =0;
        if (themeSimilarity!=0){
            themeScore =((themeSimilarity -THEME_SIMI_MIN)/(THEME_SIMI_MAX-THEME_SIMI_MIN))*100;
        }

        essay.getNomalizeScores().put("themeScore",themeScore);

        return essay;
    }

}