package net.codingme.lucene.index;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import net.codingme.lucene.ik.IKAnalyzer6x;
import net.codingme.lucene.pojo.News;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;

/**
 * <p>
 * 创建索引
 *
 * @author niujinpeng
 * @date 2018年6月21日下午2:07:28
 */
public class CreateIndex {

    public static FieldType idType;
    public static FieldType titleType;
    public static FieldType contentType;

    public static void main(String[] args) throws IOException {

        // 创建三个news对象
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("习近平会见美国总统奥巴马，学习国外经验");
        news1.setContent("国家主席习近平9月3日在杭州西湖国宾馆会见前来出席二十国集团领导人"
                + "杭州峰会的美国总统奥巴马....");
        news1.setReply(672);

        News news2 = new News();
        news2.setId(2);
        news2.setTitle("北大迎4380名新生，农村学生700多人近年最多南京");
        news2.setContent("昨天，北京大学迎来4380名来自全国各地以及数十个国家的本科新生，"
                + "其中，农村学生共700名，为近年最多南京");
        news1.setReply(995);

        News news3 = new News();
        news3.setId(3);
        news3.setTitle("特朗普宣誓（Donald Trump）就任美国第45任之总统");
        news3.setContent("当地时间1月20日，唐纳德特朗普在美国国会宣布就职，正式成为美国第"
                + "45任总统。");
        news3.setReply(1872);

        // 创建IK分词器
        IKAnalyzer6x analyzer6x = new IKAnalyzer6x();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer6x);
        // OpenMode.CREATE 则为清空索引重新创建
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        Directory directory = null;
        IndexWriter indexWriter = null;

        // 索引目录
        Path indexPath = Paths.get("indexdir");
        // 开始时间
        Date start = new Date();
        try {
            if (!Files.isReadable(indexPath)) {
                System.out.println("Document directory '" + indexPath.toAbsolutePath() + "' "
                        + "does not exist or is not readable, please check the path");
            }
        } catch (Exception e) {
            System.exit(1);
        }

        directory = FSDirectory.open(indexPath);
        indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 设置新闻ID索引并存储
        idType = new FieldType();
        idType.setIndexOptions(IndexOptions.DOCS);
        // 存储字段值
        idType.setStored(true);

        // 设置新闻标题索引文档、词项频率、位移信息和偏移量、村粗并词条化
        titleType = new FieldType();
        titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        titleType.setStored(true);
        // 对字段值进行词条化
        titleType.setTokenized(true);

        contentType = new FieldType();
        contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        // 是否存储字段值
        contentType.setStored(true);
        // 使用配置的分词器对其进行词条化
        contentType.setTokenized(true);
        // 是否存储词向量
        contentType.setStoreTermVectors(true);
        // 是否存储词向量中的偏移信息
        contentType.setStoreTermVectorOffsets(true);
        contentType.setStoreTermVectorPayloads(true);
        // 是否存储词向量中的位移信息
        contentType.setStoreTermVectorPositions(true);

        // 创建Document对象
        Document doc1 = getDocument(news1);
        Document doc2 = getDocument(news2);
        Document doc3 = getDocument(news3);

        indexWriter.addDocument(doc1);
        indexWriter.addDocument(doc2);
        indexWriter.addDocument(doc3);

        // 创建索引
        indexWriter.commit();
        indexWriter.close();
        directory.close();

        Date end = new Date();
        System.out.println("索引文档消耗时间:" + (end.getTime() - start.getTime()) + "ms");
    }

    public static Document getDocument(News news) {
        Document doc = new Document();
        doc.add(new Field("id", String.valueOf(news.getId()), idType));
        doc.add(new Field("title", news.getTitle(), titleType));
        doc.add(new Field("content", news.getContent(), contentType));
        doc.add(new IntPoint("reply", news.getReply()));
        doc.add(new StoredField("reply_display", news.getReply()));
        return doc;
    }


}
