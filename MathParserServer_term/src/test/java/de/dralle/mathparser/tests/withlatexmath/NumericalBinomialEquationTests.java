package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

public class NumericalBinomialEquationTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"(a+b)^2 = a^2 +2ab+b^2",
			"(x+5)^2 = x^2 +10x+25",
			"(x+9)^2 = x^2 +18x+81",
			"(-x-9)^2 = x^2 +18x+81",
			"(2x+5)^2 = 4x^2 +20x+25",
			"(6x+5)^2 = 36x^2 +60x+25",
			"(x+y)^2 =x^2+2xy+y^2",
			"(xz+y)^2 =x^2*z^2 +2xzy+y^2"
			})
	public void testNumericalCorrectnessFirstBinomialEquation(String equation) {
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(equation, -10, 10, 1);
		assertTrue(numericalDeviation<0.0001, "Gleichung : " + equation + " Abweichung : " + numericalDeviation);
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = {
			"(a-b)^2 = a^2-2ab+b^2",
			"(x-5)^2 = x^2-10x+25",
			"(x-9)^2 = x^2-18x+81",
			"(-x+9)^2 = x^2-18x+81",
			"(2x-5)^2 = 4x^2-20x+25",
			"(6x-5)^2 = 36x^2-60x+25",
			"(x-y)^2 = x^2-2xy+y^2",
			"(xz-y)^2 = x^2*z^2-2xzy+y^2"
			})
	public void testNumericalCorrectnessSecondBinomialEquation(String equation) {
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(equation, -10, 10, 1);
		assertTrue(numericalDeviation<0.0001, "Gleichung : " + equation + " Abweichung : " + numericalDeviation);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"(a+b)(a-b) = a^2-b^2",
			"(x+5)(x-5) = x^2-25",
			"(x+9)(x-9) = x^2-81",
			"(3x+5)(3x-5) = 9x^2 -25",
			"(7x+9)(7x-9) = 49x^2 -81",	
			"(x+y)(x-y) =x^2-y^2",
			"(xz-y)^2 =x^2*z^2-2xzy+y^2"
			})
	public void testNumericalCorrectnessThirdBinomialEquation(String equation) {
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(equation, -10, 10, 1);
		assertTrue(numericalDeviation<0.0001, "Gleichung : " + equation + " Abweichung : " + numericalDeviation);
	}
}
