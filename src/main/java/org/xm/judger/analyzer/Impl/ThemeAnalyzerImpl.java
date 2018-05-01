package org.xm.judger.analyzer.Impl;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.openapi.Operation;
import com.hankcs.hanlp.openapi.impl.OperationImpl;
import lombok.Data;
import org.xm.judger.analyzer.ThemeAnalyzer;
import org.xm.judger.domain.CNEssayInstance;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;


@Data
public class ThemeAnalyzerImpl implements ThemeAnalyzer {

    Operation operation=new OperationImpl();

    @Deprecated
    public HashMap<Integer,Double> similary=null;


    @Override
    public List<String > getKeyWords(CNEssayInstance essayInstance,int size){
        String content=essayInstance.essay;
        List<String> keywords= HanLP.extractKeyword(content,size);
        return keywords;
    }


    @Override
    public List<String>  getSummary(CNEssayInstance essayInstance,int size){
        List<String>summarys=HanLP.extractSummary(essayInstance.essay,size);
        return summarys;
    }



    @Override
    public double themeSimilarity(CNEssayInstance theme,CNEssayInstance text){
        if (!useTheme){
            return 0;
        }
        double similarity =operation.textSimilarity(theme.essay,text.essay);

        return similarity;
    }



}
