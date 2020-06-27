/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

/**
 * @author Nils Dralle
 *
 */
public class StructuralEqualityExpressionTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void testExaxtStructuralEquality() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		assertTrue(ExpressionUtil.isEqual(eq1, eq2));
	}

	@Test
	void testExaxtStructuralInequality() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a*b)^2");
		assertFalse(ExpressionUtil.isEqual(eq1, eq2));
	}

	@Test
	void testExaxtStructuralInequalityDeepTree() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("((a+a)+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		assertFalse(ExpressionUtil.isEqual(eq1, eq2));
	}

	@Test
	void testExaxtStructuralEqualityNotIgnoreNumberValues() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^3");
		assertFalse(ExpressionUtil.isEqual(eq1, eq2));
	}

	@Test
	void testExaxtStructuralEqualityIgnoreNumberValues() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^3");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, true, false, true, false));
	}

	@Test
	void testExaxtStructuralEqualityNotIgnoreConstantValues() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^\\epsilon");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^\\pi");
		assertFalse(ExpressionUtil.isEqual(eq1, eq2));
	}

	@Test
	void testExaxtStructuralEqualityIgnoreConstantValues() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^\\epsilon");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^\\pi");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, true, false, true, false));
	}

	@Test
	void testExaxtStructuralEqualityNotIgnoreNames() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(c+d)^2");
		assertFalse(ExpressionUtil.isEqual(eq1, eq2));
	}

	@Test
	void testExaxtStructuralEqualityIgnoreNames() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(c+d)^2");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, true, true, false, false));
	}

	@Test
	void testExaxtStructuralEqualityIgnoreNamesAndValues() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(c+d)^3");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, true, false, false, false));
	}

	@Test
	void testExaxtStructuralEqualityCommutative() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		assertEquals(ExpressionUtil.isEqual(eq1, eq2), ExpressionUtil.isEqual(eq2, eq1));
	}

	@Test
	void testInexaxtStructuralEquality() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("((c*d)+(e*f))^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}

	@Test
	void testInexaxtStructuralInequalitySwapParams() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("((c*d)+(e*f))^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		assertFalse(ExpressionUtil.isStructurallyEqual(eq2, eq1, false, true, true, true));
	}

	@Test
	void testInexaxtStructuralUnequality() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("((c+d)*(e+f))^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		assertFalse(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}

	@Test
	void testInexactStructuralEqualityNotIgnoreNumberValues() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("((c*d)+(e*f))^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^3");
		assertFalse(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}

	@Test
	void testInexactStructuralEqualityIgnoreNumberValues() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("((c*d)+(e*f))^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(a+b)^3");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, false, true, true));
	}

	@Test
	void testInexactStructuralEqualityIrrelevantNames() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("(a+b)^2");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("(c+d)^2");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}

	@Test
	void testInexactStructuralEqualityyMultinodesMul() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("x*x*2*3");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("x*x");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}

	@Test
	void testInexactStructuralEqualityyMultinodesAdd() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("x+x+2+3");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("x+x");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}

	@Test
	void testInexactStructuralEqualityyMultinodesAddNested() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("x+x+2*3");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("x+x");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}

	@Test
	void testInexactStructuralEqualityyMultinodesAddNested2() {
		ExpressionNode eq1 = ExpressionParser.buildExpressionTreeFromString("x+x+2/3");
		ExpressionNode eq2 = ExpressionParser.buildExpressionTreeFromString("x+x");
		assertTrue(ExpressionUtil.isStructurallyEqual(eq1, eq2, false, true, true, true));
	}
}
