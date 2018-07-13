package net.codingme.lucene.queries;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

/**
 * <p>
 * 词项搜索
 * 是最简单最常用的搜索，在索引中搜索某一个词条
 * @author niujinpeng
 * @date 2018年7月8日下午11:37:53
 */
public class TermQueryTest {

	public static void main(String[] args) throws Exception {
		IndexSearcher indexSearch = LuceneQueryUtil.getIndexSearch();

		// 词项搜索，搜索包含“美国”的title
		Term term = new Term("title", "美国");
		TermQuery termQuery = new TermQuery(term);
		TopDocs topDocs = indexSearch.search(termQuery, 10);
		LuceneQueryUtil.printQueryInfo(termQuery, topDocs);

		LuceneQueryUtil.close();

	}
}
