/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public abstract class MathParserBaseTreeVisitor<T> {
	public T visit(IMathParserBaseNode node) {
		visitChildren(node);
		return null;
	}
	protected void visitChildren(IMathParserBaseNode node) {
		for (int i = 0; i < node.getChildcount(); i++) {
			node.getChild(i).accept(this);
		}
	}
	
}
