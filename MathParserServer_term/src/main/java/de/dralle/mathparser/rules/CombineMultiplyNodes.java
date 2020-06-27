/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;

/**
 * Combine neighboring multiply nodes into one.
 * @author Nils Dralle
 *
 */
public class CombineMultiplyNodes extends AbstractTransformationRule {

	@Override
	public boolean isPossible(ExpressionNode expression) {
		return NodeUtils.isAnyNodeTypeInTree(expression, MultiMultiplyNode.class);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		return apply(expression, true);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {
		int transformed = 0;
		if (NodeUtils.isAnyNodeType(expression, MultiMultiplyNode.class)) {
			List<ExpressionNode> childs = new ArrayList<ExpressionNode>();
			Sign sign = getSignFromAllMultiplyChildren(expression, childs);
			ExpressionNode newEx = NodeUtils.buildMultiplicativeExpressionFromList(childs, sign);
			if (newEx != null&&!newEx.equals(expression)) {
				transformed++;
				expression = newEx;
				if (!transformAll) {
					return transformed > 0 ? expression : null;
				}
			}
		}
		for (int i = 0; i < expression.getChildcount(); i++) {
			ExpressionNode transformedChild = apply(expression.getChild(i), transformAll);
			if (transformedChild != null) {
				expression.setChild(transformedChild, i);
				transformed++;
				if (!transformAll) {
					return transformed > 0 ? expression : null;
				}
			}
		}
		return transformed > 0 ? expression : null;
	}

	private Sign getSignFromAllMultiplyChildren(ExpressionNode expression, List<ExpressionNode> childList) {
		if (expression instanceof MultiMultiplyNode) {
			return getSignFromAllMultiplyChildren((MultiMultiplyNode) expression, childList);
		}
		return Sign.POSITIVE;
	}

	private Sign getSignFromAllMultiplyChildren(MultiMultiplyNode expression, List<ExpressionNode> childList) {
		Sign sign = expression.getSign();
		for (int i = 0; i < expression.getChildcount(); i++) {
			List<ExpressionNode> tmpChildList = new ArrayList<ExpressionNode>();
			Sign tmpSign = getSignFromAllMultiplyChildren(expression.getChild(i), tmpChildList);
			if (tmpChildList.size() > 0) {
				childList.addAll(tmpChildList);
				if (tmpSign.equals(Sign.NEGATIVE)) {
					sign = sign.equals(Sign.NEGATIVE) ? Sign.POSITIVE : Sign.NEGATIVE;
				}
			} else {
				childList.add(expression.getChild(i));
			}
		}
		return sign;
	}

}
