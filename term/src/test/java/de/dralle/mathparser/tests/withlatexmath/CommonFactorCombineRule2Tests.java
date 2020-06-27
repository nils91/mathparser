/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

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
import de.dralle.mathparser.rules.CombineAddNodes;
import de.dralle.mathparser.rules.CommonFactorCombinationRule2;
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.NumberCombinationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

/**
 * @author Nils Dralle
 *
 */
class CommonFactorCombineRule2Tests {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void testEz() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("ac+bc");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(a+b)c");
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testTripleCombineTriple() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("ac+bc+cd");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(a+b+d)c");
		CombineAddNodes prerule = new CombineAddNodes();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			ExpressionNode transformedTarget = prerule.apply(target);
			if (transformedTarget != null) {
				target = transformedTarget;
			}
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testEzNotCombine() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("ac+bc");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(a+b)c");
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertFalse(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testTripleCombineTripleNotCombine() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("ac+bc+cd");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(a+b+d)c");
		CombineAddNodes prerule = new CombineAddNodes();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			target = prerule.apply(target);
			transformed = prerule.apply(start);
			transformed = rule.apply(transformed);
		} catch (Exception e) {
		}
		assertFalse(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testDualCombineInteger() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2c+3c");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3)c");
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testTripleCombineTripleIntegerNumbersCombine() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2c+3c+4c");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3+4)c");
		CombineAddNodes prerule = new CombineAddNodes();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			ExpressionNode transformedTarget = prerule.apply(target);
			if (transformedTarget != null) {
				target = transformedTarget;
			}
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testDualCombineLongCommonFactor() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2ab+3ab");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3)ab");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
			transformed = prerule.apply(transformed);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testDualCombineLongCommonFactor1() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2a^2b+5a+3a^2b+6a+7*\\sin{a}");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3)a^2b+(5+6)a+7*\\sin{a}");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		NumberCombinationRule postrule = new NumberCombinationRule();
		rule.setOnlyForConstantValues(false);
		rule.setOnlyForNonConstantCommonFactors(false);
		ExpressionNode transformed = null;
		try {
			ExpressionNode transformedTarget = prerule.apply(target);
			if (transformedTarget != null) {
				target = transformedTarget;
			}
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
			transformed = prerule.apply(transformed);
			target = postrule.apply(target);
			transformed = postrule.apply(transformed);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testTripleCombineLongCommonFactor() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2ab+3ab+4ab");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3+4)ab");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			ExpressionNode transformedTarget = prerule.apply(target);
			if (transformedTarget != null) {
				target = transformedTarget;
			}
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
			transformed = prerule.apply(transformed);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testDualCombineLongCommonFactorP() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2(ab)+3(ab)");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3)(ab)");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
			transformed = prerule.apply(transformed);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testTripleCombineLongCommonFactorP() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2(ab)+3(ab)+4(ab)");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3+4)(ab)");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			ExpressionNode transformedTarget = prerule.apply(target);
			if (transformedTarget != null) {
				target = transformedTarget;
			}
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);

			transformedTarget = prerule.apply(target);
			if (transformedTarget != null) {
				target = transformedTarget;
			}
			transformed = prerule.apply(transformed);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testCombineNoRemainder() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2x+x");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+1)x");
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testCombineNoRemainders() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x+x");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(1+1)x");
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testCombineNoRemaindersDifferent() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2y+x+x");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(1+1)x+y*2");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CompositeTransformationRule rule = TransformationRuleBuilder.buildCommonFactorsRuleForNumbers();

		ExpressionNode transformed = null;
		try {
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testCombineNoRemaindersDifferentLongTerm() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2y+wx+wx");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(1+1)(wx)+2y");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
			transformed = prerule.apply(transformed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testCombineNoRemaindersX3() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x+x+x");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(1+1+1)x");
		CombineAddNodes prerule = new CombineAddNodes();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void testCombineNoRemainderX3() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2x+x+x+x");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+1+1+1)x");
		CombineAddNodes prerule = new CombineAddNodes();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}
}
