/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * Use multiple rules as one.
 * @author Nils Dralle
 *
 */
public class CompositeTransformationRule extends AbstractTransformationRule implements ICompositeTransformationRule{

	private static Logger log = Logger.getLogger(CompositeTransformationRule.class);
	
	public CompositeTransformationRule() {
		childRules = new ArrayList<AbstractTransformationRule>();
	}

	public CompositeTransformationRule(List<AbstractTransformationRule> childRules) {
		this.childRules = childRules;
	}

	private List<AbstractTransformationRule> childRules;
	/**
	 * True by default. If false, every child rule needs to be possible for this
	 * rule to be possible, if true only one child rule needs to be possible.
	 */
	private boolean ignoreNotPossibleRules = true;

	/**
	 * @return the childRules
	 */
	public List<AbstractTransformationRule> getChildRules() {
		return childRules;
	}

	public void addChildRule(AbstractTransformationRule childRule) {
		childRules.add(childRule);
	}

	public boolean removeChildRule(AbstractTransformationRule childRule) {
		return childRules.remove(childRule);
	}

	@Override
	public boolean isPossible(ExpressionNode expression) {
		boolean allRulesPossible = true;
		for (AbstractTransformationRule abstractTransformationRule : childRules) {
			if (abstractTransformationRule.isPossible(expression)) {
				if (ignoreNotPossibleRules) {
					return true;
				}
			} else {
				allRulesPossible = false;
			}
		}
		return allRulesPossible;
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		ExpressionNode transformedExpression = apply(expression, true);
		return transformedExpression;
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {		
		ExpressionNode oldEx = expression;
		int rulesApplied=0;
		for (AbstractTransformationRule abstractTransformationRule : childRules) {
			if (abstractTransformationRule.isPossible(oldEx)) {
				ExpressionNode newEx = null;
				try {
					newEx = abstractTransformationRule.apply(oldEx.clone(), transformAll);
					if (newEx!=null) {			
						log.debug(oldEx + " => " + newEx + " by Rule " + abstractTransformationRule);
					}
				} catch (Exception e) {
					if (!ignoreNotPossibleRules) {
						throw new RuntimeException(String.format("Rule %s has failed.", abstractTransformationRule.toString()),
								e);
					}
				}
				if(newEx!=null) {
					oldEx=newEx;
					rulesApplied++;
				}
			} else {
				if(!ignoreNotPossibleRules) {
					throw new RuntimeException(String.format("Rule %s is not possible", abstractTransformationRule.toString()));
				}
			}
		}		
		ExpressionNode transformedExpression = rulesApplied==0?null:oldEx;
		return transformedExpression;
	}

	/**
	 * True by default. If false, every child rule needs to be possible for this
	 * rule to be possible, if true only one child rule needs to be possible.
	 */
	public boolean isIgnoreNotPossibleRules() {
		return ignoreNotPossibleRules;
	}

	/**
	 * True by default. If false, every child rule needs to be possible for this
	 * rule to be possible, if true only one child rule needs to be possible.
	 */
	public void setIgnoreNotPossibleRules(boolean ignoreNotPossibleRules) {
		this.ignoreNotPossibleRules = ignoreNotPossibleRules;
	}

}
