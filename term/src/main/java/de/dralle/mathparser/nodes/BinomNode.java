/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Functions;
import de.dralle.mathparser.nodes.interfaces.IBinaryNode;
import de.dralle.mathparser.nodes.interfaces.IExpressionNode;

/**
 * First param n, 2nd k
 * @author Nils Dralle
 *
 */
public class BinomNode extends FunctionNode implements IBinaryNode {
	
	public BinomNode() {
		super(Functions.BINOM);
		// TODO Auto-generated constructor stub
	}
	public BinomNode(ExpressionNode n, ExpressionNode k) {
		super(Functions.BINOM);
		addChild(n);
		addChild(k);
	}
	public IExpressionNode getN() {
		if (getChild(0) instanceof IExpressionNode) {
			return (IExpressionNode) getChild(0);
		}
		throw new RuntimeException("Not implemented for this type of node");
	}
	public IExpressionNode getK() {
		if (getChild(0) instanceof IExpressionNode) {
			return (IExpressionNode) getChild(1);
		}
		throw new RuntimeException("Not implemented for this type of node");
	}
	@Override
	public IExpressionNode getLeftExpression() {
		return getN();
	}

	@Override
	public IExpressionNode getRightExpression() {
		return getK();
	}

}
