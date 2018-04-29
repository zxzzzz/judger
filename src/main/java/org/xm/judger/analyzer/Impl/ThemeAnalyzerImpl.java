package org.xm.judger.analyzer.Impl;

import com.hankcs.hanlp.HanLP;
import lombok.Data;
import org.xm.judger.analyzer.ThemeAnalyzer;
import org.xm.judger.domain.CNEssayInstance;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;


@Data
public class ThemeAnalyzerImpl implements ThemeAnalyzer {


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
    public double themeSimilarity(ArrayList<CNEssayInstance> cnEssayInstances){
        if (!useTheme){
            return 0;
        }
        //todo Hanlp分词 待打包
        return 0;
    }



}
