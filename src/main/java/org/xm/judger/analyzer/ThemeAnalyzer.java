package org.xm.judger.analyzer;

import com.hankcs.hanlp.HanLP;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.xmnlp.Xmnlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class ThemeAnalyzer {

    //得到文章的关键字
    //得到文章的摘要
    //主题相似度
    //文本相似度
    private static final int KEYWORDS_SIZE=10;
    public HashMap<Integer,Double> similary=null;
    /**
     * 解析文本关键字
     * @return
     */
    public List<String > getKeyWords(CNEssayInstance essayInstance,int size){
        String content=essayInstance.essay;
        List<String> keywords= HanLP.extractKeyword(content,size);
        return keywords;
    }

    /**
     * 解析文本摘要
     * @param essayInstance
     * @return
     */
    public List<String>  getSummary(CNEssayInstance essayInstance,int size){
        List<String>summarys=HanLP.extractSummary(essayInstance.essay,size);
        return summarys;
    }

    /**
     * 主题相似度  TF-IDF算法  todo  TF-IDF算法
     */
    public void textSimilarity(ArrayList<CNEssayInstance> cnEssayInstances){
        ArrayList<List<String>> keywords=null;
        HashSet<String> allKeyword =null;
        for (CNEssayInstance instance:cnEssayInstances){
            List<String> key=new ArrayList<>();
            key=getKeyWords(instance,KEYWORDS_SIZE);
            keywords.add(key);
            allKeyword.addAll(key);
        }
        //词向量数组
        Integer [][]vertext=new Integer[cnEssayInstances.size()+1][allKeyword.size()+1];
        for (int i=0;i<allKeyword.size();i++){
            for (String s:keywords.get(i)){
                ////
            }
        }


    }


}
