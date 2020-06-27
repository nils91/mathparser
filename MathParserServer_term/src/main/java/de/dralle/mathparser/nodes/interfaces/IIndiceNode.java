/**
 * 
 */
package de.dralle.mathparser.nodes.interfaces;

import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * For all node types that can have an indice.
 * 
 * @author Nils Dralle
 *
 */
public interface IIndiceNode {
	ExpressionNode getIndice();
	void setIndice(ExpressionNode indice);
	boolean hasSimpleTextualNumericIndice();
	boolean hasAdvancedIndice();
}
