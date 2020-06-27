package de.dralle.mathparser.tests.withlatexmath;

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
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

class SortingTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void testAddSortNotSwap() {
		ExpressionNode expressionBeforeSorting = ExpressionParser.buildExpressionTreeFromString("x+y");
		ExpressionNode expressionExpected = ExpressionParser.buildExpressionTreeFromString("x+y");
		CompositeTransformationRule rule = TransformationRuleBuilder.buildNodeContentReorderingRule();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(expressionBeforeSorting);
		} catch (Exception e) {
		}
		assertTrue(transformed == null || ExpressionUtil.isEqual(transformed, expressionExpected));

	}

	@Test
	void testMulSortNotSwap() {
		ExpressionNode expressionBeforeSorting = ExpressionParser.buildExpressionTreeFromString("x*y");
		ExpressionNode expressionExpected = ExpressionParser.buildExpressionTreeFromString("x*y");
		CompositeTransformationRule rule = TransformationRuleBuilder.buildNodeContentReorderingRule();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(expressionBeforeSorting);
		} catch (Exception e) {
		}
		assertTrue(transformed == null || ExpressionUtil.isEqual(transformed, expressionExpected));
	}

	@Test
	void testAddSortSwap() {
		ExpressionNode expressionBeforeSorting = ExpressionParser.buildExpressionTreeFromString("z+y");
		ExpressionNode expressionExpected = ExpressionParser.buildExpressionTreeFromString("y+z");
		CompositeTransformationRule rule = TransformationRuleBuilder.buildNodeContentReorderingRule();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(expressionBeforeSorting);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(transformed, expressionExpected));

	}

	@Test
	void testMulSortSwap() {
		ExpressionNode expressionBeforeSorting = ExpressionParser.buildExpressionTreeFromString("z*y");
		ExpressionNode expressionExpected = ExpressionParser.buildExpressionTreeFromString("y*z");
		CompositeTransformationRule rule = TransformationRuleBuilder.buildNodeContentReorderingRule();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(expressionBeforeSorting);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(transformed, expressionExpected));

	}

	@Test
	void testAddSortSwapNested() {
		ExpressionNode expressionBeforeSorting = ExpressionParser.buildExpressionTreeFromString("cd+ba");
		ExpressionNode expressionExpected = ExpressionParser.buildExpressionTreeFromString("ab+cd");
		CompositeTransformationRule rule = TransformationRuleBuilder.buildNodeContentReorderingRule();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(expressionBeforeSorting);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(transformed, expressionExpected));
	}

}
