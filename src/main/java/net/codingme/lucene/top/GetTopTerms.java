package net.codingme.lucene.top;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>
 * 关键词提取
 * 这里演示如何从Lucene的索引中提取词项频率。
 * 思路：使用IndexReader的getTermVectpr获取文档的某一个字段Terms
 * 从Terms中取出tf(term frequency)
 * tf放到Map中降序
 * 取出TopN
 *
 * @Author niujinpeng
 * @Date 2018/7/21 9:51
 */
public class GetTopTerms {

    public static void main(String[] args) throws IOException {
        Terms terms = getTerms();
        Map map = termsToMap(terms);

        Set<Map.Entry<String,Integer>> wordSet = new TreeSet<Map.Entry<String,Integer>>(new ComparatorByValue());
        wordSet.addAll(map.entrySet());

        // 取前10个词项
        int i= 0;
        for (Map.Entry<String,Integer> entry : wordSet){
            System.out.println(entry.getKey()+"："+entry.getValue());
            if( ++i == 10){
                break;
            }
        }
    }

    /**
     * 取词项信息terms
     * @return
     * @throws IOException
     */
    public static Terms getTerms() throws IOException {
        // 取得创建的索引信息，因为现在索引中只有一个信息，直接取0即可
        FSDirectory directory = FSDirectory.open(Paths.get(IndexDocs.indexDir));
        IndexReader reader = DirectoryReader.open(directory);
        Terms terms = reader.getTermVector(0, "content");
        return terms;
    }

    /**
     * 读取词项信息中的取值放入map
     * @param terms
     * @return
     * @throws IOException
     */
    public static Map termsToMap(Terms terms) throws IOException {
        HashMap<String, Integer> map = new HashMap<>();
        TermsEnum termsEnum = terms.iterator();
        BytesRef thisTerm;
        while ((thisTerm = termsEnum.next()) != null){
            String termText = thisTerm.utf8ToString();
            Integer totalTerm = Math.toIntExact(termsEnum.totalTermFreq());
            map.put(termText,totalTerm);
        }
        return map;
    }

    /**
     * <p>
     * Description：获取按字符串长度比较的比较器，结果按字符串长度逆序
     *
     * @author niujinpeng
     * @date 2018年5月25日下午2:27:33
     */
    public static class ComparatorByValue implements Comparator {
        @Override
        public int compare(Object obj1, Object obj2)// 固定的方法，要比较多少个，就写多少个参数
        {
            if (!(obj1 instanceof Map.Entry) || !(obj2 instanceof Map.Entry)) {
                System.err.println(new ClassCastException("ComparatorByValue ClassCastException"));
                return 1;
            }
            // 强制转换成字符类型
            Map.Entry<String,Integer> entry1 = (Map.Entry) obj1;
            // 强制转换成字符类型
            Map.Entry<String,Integer> entry2 = (Map.Entry) obj2;
            // 比较
            int num = entry2.getValue() - entry1.getValue();
            return num;
        }
    }


}
