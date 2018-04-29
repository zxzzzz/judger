package org.xm.judger.analyzer;

import lombok.Data;
import org.xm.judger.domain.CNEssayInstance;

import java.util.ArrayList;
import java.util.List;


public interface ThemeAnalyzer {

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
    public List<String> getKeyWords(CNEssayInstance essayInstance, int size);

    /**
     * 解析文本摘要
     *
     * @param essayInstance  中文文章
     * @param size 摘要句数
     * @return
     */
    public List<String> getSummary(CNEssayInstance essayInstance, int size);

    /**
     * 主题相似度分析
     * @param cnEssayInstances 主题与文章
     * @return
     */
    public double themeSimilarity(ArrayList<CNEssayInstance> cnEssayInstances);

    }