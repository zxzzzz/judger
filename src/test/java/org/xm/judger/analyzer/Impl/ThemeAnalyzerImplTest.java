package org.xm.judger.analyzer.Impl;

import org.junit.jupiter.api.Test;
import org.xm.judger.analyzer.ThemeAnalyzer;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.parser.CNEssayInstanceParser;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThemeAnalyzerImplTest {

    private static final String path1 ="data/law/law01.txt";
    private static final String path2 ="data/law/law02.txt";
    private ThemeAnalyzer themeAnalyzer=new ThemeAnalyzerImpl();
    private CNEssayInstanceParser parser =new CNEssayInstanceParser();
    @Test
    void getKeyWords() throws IOException {
        CNEssayInstance essay= parser.loadText(path1);
        List<String> keyWords =themeAnalyzer.getKeyWords(essay,5);
        keyWords.forEach(p -> System.out.println(p));
    }

    @Test
    void getSummary() throws IOException{
        CNEssayInstance essayInstance =parser.loadText(path1);
        List<String> summary =themeAnalyzer.getSummary(essayInstance,4);
        summary.forEach(p->System.out.println(p));

    }

    @Test
    void themeSimilarity() throws IOException{
        CNEssayInstance theme =parser.loadText(path1);
        CNEssayInstance text = parser.loadText(path2);
        System.out.println(themeAnalyzer.themeSimilarity(theme,text));

    }
}