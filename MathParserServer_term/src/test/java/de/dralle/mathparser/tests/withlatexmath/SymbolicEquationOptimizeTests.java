package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.RuleBlocks;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.AbstractTransformationRule;
import de.dralle.mathparser.rules.RepeatableRuleBlock;

public class SymbolicEquationOptimizeTests {

	private static Logger log = Logger.getLogger(SymbolicEquationOptimizeTests.class);

	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@ParameterizedTest
	@ValueSource(strings = { "2^3 =2*2*2", "x^4 =x*x*x*x", "4^0 = 1", "x^0 = 1", "4^1 = 4", "x^1 = x" })
	public void testSymbolicCorrectnessPowDefinitions(String equation) {

		AbstractTransformationRule combinedRules = buildRules();
		solveAndTestRules(equation, combinedRules);

	}

	@ParameterizedTest
	@ValueSource(strings = { "3^2 *3^5 =3^{2+5}", "3^{2+5}=3^7", "x^3 *x^5 =x^{3+5}", "x^{3+5}=x^8",
			"\\epsilon^3 * \\epsilon^{-5} = \\epsilon^{3+{-5}}", "\\epsilon^{3+{-5}}=\\epsilon^{-2}" })
	public void testSymbolicCorrectnessPowFunctionMultiplication(String equation) {

		AbstractTransformationRule combinedRules = buildRules();
		solveAndTestRules(equation, combinedRules);
	}

	@ParameterizedTest
	@ValueSource(strings = { "((a+a)*b) = 2ab", "(a*b)+(b*a) = 2ab", "a(a+b)+b(a+b)=a^2+2ab+b^2",
			"(a+b)^2+a(a+b)+b(a+b)=2*a^2+2ab+2ab+2*b^2" })
	public void testOptimizedCorrectnessPowFunctionMultiplication(String equation) {

		List<AbstractTransformationRule> linearRules = buildLinearRules();
		optimzedSolveAndTestRules(equation, linearRules);
	}

	@ParameterizedTest
	@ValueSource(strings = { "((a+a)*b) = 2ab", "(a*b)+(b*a) = 2ab", "a(a+b)+b(a+b)=a^2+2ab+b^2",
			"(a+b)^2+a(a+b)+b(a+b)=2*a^2+2ab+2ab+2*b^2",
			"(a+b)^2+a(a+b)+b(a+b)+\\sin{a}=2*a^2+2ab+2ab+2*b^2+\\sin{a}" })
	public void testCorrectnessPowFunctionMultiplication(String equation) {

		AbstractTransformationRule rules = buildRules();
		solveAndTestRules(equation, rules);
	}

	@ParameterizedTest
	@ValueSource(strings = { "1-\\cos^{2}{x}=\\sin^2{x}" })
	public void testCorrectnessTrignometricFunctions(String equation) {

		AbstractTransformationRule rules = buildRules();
		solveAndTestRules(equation, rules);
	}

	@Test
	void targetedDistributiveRuleTest() {
		AbstractTransformationRule combinedRules = buildRules();
		solveAndTestRules("(a+b)^2+a(a+b)+b(a+b)+\\sin{a}=2*a^2+2ab+2ab+2*b^2+\\sin{a}", combinedRules);
	}

	private void optimzedSolveAndTestRules(String equation, List<AbstractTransformationRule> linearRules) {

		EquationNode equationTree = EquationParser.buildEquationTreeFromString(equation);
		ExpressionNode leftExpression = equationTree.getLeftExpression();
		ExpressionNode rightExpression = equationTree.getRightExpression();
		List<AbstractTransformationRule> possibleRules = new ArrayList<AbstractTransformationRule>();

		log.debug("-----------BEGIN LeftExpression = " + leftExpression);
		possibleRules.clear();
		for (Iterator iterator = linearRules.iterator(); iterator.hasNext();) {
			AbstractTransformationRule abstractTransformationRule = (AbstractTransformationRule) iterator.next();
			if (abstractTransformationRule.isPossible(leftExpression)) {
				possibleRules.add(abstractTransformationRule);
			}
		}
		if (possibleRules.size() > 0) {
			RepeatableRuleBlock repeatableRuleBlock = new RepeatableRuleBlock(possibleRules);
			ExpressionNode transformedLeftExpression;
			try {
				transformedLeftExpression = repeatableRuleBlock.apply(leftExpression, true);
				log.debug("-------------END LeftExpression = " + transformedLeftExpression);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		log.debug("-----------BEGIN RightExpression = " + rightExpression);
		possibleRules.clear();
		for (Iterator iterator = linearRules.iterator(); iterator.hasNext();) {
			AbstractTransformationRule abstractTransformationRule = (AbstractTransformationRule) iterator.next();
			if (abstractTransformationRule.isPossible(rightExpression)) {
				possibleRules.add(abstractTransformationRule);
			}
		}
		if (possibleRules.size() > 0) {
			RepeatableRuleBlock repeatableRuleBlock = new RepeatableRuleBlock(possibleRules);
			ExpressionNode transformedRightExpression;
			try {
				transformedRightExpression = repeatableRuleBlock.apply(rightExpression, true);
				log.debug("-------------END RightExpression = " + transformedRightExpression);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void solveAndTestRules(String equation, AbstractTransformationRule combinedRules) {
		EquationNode equationTree = EquationParser.buildEquationTreeFromString(equation);
		ExpressionNode leftExpression = equationTree.getLeftExpression();
		ExpressionNode rightExpression = equationTree.getRightExpression();

		log.debug("Equation = " + equation);
		try {
			log.debug("-----------BEGIN LeftExpression = " + leftExpression);
			ExpressionNode transformedLeftExpression = combinedRules.apply(leftExpression, true);
			log.debug("-------------END LeftExpression = " + transformedLeftExpression);

			log.debug("-----------BEGIN RightExpression = " + rightExpression);
			ExpressionNode transformedRightExpression = combinedRules.apply(rightExpression, true);
			log.debug("-------------END RightExpression = " + transformedRightExpression);
			log.debug("==================================");

			transformedLeftExpression = transformedLeftExpression == null ? leftExpression : transformedLeftExpression;
			transformedRightExpression = transformedRightExpression == null ? rightExpression
					: transformedRightExpression;
			assertTrue(transformedLeftExpression.equals(transformedRightExpression));

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

	private List<AbstractTransformationRule> buildLinearRules() {
		List<AbstractTransformationRule> ruleList = new ArrayList<AbstractTransformationRule>();
		ruleList.addAll(RuleBlocks.buildBinomialRules());
		ruleList.addAll(RuleBlocks.buildDistributiveRules());
		ruleList.addAll(RuleBlocks.buildNeutralElementRules());
		ruleList.addAll(RuleBlocks.buildReplaceWithConstantFactorRules());
		return ruleList;
	}

	private AbstractTransformationRule buildRules() {
		return SymbolicEqualityUtil.buildSimplificationRule();
	}
}
