/**
 * 
 */
package de.dralle.mathparser.parser.latexmath;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import de.dralle.generated.antlr4.latexmathLexer;
import de.dralle.generated.antlr4.latexmathParser;
import de.dralle.mathparser.parser.AbstractANTLR4Parser;

/**
 * @author Nils Dralle
 *
 */
public abstract class AbstractLatexMathParser<T> extends AbstractANTLR4Parser<T> {
	
	@Override
	protected void initalizeLexerParser(String str) {
		lexer=new latexmathLexer(CharStreams.fromString(str));
		parser=new latexmathParser(new CommonTokenStream(lexer));		
	}

}
