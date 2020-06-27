/**
 * 
 */
package de.dralle.mathparser;

import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.IParser;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

/**
 * Parser helper class for equations. Equations are parsed from two expressions separated by an equal sign.
 * 
 * @author Nils Dralle
 *
 */
public class EquationParser {

	private static IParser<EquationNode> parser=new LatexMathEquationParser();
	/**
	 * @return the parser
	 */
	public static IParser<EquationNode> getParser() {
		return parser;
	}
	/**
	 * @param parser the parser to set
	 */
	public static void setParser(IParser<EquationNode> parser) {
		EquationParser.parser = parser;
	}
	
	public static EquationNode buildEquationTreeFromString(String equation) {		
		if(equation==null) {
			return null;
		}
		EquationNode tree = parser.fromString(equation);
		CompositeTransformationRule combineRule = TransformationRuleBuilder.buildNodeCombineRule();
		EquationNode tree2 = SymbolicEqualityUtil.applyToBothSides(tree, combineRule, true, false);
		return tree2==null?tree:tree2;
	}
	/**
	 * If parsing is successful, returns the equation as parsed. If not throws an exception.
	 * @param expression
	 * @return
	 * @throws Exception 
	 */
	public static String checkEquationSyntaxThrowException(String equation) throws Exception {
		return parser.checkSyntaxThrowException(equation);
	}
	
	public static boolean checkEquationSyntax(String equation) {
		return parser.checkSyntax(equation);
	}
}
