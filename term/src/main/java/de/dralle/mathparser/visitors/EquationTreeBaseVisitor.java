/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.GreaterEqualsNode;
import de.dralle.mathparser.nodes.GreaterThanNode;
import de.dralle.mathparser.nodes.LessEqualsNode;
import de.dralle.mathparser.nodes.LessThanNode;
import de.dralle.mathparser.nodes.UnequalityNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 * @param <T>
 *
 */
public abstract class EquationTreeBaseVisitor<T> extends MathParserBaseTreeVisitor<T> {

	@Override
	public T visit(IMathParserBaseNode node) {
		T value = null;
		if (node instanceof EquationNode) {
			if (value == null)
				value = visitNode((EquationNode) node);
		}
		if (node instanceof EqualityNode) {
			if (value == null)
				value = visitNode((EqualityNode) node);
		}
		if (node instanceof GreaterEqualsNode) {
			if (value == null)
				value = visitNode((GreaterEqualsNode) node);
		}
		if (node instanceof GreaterThanNode) {
			if (value == null)
				value = visitNode((GreaterThanNode) node);
		}
		if (node instanceof LessEqualsNode) {
			if (value == null)
				value = visitNode((LessEqualsNode) node);
		}
		if (node instanceof LessThanNode) {
			if (value == null)
				value = visitNode((LessThanNode) node);
		}
		if (node instanceof UnequalityNode) {
			if (value == null)
				value = visitNode((UnequalityNode) node);
		}
		return value;
	}

	protected T visitNode(EquationNode node) {
		
		return null;
	}

	protected T visitNode(EqualityNode node) {
		
		return null;
	}

	protected T visitNode(GreaterEqualsNode node) {
		
		return null;
	}

	protected T visitNode(GreaterThanNode node) {
		
		return null;
	}

	protected T visitNode(LessEqualsNode node) {
		
		return null;
	}

	protected T visitNode(LessThanNode node) {
		
		return null;
	}

	protected T visitNode(UnequalityNode node) {
		
		return null;
	}
}
