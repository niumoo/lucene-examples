package net.codingme.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * <p>
 * Lucene多种分词器测试
 * <p>
 * 标准分词：class org.apache.lucene.analysis.standard.StandardAnalyzer：
 * 中|华|人|民|共|和|国|简|称|中|国|是|一|个|有|13|亿|人|口|的|国|家|
 * 空格分词：class org.apache.lucene.analysis.core.WhitespaceAnalyzer：
 * 中华人民共和国简称中国，|是一个有13亿人口的国家|
 * 简单分词：class org.apache.lucene.analysis.core.SimpleAnalyzer：
 * 中华人民共和国简称中国|是一个有|亿人口的国家|
 * 二分法分词：class org.apache.lucene.analysis.cjk.CJKAnalyzer：
 * 中华|华人|人民|民共|共和|和国|国简|简称|称中|中国|是一|一个|个有|13|亿人|人口|口的|的国|国家|
 * 关键词分词：class org.apache.lucene.analysis.core.KeywordAnalyzer：
 * 中华人民共和国简称中国， 是一个有13亿人口的国家|
 * 停用分词：class org.apache.lucene.analysis.core.StopAnalyzer：
 * 中华人民共和国简称中国|是一个有|亿人口的国家|
 * 中文智能分词：class org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer：
 * 中华人民共和国|简称|中国|是|一个|有|13|亿|人口|的|国家|
 *
 * @author niujinpeng
 * @date 2018年6月20日下午10:33:47
 */
public class VariousAnalyzers {

    private static String strCh = "中华人民共和国简称中国， 是一个有13亿人口的国家";
    private static String strEn = "Dogs can not achiece a place, eyes can reach";

    public static void main(String[] args) throws IOException {

        Analyzer analyzer = null;

        analyzer = new StandardAnalyzer(); // 标准分词
        System.out.println("标准分词：" + analyzer.getClass() + "：");
        printAnalyzer(analyzer);

        analyzer = new WhitespaceAnalyzer(); // 空格分词
        System.out.println("空格分词：" + analyzer.getClass() + "：");
        printAnalyzer(analyzer);

        analyzer = new SimpleAnalyzer(); // 简单分词
        System.out.println("简单分词：" + analyzer.getClass() + "：");
        printAnalyzer(analyzer);

        analyzer = new CJKAnalyzer(); // 二分法分词
        System.out.println("二分法分词：" + analyzer.getClass() + "：");
        printAnalyzer(analyzer);

        analyzer = new KeywordAnalyzer(); // 关键词分词
        System.out.println("关键词分词：" + analyzer.getClass() + "：");
        printAnalyzer(analyzer);

        analyzer = new StopAnalyzer(); // 停用分词
        System.out.println("停用分词：" + analyzer.getClass() + "：");
        printAnalyzer(analyzer);


        analyzer = new SmartChineseAnalyzer(); // 中文智能分词
        System.out.println("中文智能分词：" + analyzer.getClass() + "：");
        printAnalyzer(analyzer);

    }


    // Lucene分词处理
    public static void printAnalyzer(Analyzer analyzer) throws IOException {

        StringReader reader = new StringReader(strCh);
        TokenStream tokenStream = analyzer.tokenStream(strCh, reader);

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
