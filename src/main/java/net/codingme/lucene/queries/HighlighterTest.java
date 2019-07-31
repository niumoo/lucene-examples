package net.codingme.lucene.queries;

import net.codingme.lucene.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.BooleanClause.Occur;
import org.junit.Test;


import java.io.IOException;
import java.io.StringReader;

/**
 * <p>
 * 高亮查询
 * 高亮查询可以在搜索的结果中标记出匹配的关键词，
 * 减少用户的查找主要信息的时间
 *
 * @Author niujinpeng
 * @Date 2018/7/1422:02
 */
public class HighlighterTest {

    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
        // 获取搜索对象
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        // 获取IK分词器
        IkAnalyzer6x ikAnalyzer6x = new IkAnalyzer6x();

        String field = "title";
        QueryParser queryParser = new QueryParser("title", ikAnalyzer6x);
        Query query = queryParser.parse("北大");
        System.out.println("查询Query：" + query.toString());
        TopDocs topDocs = indexSearch.search(query, 10);

        // 定义高亮
        QueryScorer queryScorer = new QueryScorer(query, field);
        // 定制高亮标签
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("【", "】");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, queryScorer);

        // 输出
        for (ScoreDoc sdoc : topDocs.scoreDocs) {
            Document document = indexSearch.doc(sdoc.doc);
            System.out.println("DocId：" + sdoc.doc);
            System.out.println("id：" + document.get("id"));
            System.out.println("title：" + document.get("title"));
            System.out.println("content：" + document.get("content"));
            System.out.println("文档评分：" + sdoc.score);
            System.out.println("----------------------------------");
            // 获取TokenStream
            TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearch.getIndexReader(), sdoc.doc, field, ikAnalyzer6x);
            SimpleSpanFragmenter fragmenter = new SimpleSpanFragmenter(queryScorer);
            highlighter.setTextFragmenter(fragmenter);

            String bestFragment = highlighter.getBestFragment(tokenStream, document.get(field));
            System.out.println("高亮片段：" + bestFragment);

        }

        LuceneQueryUtil.close();
    }

    /**
     * 使用Lucene单独取摘要测试
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     */
    @Test
    public void test() throws IOException, InvalidTokenOffsetsException {
        String text = "模糊逻辑(Fuzzy Logic)：模仿人脑的不确定性概念判断、推理思维方式，对于模型未知或不能确定的描述系统，以及强非线性、大滞后的控制对象，应用模糊集合和模糊规则进行推理，表达过渡性界限或定性知识经验，模拟人脑方式，实行模糊综合判断，推理解决常规方法难于对付的规则型模糊信息问题。模糊逻辑善于表达界限不清晰的定性知识与经验，它借助于隶属度函数概念，区分模糊集合，处理模糊关系，模拟人脑实施规则型推理，解决因“排中律”的逻辑破缺产生的种种不确定问题 。";
        String highLighter = getHighLighter(text, "", 50);
        System.out.println(highLighter);
    }

    /**
     * 获取高亮内容
     *
     * @param content     内容
     * @param keyword     关键词
     * @param summarySize 高亮文本长度
     * @return
     */
    public static String getHighLighter(String content, String keyword, int summarySize) throws IOException, InvalidTokenOffsetsException {
        Analyzer analyzer = new IkAnalyzer6x();

        // 查询标题中包含“思维”或者“模型”的数据信息
        TermQuery termQuery1 = new TermQuery(new Term("title", "思维"));
        TermQuery termQuery2 = new TermQuery(new Term("title", "模型"));
        // 构造逻辑关系
        BooleanClause bClause1 = new BooleanClause(termQuery1, Occur.SHOULD);
        BooleanClause bClause2 = new BooleanClause(termQuery2, Occur.SHOULD);
        // 创建Query对象
        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(bClause1).add(bClause2).build();
        QueryScorer queryScorer = new QueryScorer(booleanQuery);
        System.out.println(booleanQuery.toString());

        // 设置高亮文本长度
        SimpleFragmenter simpleFragmenter = new SimpleFragmenter(summarySize);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("【", "】");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, queryScorer);
        highlighter.setTextFragmenter(simpleFragmenter);

        TokenStream tokenStream = analyzer.tokenStream("", new StringReader(content));
        String hightContent = highlighter.getBestFragment(tokenStream, content);

        return hightContent;
    }



}






















