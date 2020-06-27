package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

public class NumericalTests {

	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
//			"\\sqrt[a]{b^{-1*c}}=\\frac{1}{\\sqrt[a]{b^c}}",
//			"\\frac{a}{\\frac{b}{c}}=\\frac{a*c}{b}",
			"\\epsilon^{\\log_{\\epsilon}{a}}=a"}
	)
	void testNumericalCorrectnessForRules(String equation) {
		double percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(equation, 1, 10, 1, 0.0001);
		assertTrue(percentage>0.999, "Gleichung : " + equation + " Abweichung : " + percentage);
	}
	

	
}
