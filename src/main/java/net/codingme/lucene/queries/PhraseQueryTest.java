package net.codingme.lucene.queries;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

/**
 * <p>
 * 多关键词搜索（PhraseQuery)
 * 在查询的过程中，很有可能不是一个简单的单词，几个单词在搜索的过程中，
 * 可能是紧密相连的几个单词，也有可能几个单词之间还有其他的词汇。
 * PhraseQuery就可以完成以上的功能。
 * <p>
 * 同时，PhraseQuery还提供了一个slop参数，可以用于设置关键词之间可以
 * 存在多少不相关的其他词汇。
 *
 * @Author niujinpeng
 * @Date 2018/7/1414:09
 */
public class PhraseQueryTest {

    // 定位的测试还没有出结果，不明白
    public static void main(String[] args) throws IOException {
        IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        builder.add(new Term("title","习近平"));
        builder.add(new Term("title","奥巴马"));
        builder.setSlop(10);
        PhraseQuery phraseQuery = builder.build();
        TopDocs topDocs = indexSearch.search(phraseQuery, 10);

        LuceneQueryUtil.printQueryInfo(phraseQuery,topDocs);
        LuceneQueryUtil.close();

    }
}
