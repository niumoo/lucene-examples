package net.codingme.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;

import net.codingme.lucene.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * <p>
 * IK 扩展词库测试
 * 添加扩展词：厉害了我的国
 * Result：厉害了我的国|是|春节|推出|的|充满了|正|能量|的|国家|大片|
 *
 * @author niujinpeng
 * @date 2018年6月21日上午11:31:28
 */
public class IKExtDicTest {

    private static String string = "厉害了我的国， 是春节推出的充满了正能量的国家大片";

    public static void main(String[] args) throws IOException {
        printlnAnalyzer(new IkAnalyzer6x(true), string);
    }

    public static void printlnAnalyzer(Analyzer analyzer, String string) throws IOException {
        StringReader reader = new StringReader(string);
        TokenStream tokenStream = analyzer.tokenStream(string, reader);

        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(attribute.toString() + "|");
        }
        System.out.println();

        analyzer.close();
    }

}
