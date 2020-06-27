/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.TransformationUtil;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.GeneralTransformationRule;

/**
 * @author Nils Dralle
 *
 */
class TransformTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void testNotSameReference() {
		ExpressionNode expr = ExpressionParser.buildExpressionTreeFromString("x²+y");
		GeneralTransformationRule r = TransformationRuleParser.buildTransformationRuleFromString("a²->a*a");
		try {
			IMathParserBaseNode res = TransformationUtil.transformExpression(expr, r);
			assertFalse(expr == res);
		} catch (Exception e) {
			fail("");
		}
	}

	@Test
	void testTreeChangeTestable() {
		ExpressionNode expr = ExpressionParser.buildExpressionTreeFromString("x²+y");
		GeneralTransformationRule r = TransformationRuleParser.buildTransformationRuleFromString("a²->a*a");
		try {
			IMathParserBaseNode res = TransformationUtil.transformExpression(expr, r);
			assertFalse(ExpressionUtil.isEqual(expr, res));
		} catch (Exception e) {
			fail("");
		}
	}

	@Test
	void testTransformationApplied() {
		ExpressionNode expr = ExpressionParser.buildExpressionTreeFromString("x^2+y");
		GeneralTransformationRule r = TransformationRuleParser.buildTransformationRuleFromString("a^2 \\to a*a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("x*x+y");
		try {
			IMathParserBaseNode res = r.apply(expr);
			assertTrue(ExpressionUtil.isEqual(expected, res));
		} catch (Exception e) {
			fail("");
		}
	}

}
