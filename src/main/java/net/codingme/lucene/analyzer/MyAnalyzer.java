package net.codingme.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.cjk.CJKWidthFilter;

import java.io.IOException;
import java.io.StringReader;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2019/4/9 17:34
 */
public class MyAnalyzer {


    public void testMyAnalyzer() {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {

                Tokenizer source = new Tokenizer() {
                    @Override
                    public boolean incrementToken() throws IOException {
                        return false;
                    }
                };
                // 配置 charFilter
                // source.setReader(new HTMLStripCharFilter(new StringReader("")));

                CJKWidthFilter cjkWidthFilter = new CJKWidthFilter(source);
                return new TokenStreamComponents(source);
            }
        };

    }
}
