package de.dralle.mathparser.tests.withlatexmath;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
/**
 * 
 * @author Nils Dralle
 *
 */
public class SymbolicEquationTests {

	
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"3+2=5",
			"2x+3x=5x",
			"2x^2+3x^2=5x^2",
			"5x^2y + 7x^2y=12x^2y",
			"2xy + 3xy + 4z + 5z=5xy+9z",
			"xy + 3xy + 4z + 5z=4xy+9z",
			"xy - 3xy + 4z + 5z=-2xy+9z",
			"(x+y)^2+(x+y)*z=(x+y)^2+(x+y)*z"})
	void testSymbolicEqualityBasicArithmeticAddition(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"3-2=1",
			"3x-2x=x",
			"2x^2-3x^2=-x^2",
			"5x^2y-7x^2y=-2x^2y",
			"3\\epsilon^x-2\\epsilon^x=\\epsilon^x"})
	void testSymbolicEqualityBasicArithmeticSubtraction(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"3*2=6",
			"2x*3x=6x^2",
			"2x^2*3x^2=6x^4",
			"5x^2y*7x^2*y=35x^4y^2",
			"2xy*3xy*4z*5z=120x^2y^2z^2"})
	void testSymbolicEqualityBasicArithmeticMultiplication(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"12/3=4"})
	void testSymbolicEqualityBasicArithmeticDivision(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"3+2=2+3",
			"3x+2x=2x+3x",
			"3*2=2*3",
			"3x*2x=2x*3x"}
	)
	void testSymbolicEqualityBasicRuleCommutative(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"4+(3+2)=(4+3)+2",
			"4x+(3x+2x)=(4x+3x)+2x",
			"4*(3*2)=(4*3)*2",
			"4x*(3x*2x)=(4x*3x)*2x"}
	)
	void testSymbolicEqualityBasicRuleAssociative(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"3*(2+5)=3*2+3*5",
			"3*(2x+5)=3*2x+3*5",
			"3*2x+3*5=6x+15",
			"3x*(2x+5)=3x*2x+3x*5",
			"3x*2x+3x*5=6x^2+15x",
			"(2x+5)*3x=6x^2+15x"}
	)
	void testSymbolicEqualityBasicRuleDistributiv(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"+(+2) = +2",
			"-(-2) = +2",
			"+(-2) = -2",
			"-(+2) = -2",
			"+(+2x) = +2x",
			"-(-2x) = +2x",
			"+(-2x) = -2x",
			"-(+2x) = -2x"}
	)
	void testSymbolicEqualityBasicRuleSignsAndBracesAddition(String equation) {
		EquationNode buildEquationTreeFromString = EquationParser.buildEquationTreeFromString(equation);
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"+3*(+2) = +6",
			"-3*(-2) = +6",
			"+3*(-2) = -6",
			"-3*(+2) = -6",
			"+3x*(+2x) = +6x^2",
			"-3x*(-2x) = +6x^2",
			"+3x*(-2x) = -6x^2",
			"-3x*(+2x) = -6x^2"}
	)
	void testSymbolicEqualityBasicRuleSignsAndBracesMultipikation(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"+6/+3 = +2",
			"-6/-3 = +2",
			"+6/-3 = -2",
			"-6/+3 = -2",
			"+6x/+3x = +2",
			"-6x/-3x = +2",
			"+6x/-3x = -2",
			"-6x/+3x = -2"}
	)
	void testSymbolicEqualityBasicRuleSignsAndBracesDivision(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"5x^2 - 20x + 23 = 5x^2 - 20x + 23",
			"5 \\cdot x^2 - 20 \\cdot x + 23 = 5x^2 - 20x + 23",
			"23 + 5x^2 - 20x = 5x^2 - 20x + 23",
			"5 \\cdot (x - 2)^2 + 3 = 5x^2 - 20x + 23",
			"\\frac{2x + x^2}{x} = 2 + x",
			"\\frac{1+2x^2}{x} = \\frac{1}{x}+2x"}
	)
	void testSymbolicEqualityMisc(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
}
