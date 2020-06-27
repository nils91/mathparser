/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
import de.dralle.mathparser.rules.AbstractTransformationRule;
import de.dralle.mathparser.rules.NumberCombinationRule;

/**
 * @author Nils Dralle
 *
 */
class CombineNumberRuleTests {

	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void testSimpleAddReplaceIntegerNotNull() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3+5");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("8");
		AbstractTransformationRule rule = new NumberCombinationRule();
		try {
			assertNotNull(rule.apply(start));
		} catch (Exception e) {
			fail("Bad");
		}
	}

	@Test
	void testSimpleAddReplaceIntegerNode() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3+5");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("8");
		AbstractTransformationRule rule = new NumberCombinationRule();
		try {
			assertTrue(ExpressionUtil.isEqual(rule.apply(start), target));
		} catch (Exception e) {
			fail("Bad");
		}
	}

	@Test
	void testSimpleAddReplaceNullIfNotInteger() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3.4+5.6");
		AbstractTransformationRule rule = new NumberCombinationRule();
		try {
			assertNull(rule.apply(start));
		} catch (Exception e) {
			fail("Bad");
		}
	}

	@Test
	void testSimpleAddReplaceRealResultInteger() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3.4+5.6");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("9");
		NumberCombinationRule rule = new NumberCombinationRule();
		rule.setOnlyOnIntegers(false);
		try {
			assertTrue(ExpressionUtil.isEqual(rule.apply(start), target));
		} catch (Exception e) {
			fail("Bad");
		}
	}

	@Test
	void testSimpleAddReplaceRealResultNotInteger() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3.4+5.61");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("9.01");
		NumberCombinationRule rule = new NumberCombinationRule();
		rule.setOnlyOnIntegers(false);
		rule.setResultInteger(false);
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e1) {
			fail("Bad");
		}
		assertTrue(ExpressionUtil.isEqual(transformed, target));

	}

	@Test
	void testSimpleDivisionReplaceIntResultNotInteger() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3.4+5.6");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("9");
		NumberCombinationRule rule = new NumberCombinationRule();
		rule.setOnlyOnIntegers(false);
		try {
			assertTrue(ExpressionUtil.isEqual(rule.apply(start), target));
		} catch (Exception e) {
			fail("Bad");
		}
	}

	@Test
	void testSimpleDivisionReplaceIntResultInteger() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("30/5");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("6");
		NumberCombinationRule rule = new NumberCombinationRule();
		rule.setResultInteger(false);
		try {
			assertTrue(ExpressionUtil.isEqual(rule.apply(start), target));
		} catch (Exception e) {
			fail("Bad");
		}
	}

	@Test
	void testSimpleAddReplaceIntegerRecursion() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3*5+x");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("15+x");
		AbstractTransformationRule rule = new NumberCombinationRule();
		try {
			assertTrue(ExpressionUtil.isEqual(rule.apply(start), target));
		} catch (Exception e) {
			fail("Bad");
		}
	}
}
