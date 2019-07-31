package net.codingme.lucene.queries;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.NIOFSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            System.out.println("content：" + document.get("content"));
            System.out.println("文档评分：" + sdoc.score);
        }
    }

    public static void printQueryInfo(Query query, TopDocs topDocs, long start, long end) throws IOException {
        System.out.println("耗时：" + (end - start) + "ms");
        printQueryInfo(query, topDocs);

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

    public static IndexSearcher getIndexSearchByNIO() throws IOException {
        Path indexPath = Paths.get(INDEX_DIR);
        directory = NIOFSDirectory.open(indexPath);
        directoryReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(directoryReader);
        return indexSearcher;
    }

    public static IndexSearcher getIndexSearchByMMap() throws IOException {
        Path indexPath = Paths.get(INDEX_DIR);
        directory = MMapDirectory.open(indexPath);
        directoryReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(directoryReader);
        return indexSearcher;
    }

    //public static IndexSearcher getIndexSearchByRAM() throws IOException {
    //    Path indexPath = Paths.get(INDEX_DIR);
    //    directory = FSDirectory.open(indexPath);
    //    RAMDirectory ramDirectory = new RAMDirectory(directory);
    //    directoryReader = DirectoryReader.open(ramDirectory);
    //    indexSearcher = new IndexSearcher(directoryReader);
    //    return indexSearcher;
    //}


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
