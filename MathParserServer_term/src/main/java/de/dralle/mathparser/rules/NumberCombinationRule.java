/**
 * 
 */
package de.dralle.mathparser.rules;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.visitors.NumberCombinationVisitor;

/**
 * Combine numbers. By default only on integers.
 * @author Nils Dralle
 *
 */
public class NumberCombinationRule extends AbstractTransformationRule {
	private NumberCombinationVisitor visitor;

	/**
	 * @return the onlyOnIntegers
	 */
	public boolean isOnlyOnIntegers() {
		return visitor.isOnlyOnIntegers();
	}

	/**
	 * @param onlyOnIntegers the onlyOnIntegers to set
	 */
	public void setOnlyOnIntegers(boolean onlyOnIntegers) {
		visitor.setOnlyOnIntegers(onlyOnIntegers);
	}

	/**
	 * @return the resultInteger
	 */
	public boolean isResultInteger() {
		return visitor.isResultInteger();
	}

	/**
	 * @param resultInteger the resultInteger to set
	 */
	public void setResultInteger(boolean resultInteger) {
		visitor.setResultInteger(resultInteger);
	}

	public NumberCombinationRule() {
		visitor = new NumberCombinationVisitor();
	}

	@Override
	public boolean isPossible(ExpressionNode expression) {
		for (int i = 0; i < expression.getChildcount(); i++) {
			if (isPossible(expression.getChild(i))) {
				return true;
			}
		}
		return visitor.correctNodeType(expression);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		return apply(expression, true);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {
		int transformed = 0;
		ExpressionNode newnode = expression.accept(visitor);
		if (newnode != null) {
			transformed++;
			if (!transformAll) {
				return newnode;
			} else {
				expression = newnode;
			}
		}
		for (int i = 0; i < expression.getChildcount(); i++) {
			ExpressionNode newchildnode = apply(expression.getChild(i));
			if (newchildnode != null) {
				transformed++;
				expression.setChild(newchildnode, i);
				if (!transformAll) {
					return expression;
				}
			}
		}
		if (transformed > 0) {
			return expression;
		} else {
			return null;
		}
	}

}
