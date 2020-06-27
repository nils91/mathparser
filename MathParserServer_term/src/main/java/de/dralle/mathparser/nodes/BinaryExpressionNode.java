/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.Operator;
import de.dralle.mathparser.nodes.interfaces.IBinaryNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.nodes.interfaces.IOperatorNode;

/**
 * @author Nils Dralle
 * Binary not because its binary but because it has two childs.
 */
public abstract class BinaryExpressionNode extends ExpressionNode implements IBinaryNode,IOperatorNode{
	private ExpressionNode rightChild;
	private ExpressionNode leftChild;
	@Override
	public ExpressionNode getLeftExpression() {
		return leftChild;
	}

	@Override
	public void clearChilds() {
		leftChild=null;
		rightChild=null;
		
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
		List<IMathParserBaseNode> tmpList=new ArrayList<IMathParserBaseNode>();
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
		switch(i) {
		case 0:
			leftChild=(ExpressionNode) node;
			break;
		case 1:
			rightChild=(ExpressionNode) node;
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
	public BinaryExpressionNode clone() throws CloneNotSupportedException {
		BinaryExpressionNode node = (BinaryExpressionNode) NodeUtils.buildNode((Operator)getOperator());
		node.setLeftExpression(leftChild.clone());
		node.setRightExpression(rightChild.clone());
		node.sign=sign;
		return node;
	}
	@Override
	public int hashCode() {
		return super.hashCode()+getOperator().hashCode();
	}
	

}
