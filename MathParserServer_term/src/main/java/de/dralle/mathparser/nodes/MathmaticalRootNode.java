/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Functions;
import de.dralle.mathparser.nodes.interfaces.IBinaryNode;
import de.dralle.mathparser.nodes.interfaces.IExpressionNode;

/**
 * First param value, 2nd nth root
 * @author Nils Dralle
 *
 */
public class MathmaticalRootNode extends FunctionNode implements IBinaryNode{

	public MathmaticalRootNode() {
		super(Functions.ROOT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IExpressionNode getLeftExpression() {
		if (getChild(0) instanceof IExpressionNode) {
			return (IExpressionNode) getChild(0);
		}
		throw new RuntimeException("Not implemented for this type of node");
	}

	@Override
	public IExpressionNode getRightExpression() {
		if (getChild(1) instanceof IExpressionNode) {
			return (IExpressionNode) getChild(1);
		}
		throw new RuntimeException("Not implemented for this type of node");
	}
}
