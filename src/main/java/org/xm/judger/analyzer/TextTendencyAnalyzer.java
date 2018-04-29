package org.xm.judger.analyzer;

/**
 * 文本倾向性分析
 *  1.情感倾向
 *  2.主题倾向
 *  3.文章分类
 * @author zhouxuan
 * @create 2018-04-29 下午1:17
 **/
public interface TextTendencyAnalyzer {

    /**
     * 情感倾向：正/负
     * @param text
     * @return
     */
    public String sentimentPos(String text);

    /**
     * 主题倾向
     * @param text
     * @return
     */
    public String themeAnalyzer(String text);

}
