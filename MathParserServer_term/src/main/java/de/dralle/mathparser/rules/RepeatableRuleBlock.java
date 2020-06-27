/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * @author Nils Dralle
 *
 */
public class RepeatableRuleBlock extends AbstractTransformationRule
		implements IRepeatingTransformationRule, ICompositeTransformationRule {
	private static Logger log = Logger.getLogger(RepeatableRuleBlock.class);

	public RepeatableRuleBlock() {
		ruleBlock = new CompositeTransformationRule();
		repeatingRule = new RepeatingTransformationRule(ruleBlock);
	}

	public RepeatableRuleBlock(List<AbstractTransformationRule> childRules) {
		ruleBlock = new CompositeTransformationRule(childRules);
		repeatingRule = new RepeatingTransformationRule(ruleBlock);
	}

	
	public RepeatableRuleBlock(AbstractTransformationRule... childRules) {		 
		ruleBlock = new CompositeTransformationRule(Arrays.asList(childRules));
		repeatingRule = new RepeatingTransformationRule(ruleBlock);
	}
	
	private RepeatingTransformationRule repeatingRule;
	private CompositeTransformationRule ruleBlock;

	@Override
	public boolean isPossible(ExpressionNode expression) {
		return repeatingRule.isPossible(expression);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		ExpressionNode transformedExpression = repeatingRule.apply(expression);
		log.debug(expression + " => " + transformedExpression);
		return transformedExpression;
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {
		ExpressionNode transformedExpression = repeatingRule.apply(expression, transformAll);
		log.debug(expression + " => " + transformedExpression);
		return transformedExpression;
	}
	
	@Override
	public int getMaxRepeats() {
		return repeatingRule.getMaxRepeats();
	}

	@Override
	public void setMaxRepeats(int maxRepeats) {
		repeatingRule.setMaxRepeats(maxRepeats);
	}

	@Override
	public void addChildRule(AbstractTransformationRule childRule) {
		ruleBlock.addChildRule(childRule);

	}

	@Override
	public boolean removeChildRule(AbstractTransformationRule childRule) {
		return ruleBlock.removeChildRule(childRule);
	}

	@Override
	public boolean isIgnoreNotPossibleRules() {
		return ruleBlock.isIgnoreNotPossibleRules();
	}

	@Override
	public void setIgnoreNotPossibleRules(boolean ignoreNotPossibleRules) {
		ruleBlock.setIgnoreNotPossibleRules(ignoreNotPossibleRules);

	}

}
