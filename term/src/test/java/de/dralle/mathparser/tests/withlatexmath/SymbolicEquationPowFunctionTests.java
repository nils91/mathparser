/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;



import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;


/**
 * @author Nils Dralle
 *
 */

public class SymbolicEquationPowFunctionTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	@ParameterizedTest
	@ValueSource(strings = {
			"2^3 =2*2*2",
			"x^4 =x*x*x*x",
			"4^0 = 1",
			"x^0 = 1",
			"4^1 = 4",
			"x^1 = x"
			})
	public void testSymbolicCorrectnessPowDefinitions(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"3^2 *3^5 =3^{(2+5)}",
			"3^{(2+5)}=3^7",
			"x^3 *x^5 =x^{(3+5)}",
			"x^{(3+5)}=x^8",
			"\\epsilon^3 * \\epsilon^{-5} = \\epsilon^{(3+(-5))}",
			"\\epsilon^{(3+(-5))}=\\epsilon^{-2}"
			})
	void testSymbolicCorrectnessPowFunctionMultiplication(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"3^7/3^5 =3^{(7-5)}",
			"3^{(7-5)}=3^2",
			"\\frac{x^5}{x^3} =x^{(5-3)}",
			"x^{(5-3)}=x^2",
			"\\epsilon^5/\\epsilon^{-3} = \\epsilon^{(5-(-3))}",
			"\\epsilon^{(5-(-3))}=\\epsilon^8"
			})
	void testSymbolicCorrectnessPowFunctionDivision(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"3^2*5^2=(3*5)^2",
			"(3*5)^2=15^2",
			"x^2*y^2=(x*y)^2"
			})
	void testSymbolicCorrectnessPowFunctionExpansion(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"(2^3)^4=2^{(3*4)}",
			"(x^2)^3=x^6",
			"(x^2*4)^2=x^4*4^2",
			"(\\epsilon^x)^2=\\epsilon^{(2x)}"
			})
	void testSymbolicCorrectnessPowInPowFunction(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"2^{-1}=1/2",
			"2^{-1}=1/2",
			"3^{-2}=1/3^2",
			"x^{-2}=1/x^2",
			"x^{-3}*y^{-2} = 1/{(x^3*y^2)}"
			})
	void testSymbolicCorrectnessPowFunctionWithNegativExponent(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"2^{(1/2)}=\\sqrt[2]{2}",
			"x^{(1/2)}=\\sqrt[2]{x}",
			"5^{(1/3)}=\\sqrt[3]{5}",
			"4^{(-1/2)}=1/\\sqrt[2]{4}",
			"4^{^{-1}/_2}=1/\\sqrt[2]{4}"
			})
	void testSymbolicCorrectnessPowFunctionRootFunction(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"2^{(3/5)}=\\sqrt[5]{2^3}",
			"2^{(3 \\div 5)}=\\sqrt[5]{2^3}",
			"x^{(3/5)}=\\sqrt[5]{x^3}",
			"2^{(3 \\div 5)}=\\sqrt[5]{2^3}",
			"\\epsilon^{(3/5)}=\\sqrt[5]{\\epsilon^3}",
			"2^{(-3/5)}=1/\\sqrt[5]{2^3}",
			"2^{(-3 \\div 5)}=1/\\sqrt[5]{2^3}",
			"2^{(-3 \\div 5)}=1\\div\\sqrt[5]{2^3}"
			})
	void testSymbolicCorrectnessPowFunctionRootFunctionRationalNumber(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\sqrt[3]{2}*\\sqrt[3]{4}=\\sqrt[3]{2*4}",
			"\\sqrt[3]{54}/\\sqrt[3]{2}=\\sqrt[3]{54/2}",
			"\\sqrt[3]{x}*\\sqrt[3]{y}=\\sqrt[3]{x*y}",
			"\\sqrt[3]{x}/\\sqrt[3]{y}=\\sqrt[3]{x/y}"
			})
	void testSymbolicCorrectnessRootFunctionMultiplicationDivision(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"\\sqrt[2]{\\sqrt[3]{5}}=\\sqrt[6]{5}",
			"\\sqrt[2]{\\sqrt[3]{x}}=\\sqrt[6]{x}",
			"\\sqrt{\\sqrt[3]{5}}=\\sqrt[6]{5}",
			"\\sqrt{\\sqrt[3]{x}}=\\sqrt[6]{x}",
			"\\sqrt[3]{\\sqrt{5}}=\\sqrt[6]{5}",
			"\\sqrt[3]{\\sqrt{x}}=\\sqrt[6]{x}",
			})	
	void testSymbolicCorrectnessRootInRootFunction(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));

	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"3= \\log_2{8}",
			"\\log_{\\epsilon}{3}= \\ln{3}",
			"\\epsilon^{\\ln{3}}=3",
			"\\ln{\\epsilon^3}=3",
			"\\log_{10}{2}=\\lg{2}",
			"\\lg{10^3}=3",
			"\\log_2{4}+\\log_2{8}=\\log_{2}{4*8}",
			"\\log_{3}{x}+\\log_{3}{y}=\\log_{3}{x*y}",
			"\\log_{3}{5}-\\log_{3}{7}=\\log_{3}{5/7}",
			"\\log_{3}{5^2}=2\\log_{3}{5}",
			"\\log_{4}{\\sqrt[5]{3}}=1/5*\\log_{4}{3}"
			})	
	void testSymbolicCorrectnessLogarithms(String equation) {
		assertTrue(SymbolicEqualityUtil.checkSymbolicEquality(equation, SymbolicEqualityUtil.buildSimplificationRule()));
	}
}
