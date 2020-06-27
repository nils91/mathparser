/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Functions;

/**
 * 
 * @author Nils Dralle
 *
 */
public class SummationNode extends LoopFunctionNode {
	
	public SummationNode() {
		super(Functions.SUM);
	}

	public SummationNode(EqualityNode identifierAssignment, ExpressionNode bound,
			ExpressionNode expression) {
		super(Functions.SUM, identifierAssignment, bound, expression);
	}


	
	
}
