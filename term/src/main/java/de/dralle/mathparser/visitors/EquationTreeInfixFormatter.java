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
public class EquationTreeInfixFormatter extends EquationTreeStringVisitor{

	private final ExpressionTreeInfixFormatter expressionFormatter=new ExpressionTreeInfixFormatter();
	@Override
	protected String visitNode(EqualityNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("%s=%s", left,right);
	}

	@Override
	protected String visitNode(GreaterEqualsNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("%s=>%s", left,right);
	}

	@Override
	protected String visitNode(GreaterThanNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("%s>%s", left,right);
	}

	@Override
	protected String visitNode(LessEqualsNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("%s<=%s", left,right);
	}

	@Override
	protected String visitNode(LessThanNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("%s<%s", left,right);
	}

	@Override
	protected String visitNode(UnequalityNode node) {
		String left = node.getLeftExpression().accept(expressionFormatter);
		String right = node.getRightExpression().accept(expressionFormatter);
		return String.format("%s<>%s", left,right);
	}

}
