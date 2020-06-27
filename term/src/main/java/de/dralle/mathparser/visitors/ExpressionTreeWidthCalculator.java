/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * Calculates tree width. Only leaf nodes arre counted.
 * @author Nils Dralle
 *
 */
public class ExpressionTreeWidthCalculator extends ExpressionTreeBaseVisitor<Integer> {

	@Override
	public Integer visit(IMathParserBaseNode node) {
		int width=1;
		for (int i = 0; i < node.getChildcount(); i++) {
			width+=node.getChild(i).accept(this);
		}
		return width;
	}

	
	
}
