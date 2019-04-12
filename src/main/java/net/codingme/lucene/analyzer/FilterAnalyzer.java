package net.codingme.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.cjk.CJKWidthFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * <p>
 * hello|world|
 *
 * @Author niujinpeng
 * @Date 2019/4/9 16:45
 */
public class FilterAnalyzer {

    @Test
    public void charFilter() throws IOException {
        String str = "<div>Hello world</div>";
        Analyzer analyzer = new StandardAnalyzer();
        // 使用 HTML 过滤器
        HTMLStripCharFilter htmlStripCharFilter = new HTMLStripCharFilter(new StringReader(str));
        TokenStream tokenStream = analyzer.tokenStream(str, htmlStripCharFilter);
        //CJKWidthFilter cjkWidthFilter = new CJKWidthFilter(tokenStream);
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
