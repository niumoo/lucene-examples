package net.codingme.lucene.top;

import net.codingme.lucene.ik.IkAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * <p>
 * 为了提取TopN关键词，在这里创建索引
 * </p>
 *
 * @Author niujinpeng
 * @Date 2018/7/20 0:57
 */
public class IndexDocs {

    public static String indexDir = "indexDir";


    public static void main(String[] args) throws IOException {
        createIndex("testfile/news.txt");
    }

    /**
     * 创建索引信息
     *
     * @return
     * @throws IOException
     */
    public static boolean createIndex(String filePath) throws IOException {
        Long startTime = System.currentTimeMillis();
        File newsFile = new File(filePath);
        String fileContent = fileToString(newsFile);
        // 构造一个分词器，使用IK的智能分词
        IkAnalyzer6x smIkAnalyzer6x = new IkAnalyzer6x(true);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(smIkAnalyzer6x);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        // 设置索引的存储路径
        Directory directory = null;
        IndexWriter indexWriter = null;
        directory = FSDirectory.open(Paths.get(indexDir));
        indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 新建FieldType用于指定字段索引时的信息
        FieldType fieldType = new FieldType();
        // 索引时候保存文档、词项频率、位置信息、偏移信息。
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        // 原始字符串都保存在索引中
        fieldType.setStored(true);
        // 存储词项量
        fieldType.setStoreTermVectors(true);
        // 词条化
        fieldType.setTokenized(true);

        // 构造document用于存储
        Document document = new Document();
        Field field = new Field("content", fileContent, fieldType);
        document.add(field);

        // 写入索引
        indexWriter.addDocument(document);
        indexWriter.close();
        directory.close();
        Long endTime = System.currentTimeMillis();
        System.out.println("create index complete ! Time consuming " + (endTime - startTime) + "ms");
        return true;
    }

    /**
     * 把文件读出返回文本字符串
     *
     * @param file
     * @return
     */
    public static String fileToString(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        // 构造一个BufferReader类用于读取文件
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = null;
        // 一次读取一行
        while ((str = br.readLine()) != null) {
            sb.append(System.lineSeparator() + str);
        }
        br.close();

        return sb.toString();
    }


}
