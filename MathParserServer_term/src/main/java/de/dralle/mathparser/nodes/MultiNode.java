/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.Operator;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.nodes.interfaces.IAssociativeNode;
import de.dralle.mathparser.nodes.interfaces.ICommutativeNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.nodes.interfaces.IOperatorNode;

/**
 * @author Nils Dralle
 *
 */
public class MultiNode extends ExpressionNode implements ICommutativeNode, IAssociativeNode, IOperatorNode {
	private List<ExpressionNode> childs;

	/**
	 * @param childs the childs to set
	 */
	public void setChildren(List<ExpressionNode> childs) {
		this.childs = childs;
	}

	private Operator operator;

	/**
	 * 
	 */
	public MultiNode() {
		super();
		childs = new ArrayList<ExpressionNode>();
	}

	/**
	 * @param sign
	 */
	public MultiNode(Sign sign) {
		super(sign);
		childs = new ArrayList<ExpressionNode>();
	}

	/**
	 * 
	 */
	public MultiNode(Operator op) {
		super();
		operator = op;
		childs = new ArrayList<ExpressionNode>();
	}

	/**
	 * @param sign
	 */
	public MultiNode(Operator op, Sign sign) {
		super(sign);
		operator = op;
		childs = new ArrayList<ExpressionNode>();
	}

	@Override
	public List<IMathParserBaseNode> getChilds() {
		List<IMathParserBaseNode> tmp = new ArrayList<IMathParserBaseNode>();
		tmp.addAll(childs);
		return tmp;
	}

	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		if (childs == null) {
			childs = new ArrayList<ExpressionNode>();
		}
		if (i < 0 || i > childs.size() - 1) {
			childs.add((ExpressionNode) node);
		} else {
			childs.set(i, (ExpressionNode) node);
		}

	}

	@Override
	public int getChildcount() {
		return childs.size();
	}

	@Override
	public ExpressionNode clone() throws CloneNotSupportedException {
		MultiNode node = (MultiNode) NodeUtils.buildNode(operator);
		for (ExpressionNode expressionNode : childs) {
			if (expressionNode != null) {
				node.setChild(expressionNode.clone(), -1);
			}
		}
		node.sign = sign;
		return node;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return childs.get(i);
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	@Override
	public void clearChilds() {
		childs.clear();

	}
}
