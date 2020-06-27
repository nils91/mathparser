/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.visitors.CalculateNumericalValueVisitor;

/**
 * @author Nils Dralle
 *
 */
class MatrixDeterminantCalculationTests {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void testDet2x2() {
		ExpressionNode expr = ExpressionParser
				.buildExpressionTreeFromString("\\det{\\begin{matrix}3&8\\\\4&6\\end{matrix}}");
		Double actual = expr.accept(new CalculateNumericalValueVisitor());
		int expected = -14;
		assertTrue(FastMath.abs(actual - expected) < 0.0001);
	}

	@Test
	void testDet2x2_2() {
		ExpressionNode expr = ExpressionParser
				.buildExpressionTreeFromString("\\det{\\begin{matrix}4&6\\\\3&8\\end{matrix}}");
		Double actual = expr.accept(new CalculateNumericalValueVisitor());
		int expected = 14;
		assertTrue(FastMath.abs(actual - expected) < 0.0001);
	}

	@Test
	void testDet2x2_dyn() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\det{\\begin{matrix}a&b\\\\c&d\\end{matrix}}=ad-bc");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, -10, 10, 2, 0.0001) > .99);
	}

	@Test
	void testDet3x3() {
		ExpressionNode expr = ExpressionParser
				.buildExpressionTreeFromString("\\det{\\begin{matrix}1&2&3\\\\4&5&6\\\\7&8&9\\end{matrix}}");
		Double actual = expr.accept(new CalculateNumericalValueVisitor());
		int expected = 0;
		assertTrue(FastMath.abs(actual - expected) < 0.0001);
	}

	@Test
	void testDet3x3_2() {
		ExpressionNode expr = ExpressionParser
				.buildExpressionTreeFromString("\\det{\\begin{matrix}2&5&2\\\\3&-3&1\\\\1&4&-4\\end{matrix}}");
		Double actual = expr.accept(new CalculateNumericalValueVisitor());
		int expected = 111;
		assertTrue(FastMath.abs(actual - expected) < 0.0001);
	}

	@Test
	void testDet3x3_3() {
		ExpressionNode expr = ExpressionParser
				.buildExpressionTreeFromString("\\det{\\begin{matrix}5&-1&9\\\\-1&6&-1\\\\9&-1&7\\end{matrix}}");
		Double actual = expr.accept(new CalculateNumericalValueVisitor());
		int expected = -270;
		assertTrue(FastMath.abs(actual - expected) < 0.0001);
	}

	@Test
	void testDet3x3_4() {
		ExpressionNode expr = ExpressionParser
				.buildExpressionTreeFromString("\\det{\\begin{matrix}6&1&1\\\\4&-2&5\\\\2&8&7\\end{matrix}}");
		Double actual = expr.accept(new CalculateNumericalValueVisitor());
		int expected = -306;
		assertTrue(FastMath.abs(actual - expected) < 0.0001);
	}

	@Test
	void testDet3x3_dyn() {
		EquationNode eq = EquationParser.buildEquationTreeFromString(
				"\\det{\\begin{matrix}a&b&c\\\\d&e&f\\\\g&h&i\\end{matrix}}=a(ei-fh)-b(di-fg)+c(dh-eg)");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, -5, 5, 5, 0.0001) > .99);
	}

	@Test
	void testDet4x4() {
		ExpressionNode expr = ExpressionParser.buildExpressionTreeFromString(
				"\\det{\\begin{matrix}5&0&3&-1\\\\3&0&0&4\\\\-1&2&4&-2\\\\1&0&0&5\\end{matrix}}");
		Double actual = expr.accept(new CalculateNumericalValueVisitor());
		int expected = 66;
		assertTrue(FastMath.abs(actual - expected) < 0.0001);
	}

}
