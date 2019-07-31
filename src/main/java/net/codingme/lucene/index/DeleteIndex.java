package net.codingme.lucene.index;

import java.nio.file.Path;
import java.nio.file.Paths;

import net.codingme.lucene.ik.IkAnalyzer6x;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * <p>
 * 删除索引
 * 
 * @author niujinpeng
 * @date 2018年6月21日下午2:56:43
 */
public class DeleteIndex {

	public static void main(String[] args) {
		deleteDoc("title","美国");
	}

	private static void deleteDoc(String field,String key) {
		IkAnalyzer6x ikAnalyzer6x = new IkAnalyzer6x();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer6x);
		Path indexPath = Paths.get("indexdir");
		Directory directory ;
		try {
			directory = FSDirectory.open(indexPath);
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			indexWriter.deleteDocuments(new Term(field,key));
			indexWriter.commit();
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
