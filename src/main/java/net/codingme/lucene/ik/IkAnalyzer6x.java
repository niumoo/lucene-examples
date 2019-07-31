package net.codingme.lucene.ik;

import org.apache.lucene.analysis.Analyzer;

/**
 * <p>
 *
 * @author niujinpeng
 * @date 2018年6月21日上午9:58:50
 */
public class IkAnalyzer6x extends Analyzer {

    private boolean useSmart;

    public boolean useSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    /**
     * IK分词器Lucene analyzer接口实现类 ，默认细粒度切分
     */
    public IkAnalyzer6x() {
        this(false);
    }

    /**
     * IK分词器Lucene analyzer接口实现类，当为true的时候，分词器进行智能切分
     * @param useSmart
     */
    public IkAnalyzer6x(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    /**
     * 重写最新版的createComponents，重载analyzer接口，构造分词组件
     *
     * @param fileName
     * @return
     */
    @Override
    protected TokenStreamComponents createComponents(String fileName) {
        IkTokenizer6x ikAnalyzer6x = new IkTokenizer6x(this.useSmart);
        return new TokenStreamComponents(ikAnalyzer6x);
    }

}

	