/**
 * 
 */
package de.dralle.mathparser.nodes.interfaces;

/**
 * Interface for all binary nodes. Binary here means the node has two childs, a left and a right.
 * 
 * @author Nils Dralle
 *
 */
public interface IBinaryNode{
	IExpressionNode getLeftExpression();
	IExpressionNode getRightExpression();
}
