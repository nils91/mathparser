/**
 * 
 */
package de.dralle.mathparser;

import java.util.HashMap;
import java.util.Map;

import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.RealNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public class ExpressionUtil {
	public static boolean isEqual(IMathParserBaseNode left, IMathParserBaseNode right) {
		return isStructurallyEqual(left, right, true, true, true,false);
	}

	/**
	 * This is basically the same Implementation as EquationUtil#isStructurallyEqual
	 * 
	 * @param left             The tree on which a match is to be found.
	 * @param right            The other tree, which is the 'pattern' the first tree
	 *                         is compared to.
	 * @param exact            If true, every node type in both trees is checked
	 *                         whether they have the same type (100% structural
	 *                         equality between left and right). If false, then
	 *                         identifier nodes in the tree 'right' match the whole
	 *                         subtree on tree 'left'.
	 * @param checkContent     Check the content of number containing nodes. (int,
	 *                         real and constants)
	 * @param checkIdentifiers If an identifier node is matched, check whether its
	 *                         name is the same. Only has an effect if exact = true.
	 * @param ignoreChildcount If set to true and the childcount of the left
	 *                         expression is higher than that of the right, the
	 *                         method will look at the child of left and try to
	 *                         match them to the childs of right left to right.
	 * @return
	 */
	public static boolean isStructurallyEqual(IMathParserBaseNode left, IMathParserBaseNode right, boolean exact,
			boolean checkContent, boolean checkIdentifiers, boolean ignoreChildcount) {
		if (left == null || right == null) {
			return false;
		}
		if (!exact) {
			if (right instanceof IdentifierNode) {
				return true;
			}
		} else if (checkIdentifiers) {
			if (left instanceof IdentifierNode && right instanceof IdentifierNode) {
				IdentifierNode leftIdentifer = (IdentifierNode) left;
				IdentifierNode rightIdentifier = (IdentifierNode) right;
				if (!leftIdentifer.hasAdvancedIndice() || !rightIdentifier.hasAdvancedIndice()) {
					return leftIdentifer.equals(rightIdentifier);
				}
			}
		}
		if (checkContent) {
			if (NodeUtils.isAnyNodeType(left, ConstantNode.class, IntegerNode.class, RealNode.class)
					&& NodeUtils.isAnyNodeType(right, ConstantNode.class, IntegerNode.class, RealNode.class)) {
				return left.equals(right);
			}
		}
		boolean signEqual = true;
		if (left instanceof ExpressionNode && right instanceof ExpressionNode) {
			signEqual = ((ExpressionNode) left).getSign().equals(((ExpressionNode) right).getSign());
		}
		if (signEqual) {
			if (left.getClass().equals(right.getClass())) {
				if (ignoreChildcount &&  left.getChildcount() >= right.getChildcount()) {
					Map<Integer, Integer> rightSideMatchingLeftIndices = new HashMap<Integer, Integer>();
					int lastMatchedLeftIndex = -1;
					for (int i = 0; i < right.getChildcount(); i++) {
						for (int j = 0; j < left.getChildcount(); j++) {
							IMathParserBaseNode leftChild = left.getChild(j);
							IMathParserBaseNode rightChild = right.getChild(i);
							boolean match = isStructurallyEqual(leftChild, rightChild, exact, checkContent,
									checkIdentifiers,ignoreChildcount);
							if (match && (lastMatchedLeftIndex == -1 || j > lastMatchedLeftIndex)) {
								rightSideMatchingLeftIndices.put(i, j);
								lastMatchedLeftIndex = j;
								break;
							}
						}
					}
					return rightSideMatchingLeftIndices.size() == right.getChildcount();
				} else {
					if (left.getChildcount() == right.getChildcount()) {
						for (int i = 0; i < left.getChildcount(); i++) {
							IMathParserBaseNode leftChild = left.getChild(i);
							IMathParserBaseNode rightChild = right.getChild(i);
							if (!isStructurallyEqual(leftChild, rightChild, exact, checkContent, checkIdentifiers,ignoreChildcount)) {
								return false;
							}
						}
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}	
}
