/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Operator;

/**
 * @author Nils Dralle
 *
 */
public class PowNode extends BinaryExpressionNode {

	@Override
	public Operator getOperator() {
		return Operator.POW;
	}
	public ExpressionNode getBase() {
		return super.getLeftExpression();
	}
	public ExpressionNode getExponent() {
		return super.getRightExpression();
	}
	public void setBase(ExpressionNode base) {
		super.setLeftExpression(base);
	}
	public void setExponent(ExpressionNode exponent) {
		super.setRightExpression(exponent);
	}
}
