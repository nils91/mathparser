/**
 * 
 */
package de.dralle.mathparser.visitors;

import org.apache.commons.math3.util.FastMath;

import de.dralle.mathparser.ValueLookupTable;
import de.dralle.mathparser.nodes.EquationNode;

/**
 * @author Nils Dralle
 *
 */
public class FindDeviationEquationTreeVisitor extends EquationTreeBaseVisitor<Double>{
	private ValueLookupTable lookup;
	public FindDeviationEquationTreeVisitor(ValueLookupTable lookup) {
		super();
		this.lookup = lookup;
	}

	public FindDeviationEquationTreeVisitor() {
		super();
	}

	@Override
	protected Double visitNode(EquationNode node) {
		Double leftValue = node.getLeftExpression().accept(new CalculateNumericalValueVisitor(lookup));
		Double rightValue = node.getRightExpression().accept(new CalculateNumericalValueVisitor(lookup));
		double deviation = FastMath.abs((leftValue)-(rightValue));
		if(Double.isNaN(deviation)) {
			return Double.POSITIVE_INFINITY;
		}
		return deviation;
	}

	
}
