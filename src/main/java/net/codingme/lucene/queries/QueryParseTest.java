package net.codingme.lucene.queries;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.codingme.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * <p>
 * 搜索入门 - 单项搜索<BR/>
 * 查询的过程就是构建Query对象的过程，Lucene搜索文档需要实例化一个IndexSearcher对象，IndexSearch对象的
 * search方法完成搜索，query作为search的对象，结果则保存在TopDocs类型文档集合，遍历TopDocs则得到文档信 息
 * 
 * @author niujinpeng
 * @date 2018年6月22日下午4:38:47
 */
public class QueryParseTest {

	public static void main(String[] args) throws IOException, ParseException {

		IndexSearcher indexSearcher = LuceneQueryUtil.getIndexSearch();
		IKAnalyzer6x analyzer6x = new IKAnalyzer6x();

		String field = "title";
		QueryParser queryParser = new QueryParser(field, analyzer6x);
		queryParser.setDefaultOperator(Operator.AND);

		// 查询关键词
		Query query = queryParser.parse("农村学生");
		System.out.println("Query:" + query.toString());

		// 返回前10条
		Long start = System.currentTimeMillis();
		TopDocs topDocs = indexSearcher.search(query, 10);
		Long end = System.currentTimeMillis();

		LuceneQueryUtil.printQueryInfo(query,topDocs);
		LuceneQueryUtil.close();
		System.out.println("耗时：" + (end - start) + "ms");

	}
	
	
	
	
	
}
