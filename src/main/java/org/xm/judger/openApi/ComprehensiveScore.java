package org.xm.judger.openApi;

import org.xm.judger.domain.CNEssayInstance;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 综合得分
 * @author zhouxuan
 *
 */
public interface ComprehensiveScore {



    /**
     * 计算综合得分：形式：维度名称-得分
     * @param  cnEssayInstances 主题 & 文本
     * @return
     */
    public HashMap<String ,Double> getComprehensiveScore(ArrayList<CNEssayInstance> cnEssayInstances);

    /**
     * 计算评分细则
     * @param cnEssayInstances 主题 & 文本
     */
    public HashMap<String ,Double> getDetailScore(ArrayList<CNEssayInstance> cnEssayInstances);

    /**
     * 自动评分
     * @param themePath 主题路径
     * @param textPath  文章路径
     * @return
     */
    public CNEssayInstance automaticScoring(String themePath,String textPath);
}
