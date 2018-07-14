package net.codingme.lucene.queries;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

/**
 * <p>
 * 模糊查询
 * 模糊查询可以识别拼写错误的词语，
 * <p>
 * 如：Trump拼成Trmp或者Tramp，使用FuzzyQuery仍然可以搜索到正确的结果。
 *
 * @Author niujinpeng
 * @Date 2018/7/1415:44
 */
public class FuzzyQueryTest {

    // 模糊查询
    public static void main(String[] args) throws IOException {
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        Term term = new Term("title", "Tramp");
        FuzzyQuery fuzzyQuery = new FuzzyQuery(term);

        TopDocs topDocs = indexSearch.search(fuzzyQuery, 10);
        LuceneQueryUtil.printQueryInfo(fuzzyQuery, topDocs);
        LuceneQueryUtil.close();

    }
}
