/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

/**
 * @author Nils Dralle
 *
 */
public class TrigonometryFunctionTests {

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
	void testSineDegreeRadians() {
		EquationNode equation = EquationParser.buildEquationTreeFromString("\\sin{90°}=\\sin{\\frac{\\pi}{2}}");
		assertTrue(NumericalEquationUtil.getNumericalDeviation(equation, 0, 1, 1)<0.0001);
	}
	@Test
	void testSineDegreeRadiansDyn() {
		EquationNode equation = EquationParser.buildEquationTreeFromString("\\sin{x°}=\\sin{x*\\pi/180}");
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(equation, 0, 360,1,0.0001)>0.99);
	}

}
