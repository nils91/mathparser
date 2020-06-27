/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.CombineAddNodes;

/**
 * @author Nils Dralle
 *
 */
class CombineAddNodeTests {

	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	@Test
	@Disabled
	void test() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("a+b+c+(d+e)*f+(a+b+c)");
		System.out.println(ex);
		try {
			ExpressionNode combined = new CombineAddNodes().apply(ex,true);
			System.out.println(combined);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testIsPossible() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("b+a+c");
		CombineAddNodes rule = new CombineAddNodes();
		assertTrue(rule.isPossible(ex));
	}
	@Test
	void testNotPossible() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("b-a-c");
		CombineAddNodes rule = new CombineAddNodes();
		assertFalse(rule.isPossible(ex));
	}
}
