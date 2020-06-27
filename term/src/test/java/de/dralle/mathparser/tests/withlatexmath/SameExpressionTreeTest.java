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
import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

/**
 * @author Nils Dralle
 *
 */
public class SameExpressionTreeTest {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void testImplicitMultiplication() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("xy");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x*y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplication2() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("2y");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2*y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplication3() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("-xy");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("-x*y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplication4() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("2xy");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2*x*y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplicationParenthesis() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("(x)(y)");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x*y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplicationParenthesis2() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("(x+y)(y+x)");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x+y)*(y+x)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplicationParenthesis3() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x(y+z)");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x*(y+z)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplicationParenthesis4() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("(x+y)z");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x+y)*z");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testImplicitMultiplicationParenthesis5() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("2x(y+z)");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2*x*(y+z)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testPowImplicitMultiplication() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x^2y^2z^2");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x^2)*(y^2)*(z^2)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testPowImplicitMultiplication2() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x^2y^2z^2");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x^2)*(y^2)*(z^2)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testPowImplicitMultiplication3() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x^2*y^2*z^2");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x^2)*(y^2)*(z^2)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testPowImplicitMultiplication4() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x^2(a+b)");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x^2)*(a+b)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	@Disabled
	void testPowImplicitMultiplication5() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("2x^22y^22z^2");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2*(x^2)*2*(y^2)*2*(z^2)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityPlusMul() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x+y*x+y");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x+(y*x)+y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityPlusMul2() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x*y+x*y");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x*y)+(x*y)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityPlus() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x+y+z");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x+y)+z");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityMinus() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x-y-z");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x-y)-z");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityMult() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x*y*z");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x*y)*z");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityDivision() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x/y/z");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x/y)/z");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityModulo() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x\\bmody\\bmodz");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("(x\\bmody)\\bmodz");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testAssociativityPower() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x^{y^z}");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x^{(y^z)}");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testIgnorePlusSignedExpr() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x++y");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x+y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testIgnorePlusSignedExpr2() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x-+y");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x-y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testFractionDivision() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("\\frac{x}{y}");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x/y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testFractionDivision4() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("2\\frac{x}{y}");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2x/y");
		assertFalse(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testFractionDivision5() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("2\\frac{x}{y}");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2(x/y)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testFractionDivision2() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("-\\frac{x}{y}");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("-x/y");
		assertFalse(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testFractionDivision3() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("-\\frac{x}{y}");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("-(x/y)");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}

	@Test
	void testIgnorePlusSignedExpr3() {
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("x-++y");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("x-y");
		assertTrue(ExpressionUtil.isEqual(ex1, ex2));
	}
}
