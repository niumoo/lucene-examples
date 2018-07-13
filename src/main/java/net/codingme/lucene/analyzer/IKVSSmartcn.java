package net.codingme.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;

import net.codingme.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * <p>
 * 比较Lucene自带的中文智能分词和IK分词
 * 谁的准确性更好
 * 
 * 句子一：公路局正在治理解放大道路面积水问题
 * SmartChineseAnalyzer 分词结果：
 * 公路局|正|在|治理|解放|大|道路|面积|水|问题|
 * IKAnalyzer 分词结果：
 * 公路局|正在|治理|解放|大道|路面|积水|问题|
 * IKAnalyzer 细粒度分词结果：
 * 公路局|公路|路局|正在|治理|理解|解放|放大|大道|道路|路面|面积|积水|问题|
 * -----------------------------------------
 * 句子二：IKAnalyzer是一个开源的， 基于java语言开发的轻量级的中文分词工具包
 * SmartChineseAnalyzer 分词结果：
 * ikanalyz|是|一个|开|源|的|基于|java|语言|开发|的|轻量级|的|中文|分词|工具包|
 * IKAnalyzer 分词结果：
 * ikanalyzer|是|一个|开源|的|基于|java|语言|开发|的|轻量级|的|中文|分词|工具包|
 * 
 * @author niujinpeng
 * @date 2018年6月21日上午10:50:53
 */
public class IKVSSmartcn {
	
	private static Analyzer analyzer = null ;

	private static String str1 = "公路局正在治理解放大道路面积水问题";
	private static String str2 = "IKAnalyzer是一个开源的， 基于java语言开发的轻量级的中文分词工具包";
	

	public static void main(String args[]) throws IOException {
		
		System.out.println("句子一："+str1);
		
		analyzer = new SmartChineseAnalyzer();
		System.out.println("SmartChineseAnalyzer 分词结果：");
		printAnalyzer(analyzer, str1);
		
		analyzer = new IKAnalyzer6x(true);
		System.out.println("IKAnalyzer 分词结果：");
		printAnalyzer(analyzer, str1);
		
		analyzer = new IKAnalyzer6x(false);
		System.out.println("IKAnalyzer 细粒度分词结果：");
		printAnalyzer(analyzer, str1);
		
		System.out.println("-----------------------------------------");
		
		System.out.println("句子二："+str2);
		
		analyzer = new SmartChineseAnalyzer();
		System.out.println("SmartChineseAnalyzer 分词结果：");
		printAnalyzer(analyzer, str2);
		
		analyzer = new IKAnalyzer6x(true);
		System.out.println("IKAnalyzer 分词结果：");
		printAnalyzer(analyzer, str2);

	}

	public static void printAnalyzer(Analyzer analyzer, String str) throws IOException {

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
