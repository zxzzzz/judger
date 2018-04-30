package org.xm.judger.analyzer.Impl;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.openapi.Operation;
import com.hankcs.hanlp.openapi.impl.OperationImpl;
import org.xm.judger.analyzer.TextTendencyAnalyzer;

/**
 * 文本倾向性分析impl
 *
 * @author zhouxuan
 * @create 2018-04-29 下午1:18
 **/
public class TextTendencyAnalyzerImpl implements TextTendencyAnalyzer {
    Operation operation=new OperationImpl();
    @Override
    public String sentimentPos(String text) {
        String pos=operation.sentimentAnalysis(text);
        return pos;
    }

    @Override
    public String themeAnalyzer(String text) {
        String theme =operation.themeClassified(text);
        return theme;
    }
}
