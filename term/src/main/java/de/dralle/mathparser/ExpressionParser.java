/**
 * 
 */
package de.dralle.mathparser;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.IParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

/**
 * Parser helper class for expressions.
 * @author Nils Dralle
 *
 */
public class ExpressionParser {
	private static IParser<ExpressionNode> parser=new LatexMathExpressionParser();
	/**
	 * @return the parser
	 */
	public static IParser<ExpressionNode> getParser() {
		return parser;
	}
	/**
	 * @param parser the parser to set
	 */
	public static void setParser(IParser<ExpressionNode> parser) {
		ExpressionParser.parser = parser;
	}
	public static ExpressionNode buildExpressionTreeFromString(String expression) {		
		if(expression==null) {
			return null;
		}
		ExpressionNode tree = parser.fromString(expression);
		CompositeTransformationRule combineRule = TransformationRuleBuilder.buildNodeCombineRule();
		ExpressionNode tree2 = null;
		try {
			tree2 = combineRule.apply(tree, true);
		} catch (Exception e) {
		}
		return tree2==null?tree:tree2;
	}
	/**
	 * If parsing is successful, returns the expression as parsed. If not throws an exception.
	 * @param expression
	 * @return
	 * @throws Exception 
	 */
	public static String checkExpressionSyntaxThrowException(String expression) throws Exception {
		return parser.checkSyntaxThrowException(expression);
	}
	public static boolean checkExpressionSyntax(String expression) {
		return parser.checkSyntax(expression);
	}
}
