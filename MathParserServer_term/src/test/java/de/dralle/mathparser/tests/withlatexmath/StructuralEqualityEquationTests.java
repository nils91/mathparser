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
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;

/**
 * @author Nils Dralle
 *
 */
public class StructuralEqualityEquationTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	@Test
	void testExaxtStructuralEquality() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		assertTrue(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, true, true));
	}
	@Test
	void testExaxtStructuralInequality() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a*b)^2=(a*b)^2");
		assertFalse(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, true, true));
	}
	@Test
	void testExaxtStructuralEqualityNotIgnoreNumberValues() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^3=(a+b)^3");
		assertFalse(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, true, true));
	}
	@Test
	void testExaxtStructuralEqualityIgnoreNumberValues() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^3=(a+b)^3");
		assertTrue(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, false, true));
	}
	@Test
	void testExaxtStructuralEqualityNotIgnoreNames() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(c+d)^2=(c+d)^2");
		assertFalse(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, true, true));
	}
	@Test
	void testExaxtStructuralEqualityIgnoreNames() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(c+d)^2=(c+d)^2");
		assertTrue(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, true, false));
	}
	@Test
	void testExaxtStructuralEqualityIgnoreNamesAndValues() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(c+d)^3=(c+d)^3");
		assertTrue(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, false, false));
	}
	@Test
	void testExaxtStructuralEqualityCommutative() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		assertEquals(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, true, true, true),NumericalEquationUtil.isStructurallyEqual(eq2, eq1, true, true, true));
	}
	
	@Test
	void testInexaxtStructuralEquality() {		
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("((c*d)+(e*f))^2=((c*d)+(e*f))^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		assertTrue(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, false, true, true));
	}
	@Test
	void testInexaxtStructuralInequalitySwapParams() {		
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("((c*d)+(e*f))^2=((c*d)+(e*f))^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		assertFalse(NumericalEquationUtil.isStructurallyEqual(eq2, eq1, false, true, true));
	}
	@Test
	void testInexaxtStructuralUnequality() {		
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("((c+d)*(e+f))^2=((c+d)*(e+f))^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		assertFalse(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, false, true, true));
	}
	@Test
	void testInexactStructuralEqualityNotIgnoreNumberValues() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("((c*d)+(e*f))^2=((c*d)+(e*f))^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^3=(a+b)^3");
		assertFalse(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, false, true, true));
	}
	@Test
	void testInexactStructuralEqualityIgnoreNumberValues() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("((c*d)+(e*f))^2=((c*d)+(e*f))^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(a+b)^3=(a+b)^3");
		assertTrue(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, false, false, true));
	}
	@Test
	void testInexactStructuralEqualityIrrelevantNames() {
		EquationNode eq1 = EquationParser.buildEquationTreeFromString("(a+b)^2=(a+b)^2");
		EquationNode eq2 = EquationParser.buildEquationTreeFromString("(c+d)^2=(c+d)^2");
		assertTrue(NumericalEquationUtil.isStructurallyEqual(eq1, eq2, false, true, true));
	}
}
