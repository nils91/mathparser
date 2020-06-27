/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.RelationOperator;
import de.dralle.mathparser.nodes.interfaces.IEquationNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.visitors.EquationTreeInfixFormatter;

/**
 * 
 * @author Nils Dralle
 *
 */
public abstract class EquationNode extends AbstractMathParserBaseNode implements IEquationNode {
	private ExpressionNode rightChild;
	private ExpressionNode leftChild;

	public EquationNode() {

	}

	@Override
	public void clearChilds() {
		rightChild=null;
		leftChild=null;
		
	}

	@Override
	public int hashCode() {
		return super.hashCode()+getOperator().hashCode();
	}

	/**
	 * Builds the correct equation node based on the relation operator.
	 * 
	 * @param op
	 * @return
	 */
	public static EquationNode buildNode(RelationOperator op) {
		if (op == null) {
			return null;
		}
		switch (op) {
		case EQ:
			return new EqualityNode();
		case GT:
			return new GreaterThanNode();
		case GE:
			return new GreaterEqualsNode();
		case LT:
			return new LessThanNode();
		case LE:
			return new LessEqualsNode();
		case NE:
			return new UnequalityNode();
		default:
			break;
		}
		return null;
	}

	@Override
	public ExpressionNode getLeftExpression() {
		return leftChild;
	}

	@Override
	public ExpressionNode getRightExpression() {
		return rightChild;
	}

	public void setRightExpression(ExpressionNode rightChild) {
		this.rightChild = rightChild;
	}

	public void setLeftExpression(ExpressionNode leftChild) {
		this.leftChild = leftChild;
	}

	@Override
	public List<IMathParserBaseNode> getChilds() {
		List<IMathParserBaseNode> tmpList = new ArrayList<IMathParserBaseNode>();
		tmpList.add(leftChild);
		tmpList.add(rightChild);
		return tmpList;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return (ExpressionNode) getChilds().get(i);

	}

	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		switch (i) {
		case 0:
			leftChild = (ExpressionNode) node;
			break;
		case 1:
			rightChild = (ExpressionNode) node;
			break;
		default:
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int getChildcount() {
		return getChilds().size();
	}
	
	@Override
	public String toString() {
		return accept(new EquationTreeInfixFormatter());
	}

	@Override
	public EquationNode clone() throws CloneNotSupportedException {
		EquationNode clone = buildNode(getOperator());
		clone.setLeftExpression(leftChild.clone());
		clone.setRightExpression(rightChild.clone());
		return clone;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IMathParserBaseNode) {
			return ExpressionUtil.isEqual(this, (IMathParserBaseNode) obj);
		}else {
			return false;
		}
	}
}
