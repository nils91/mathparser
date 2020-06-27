/**
 * 
 */
package de.dralle.mathparser.rules;

import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.rules.conditions.interfaces.ITransformationRuleCondition;

/**
 * Central factory class to build transformation rules, especially the parsed rules.
 * @author Nils Dralle
 *
 */
public class TransformationRuleBuilder {
	public static GeneralTransformationRule buildRuleFromString(String rule) {
		return TransformationRuleParser.buildTransformationRuleFromString(rule);
	}
	public static ConditionalTransformationRule buildRuleFromString(String rule,ITransformationRuleCondition con) {
		return new ConditionalTransformationRule(buildRuleFromString(rule),con);
	}
	@Deprecated
	public static CompositeTransformationRule buildCombinePowersRule() {
		CompositeTransformationRule rule = new CompositeTransformationRule();
		rule.setIgnoreNotPossibleRules(true);
		rule.addChildRule(buildRuleFromString("a*(b*c) \\to (a*b)*c"));
		rule.addChildRule(buildRuleFromString("x^y*a \\to a*x^y"));
		rule.addChildRule(buildRuleFromString("a*x^y+b*x^y \\to (a+b)*x^y"));
		NumberCombinationRule intCombineRule = new NumberCombinationRule();
		intCombineRule.setOnlyOnIntegers(true);
		rule.addChildRule(intCombineRule);
		return rule;
	}
	public static CompositeTransformationRule buildCommonFactorsRuleForNumbers() {
		CompositeTransformationRule combineRule = buildNodeCombineRule();
		CompositeTransformationRule oorderRule = buildNodeContentReorderingRule();
		CommonFactorCombinationRule2 combineFactorsForNumbers = new CommonFactorCombinationRule2();
		combineFactorsForNumbers.setOnlyForConstantValues(true);
		combineFactorsForNumbers.setOnlyForNonConstantCommonFactors(true);
		
		CompositeTransformationRule compositeRule = new CompositeTransformationRule();
		compositeRule.addChildRule(combineRule);
		compositeRule.addChildRule(oorderRule);
		compositeRule.addChildRule(combineFactorsForNumbers);
		compositeRule.setIgnoreNotPossibleRules(true);
		return compositeRule;
	}
	public static CompositeTransformationRule buildNodeCombineRule() {
		CombineAddNodes addCombine = new CombineAddNodes();
		CombineMultiplyNodes multiplyCombine = new CombineMultiplyNodes();
		CompositeTransformationRule compositeNode = new CompositeTransformationRule();
		compositeNode.addChildRule(addCombine);
		compositeNode.addChildRule(multiplyCombine);
		compositeNode.setIgnoreNotPossibleRules(true);
		return compositeNode;
	}
	public static CompositeTransformationRule buildNodeContentReorderingRule() {
		AdditionReordering addreordering = new AdditionReordering();
		MultiplicationReordering multiplyReordering = new MultiplicationReordering();
		CompositeTransformationRule compositeNode = new CompositeTransformationRule();
		compositeNode.addChildRule(addreordering);
		compositeNode.addChildRule(multiplyReordering);
		compositeNode.setIgnoreNotPossibleRules(true);
		return compositeNode;
	}
}
