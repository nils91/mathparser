/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.RelationOperator;

/**
 * @author Nils Dralle
 *
 */
public class UnequalityNode extends EquationNode {

	@Override
	public RelationOperator getOperator() {
		return RelationOperator.NE;
	}

	
}
