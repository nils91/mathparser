/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.ValueLookupTable;
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
public class ExactResultEquationTreeVisitor extends EquationTreeBaseVisitor<Boolean>{
	private ValueLookupTable lookup;
	

	private double numericalErrorThreshold=0;
	public ExactResultEquationTreeVisitor(ValueLookupTable lookup,
			double numericalErrorThreshold) {
		super();
		this.lookup = lookup;
		this.numericalErrorThreshold = numericalErrorThreshold;
	}
	public ExactResultEquationTreeVisitor(double numericalErrorThreshold) {
		super();
		this.numericalErrorThreshold = numericalErrorThreshold;
	}
	public ExactResultEquationTreeVisitor() {
		super();
	}
	

	@Override
	protected Boolean visitNode(EqualityNode node) {
		Double valueDeviation = node.accept(new FindDeviationEquationTreeVisitor(lookup));
		return valueDeviation<=numericalErrorThreshold;
	}

	@Override
	protected Boolean visitNode(GreaterEqualsNode node) {
		Double leftValue = node.getLeftExpression().accept(new CalculateNumericalValueVisitor(lookup));
		Double rightValue = node.getRightExpression().accept(new CalculateNumericalValueVisitor(lookup));
		if(Double.isNaN(leftValue)||Double.isNaN(rightValue)) {
			return false;
		}
		return leftValue>=rightValue+numericalErrorThreshold;
	}

	@Override
	protected Boolean visitNode(GreaterThanNode node) {
		Double leftValue = node.getLeftExpression().accept(new CalculateNumericalValueVisitor(lookup));
		Double rightValue = node.getRightExpression().accept(new CalculateNumericalValueVisitor(lookup));
		if(Double.isNaN(leftValue)||Double.isNaN(rightValue)) {
			return false;
		}
		return leftValue>rightValue+numericalErrorThreshold;
	}

	@Override
	protected Boolean visitNode(LessEqualsNode node) {
		Double leftValue = node.getLeftExpression().accept(new CalculateNumericalValueVisitor(lookup));
		Double rightValue = node.getRightExpression().accept(new CalculateNumericalValueVisitor(lookup));
		if(Double.isNaN(leftValue)||Double.isNaN(rightValue)) {
			return false;
		}
		return leftValue+numericalErrorThreshold<=rightValue;
	}

	@Override
	protected Boolean visitNode(LessThanNode node) {
		Double leftValue = node.getLeftExpression().accept(new CalculateNumericalValueVisitor(lookup));
		Double rightValue = node.getRightExpression().accept(new CalculateNumericalValueVisitor(lookup));
		if(Double.isNaN(leftValue)||Double.isNaN(rightValue)) {
			return false;
		}
		return leftValue+numericalErrorThreshold<rightValue;
	}

	@Override
	protected Boolean visitNode(UnequalityNode node) {
		Double valueDeviation = node.accept(new FindDeviationEquationTreeVisitor(lookup));
		return valueDeviation>numericalErrorThreshold;
	}

}
