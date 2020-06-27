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
class SumProdTests {

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
	void testSumMul() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\sum_{i=0}^{n-1}a=a*n");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq,0, 20, 1, 0.0001) > .99);
	}
	@Test
	void testProdPow() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\prod_{i=0}^{n-1}a=a^n");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq,1, 5, 1, 0.0001) > .99);
	}
	@Test
	void testProdFactorial() {
		EquationNode eq = EquationParser
				.buildEquationTreeFromString("\\prod_{i=1}^{n}i=n!");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq,1, 10, 1, 0.0001) > .99);
	}
}
