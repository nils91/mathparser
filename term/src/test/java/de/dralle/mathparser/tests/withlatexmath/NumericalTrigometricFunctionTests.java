package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

public class NumericalTrigometricFunctionTests {

	
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	@Test
	public void sineSquaredNumeric() {
		EquationNode eq = EquationParser.buildEquationTreeFromString("\\cos^2{x}=1-\\sin^2{x}");
//		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(eq,  -1,1,0.1);
//		float percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq,  -1,1,0.1,0.0001);
		
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(eq,  (-Math.PI + Math.PI/10),( Math.PI-Math.PI/10), Math.PI/10);
		float percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq,  (-Math.PI + Math.PI/10),( Math.PI -  Math.PI/10),Math.PI/10, 0.0001);
		

		assertTrue(numericalDeviation>0, "Gleichung : " + eq + " Abweichung : " + numericalDeviation);
		assertTrue(percentage>0.999, "Gleichung : " + eq + " Prozent Übereinstimmung: " + percentage);
	}
	@Test
	public void testCoSineSquaredNumeric() {
		EquationNode eq = EquationParser.buildEquationTreeFromString("\\cos^2{x}=1-\\sin^2{x}");
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(eq,  -1,1,1);
		float percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq,  -1,1,1,0.0001);
		assertTrue(numericalDeviation<0.001, "Gleichung : " + eq + " Abweichung : " + numericalDeviation);
		assertTrue(percentage>0.999, "Gleichung : " + eq + " Prozent Übereinstimmung: " + percentage);
	}
//	@Disableds
	@ParameterizedTest
	@ValueSource(strings = {

			 "\\sin{-x}=-\\sin{x}"
			,"\\sin^2{x}=1-\\cos^2{x}"
			,"\\sin^2{x}=\\frac{\\tan^2{x}}{1+\\tan^2{x}}"
			,"\\sin^2{x}=\\frac{1}{1+\\cot^2{x}}"
//			"\\sin{-x}=-\\sin{x}"
//			,"|\\sin{x}|=\\sqrt{1-\\cos^{2}{x}}"
//			,"\\sin^2{x}=1-\\cos^2{x}"
//			,"|\\sin{x}|=\\frac{|\\tan{x}|}{\\sqrt{1+\\tan^2{x}}}"
//			,"|\\sin{x}|=\\frac{1}{\\sqrt{1+\\cot^2{x}}}"
			})
	public void testNumericalSinusFunctions(String equation) {
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(equation,  (-Math.PI + Math.PI/9),( Math.PI-Math.PI/10), Math.PI/10);
		float percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(equation,  (-Math.PI + Math.PI/9),( Math.PI -  Math.PI/10),Math.PI/10, 0.0001);
		assertTrue(numericalDeviation<0.0001, "Gleichung : " + equation + " Abweichung : " + numericalDeviation);
		assertTrue(percentage>0.999, "Gleichung : " + equation + " Prozent Übereinstimmung: " + percentage);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\cos{-x}=\\cos{x}"
			,"\\cos^2{x}=1-\\sin^2{x}"
			,"\\cos^2{x}=\\frac{1}{1+\\tan^2{x}}"
			,"\\cos^2{x}=\\frac{\\cot^2{x}}{1+\\cot^2{x}}"
			})
	public void testNumericalCosinusFunctions(String equation) {
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(equation,  (-Math.PI + Math.PI/9),( Math.PI-Math.PI/10), Math.PI/10);
		float percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(equation,  (-Math.PI + Math.PI/9),( Math.PI -  Math.PI/10),Math.PI/10, 0.0001);
		assertTrue(numericalDeviation<0.0001, "Gleichung : " + equation + " Abweichung : " + numericalDeviation);
		assertTrue(percentage>0.999, "Gleichung : " + equation + " Prozent Übereinstimmung: " + percentage);
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\tan{x}=-\\tan{-x}"
			,"\\tan^2{x}=\\frac{\\sin^2{x}}{1-\\sin^{2}{x}}"
			,"\\tan^2{x}=\\frac{1-\\cos^{2}{x}}{\\cos^2{x}}"
			,"\\tan^2{x}=\\frac{1}{\\cot^2{x}}"
			})
	public void testNumericalTangensFunctions(String equation) {
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(equation,  (-Math.PI + Math.PI/9),( Math.PI-Math.PI/10), Math.PI/10);
		float percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(equation,  (-Math.PI + Math.PI/9),( Math.PI -  Math.PI/10),Math.PI/10, 0.0001);
		assertTrue(numericalDeviation<0.0001, "Gleichung : " + equation + " Abweichung : " + numericalDeviation);
		assertTrue(percentage>0.999, "Gleichung : " + equation + " Prozent Übereinstimmung: " + percentage);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\cot{x}=-\\cot{-x}"
			,"\\cot^2{x}=\\frac{\\cos^2{x}}{1-\\cos^{2}{x}}"
			,"\\cot^2{x}=\\frac{1-\\sin^{2}{x}}{\\sin^2{x}}"
			,"\\cot^2{x}=\\frac{1}{\\tan^2{x}}"
			})
	public void testNumericalCotangensFunctions(String equation) {
		double numericalDeviation = NumericalEquationUtil.getNumericalDeviation(equation,  (-Math.PI + Math.PI/9),( Math.PI-Math.PI/10), Math.PI/10);
		float percentage = NumericalEquationUtil.checkNumericalCorrectnessPercentage(equation,  (-Math.PI + Math.PI/9),( Math.PI -  Math.PI/10),Math.PI/10, 0.0001);
		assertTrue(numericalDeviation<0.0001, "Gleichung : " + equation + " Abweichung : " + numericalDeviation);
		assertTrue(percentage>0.999, "Gleichung : " + equation + " Prozent Übereinstimmung: " + percentage);
	}
	
}
