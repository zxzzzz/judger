package org.xm.judger.domain;

import org.junit.Test;
import org.xm.judger.analyzer.ThemeAnalyzer;
import org.xm.judger.parser.CNEssayInstanceParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThemeTest {

    @Test
    public void test_getKeyWords() throws IOException {
        String trainSetPath = "data/law";
        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        // Parse the input training file
        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);
        ThemeAnalyzer themeAnalyzer=new ThemeAnalyzer();
        for (CNEssayInstance instance:instances){
            List<String> key=themeAnalyzer.getKeyWords(instance,5);
            List<String> summary=themeAnalyzer.getSummary(instance,3);
            for (String s : key) {
                System.out.print(s+" ");
            }
            System.out.println();
            for (String s:summary){
                System.out.println(s);
            }
            System.out.println();
        }
    }
    @Test
    public void test_getSummary() throws IOException{
        String trainSetPath = "data/law";
        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        // Parse the input training file
        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);
        ThemeAnalyzer themeAnalyzer=new ThemeAnalyzer();
        for (CNEssayInstance instance : instances) {
            System.out.println(themeAnalyzer.getSummary(instance,3));
        }
    }

}
