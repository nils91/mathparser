/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.TransformationUtil;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

/**
 * @author Nils Dralle
 *
 */
public class TransformationDirectionTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	@Test
	void testLeft2Right() {
		String expression = "(x+y)^2";
		String transformationRule = "(a+b)^2->a^2+2*a*b+b^2";
		assertTrue(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}

	@Test
	void testTransformationNotPossible() {
		String expression = "(x+y)^2";
		String transformationRule = "a^2+2*a*b+b^2->a^2+2*a*b+b^2";
		assertFalse(TransformationUtil.isTransformationPossible(expression, transformationRule, true));
	}
}
