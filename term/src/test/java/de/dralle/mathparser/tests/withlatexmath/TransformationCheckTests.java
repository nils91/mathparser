/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.TransformationUtil;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.GeneralTransformationRule;

/**
 * @author Nils Dralle
 *
 */
public class TransformationCheckTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	@Test
	void testBidirectionalRuleL2RMatch() {
		String expression="(x+y)^2";		
		String transformationRule="(a+b)^2->a^2+2*a*b+b^2";
		assertTrue(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}

	@Test
	void testBidirectionalRuleExplicitL2RMatch() {
		String expression="(x+y)^2";	
		ExpressionNode parsedExpression = new LatexMathExpressionParser().fromString(expression);
		String transformationRule="(a+b)^2 \\to a^2+2*a*b+b^2";
		GeneralTransformationRule parsedRule = new LatexMathTransformationRuleParser().fromString(transformationRule);
		assertTrue(parsedRule.isPossible(parsedExpression, true));
	}
	@Test
	void testL2RRuleNoMatch() {
		String expression="(x+y)^2";		
		String transformationRule="a^2+2*a*b+b^2->(a+b)^2";
		assertFalse(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
	
	@Test
	void testBidirectionalRuleExplicitL2RNoMatch() {
		String expression="(x+y)^2";		
		String transformationRule="a^2+2*a*b+b^2->(a+b)^2";
		assertFalse(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
	@Test
	void testBidirectionalRuleOperatorMisMatch() {
		String expression="(x-y)^2";		
		String transformationRule="(a+b)^2->a^2+2*a*b+b^2";
		assertFalse(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
	@Test
	void testBidirectionalRuleNumberMisMatch() {
		String expression="(x+y)^3";		
		String transformationRule="(a+b)^2->a^2+2*a*b+b^2";
		assertFalse(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
	@Test
	void testBidirectionalRuleStructuralMisMatch() {
		String expression="(x+y)";		
		String transformationRule="(a+b)^2->a^2+2*a*b+b^2";
		assertFalse(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
	@Test
	void testBidirectionalRuleRecursionMatch() {
		String expression="(x+y)^2";		
		String transformationRule="a+b->b+a";
		assertTrue(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
	@Test
	void testBidirectionalRuleRecursionDisallowedNoMatch() {
		String expression="(x+y)^2";		
		String transformationRule="a+b->b+a";
		boolean result = TransformationUtil.isTransformationPossible(expression, transformationRule, false);
		assertFalse(TransformationUtil.isTransformationPossible(expression, transformationRule, false));
	}
	@Test
	void testBidirectionalRuleRecursionMatch2() {
		String expression="(x-y)^2";		
		String transformationRule="a-b->-b+a";
		assertTrue(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
}
