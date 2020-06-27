package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

public class SymbolicTrigometricFunctionTests {

	
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
//	@Disableds
	@ParameterizedTest
	@ValueSource(strings = {
			 "\\sin{-x}=-\\sin{x}"
			,"\\sin^2{x}=1-\\cos^2{x}"
			,"\\sin^2{x}=\\frac{\\tan^2{x}}{1+\\tan^2{x}}"
			,"\\sin^2{x}=\\frac{1}{1+\\cot^2{x}}"
			})
	public void testSymbolicEqSinusFunctions(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\cos{-x}=\\cos{x}"
			,"\\cos^2{x}=1-\\sin^2{x}"
			,"\\cos^2{x}=\\frac{1}{1+\\tan^2{x}}"
			,"\\cos^2{x}=\\frac{\\cot^2{x}}{1+\\cot^2{x}}"
			})
	public void testSymbolicCosinusFunctions(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\tan{x}=-\\tan{-x}"
			,"\\tan^2{x}=\\frac{\\sin^2{x}}{1-\\sin^{2}{x}}"
			,"\\tan^2{x}=\\frac{1-\\cos^{2}{x}}{\\cos^2{x}}"
			,"\\tan^2{x}=\\frac{1}{\\cot^2{x}}"
			})
	public void testSymbolicTangensFunctions(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\cot{x}=-\\cot{-x}"
			,"\\cot^2{x}=\\frac{\\cos^2{x}}{1-\\cos^{2}{x}}"
			,"\\cot^2{x}=\\frac{1-\\sin^{2}{x}}{\\sin^2{x}}"
			,"\\cot^2{x}=\\frac{1}{\\tan^2{x}}"
			})
	public void testSymbolicCotangensFunctions(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
}
