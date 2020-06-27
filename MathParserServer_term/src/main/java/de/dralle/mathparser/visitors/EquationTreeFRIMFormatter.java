/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.GreaterEqualsNode;
import de.dralle.mathparser.nodes.GreaterThanNode;
import de.dralle.mathparser.nodes.LessEqualsNode;
import de.dralle.mathparser.nodes.LessThanNode;
import de.dralle.mathparser.nodes.UnequalityNode;

/**
 * @author Nils Dralle
 *
 */
public class EquationTreeFRIMFormatter extends EquationTreeStringVisitor{

	private final ExpressionTreeFRIMFormatter expressionFormatter=new ExpressionTreeFRIMFormatter();
	@Override
	protected String visitNode(EqualityNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("eq(%s,%s)", left,right);
	}

	@Override
	protected String visitNode(GreaterEqualsNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("ge(%s,%s)", left,right);
	}

	@Override
	protected String visitNode(GreaterThanNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("gt(%s,%s)", left,right);
	}

	@Override
	protected String visitNode(LessEqualsNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("le(%s,%s)", left,right);
	}

	@Override
	protected String visitNode(LessThanNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("lt(%s,%s)", left,right);
	}

	@Override
	protected String visitNode(UnequalityNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("ne(%s,%s)", left,right);
	}

}
