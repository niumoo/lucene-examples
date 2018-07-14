package net.codingme.lucene.queries;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

/**
 * <p>
 * 前缀搜索PrefixQuery
 * 先定义一个词条，词条中包含要查找的关键字的前缀
 * 然后构造一个PrefixQuery对象
 *
 * @Author niujinpeng
 * @Date 2018/7/1413:47
 */
public class PrefixQueryTest {

    public static void main(String[] args) throws IOException {
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        Term term = new Term("title", "学习");
        PrefixQuery prefixQuery = new PrefixQuery(term);

        TopDocs topDocs = indexSearch.search(prefixQuery, 10);

        LuceneQueryUtil.printQueryInfo(prefixQuery, topDocs);
        LuceneQueryUtil.close();
    }

}
