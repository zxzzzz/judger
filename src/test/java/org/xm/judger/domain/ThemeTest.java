package org.xm.judger.domain;

import org.junit.Test;
import org.xm.judger.analyzer.ThemeAnalyzer;
import org.xm.judger.parser.CNEssayInstanceParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThemeTest {

    /**
     * 关键词识别
     * @throws IOException
     */
    @Test
    public void test_getKeyWords() throws IOException {
        String trainSetPath = "data/law";
        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        // Parse the input training file
        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);
        ThemeAnalyzer themeAnalyzerImpl =new ThemeAnalyzer();
        for (CNEssayInstance instance:instances){
            List<String> key= themeAnalyzerImpl.getKeyWords(instance,5);
            List<String> summary= themeAnalyzerImpl.getSummary(instance,3);
            for (String s : key) {
                System.out.print(s+" ");
            }
            System.out.println();

        }
    }

    /**
     * 生成摘要
     * @throws IOException
     */
    @Test
    public void test_getSummary() throws IOException{
        String trainSetPath = "data/law";
        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        // Parse the input training file
        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);
        ThemeAnalyzer themeAnalyzerImpl =new ThemeAnalyzer();
        for (CNEssayInstance instance : instances) {
            List<String> summary= themeAnalyzerImpl.getSummary(instance,3);
            summary.forEach(p->System.out.println(p));


        }
    }

}
