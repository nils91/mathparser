/**
 * 
 */
package de.dralle.mathparser.parser.latexmath;

import org.antlr.v4.runtime.tree.ParseTree;

import de.dralle.generated.antlr4.latexmathParser;
import de.dralle.mathparser.antlr4.visitors.latexmath.ExpressionVisitor;
import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * @author Nils Dralle
 *
 */
public class LatexMathExpressionParser extends AbstractLatexMathParser<ExpressionNode>{

	@Override
	public ExpressionNode fromString(String str) {
		super.initalizeLexerParser(str);
		ParseTree pt = getParseTree();
		return pt.accept(new ExpressionVisitor());
	}

	@Override
	protected ParseTree getParseTree() {
		return ((latexmathParser)parser).expr();
	}

}
