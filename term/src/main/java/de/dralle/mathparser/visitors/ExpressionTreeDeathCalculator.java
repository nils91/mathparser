/**
 * 
 */
package de.dralle.mathparser.visitors;

import org.apache.commons.math3.util.FastMath;

import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * Calculates tree depth.
 * @author Nils Dralle
 *
 */
public class ExpressionTreeDeathCalculator extends ExpressionTreeBaseVisitor<Integer> {

	@Override
	public Integer visit(IMathParserBaseNode node) {
		int maxDepth=0;
		for (int i = 0; i < node.getChildcount(); i++) {
			maxDepth=FastMath.max(maxDepth, node.getChild(i).accept(this));
		}
		return maxDepth+1;
	}

	
	
}
