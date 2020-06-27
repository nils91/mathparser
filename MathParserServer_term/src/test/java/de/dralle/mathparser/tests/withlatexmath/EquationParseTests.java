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
public class EquationParseTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	@ParameterizedTest
	@ValueSource(strings = {"x=y","x<y","x>y","x<y","x \\geq y","x \\leq y","x \\neq y"})
	void testParseSuccessful(String equation) {
		assertTrue(EquationParser.checkEquationSyntax(equation));
	}
	@ParameterizedTest
	@ValueSource(strings = {"x=y=z","x<y<z","x>y>z","x<y<z","x>=y>=z","x<=y<=z","x<>y<>z","x!=y!=z"})
	void testParseFails(String equation) {
		try {
			assertNotEquals(equation, EquationParser.checkEquationSyntaxThrowException(equation));
		} catch (Exception e) {
			assertTrue(true);
		}		
	}
}
