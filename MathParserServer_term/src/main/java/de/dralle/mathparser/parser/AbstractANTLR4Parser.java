/**
 * 
 */
package de.dralle.mathparser.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;

import de.dralle.mathparser.listeners.MathGrammar2ErrorListener;

/**
 * Base helper class for all used ANTLR4-based parsers.
 * @author Nils Dralle
 * @param <T>
 *
 */
public abstract class AbstractANTLR4Parser<T> implements IParser<T>{
	protected Lexer lexer;
	protected Parser parser;
	
	protected abstract void initalizeLexerParser(String str);
	protected abstract ParseTree getParseTree();
	protected ParseTree getParseTree(String str) {
		initalizeLexerParser(str);
		return getParseTree();
	}
	
	@Override
	public String checkSyntaxThrowException(String str) throws Exception {
		initalizeLexerParser(str);
		parser.removeErrorListeners();
		parser.addErrorListener(new MathGrammar2ErrorListener());
		return getParseTree().getText();
	}

	@Override
	public boolean checkSyntax(String str) {
		try {
			checkSyntaxThrowException(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
