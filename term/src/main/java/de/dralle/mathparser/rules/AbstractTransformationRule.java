/**
 * 
 */
package de.dralle.mathparser.rules;

import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * Base for all transformation rules.
 * @author Nils Dralle
 * 
 */
public abstract class AbstractTransformationRule implements ITransformationRule{

	public abstract boolean isPossible(ExpressionNode expression);

	public ExpressionNode apply(ExpressionNode expression) throws Exception{
		return apply(expression, true);
	}

	public abstract ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception;

}
