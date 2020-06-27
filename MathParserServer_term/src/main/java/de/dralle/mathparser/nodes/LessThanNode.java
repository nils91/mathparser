/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.RelationOperator;

/**
 * @author Nils Dralle
 *
 */
public class LessThanNode extends EquationNode {

	@Override
	public RelationOperator getOperator() {
		return RelationOperator.LT;
	}


}
