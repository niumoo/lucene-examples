package net.codingme.lucene.queries;

import net.codingme.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import java.io.IOException;

/**
 * <p>
 * 范围搜索 - 查找满足指定范围之内的文档信息，如果查找某个时间段的文档
 *
 * @Author niujinpeng
 * @Date 2018/7/1413:34
 */
public class RangeQueryTest {

    public static void main(String[] args) throws IOException {
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        IKAnalyzer6x ikAnalyzer6x = new IKAnalyzer6x();

        Query rangeQuery = IntPoint.newRangeQuery("reply", 500, 1000);
        TopDocs topDocs = indexSearch.search(rangeQuery, 10);

        LuceneQueryUtil.printQueryInfo(rangeQuery, topDocs);
        LuceneQueryUtil.close();
    }

}
