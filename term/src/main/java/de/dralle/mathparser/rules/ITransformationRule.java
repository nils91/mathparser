/**
 * 
 */
package de.dralle.mathparser.rules;

import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * Base interface for all rules. Defines mandatory methods.
 * @author Nils Dralle
 *
 */
public interface ITransformationRule {
	/**
	 * If this transformation rule can be applied to the given expression, this must
	 * return true. In all other cases it may still return true. The more accurate
	 * this method is the better the performance of the rule, but executing this
	 * method should take less time than the application of the rule. Should be a
	 * quick check.
	 * 
	 * @param expression
	 * @return
	 */
	  boolean isPossible(ExpressionNode expression);

	  /**
	   * Applies this rule to the given expression. If the rule can´t be applied this may return null and throw an exception, depending on the severity of the internal error.
	   * @param expression
	   * @return The modified expression. If the rule can't be applied returns null.
	   * @throws Exception
	   */
	  ExpressionNode apply(ExpressionNode expression) throws Exception;
	  
	/**
	 * Applies this rule to the given expression. If the rule can´t be applied this
	 * may return null and throw an exception, depending on the severity of the
	 * internal error.
	 * 
	 * @param expression
	 * @param transformAll Whether this rule should be applied wherever possible or
	 *                     only to the first occurrence found.
	 * @return The modified expression. If the rule can't be applied returns null.
	 * @throws Exception
	 */
	  ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception;
}
