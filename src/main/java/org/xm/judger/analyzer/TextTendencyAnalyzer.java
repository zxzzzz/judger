package org.xm.judger.analyzer;

import com.hankcs.hanlp.openapi.Operation;
import com.hankcs.hanlp.openapi.impl.OperationImpl;
import org.xm.judger.domain.CNEssayInstance;

import java.util.HashMap;

/**
 * 文本倾向性分析
 *  1.情感倾向
 *  2.主题倾向
 *  3.文章分类
 * @author zhouxuan
 * @create 2018-04-29 下午1:17
 **/
public class TextTendencyAnalyzer {

    /**
     *正 负情感指数
     */
    private static final double POSITIVE_RANGE =1;
    private static final double NEGITIVE_RANGE =0.8;

    Operation operation=new OperationImpl();

    /**
     * 情感倾向：正/负
     * @param instance 中文实例
     * @return
     */
    public String sentimentPos(CNEssayInstance instance){
        String text =instance.essay;
        String pos=operation.sentimentAnalysis(text);
        return pos;
    }

    /**
     * 主题倾向
     * @param instance 中文实例
     * @return
     */
    public String themeAnalyzer(CNEssayInstance instance){
        String text =instance.essay;
        String theme =operation.themeClassified(text);
        return theme;
    }

    /**
     * 文本倾向性特征分数
     * @return
     */
    public CNEssayInstance  getTextTendencyScore(CNEssayInstance instance){

        String sentimentPos =instance.getSentimentPos();

        String classied =instance.getThemeClassified();



        double textTendencyScore =1;
        //正负向得分不同
        if (sentimentPos.equals("正向")){
            textTendencyScore = POSITIVE_RANGE*(1+Math.random()*100);

        }else{
            textTendencyScore =NEGITIVE_RANGE*(1+Math.random()*100);
        }

        instance.getNomalizeScores().put("textTendencyScore",textTendencyScore);

        return instance;
    }

}
