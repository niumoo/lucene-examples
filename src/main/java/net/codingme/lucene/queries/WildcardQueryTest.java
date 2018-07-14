package net.codingme.lucene.queries;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;

import java.io.IOException;

/**
 * <p>
 * 通配符搜索（WildcardQuery）
 * 这种搜索可以使用通配符进行处理，如 ?
 *
 * @Author niujinpeng
 * @Date 2018/7/1421:55
 */
public class WildcardQueryTest {

    public static void main(String[] args) throws IOException {
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        Term term = new Term("title", "学?");
        WildcardQuery wildcardQuery = new WildcardQuery(term);

        // 传入使用的Query以及要查询的条数
        TopDocs topDocs = indexSearch.search(wildcardQuery, 10);
        LuceneQueryUtil.printQueryInfo(wildcardQuery,topDocs);
        LuceneQueryUtil.close();


    }
}
