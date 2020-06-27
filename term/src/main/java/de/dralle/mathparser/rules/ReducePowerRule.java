/**
 * 
 */
package de.dralle.mathparser.rules;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.PowNode;
import de.dralle.mathparser.nodes.SubtractNode;

/**
 * @author Nils Dralle
 * Transformation rule to reduce powers. a^m -> a^(m-1)*a. Deprecated.
 */
@Deprecated
public class ReducePowerRule extends AbstractTransformationRule {
	private boolean onlyOnIntegers = true;
	private int reduceUntil = 1;

	/**
	 * @return the onlyOnIntegers
	 */
	public boolean isOnlyOnIntegers() {
		return onlyOnIntegers;
	}

	/**
	 * @param onlyOnIntegers the onlyOnIntegers to set
	 */
	public void setOnlyOnIntegers(boolean onlyOnIntegers) {
		this.onlyOnIntegers = onlyOnIntegers;
	}

	/**
	 * Only has an effect if onlyOnIntegers is set
	 * 
	 * @return the reduceUntil
	 */
	public int getReduceUntil() {
		return reduceUntil;
	}

	/**
	 * Only has an effect if onlyOnIntegers is set
	 * 
	 * @param reduceUntil the reduceUntil to set
	 */
	public void setReduceUntil(int reduceUntil) {
		this.reduceUntil = reduceUntil;
	}

	@Override
	public boolean isPossible(ExpressionNode expression) {
		if (expression instanceof PowNode) {
			if (onlyOnIntegers) {
				ExpressionNode exponent = ((PowNode) expression).getRightExpression();
				if (exponent instanceof IntegerNode) {
					if (((IntegerNode) exponent).getValue().intValue() > reduceUntil) {
						return true;
					}
				}
			} else {
				return true;
			}
		}
		for (int i = 0; i < expression.getChildcount(); i++) {
			if (isPossible(expression.getChild(i))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		return apply(expression, true);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {
		ExpressionNode expressionClone = expression.clone();
		MultiMultiplyNode ret = null; // will hold the new root of the tree
		if (expressionClone instanceof PowNode) {
			if (onlyOnIntegers) {
				ExpressionNode exponent = ((PowNode) expressionClone).getRightExpression();
				if (exponent instanceof IntegerNode) {
					if (((IntegerNode) exponent).getValue().intValue() > reduceUntil) {
						MultiMultiplyNode newroot = new MultiMultiplyNode();
						ExpressionNode powbase = ((PowNode) expressionClone).getLeftExpression();						
						IntegerNode newexponent = new IntegerNode(
								((IntegerNode) expressionClone).getValue().intValue() - 1);
						if (newexponent.getValue().intValue() == 1) {
							expressionClone = powbase;
						} else if (newexponent.getValue().intValue() == 0) {
							expressionClone = new IntegerNode(1);
						} else {
							((PowNode) expressionClone).setRightExpression(newexponent);
						}
						newroot.setChild(expressionClone, -1);
						newroot.setChild(powbase, -1);
						if (transformAll) {
							ret = newroot;
						} else {
							return newroot;
						}
					}
				}
			} else {
				ExpressionNode exponent = ((PowNode) expressionClone).getRightExpression();
				MultiMultiplyNode newroot = new MultiMultiplyNode();
				ExpressionNode powbase = ((PowNode) expressionClone).getLeftExpression();				
				SubtractNode newexponent = new SubtractNode();
				newexponent.setLeftExpression(exponent);
				newexponent.setRightExpression(new IntegerNode(1));
				((PowNode) expressionClone).setRightExpression(newexponent);
				newroot.setChild(expressionClone, -1);
				newroot.setChild(powbase, -1);
				if (transformAll) {
					ret = newroot;
				} else {
					return newroot;
				}
			}
		}
		int childsTransformed = 0;
		for (int i = 0; i < expressionClone.getChildcount(); i++) {
			ExpressionNode transformed = apply(expressionClone.getChild(i), transformAll);
			if (transformed != null) {
				expressionClone.setChild(transformed, i);
				if (!transformAll) {
					return expressionClone;
				}
				childsTransformed++;
			}
		}
		if (ret != null) {
			expressionClone = ret;
		}
		if (transformAll && (childsTransformed > 0 || ret != null)) {
			return expressionClone;
		} else {
			return null;
		}
	}

}
