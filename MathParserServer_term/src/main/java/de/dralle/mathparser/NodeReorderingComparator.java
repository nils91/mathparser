/**
 * 
 */
package de.dralle.mathparser;

import java.util.Comparator;

import org.apache.commons.math3.util.FastMath;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.NumberNode;
import de.dralle.mathparser.nodes.PowNode;
import de.dralle.mathparser.visitors.CalculateNumericalValueVisitor;

/**
 * 1: Anzahl unterschiedlicher Variablen 
 * 2: Wenn gleichviele
 *         Variablen -> höchste Potenz 
 *         3: Wenn höchste Potenz gleich -> Summe
 *         der Exponenten 
 *         4: Wenn 3 gleich: Namen der Variablen 
 *         5: Wenn 4 gleich
 *         -> Anzahle Konstanten (+Zahlen) 
 *         6: Wenn 5 gleich: Größe der Zahlen
 *         7: hashCode() als fallback
 *         
 * @author Nils Dralle 
 */
public class NodeReorderingComparator implements Comparator<ExpressionNode> {
	@Override
	public int compare(ExpressionNode expression0, ExpressionNode expression1) {
		SymbolTable s0 = SymbolTable.fromTree(expression0);
		SymbolTable s1 = SymbolTable.fromTree(expression1);
		double exponentEx0 = getHighestExponent(expression0);
		double exponentEx1 = getHighestExponent(expression1);
		double diffExponent = exponentEx1 - exponentEx0;
		if (diffExponent != 0) {
			if (diffExponent > 1) {
				return (int) diffExponent;
			} else {
				if (diffExponent > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		}
		int diffVarCount = s1.getIdentifiers().size() - s0.getIdentifiers().size();
		if (diffVarCount != 0) {
			return diffVarCount;
		}
		double exponentSum0 = getExponentSum(expression0);
		double exponentSum1 = getExponentSum(expression1);
		double diffExponentSum = exponentSum1 - exponentSum0;
		if (diffExponentSum != 0) {
			if (FastMath.abs(diffExponentSum) > 1) {
				return (int) diffExponentSum;
			} else {
				if (diffExponentSum > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		}
		SymbolIdentifier[] s1IdentifiersSorted = s1.getIdentifiersAsArray();
		SymbolIdentifier[] s0IdentifiersSorted = s0.getIdentifiersAsArray();
		for (int i = 0; i < s0IdentifiersSorted.length; i++) {// identifier counts here are guaranteed to be the same
			int diff = s0IdentifiersSorted[i].compareTo(s1IdentifiersSorted[i]);
			if (diff != 0) {
				return diff;
			}
		}
		int diffConstCount = s1.getConstants().size() - s0.getConstants().size();
		if (diffConstCount != 0) {
			return diffConstCount;
		}
		double maxNumberEx0 = getHighestNumber(expression0);
		double maxNumberEx1 = getHighestNumber(expression1);
		double diffMaxNumber = maxNumberEx1 - maxNumberEx0;
		if (diffMaxNumber != 0) {
			if (diffMaxNumber > 1) {
				return (int) diffMaxNumber;
			} else {
				if (diffMaxNumber > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		}
		return expression0.hashCode()-expression1.hashCode();
	}

	private double getHighestNumber(ExpressionNode expression) {
		if (expression instanceof NumberNode) {
			return ((NumberNode) expression).getValue().doubleValue();
		}
		double max = Double.MIN_VALUE;
		for (int i = 0; i < expression.getChildcount(); i++) {
			max = FastMath.max(max, getHighestNumber(expression.getChild(i)));
		}
		return max;
	}

	private double getExponentSum(ExpressionNode expression) {
		if (expression instanceof PowNode) {
			return getExponent(expression);
		}
		double sum = 0;
		for (int i = 0; i < expression.getChildcount(); i++) {
			sum += getExponentSum(expression.getChild(i));
		}
		return sum;
	}

	private double getHighestExponent(ExpressionNode expression) {
		if (expression instanceof PowNode) {
			return getExponent(expression);
		}
		double max = Double.MIN_VALUE;
		for (int i = 0; i < expression.getChildcount(); i++) {
			max = FastMath.max(max, getHighestExponent(expression.getChild(i)));
		}
		return max;
	}

	private double getExponent(ExpressionNode expression) {
		if (expression instanceof PowNode) {
			ExpressionNode exponentExpression = ((PowNode) expression).getRightExpression();
			double exponentValue = 0;
			try {
				exponentValue = exponentExpression.accept(new CalculateNumericalValueVisitor());
			} catch (Exception e) {

			}
			return exponentValue;
		}
		if (expression.getChildcount() == 1) {
			return getExponent(expression.getChild(0));
		}
		return 1;
	}

}
