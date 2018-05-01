package org.xm.judger;

import org.junit.Test;
import org.xm.judger.analyzer.FeatureHandler;
import org.xm.judger.domain.CNEssayInstance;
import org.xm.judger.parser.CNEssayInstanceParser;
import org.xm.xmnlp.Xmnlp;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author xuming
 */
public class AnalysisEssayTest {
    @Test
    public void testCN() throws IOException {
        String trainSetPath = "data/jinyong";
        String saveTrainFeaturesPath = "data/jinyong_training_result.arff";

        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        // Parse the input training file
        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);
        Judger.setCninstances(instances);

        // Get feature Scores for each instance
        ArrayList<CNEssayInstance> instancesFeatures = FeatureHandler.getFeatures(instances);

        // Now we have all the instances and features
        // use any Machine Learning Tools (such as Weka)
        FeatureHandler.saveFeatures(instancesFeatures, saveTrainFeaturesPath);
    }

    @Test
    public void testCN_law() throws IOException {
        String trainSetPath = "data/novels";
        String saveTrainFeaturesPath = "data/law.arff";

        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        // Parse the input training file
        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);
        Judger.setCninstances(instances);

        ArrayList<CNEssayInstance> instancesFeatures = FeatureHandler.getFeatures(instances);
        FeatureHandler.saveFeatures(instancesFeatures, saveTrainFeaturesPath);
    }
    @Test
    public void test_Length() throws IOException {
        String trainSetPath = "data/law";
        String saveTrainFeaturesPath = "data/law.arff";

        CNEssayInstanceParser parser = new CNEssayInstanceParser();
        // Parse the input training file
        ArrayList<CNEssayInstance> instances = parser.load(trainSetPath);

        for (CNEssayInstance instance:instances){
            System.out.println(Xmnlp.segment(instance.essay));
        }
    }

}
