package net.codingme.lucene.queries;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.codingme.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * <p>
 * 多域搜索<br>
 * QueryParser 可以搜索单个字段，而MultiFieldQueryParser 可以搜索 多个字段。
 * 
 * @author niujinpeng
 * @date 2018年7月7日下午2:20:57
 */
public class MultiFieldQueryParserTest {


	public static void main(String[] args) throws IOException, ParseException {

        IndexSearcher indexSearcher = LuceneQueryUtil.getIndexSearch();
        IKAnalyzer6x ikAnalyzer6x = new IKAnalyzer6x();

		// 要查询的字段
		String[] fields = { "title", "content" };
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, ikAnalyzer6x);
		// 要查询的关键词
		Query query = queryParser.parse("美国");

		System.out.println("query语句:" + query.toString());
		TopDocs docs = indexSearcher.search(query, 10);

		LuceneQueryUtil.printQueryInfo(query,docs);
		LuceneQueryUtil.close();

	}

}
