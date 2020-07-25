/**
 * 
 */
package de.dralle.mathparser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.rules.AbstractTransformationRule;
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.NumberCombinationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

/**
 * Util class for performing symbolic equality checks.
 * 
 * @author Nils Dralle
 *
 */
public class SymbolicEqualityUtil {

	private static Logger log = Logger.getLogger(SymbolicEqualityUtil.class);

	public static boolean checkSymbolicEquality(String equation) {
		return checkSymbolicEquality(equation, buildSimplificationRule());
	}

	public static boolean checkSymbolicEquality(String equation, AbstractTransformationRule combinedRule) {
		log.debug("Equation before parsing= " + equation);
		EquationNode equationTree = EquationParser.buildEquationTreeFromString(equation);
		if (equationTree instanceof EqualityNode) {
			return checkSymbolicEquality((EqualityNode) equationTree, combinedRule);
		} else {
			log.warn(
					String.format("Operation not supported on equation of type %s", equationTree.getClass().getName()));
			throw new RuntimeException(
					String.format("Operation not supported on equation of type %s", equationTree.getClass().getName()));
		}
	}

	public static boolean checkSymbolicEquality(EqualityNode equation) {
		return checkSymbolicEquality(equation, buildSimplificationRule());
	}

	public static boolean checkSymbolicEquality(EqualityNode equation, AbstractTransformationRule combinedRule) {
		ExpressionNode leftExpression = equation.getLeftExpression();
		ExpressionNode rightExpression = equation.getRightExpression();
		log.debug("Equation = " + equation);
		return checkSymbolicEquality(leftExpression, rightExpression, combinedRule);
	}

	/**
	 * @param combinedRule
	 * @param leftExpression
	 * @param rightExpression
	 * @return
	 */
	public static boolean checkSymbolicEquality(ExpressionNode leftExpression, ExpressionNode rightExpression,
			AbstractTransformationRule combinedRule) {
		try {
			log.debug("-----------BEGIN LeftExpression = " + leftExpression);
			ExpressionNode transformedLeftExpression = simplify(leftExpression, combinedRule);
			log.debug("-------------END LeftExpression = " + transformedLeftExpression);

			log.debug("-----------BEGIN RightExpression = " + rightExpression);
			ExpressionNode transformedRightExpression = simplify(rightExpression, combinedRule);
			log.debug("-------------END RightExpression = " + transformedRightExpression);
			log.debug("==================================");

			transformedLeftExpression = transformedLeftExpression == null ? leftExpression : transformedLeftExpression;
			transformedRightExpression = transformedRightExpression == null ? rightExpression
					: transformedRightExpression;
			return transformedLeftExpression.equals(transformedRightExpression);
		} catch (Exception e1) {
			log.error("Error while checking symbolic equality", e1);
			return false;
		}
	}

	/**
	 * Attempt to simplify origin using rule. Different from rule.apply in that it
	 * always returns a result, not only when something changed.
	 * 
	 * @param origin
	 * @param rule
	 * @return
	 */
	public static ExpressionNode simplify(ExpressionNode origin, AbstractTransformationRule rule) {
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(origin, true);
		} catch (Exception e) {
			log.warn(String.format("Problem while simplifying %s", origin), e);
		}
		if (transformed == null) {
			log.info(String.format("No simplification applied to %s", origin));
		}
		return transformed == null ? origin : transformed;
	}

	/**
	 * @param combinedRule
	 * @param leftExpression
	 * @param rightExpression
	 * @return
	 */
	public static boolean checkSymbolicEquality(ExpressionNode leftExpression, ExpressionNode rightExpression) {
		return checkSymbolicEquality(leftExpression, rightExpression, buildSimplificationRule());
	}

	public static EquationNode applyToBothSides(EquationNode equation, AbstractTransformationRule rule,
			boolean transformAll, boolean mandatory) {
		ExpressionNode leftExpression = equation.getLeftExpression();
		ExpressionNode rightExpression = equation.getRightExpression();
		ExpressionNode transformedLeft = null;
		ExpressionNode transformedRight = null;
		try {
			transformedLeft = rule.apply(leftExpression, transformAll);
		} catch (Exception e) {
		}
		try {
			transformedRight = rule.apply(rightExpression, transformAll);
		} catch (Exception e) {
		}
		if (mandatory) {
			if (transformedLeft != null && transformedRight != null) {
				equation.setLeftExpression(transformedLeft);
				equation.setRightExpression(transformedRight);
				return equation;
			}
			throw new RuntimeException(
					String.format("Transform %s could not be applied to both sides of %s", rule, equation));
		} else {
			int transformed = 0;
			if (transformedLeft != null) {
				transformed++;
				equation.setLeftExpression(transformedLeft);
			}
			if (transformedRight != null) {
				transformed++;
				equation.setRightExpression(transformedRight);
			}
			return transformed > 0 ? equation : null;
		}
	}

	public static EquationNode equivalizationTransform(EquationNode equation, AbstractTransformationRule rule) {
		if (rule instanceof GeneralTransformationRule) {
			((GeneralTransformationRule) rule).setWriteUnknownIdentifiersOnRHS(true);
		}
		return applyToBothSides(equation, rule, false, true);
	}

	public static boolean checkEquivalizationTransform(EquationNode startEquation, EquationNode targetEquation,
			AbstractTransformationRule rule) {
		EquationNode transformedStart = null;
		if (rule != null) {
			transformedStart = equivalizationTransform(startEquation, rule);
		}
		if (transformedStart == null) {
			transformedStart = startEquation;
		}
		boolean leftEqual=transformedStart.getLeftExpression().equals(targetEquation.getLeftExpression());
		log.info(String.format("Equations %s <=> %s after left side simplification stage 0. Equal: %b", transformedStart,targetEquation,leftEqual));
		if(!leftEqual) {
			leftEqual = checkSymbolicEquality(transformedStart.getLeftExpression(), targetEquation.getLeftExpression(),buildSimplificationRule());
			log.info(String.format("Equations %s <=> %s after left side simplification stage 1. Equal: %b", transformedStart,targetEquation,leftEqual));
		}
		if(!leftEqual) {
			leftEqual = checkSymbolicEquality(transformedStart.getLeftExpression(), targetEquation.getLeftExpression(),buildSimplificationRule(false,true));
			log.info(String.format("Equations %s <=> %s after left side simplification stage 2. Equal: %b", transformedStart,targetEquation,leftEqual));
		}
		if(!leftEqual) {
			leftEqual = checkSymbolicEquality(transformedStart.getLeftExpression(), targetEquation.getLeftExpression(),buildSimplificationRule(true,true));
			log.info(String.format("Equations %s <=> %s after left side simplification stage 3. Equal: %b", transformedStart,targetEquation,leftEqual));
		}
		
		boolean rightEqual=transformedStart.getRightExpression().equals(targetEquation.getRightExpression());
		log.info(String.format("Equations %s <=> %s after right side simplification stage 0. Equal: %b", transformedStart,targetEquation,rightEqual));
		if(!rightEqual) {
			rightEqual = checkSymbolicEquality(transformedStart.getRightExpression(), targetEquation.getRightExpression(),buildSimplificationRule());
			log.info(String.format("Equations %s <=> %s after right side simplification stage 1. Equal: %b", transformedStart,targetEquation,rightEqual));
		}
		if(!rightEqual) {
			rightEqual = checkSymbolicEquality(transformedStart.getRightExpression(), targetEquation.getRightExpression(),buildSimplificationRule(false,true));
			log.info(String.format("Equations %s <=> %s after right side simplification stage 2. Equal: %b", transformedStart,targetEquation,rightEqual));
		}
		if(!rightEqual) {
			rightEqual = checkSymbolicEquality(transformedStart.getRightExpression(), targetEquation.getRightExpression(),buildSimplificationRule(true,true));
			log.info(String.format("Equations %s <=> %s after right side simplification stage 3. Equal: %b", transformedStart,targetEquation,rightEqual));
		}
		return leftEqual&&rightEqual;
	}

	public static AbstractTransformationRule buildSimplificationRule() {
		return buildSimplificationRule(false, false);
	}

	public static AbstractTransformationRule buildSimplificationRule(boolean combineReals,
			boolean acceptRealsResult) {
		NumberCombinationRule intCombineRule = new NumberCombinationRule();
		intCombineRule.setResultInteger(!acceptRealsResult);
		intCombineRule.setOnlyOnIntegers(!combineReals);

		CompositeTransformationRule combineCommonFactorRule = TransformationRuleBuilder
				.buildCommonFactorsRuleForNumbers();

		AbstractTransformationRule combinedRules = RuleBlocks.combineRules(
				new AbstractTransformationRule[] { buildSymbolicPlusRule(), combineCommonFactorRule, intCombineRule },
				true, true);
		return combinedRules;
	}

	public static AbstractTransformationRule buildOnlySymbolicRules() {
		List<AbstractTransformationRule> allRules = new ArrayList<AbstractTransformationRule>();
		allRules.addAll(RuleBlocks.buildNegativeSignReplacementRules());
		allRules.addAll(RuleBlocks.buildReplaceWithConstantFactorRules());
		allRules.addAll(RuleBlocks.buildNeutralElementRules());
		allRules.addAll(RuleBlocks.buildTriginometicRules());
		allRules.addAll(RuleBlocks.buildBinomialRules());
		allRules.addAll(RuleBlocks.buildDistributiveRules());
		allRules.addAll(RuleBlocks.buildPowRules());
		allRules.addAll(RuleBlocks.buildFractionRules());
		allRules.addAll(RuleBlocks.buildMultiplicationRules());
		allRules.addAll(RuleBlocks.buildSqrtRules());
		allRules.addAll(RuleBlocks.buildLogarithmRules());
		AbstractTransformationRule[] allRuleArray = allRules.toArray(new AbstractTransformationRule[allRules.size()]);
		return RuleBlocks.combineRules(allRuleArray, true, true);
	}

	public static AbstractTransformationRule buildSymbolicPlusRule() {
		AbstractTransformationRule symbolicRule = buildOnlySymbolicRules();
		CompositeTransformationRule nodeCombine = TransformationRuleBuilder.buildNodeCombineRule();
		CompositeTransformationRule reordering = TransformationRuleBuilder.buildNodeContentReorderingRule();
		AbstractTransformationRule[] allRuleArray = new AbstractTransformationRule[] { reordering, symbolicRule,
				nodeCombine };
		return RuleBlocks.combineRules(allRuleArray, true, true);
	}
}
