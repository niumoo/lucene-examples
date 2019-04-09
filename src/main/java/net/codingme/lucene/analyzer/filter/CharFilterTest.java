package net.codingme.lucene.analyzer.filter;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.charfilter.MappingCharFilter;
import org.apache.lucene.analysis.charfilter.NormalizeCharMap;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * <p>
 *
 *
 * @Author niujinpeng
 * @Date 2019/4/9 16:16
 */
public class CharFilterTest {

    /**
     * 使用 HTMLStripCharFilter 过滤字符串
     *
     * @throws IOException
     */
    @Test
    public void clearHtml() throws IOException {
        StringReader stringReader = new StringReader("<div class=\"w3-modal-content w3-animate-zoom w3-center w3-transparent w3-padding-64\"><img id=\"img01\" class=\"w3-image\" src=\"./images/girl.jpg\"><p id=\"caption\">Canoeing again</p></div>");
        HTMLStripCharFilter htmlStripCharFilter = new HTMLStripCharFilter(stringReader);

        StringBuilder sb = new StringBuilder();
        char[] cbuf = new char[1024];
        while (true) {
            int count = htmlStripCharFilter.read(cbuf);
            if (count == -1) {
                break; // end of stream mark is -1
            }
            if (count > 0) {
                sb.append(cbuf, 0, count);
            }
        }
        htmlStripCharFilter.close();
        System.out.println(sb);
    }
}
