/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.MultiAddNode;

/**
 * Combine multiple neighboring add nodes into one.
 * @author Nils Dralle
 *
 */
public class CombineAddNodes extends AbstractTransformationRule {

	@Override
	public boolean isPossible(ExpressionNode expression) {
		return NodeUtils.isAnyNodeTypeInTree(expression, MultiAddNode.class);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		return apply(expression, true);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {
		int transformed = 0;
		if (NodeUtils.isAnyNodeType(expression, MultiAddNode.class)) {
			List<ExpressionNode> childs = new ArrayList<ExpressionNode>();
			Sign sign = getSignFromAllAddChildren(expression, childs);
			ExpressionNode newEx = NodeUtils.buildAdditiveExpressionFromList(childs, sign);
			if (newEx != null && !newEx.equals(expression)) {
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

	private Sign getSignFromAllAddChildren(ExpressionNode expression, List<ExpressionNode> childList) {
		if (expression instanceof MultiAddNode) {
			return getSignFromAllAddChildren((MultiAddNode) expression, childList);
		}
		return Sign.POSITIVE;
	}

	private Sign getSignFromAllAddChildren(MultiAddNode expression, List<ExpressionNode> childList) {
		Sign sign = expression.getSign();
		for (int i = 0; i < expression.getChildcount(); i++) {
			List<ExpressionNode> tmpChildList = new ArrayList<ExpressionNode>();
			Sign tmpSign = getSignFromAllAddChildren(expression.getChild(i), tmpChildList);
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
