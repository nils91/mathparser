/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Functions;

/**
 * First param n, 2nd k
 * @author Nils Dralle
 *
 */public class ProductNode extends LoopFunctionNode {
		public ProductNode() {
			super(Functions.PROD);
		}

		public ProductNode(EqualityNode identifierAssignment, ExpressionNode bound,
				ExpressionNode expression) {
			super(Functions.PROD, identifierAssignment, bound, expression);
		}
	}