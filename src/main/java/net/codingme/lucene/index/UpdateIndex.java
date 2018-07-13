package net.codingme.lucene.index;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import net.codingme.lucene.ik.IKAnalyzer6x;

/**
 * <p>
 * 更新索引操作 索引的更新实际上是先删除再添加
 * 
 * @author niujinpeng
 * @date 2018年6月21日下午3:03:44
 */
public class UpdateIndex {

	public static void main(String[] args) {
		IKAnalyzer6x ikAnalyzer6x = new IKAnalyzer6x();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer6x);
		Path indexPath = Paths.get("indexdir");
		Directory directory;
		try {
			directory = FSDirectory.open(indexPath);
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			Document document = new Document();
			document.add(new TextField("id", "2", Store.YES));
			document.add(new TextField("title", "北大迎4380名新生，农村学生800多人近年最多", Store.YES));
			document.add(new TextField("content", "昨天，北京大学迎来4380名来自全国各地以及数十个国家的本科新生，\"\r\n" + 
					"				+ \"其中，农村学生共800名，为近年最多", Store.YES));
			indexWriter.updateDocument(new Term("title","北大"), document);
			indexWriter.commit();
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
