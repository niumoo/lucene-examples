package net.codingme.lucene.queries;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * <p>
 * Lucene查询常用操作封装
 *
 * @author niujinpeng
 * @date 2018年7月8日下午11:41:52
 */
public class LuceneQueryUtil {
	// 索引存放目录
	private static String INDEX_DIR = "indexdir";
	
	private static IndexSearcher indexSearcher;

	private static FSDirectory directory;

	private static DirectoryReader directoryReader;

	/**
	 * 输出查询结果信息
	 * 
	 * @param query
	 * @throws IOException
	 */
	public static void printQueryInfo(Query query, TopDocs topDocs) throws IOException {
		System.out.println("query语句：" + query.toString());
		for (ScoreDoc sdoc : topDocs.scoreDocs) {
			Document document = indexSearcher.doc(sdoc.doc);
			System.out.println("DocId：" + sdoc.doc);
			System.out.println("id：" + document.get("id"));
			System.out.println("title：" + document.get("title"));
			System.out.println("content："+document.get("content"));
			System.out.println("文档评分：" + sdoc.score);
		}
	}

	/**
	 * 获取索引信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public static IndexSearcher getIndexSearch() throws IOException {
		Path indexPath = Paths.get(INDEX_DIR);
		directory = FSDirectory.open(indexPath);
		directoryReader = DirectoryReader.open(directory);
		indexSearcher = new IndexSearcher(directoryReader);
		return indexSearcher;
	}

	/**
	 * 释放资源
	 * 
	 * @throws IOException
	 */
	public static void close() throws IOException {
		if (directoryReader != null) {
			directoryReader.close();
		}
		if (directory != null) {
			directory.close();
		}
	}

}
