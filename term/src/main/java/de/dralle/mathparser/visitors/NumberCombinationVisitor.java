/**
 * 
 */
package de.dralle.mathparser.visitors;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.MultiAddNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.RealNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * For the node it visits, it will return either an Integer or RealNode
 * depending on if it can be replaced without losing information.
 * 
 * @author Nils Dralle
 *
 */
public class NumberCombinationVisitor extends ExpressionTreeBaseVisitor<ExpressionNode> {

	private boolean onlyOnIntegers = true;
	private boolean resultInteger = true;

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
	 * @return the resultInteger
	 */
	public boolean isResultInteger() {
		return resultInteger;
	}

	/**
	 * @param resultInteger the resultInteger to set
	 */
	public void setResultInteger(boolean resultInteger) {
		this.resultInteger = resultInteger;
	}

	public NumberCombinationVisitor() {
		super();
	}

	@Override
	public ExpressionNode visit(IMathParserBaseNode node) {
		if (node.getChildcount() > 0 && childrenCorrectNodeType((ExpressionNode) node)) {
			Double result = node.accept(new CalculateNumericalValueVisitor());
			if (isInteger(result)) {
				return new IntegerNode(result.intValue());
			} else if (!resultInteger) {
				return new RealNode(result.doubleValue());
			}
		} else if (!onlyOnIntegers && node instanceof RealNode) {
			if (isInteger(((RealNode) node).getValue().doubleValue())) {
				return new IntegerNode(((RealNode) node).getValue().intValue());
			}
		} else if (NodeUtils.isAnyNodeType((ExpressionNode) node, MultiAddNode.class, MultiMultiplyNode.class)) {
			ExpressionNode newnode = handleMultiNodes(node);
			if(newnode!=null) {
				newnode.setSign(((ExpressionNode)node).getSign());
			}
			return newnode;
		}
		return null;
	}

	private ExpressionNode handleMultiNodes(IMathParserBaseNode node) {
		if (node instanceof MultiAddNode) {
			return handleMultiAdd((MultiAddNode) node);
		} else if (node instanceof MultiMultiplyNode) {
			return handleMultiMultiply((MultiMultiplyNode) node);
		}
		return null;
	}

	private ExpressionNode handleMultiMultiply(MultiMultiplyNode node) {
		List<ExpressionNode> correctTypeChildNodes = new ArrayList<ExpressionNode>();
		List<ExpressionNode> incorrectTypeChildNodes = new ArrayList<ExpressionNode>();
		for (int i = 0; i < node.getChildcount(); i++) {
			if (correctNodeType(node.getChild(i))) {
				correctTypeChildNodes.add(node.getChild(i));
			} else {
				incorrectTypeChildNodes.add(node.getChild(i));
			}
		}
		if (correctTypeChildNodes.size() >= 2) {
			double correctTypeChildsResult = 1;
			for (ExpressionNode expressionNode : correctTypeChildNodes) {
				correctTypeChildsResult *= expressionNode.accept(new CalculateNumericalValueVisitor());
			}
			MultiMultiplyNode newnode = new MultiMultiplyNode();
			if (correctTypeChildNodes.size() > 0) {
				if (resultInteger) {
					if (isInteger(correctTypeChildsResult)) {
						newnode.setChild(new IntegerNode((long) correctTypeChildsResult), -1);
					} else {
						return null;
					}
				} else {
					newnode.setChild(new RealNode(correctTypeChildsResult), -1);
				}
			}
			for (ExpressionNode expressionNode : incorrectTypeChildNodes) {
				newnode.setChild(expressionNode, -1);
			}
			return newnode;
		}
		return null;
	}

	private MultiAddNode handleMultiAdd(MultiAddNode node) {
		List<ExpressionNode> correctTypeChildNodes = new ArrayList<ExpressionNode>();
		List<ExpressionNode> incorrectTypeChildNodes = new ArrayList<ExpressionNode>();
		for (int i = 0; i < node.getChildcount(); i++) {
			if (correctNodeType(node.getChild(i))) {
				correctTypeChildNodes.add(node.getChild(i));
			} else {
				incorrectTypeChildNodes.add(node.getChild(i));
			}
		}
		if (correctTypeChildNodes.size() >= 2) {
			double correctTypeChildsResult = 0;
			for (ExpressionNode expressionNode : correctTypeChildNodes) {
				correctTypeChildsResult += expressionNode.accept(new CalculateNumericalValueVisitor());
			}
			MultiAddNode newnode = new MultiAddNode();
			if (correctTypeChildNodes.size() > 0) {
				if (resultInteger) {
					if (isInteger(correctTypeChildsResult)) {
						newnode.setChild(new IntegerNode((long) correctTypeChildsResult), -1);
					} else {
						return null;
					}
				} else {
					newnode.setChild(new RealNode(correctTypeChildsResult), -1);
				}
			}
			for (ExpressionNode expressionNode : incorrectTypeChildNodes) {
				newnode.setChild(expressionNode, -1);
			}
			return newnode;
		}
		return null;
	}

	private boolean isInteger(Double result) {
		if (result == null) {
			return false;
		}
		return result.doubleValue() == result.intValue();
	}

	public boolean childrenCorrectNodeType(ExpressionNode node) {
		for (int i = 0; i < node.getChildcount(); i++) {
			if (!correctNodeType(node.getChild(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean correctNodeType(ExpressionNode node) {
		return node instanceof IntegerNode || (!onlyOnIntegers && node instanceof RealNode);
	}

}
