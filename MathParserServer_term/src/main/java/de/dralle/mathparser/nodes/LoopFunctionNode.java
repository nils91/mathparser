/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.Functions;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public class LoopFunctionNode extends ExpressionNode {
	private Functions function;
	private EqualityNode identifierAssignment;
	private ExpressionNode upperBound;
	private ExpressionNode expression;

	@Override
	public void clearChilds() {
		identifierAssignment.setRightExpression(null);
		upperBound=null;
		expression=null;
		
	}

	public LoopFunctionNode(Functions function, EqualityNode identifierAssignment, ExpressionNode bound,
			ExpressionNode expression) {
		super();
		this.function = function;
		this.identifierAssignment = identifierAssignment;
		this.upperBound = bound;
		this.expression = expression;
	}

	/**
	 * @return the identifierAssignment
	 */
	public EqualityNode getIdentifierAssignment() {
		return identifierAssignment;
	}

	/**
	 * @param identifierAssignment the identifierAssignment to set
	 */
	public void setIdentifierAssignment(EqualityNode identifierAssignment) {
		this.identifierAssignment = identifierAssignment;
	}

	/**
	 * @return the bound
	 */
	public ExpressionNode getUpperBound() {
		return upperBound;
	}

	/**
	 * @param bound the bound to set
	 */
	public void setUpperBound(ExpressionNode bound) {
		this.upperBound = bound;
	}

	/**
	 * @return the bound
	 */
	public ExpressionNode getLowerBound() {
		return identifierAssignment.getRightExpression();
	}

	/**
	 * @param bound the bound to set
	 */
	public void setLowerBound(ExpressionNode bound) {
		identifierAssignment.setRightExpression(bound);
	}

	/**
	 * @return the bound
	 */
	public IdentifierNode getRunningIdentifier() {
		return (IdentifierNode) identifierAssignment.getLeftExpression();
	}

	/**
	 * @param bound the bound to set
	 */
	public void setRunningIdentifier(IdentifierNode bound) {
		identifierAssignment.setLeftExpression(bound);
	}

	/**
	 * @return the expression
	 */
	public ExpressionNode getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(ExpressionNode expression) {
		this.expression = expression;
	}

	public LoopFunctionNode(Functions function) {
		this.function = function;
	}

	@Override
	public List<IMathParserBaseNode> getChilds() {
		List<IMathParserBaseNode> tmp = new ArrayList<IMathParserBaseNode>();
		tmp.add(getLowerBound());
		tmp.add(getUpperBound());
		tmp.add(getExpression());
		return tmp;
	}

	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		switch (i) {		
		case 0:
			setLowerBound((ExpressionNode) node);
			break;
		case 1:
			setUpperBound((ExpressionNode) node);
			break;
		case 2:
			setExpression((ExpressionNode) node);
			break;
		default:
			break;
		}

	}

	@Override
	public int getChildcount() {
		return 3;
	}

	@Override
	public ExpressionNode clone() throws CloneNotSupportedException {
		LoopFunctionNode node = buildNode(function);
		node.identifierAssignment=(EqualityNode) identifierAssignment.clone();
		node.expression=expression.clone();
		node.upperBound=upperBound.clone();
		node.sign=sign;
		return node;
	}

	public static LoopFunctionNode buildNode(Functions function) {
		switch(function) {		
		case PROD:
			return new ProductNode();
		case SUM:
			return new SummationNode();
		default:
			break;		
		}
		return null;
	}

	@Override
	public ExpressionNode getChild(int i) {
		switch (i) {		
		case 0:
			return getLowerBound();
		case 1:
			return getUpperBound();
		case 2:
			return getExpression();
		default:
			break;
		}
		return null;
	}

}
