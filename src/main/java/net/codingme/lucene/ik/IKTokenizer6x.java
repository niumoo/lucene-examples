package net.codingme.lucene.ik;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * <p>
 * 
 * @author niujinpeng
 * @date 2018年6月20日下午11:11:42
 */
public class IKTokenizer6x extends Tokenizer {

	// IK 分词器实现
	private IKSegmenter _IKImplement;
	// 词元文本属性
	private CharTermAttribute termAtt;
	// 词元位移属性
	private OffsetAttribute offsetAttribute;
	// 词元分类属性
	private TypeAttribute typeAtt;
	// 记录最后一个词元的结束位置
	private int endPosition;

	// Lucene 6.X Tokenizer 适配器类构造函数，实现最新的Tokenizer接口
	public IKTokenizer6x(boolean useSmart) {
		super();
		offsetAttribute = addAttribute(OffsetAttribute.class);
		termAtt = addAttribute(CharTermAttribute.class);
		typeAtt = addAttribute(TypeAttribute.class);
		_IKImplement = new IKSegmenter(input, useSmart);
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes(); // 清除所有的词元属性
		Lexeme nextLexeme = _IKImplement.next();
		if (nextLexeme != null) {
			// 将Lexeme转化成Attributes
			termAtt.append(nextLexeme.getLexemeText()); // 设置词元文本
			termAtt.setLength(nextLexeme.getLength()); // 设置词元长度
			offsetAttribute.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition()); // 设置词元位移
			// 记录分词的最后位置
			endPosition = nextLexeme.getEndPosition();
			typeAtt.setType(nextLexeme.getLexemeText()); // 记录词元分类
			return true; // 返回true告知还有下个词元信息
		}
		return false; // 返回false告知词元输出完毕
	}
	
	@Override
	public void reset() throws IOException {
		super.reset();
		_IKImplement.reset(input);
	}
	
	@Override
	public void end() throws IOException {
		int finalOffset = correctOffset(this.endPosition);
		offsetAttribute.setOffset(finalOffset, finalOffset);
	}

}
