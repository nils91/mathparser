/**
 * 
 */
package de.dralle.mathparser;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.IParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

/**
 * Parser helper class for rules.
 * @author Nils Dralle
 *
 */
public class TransformationRuleParser {

	private static IParser<GeneralTransformationRule> parser = new LatexMathTransformationRuleParser();

	/**
	 * @return the parser
	 */
	public static IParser<GeneralTransformationRule> getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	public static void setParser(IParser<GeneralTransformationRule> parser) {
		TransformationRuleParser.parser = parser;
	}

	public static GeneralTransformationRule buildTransformationRuleFromString(String rule) {
		if (rule == null) {
			return null;
		}
		GeneralTransformationRule tree = (GeneralTransformationRule) parser.fromString(rule);
		ExpressionNode left = tree.getLeft();
		ExpressionNode right = tree.getRight();
		CompositeTransformationRule combineRule = TransformationRuleBuilder.buildNodeCombineRule();
		ExpressionNode left2 = null;
		try {
			left2 = combineRule.apply(left, true);
		} catch (Exception e) {
		}
		ExpressionNode right2 = null;
		try {
			right2 = combineRule.apply(right,true);
		} catch (Exception e) {
		}
		tree.setLeft(left2==null?left:left2);
		tree.setRight(right2==null?right:right2);
		return tree;
	}
}
