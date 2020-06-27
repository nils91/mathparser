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
class BinomialCoefficentTests {

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
	void testBinomSym_1() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\binom{n}{1}=\\binom{n}{n-1}");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, 1, 50, 1, 0.0001) > .99);
	}
	@Test
	void testBinomFactorial_1() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\binom{n}{1}=\\frac{n!}{(n-1)! \\times 1}");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, 1, 50, 1, 0.0001) > .99);
	}
	@Test
	void testBinomSym_2() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\binom{n}{2}=\\binom{n}{n-2}");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, 2, 50, 1, 0.0001) > .99);
	}
	@Test
	void testBinomFactorial_2() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\binom{n}{2}=\\frac{n!}{(n-2)! \\times 2!}");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, 2, 50, 1, 0.0001) > .99);
	}
	@Test
	void testBinomProd_1() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\binom{n}{1}=\\prod_{i=1}^1{\\frac{n-1+i}{i}}");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, 1, 50, 1, 0.0001) > .99);
	}
	@Test
	void testBinomProd_2() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\binom{n}{2}=\\prod_{i=1}^2{\\frac{n-2+i}{i}}");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, 2, 50, 1, 0.0001) > .99);
	}
}
