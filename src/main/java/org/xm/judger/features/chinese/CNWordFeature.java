package org.xm.judger.features.chinese;

import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.domain.Config;
import org.xm.judger.util.DoubleUtil;
import org.xm.xmnlp.corpus.tag.Nature;
import org.xm.xmnlp.dictionary.CoreDictionary;
import org.xm.xmnlp.dictionary.CustomDictionary;
import org.xm.xmnlp.util.LexiconUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 未登录词（OOV）占比、错误词（obvious typos）占比
 * 类符形符比（TTR，type/token ratio）：可以反映文本中词汇的丰富程度
 * 动词的类符形符比、副词的类符形符比、介词类数以及代词数量
 * <p>
 * 类符形符比：一个语料库有100万词，指的是100万tokens，即100万词次。但这100万词次的语料库中，或许只用到了5万个单词。
 * 这5万个词，就是types，词种。
 * 
 * @author xuming
 */
public class CNWordFeature implements CNFeatures {
    // add punctuation symbols
    String[] punctuation = new String[]{",", "，", ".", "。", "?", "？", "-", "!", "！", "'", "\"", "(", ")", "（", "）",
            "$", ":", "：", ";", "；", "“", "”", "《", "》"};
    //todo word feature 范围/特征项权重/综合权重待分析
    //WordFeature的权重
    public static final double WORDFEATURE_WEIGHT=0.3;
    //特征项范围
    private static final double UNREGISTER_MIN=0;
    private static final double UNREGISTER_MAX=0.2;
    private static final double ERRORWORD_MIN=0;
    private static final double ERRORWORD_MAX=0.2;
    private static final double TTR_MIN=0;
    private static final double TTR_MAX=0.2;
    private static final double VERBTTR_MIN=0;
    private static final double VERBTTR_MAX=0.05;
    private static final double ADTTR_MIN=0;
    private static final double ADTTR_MAX=0.01;
    private static final double NUMPRE_MIN=0;
    private static final double NUMPRE_MAX=30;
    private static final double NUMPRO_MIN=0;
    private static final double NUMPRO_MAX=30;
    private static final double NUMCHARS_MIN=100;
    private static final double NUMCHARS_MAX=2000;
    private static final double NUMWORD_MIN=20;
    private static final double NUMWORD_MAX=1000;

    //特征项权重
    private static final double UNREGISTER_WEIGHT=0.1;
    private static final double ERRORWORD_WEIGHT=0.1;
    private static final double TTR_WEIGHT=0.1;
    private static final double VERBTTR_WEIGHT=0.05;
    private static final double ADTTR_WEIGHT=0.05;
    private static final double NUMPRE_WEIGHT=0.1;
    private static final double NUMPRO_WEIGHT=0.1;
    private static final double NUMCHARS_WEIGHT=0.2;
    private static final double NUMWORD_WEIGHT=0.2;

    @Override
    public HashMap<String, Double> getFeatureScores(CNEssayInstance instance) {
        HashMap<String, Double> result = new HashMap<>();
        int numChars = 0; // 总字数
        int numWords = 0; // 总词语数
        int matches = 0;  //词典中可匹配的单词
        int numTypos = 0; //明显错误词
        int wordTotalSize = 0; // 分词词典的词语总数：核心词典词语数+用户自定义词典词语数
        int numVerb = 0; // 动词
        int numAdverb = 0; // 副词
        int numPrePosition = 0; // 介词
        int numPronoun = 0; // 代词
        if (CustomDictionary.dat.size() != 0 || CoreDictionary.trie.size() != 0)
            wordTotalSize += CustomDictionary.dat.size() + CoreDictionary.trie.size();
        else wordTotalSize = 153115;
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    numWords++;
                    numChars += token.length();
                    CoreDictionary.Attribute attr = LexiconUtil.getAttribute(token.toLowerCase());
                    if (attr != null) {
                        matches++;
                        if (attr.hasNature(Nature.v)) numVerb++;
                        if (attr.hasNature(Nature.d)) numAdverb++;
                        if (attr.hasNature(Nature.p)) numPrePosition++;
                        if (attr.hasNature(Nature.r)) numPronoun++;
                    } else
                        numTypos++;
                }
            }
        }

        result.put("OOVs",DoubleUtil.stayTwoDec( new Double(1 - matches / (double) numWords))); // 未登录词 占比
        result.put("obvious_typos",DoubleUtil.stayTwoDec( new Double(numTypos / (double) numWords))); // 明显错误词占比
        result.put("TTR", new Double(matches / (double) wordTotalSize)); // 类符形符比
        result.put("Verb_TTR", new Double(numVerb / (double) wordTotalSize)); // 动词的类符形符比
        result.put("Adverb_TTR", new Double(numAdverb / (double) wordTotalSize)); // 副词的类符形符比
        result.put("Num_PrePosition", new Double(numPrePosition)); // 介词数
        result.put("Num_Pronoun", new Double(numPronoun)); // 代词数
        result.put("Num_Chars", new Double(numChars)); // 总字数
        result.put("Num_Words", new Double(numWords)); // 总词数
        if (Config.DEBUG) {
            System.out.println("未登录词        OOVs for ID(" + instance.id + "): " + result.get("OOVs")*100+"%");
            System.out.println("明显错误词占比   Obvious typos  for ID(" + instance.id + "): " + result.get("obvious_typos")*100+"%");
            System.out.println("类符形符比      TTR for ID(" + instance.id + "): " + result.get("TTR")*100+"%");
            System.out.println("动词的类符形符比 Verb_TTR for ID(" + instance.id + "): " + result.get("Verb_TTR")*100+"%");
            System.out.println("副词的类符形符比 Adverb_TTR for ID(" + instance.id + "): " + result.get("Adverb_TTR")*100+"%");
            System.out.println("介词数          Num_PrePosition for ID(" + instance.id + "): " + result.get("Num_PrePosition"));
            System.out.println("代词数          Num_Pronoun for ID(" + instance.id + "): " + result.get("Num_Pronoun"));
            System.out.println("总字数          Num_Chars for ID(" + instance.id + "): " + result.get("Num_Chars"));
        }
        return result;
    }

    /**
     * 评分准则
     * wordScore
     * @param results 中文实例
     * @return
     */
    @Override
    public HashMap<String, Double> normalizeScore(CNEssayInstance results) {
        HashMap<String,Double> scores=new HashMap<>();
        //原始特征
        double unRegister=results.getFeature("OOVs");
        double errorWord=results.getFeature("obvious_typos");
        double ttr=results.getFeature("TTR");
        double verbTtr=results.getFeature("Verb_TTR");
        double adTtr=results.getFeature("Adverb_TTR");
        double numPre=results.getFeature("Num_PrePosition");
        double numPro=results.getFeature("Num_Pronoun");
        double numChars=results.getFeature("Num_Chars");
        double numWord=results.getFeature("Num_Words");

        //特征分数
        double unRegisterScore=((unRegister-UNREGISTER_MIN)/(UNREGISTER_MAX-UNREGISTER_MIN))*100;
        double errorWordScore=((errorWord-ERRORWORD_MIN)/(ERRORWORD_MAX-ERRORWORD_MIN))*100;
        double ttrScore=((ttr-TTR_MIN)/(TTR_MAX-TTR_MIN))*100;
        double verbTtrScore=((verbTtr-VERBTTR_MIN)/(VERBTTR_MAX-VERBTTR_MIN))*100;
        double adTtrScore=((adTtr-ADTTR_MIN)/(ADTTR_MAX-ADTTR_MIN))*100;
        double numPreScore=((numPre-NUMPRE_MIN)/(NUMPRE_MAX-NUMPRE_MIN))*100;
        double numProScore=((numPro-NUMPRO_MIN)/(NUMPRO_MAX-NUMPRO_MIN))*100;
        double numCharsScore=((numChars-NUMCHARS_MIN)/(NUMCHARS_MAX-NUMCHARS_MIN))*100;
        double numWordScore=((numWord-NUMWORD_MIN)/(NUMWORD_MAX-NUMWORD_MIN))*100;

        //综合分数
        double wordScore=unRegisterScore*UNREGISTER_WEIGHT+errorWordScore*ERRORWORD_WEIGHT+ttrScore*TTR_WEIGHT+
                verbTtrScore*TTR_WEIGHT+verbTtrScore*VERBTTR_WEIGHT+adTtrScore*ADTTR_WEIGHT+numPreScore*NUMPRE_WEIGHT+
                numProScore*NUMPRO_WEIGHT+numCharsScore*NUMCHARS_WEIGHT+numWordScore*NUMWORD_WEIGHT;
        wordScore =DoubleUtil.processScore(wordScore);
        scores.put("wordScore",wordScore);
        System.out.println("word-score："+wordScore);
        return scores;
    }
}

