package org.xm.judger.domain;

/**
 * 路径配置
 * @author xuming
 */
public class   Config {

    public static final boolean DEBUG = true;
    /**
     * 英文停用词路径
     */
    public static final String STOP_WORDS_PATH = "stopwords.txt";
    /**
     * 英文拼写检查词典路径
     */
    public static final String EN_SPELL_CHECKING_WORDS_PATH = "scowl-7.1/american_50.utf8";
    /**
     * 英文作文训练集路径
     */
    public static final String EN_TRAIN_SET_PATH = "Phase1/training_set_rel4.tsv";

    public static final String EN_TRAIN_TEST_SET_PATH = "Phase1/training_set1.test.tsv";
    /**
     * 中文作文训练集路径
     */
    public static final String CN_TRAIN_SET_PATH = "Phase1/cn_training_set_rel1.tsv";

}
