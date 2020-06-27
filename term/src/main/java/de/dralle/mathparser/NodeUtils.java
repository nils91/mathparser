/**
 * 
 */
package de.dralle.mathparser;

import java.util.List;

import de.dralle.mathparser.nodes.DivisionNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.ModuloNode;
import de.dralle.mathparser.nodes.MultiAddNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.PowNode;
import de.dralle.mathparser.nodes.SubtractNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * Util class to help building node instances.
 * 
 * @author Nils Dralle
 *
 */
public class NodeUtils {
	public static ExpressionNode buildNode(Operator op) {
		if (op == null) {
			return null;
		}
		switch (op) {
		case ADD:
			return new MultiAddNode();
		case SUBTRACT:
			return new SubtractNode();
		case MULTIPLY:
			return new MultiMultiplyNode();
		case DIV:
			return new DivisionNode();
		case MODULO:
			return new ModuloNode();
		case POW:
			return new PowNode();
		default:
			break;
		}
		return null;
	}
	public static ExpressionNode buildMultiplicativeExpressionFromList(List<ExpressionNode> terms, Sign sign) {
		if (terms.size() > 0) {
			if (terms.size() == 1) {
				ExpressionNode term = null;
				try {
					term = terms.get(0).clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(sign.equals(Sign.NEGATIVE)) {
					term.setSign(term.getSign().equals(Sign.POSITIVE)?Sign.NEGATIVE:Sign.POSITIVE);
				}
				return term;
			} else {
				MultiMultiplyNode tmp = new MultiMultiplyNode();
				tmp.setSign(sign);
				for (ExpressionNode expressionNode : terms) {
					tmp.setChild(expressionNode, -1);
				}
				return tmp;
			}
		}else {
			IntegerNode noder = new IntegerNode(1);
			noder.setSign(sign);
			return noder;
		}
	}

	public static ExpressionNode buildAdditiveExpressionFromList(List<ExpressionNode> terms, Sign sign) {
		if (terms.size() > 0) {
			if (terms.size() == 1) {
				ExpressionNode term = null;
				try {
					term = terms.get(0).clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(sign.equals(Sign.NEGATIVE)) {
					term.setSign(term.getSign().equals(Sign.POSITIVE)?Sign.NEGATIVE:Sign.POSITIVE);
				}
				return term;
			} else {
				MultiAddNode tmp = new MultiAddNode();
				tmp.setSign(sign);
				for (ExpressionNode expressionNode : terms) {
					tmp.setChild(expressionNode, -1);
				}
				return tmp;
			}
		}else {
			IntegerNode noder = new IntegerNode(0);
			noder.setSign(sign);
			return noder;
		}
	}

	public static ExpressionNode buildMultiplicativeExpressionFromList(List<ExpressionNode> terms) {
		return buildMultiplicativeExpressionFromList(terms, Sign.POSITIVE);
	}

	public static ExpressionNode buildAdditiveExpressionFromList(List<ExpressionNode> terms) {
		return buildAdditiveExpressionFromList(terms, Sign.POSITIVE);
	}

	public static boolean addExpressionToListIfNotInList(ExpressionNode expression, List<ExpressionNode> list) {
		boolean known = false;
		for (ExpressionNode expressionNode : list) {
			if (ExpressionUtil.isEqual(expressionNode, expression)) {
				known = true;
			}
		}
		if (!known) {
			list.add(expression);
		}
		return known;
	}

	public static boolean isAnyNodeType(IMathParserBaseNode node, Class<?>... nodeTypes) {
		if (node == null) {
			return false;
		}
		for (int i = 0; i < nodeTypes.length; i++) {
			if (nodeTypes[i].isAssignableFrom(node.getClass())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAnyNodeTypeInTree(ExpressionNode node, Class<?>... nodeTypes) {
		if (node == null) {
			return false;
		}
		if (isAnyNodeType(node, nodeTypes)) {
			return true;
		}
		for (int i = 0; i < node.getChildcount(); i++) {
			if (isAnyNodeTypeInTree(node.getChild(i), nodeTypes)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAllNodesInTreeOfAnyType(ExpressionNode node, Class<?>... nodeTypes) {
		if (node == null) {
			return false;
		}
		boolean currentNodeCorrectType = isAnyNodeType(node, nodeTypes);
		if (currentNodeCorrectType) {
			for (int i = 0; i < node.getChildcount(); i++) {
				if (!isAllNodesInTreeOfAnyType(node.getChild(i), nodeTypes)) {
					currentNodeCorrectType = false;
				}
			}
		}
		return currentNodeCorrectType;
	}

	private static boolean isEachNodeAnyType(ExpressionNode node, Class<?>... nodeTypes) {
		if (node == null) {
			return false;
		}
		if (isAnyNodeType(node, nodeTypes)) {
			return true;
		}
		boolean correctType = false;
		for (int i = 0; i < node.getChildcount(); i++) {
			if (isEachNodeAnyType(node.getChild(i), nodeTypes)) {
				correctType = true;
			}
		}
		return correctType;
	}

	public static boolean isEachNodeAnyTypeOrNull(ExpressionNode node, Class<?>... nodeTypes) {
		if (node == null) {
			return true;
		} else {
			return isEachNodeAnyType(node, nodeTypes);
		}
	}

	
}
