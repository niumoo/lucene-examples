package net.codingme.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * <p>
 * Lucene标准分词器
 * <p>
 * StandardAnalyzer对中文分词：
 * 中|华|人|民|共|和|国|简|称|中|国|是|一|个|有|13|亿|人|口|的|国|家|
 * StandardAnalyzer对英文分词：
 * dogs|can|achiece|place|eyes|can|reach|
 *
 * @author niujinpeng
 * @date 2018年6月20日下午10:33:47
 */
public class StkAnalyzer {

    private static String strCh = "中华人民共和国简称中国，是一个有13亿人口的国家";
    private static String strEn = "Dogs can not achiece a place, eyes can reach";

    public static void main(String[] args) throws IOException {

        System.out.println("StandardAnalyzer对中文分词：");
        stdAnalyzer(strCh);
        System.out.println("StandardAnalyzer对英文分词：");
        stdAnalyzer(strEn);
    }

    // Lucene标准分词处理
    public static void stdAnalyzer(String str) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        StringReader reader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream(str, reader);
        // 清空流
        tokenStream.reset();
        CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(charTermAttribute.toString() + "|");
        }
        System.out.println();
        analyzer.close();
    }
}
