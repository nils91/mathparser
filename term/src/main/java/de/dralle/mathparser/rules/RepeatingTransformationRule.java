/**
 * 
 */
package de.dralle.mathparser.rules;

import org.apache.log4j.Logger;

import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * Repeat a transformation rule as often as it can be applied or until maxRepeats is reached.
 * @author Nils Dralle
 *
 */
public class RepeatingTransformationRule extends AbstractTransformationRule implements IRepeatingTransformationRule {

	private AbstractTransformationRule childRule;
	private int maxRepeats = 50;

	private static Logger log = Logger.getLogger(RepeatingTransformationRule.class);

	/**
	 * @return the maxRepeats
	 */
	public int getMaxRepeats() {
		return maxRepeats;
	}

	/**
	 * @param maxRepeats the maxRepeats to set
	 */
	public void setMaxRepeats(int maxRepeats) {
		this.maxRepeats = maxRepeats;
	}

	public RepeatingTransformationRule(AbstractTransformationRule childRule) {
		super();
		this.childRule = childRule;
	}

	/**
	 * @return the childRule
	 */
	public AbstractTransformationRule getChildRule() {
		return childRule;
	}

	/**
	 * @param childRule the childRule to set
	 */
	public void setChildRule(AbstractTransformationRule childRule) {
		this.childRule = childRule;
	}

	@Override
	public boolean isPossible(ExpressionNode expression) {
		return childRule.isPossible(expression);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		return apply(expression, true);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {
		ExpressionNode oldExp = expression.clone();
		int repeats = 0;
		int transformed = 0;
		while(isPossible(oldExp)&&repeats<maxRepeats) {
			if (repeats > maxRepeats) {
				throw new RuntimeException(String.format("Rule %s did not terminate", childRule));
			}
			repeats++;
			ExpressionNode newEx = null;
			try {
				newEx = childRule.apply(oldExp, transformAll);
			} catch (Exception e) {
			}
			if (newEx != null) {				
				oldExp = newEx;
				transformed++;
			} else {
				break;
			}
		}
		log.debug(expression + " => " + oldExp);
		if (transformed > 0) {
			return oldExp;
		} else {
			return null;
		}
	}

}
