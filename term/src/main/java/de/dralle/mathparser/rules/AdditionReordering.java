/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.NodeReorderingComparator;
import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.MultiAddNode;

/**
 * Ordering subterms in sums.
 * @author Nils Dralle
 *
 */
public class AdditionReordering extends AbstractTransformationRule {

	@Override
	public boolean isPossible(ExpressionNode expression) {
		return NodeUtils.isAnyNodeTypeInTree(expression, MultiAddNode.class);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		return apply(expression, true);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll){
		int transformed=0;
		if (expression instanceof MultiAddNode) {
			ExpressionNode expressionBeforeSort = null;
			try {
				expressionBeforeSort = expression.clone();
			} catch (CloneNotSupportedException e) {
			}
			sortChilds(expression);
			if(!expressionBeforeSort.equals(expression)) {
				transformed++;
			}
		} else {
			for (int i = 0; i < expression.getChildcount(); i++) {
				ExpressionNode newex = apply(expression.getChild(i), transformAll);
				if(newex!=null) {
					expression.setChild(newex, i);
					transformed++;
				}
			}
		}
		return transformed>0?expression:null;
	}

	/**
	 * @param expression
	 */
	private void sortChilds(ExpressionNode expression) {
		List<ExpressionNode> childs = new ArrayList<ExpressionNode>();
		for (int i = 0; i < expression.getChildcount(); i++) {
			childs.add(expression.getChild(i));
		}
		childs.sort(new NodeReorderingComparator());
		for (int i = 0; i < childs.size(); i++) {
			expression.setChild(childs.get(i), i);
		}
	}
}
