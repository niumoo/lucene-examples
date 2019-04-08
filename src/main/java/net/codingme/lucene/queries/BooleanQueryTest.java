package net.codingme.lucene.queries;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

/**
 * <p>
 * 布尔搜索
 * 实际开发中经常使用的一个Query查询，它其实是一个组合Query,在使用时候可以把各种Query
 * 对象添加进去并且标明它们之间的逻辑关系，
 *
 * @author niujinpeng
 * @date 2018年7月8日下午11:55:11
 */
public class BooleanQueryTest {

    public static void main(String[] args) throws IOException {
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        // 查询标题中包含“美国”且内容中不包含“宣布”的数据信息
        TermQuery termQuery1 = new TermQuery(new Term("title", "美国"));
        TermQuery termQuery2 = new TermQuery(new Term("content", "宣布"));
        // 构造逻辑关系
        BooleanClause bClause1 = new BooleanClause(termQuery1, Occur.FILTER);
        BooleanClause bClause2 = new BooleanClause(termQuery2, Occur.MUST_NOT);
        // 创建Query对象
        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(bClause1).add(bClause2).build();
        long start = System.currentTimeMillis();
        TopDocs topDocs = indexSearch.search(booleanQuery, 10);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));


        start = System.currentTimeMillis();
        topDocs = indexSearch.search(booleanQuery, 10);
        end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));


        LuceneQueryUtil.printQueryInfo(booleanQuery, topDocs);
        LuceneQueryUtil.close();

    }

    @Test
    public void test() throws IOException {
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        // 查询标题中包含“美国”且内容中不包含“宣布”的数据信息
        TermQuery termQuery1 = new TermQuery(new Term("title", "美国"));
        TermQuery termQuery2 = new TermQuery(new Term("content", "宣布"));
        // 构造逻辑关系
        BooleanClause bClause1 = new BooleanClause(termQuery1, Occur.MUST);
        BooleanClause bClause2 = new BooleanClause(termQuery2, Occur.MUST_NOT);
        // 创建Query对象
        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(bClause1).add(bClause2).build();
        LuceneQueryUtil.close();
    }

}
