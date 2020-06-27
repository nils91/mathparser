/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

/**
 * @author Nils Dralle
 *
 */
public class ExpressionParseTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	@ParameterizedTest
	@ValueSource(strings = {"x+y","x^2"})
	void testParseSuccessful(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	//
	@ParameterizedTest
	@ValueSource(strings = {
			"3+2","5",
			"2x+3x","5x",
			"2x^2+3x^2","5x^2",
			"5x^2y + 7x^2y","12x^2y",
			"2xy + 3xy + 4z + 5z","5xy+9z"})
	void testParseSuccessfullyBasicArithmeticAddition(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	@ParameterizedTest
	@ValueSource(strings = {
			"3-2","1",
			"3x-2x","x",
			"2x^2-3x^2","x^2",
			"5x^2y-7x^2y","-2x^y",
			"3\\epsilon^x-2\\epsilon^x","\\epsilon^x"})
	void testParseSuccessfullyBasicArithmeticSubtraction(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	@ParameterizedTest
	@ValueSource(strings = {
			"3*2","6",
			"2x*3x","6x^2",
			"2x^2*3x^2","6x^4",
			"5x^2y*7x^2*y","35x^4y",
			"2xy*3xy*4z*5z","120x^2y^2z^2"})
	void testParseSuccessfullyBasicArithmeticMultiplication(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	@ParameterizedTest
	@ValueSource(strings = {
			"12/3","4",
			"12 \\div 3"})
	void testParseSuccessfullyBasicArithmeticDivision(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	@ParameterizedTest
	@ValueSource(strings = {
			"3+2","2+3",
			"3x+2x","2x+3x",
			"3*2","2*3",
			"3x*2x","2x*3x"}
	)
	void testParseSuccessfullyBasicRuleCommutative(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	@ParameterizedTest
	@ValueSource(strings = {
			"4+(3+2)","(4+3)+2",
			"4x+(3x+2x)","(4x+3x)+2x",
			"4*(3*2)","(4*3)*2",
			"4x*(3x*2x)","(4x*3x)*2x"}
	)
	void testParseSuccessfullyBasicRuleAssociative(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	@ParameterizedTest
	@ValueSource(strings = {
			"3*(2+5)","3*2+3*5",
			"3*(2x+5)","3*2x+3*5","6x+15",
			"3x*(2x+5)","3x*2x+3x*5","6x^2+15x"}
	)
	void testParseSuccessfullyBasicRuleDistributiv(String expression) {
		assertTrue(ExpressionParser.checkExpressionSyntax(expression));
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = {"x²³"})
	void testParseFails(String expression) {
		try {
			assertNotEquals(expression.toString(), ExpressionParser.checkExpressionSyntaxThrowException(expression));
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
