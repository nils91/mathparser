/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Operator;

/**
 * @author Nils Dralle
 *
 */
public class ModuloNode extends BinaryExpressionNode {

	@Override
	public Operator getOperator() {
		return Operator.MODULO;
	}


}
