/**
 * 
 */
package de.dralle.mathparser;

import org.apache.commons.math3.util.FastMath;

import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.RealValuedNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.visitors.CalculateNumericalValueVisitor;
import de.dralle.mathparser.visitors.ExactResultEquationTreeVisitor;
import de.dralle.mathparser.visitors.FindDeviationEquationTreeVisitor;

/**
 * Util class for all the numerical checks.
 * 
 * @author Nils Dralle
 *
 */
public class NumericalEquationUtil {
	public static boolean checkNumericalCorrectness(String equation, double start, double end, double step) {
		return checkNumericalCorrectness(EquationParser.buildEquationTreeFromString(equation), start, end, step);
	}

	public static boolean checkNumericalCorrectness(EquationNode equation, double start, double end, double step) {
		return checkNumericalCorrectness(equation, start, end, step, 0);
	}

	public static boolean checkNumericalCorrectness(String equation, double start, double end, double step,
			double numericalErrorThreshold) {
		return checkNumericalCorrectness(EquationParser.buildEquationTreeFromString(equation), start, end, step,
				numericalErrorThreshold);
	}

	public static boolean checkNumericalCorrectness(EquationNode equation, double start, double end, double step,
			double numericalErrorThreshold) {
		int valueCnt = (int) ((end - start) / step) + 1;
		double[] values = new double[valueCnt];
		for (int i = 0; i < values.length; i++) {
			values[i] = start + i * step;
		}
		return checkNumericalCorrectness(equation, values, numericalErrorThreshold);
	}

	public static boolean checkNumericalCorrectness(String equation, double[] values) {
		return checkNumericalCorrectness(equation, values, 0);
	}

	public static boolean checkNumericalCorrectness(EquationNode equation, double[] values) {
		return checkNumericalCorrectness(equation, values, 0);
	}

	public static boolean checkNumericalCorrectness(String equation, double[] values, double numericalErrorThreshold) {
		return checkNumericalCorrectness(EquationParser.buildEquationTreeFromString(equation), values,
				numericalErrorThreshold);
	}

	public static boolean checkNumericalCorrectness(EquationNode equation, double[] values,
			double numericalErrorThreshold) {
		SymbolTable symbolTable = SymbolTable.fromTree(equation);
		SymbolIdentifier[] symbols = symbolTable.getIdentifiersAsArray();
		return checkNumericalCorrectness(equation, values, symbols, numericalErrorThreshold);
	}

	/**
	 * Will return as soon as one numerical check fail. Note: Due to numerical
	 * errors during the calculation the check might fail even for an equation which
	 * is symbolically correct.
	 * 
	 * @param equation
	 * @param values
	 * @param symbols
	 * @return
	 */
	private static boolean checkNumericalCorrectness(EquationNode equation, double[] values,
			SymbolIdentifier[] symbols) {
		return checkNumericalCorrectness(equation, values, symbols, 0);

	}

	/**
	 * Will return as soon as one numerical check fail. Note: Due to numerical
	 * errors during the calculation the check might fail even for an equation which
	 * is symbolically correct.
	 * 
	 * @param equation
	 * @param values
	 * @param symbols
	 * @return
	 */
	private static boolean checkNumericalCorrectness(EquationNode equation, double[] values, SymbolIdentifier[] symbols,
			double numericalErrorThreshold) {
		int valueCnt = values.length;
		int symbolCnt = symbols.length;
		int combinationCnt = (int) FastMath.pow(valueCnt, symbolCnt);
		if (symbolCnt == 0) {
			return checkNumericalCorrectness(equation, new ValueLookupTable(), numericalErrorThreshold);
		} else {
			ValueLookupTable lookup = new ValueLookupTable();
			for (int i = 0; i < combinationCnt; i++) {
				for (int j = 0; j < symbolCnt; j++) {
					int valueIndex = (int) (i / FastMath.pow(valueCnt, symbolCnt - (j + 1))) % valueCnt;
					lookup.setValueFor(symbols[j].getName(), symbols[j].getIndex(), values[valueIndex]);
				}
				if (!checkNumericalCorrectness(equation, lookup, numericalErrorThreshold)) {
					return false;
				}
			}
			return true;
		}

	}

	private static boolean checkNumericalCorrectness(EquationNode equation, ValueLookupTable lookup) {
		return checkNumericalCorrectness(equation, lookup, 0);
	}

	private static boolean checkNumericalCorrectness(EquationNode equation, ValueLookupTable lookup,
			double numericalErrorThreshold) {
		boolean result = equation.accept(new ExactResultEquationTreeVisitor(lookup, numericalErrorThreshold));
		return result;
	}

	public static float checkNumericalCorrectnessPercentage(String equation, double start, double end, double step) {
		return checkNumericalCorrectnessPercentage(equation, start, end, step, 0);
	}

	public static float checkNumericalCorrectnessPercentage(EquationNode equation, double start, double end,
			double step) {
		return checkNumericalCorrectnessPercentage(equation, start, end, step, 0);
	}

	public static float checkNumericalCorrectnessPercentage(String equation, double[] values) {
		return checkNumericalCorrectnessPercentage(equation, values, 0);
	}

	public static float checkNumericalCorrectnessPercentage(EquationNode equation, double[] values) {
		return checkNumericalCorrectnessPercentage(equation, values, 0);
	}

	/**
	 * Will count the number of successes and divide it by the total number of
	 * combinations tested.
	 * 
	 * @param equation
	 * @param values
	 * @param symbols
	 * @return Percentage of tests passed.
	 */
	private static float checkNumericalCorrectnessPercentage(EquationNode equation, double[] values,
			SymbolIdentifier[] symbols) {
		return checkNumericalCorrectnessPercentage(equation, values, symbols, 0);
	}

	public static float checkNumericalCorrectnessPercentage(String equation, double start, double end, double step,
			double numericalErrorThreshold) {
		return checkNumericalCorrectnessPercentage(EquationParser.buildEquationTreeFromString(equation), start, end,
				step, numericalErrorThreshold);
	}

	public static float checkNumericalCorrectnessPercentage(EquationNode equation, double start, double end,
			double step, double numericalErrorThreshold) {
		int valueCnt = (int) ((end - start) / step) + 1;
		double[] values = new double[valueCnt];
		for (int i = 0; i < values.length; i++) {
			values[i] = start + i * step;
		}
		return checkNumericalCorrectnessPercentage(equation, values, numericalErrorThreshold);
	}

	public static float checkNumericalCorrectnessPercentage(String equation, double[] values,
			double numericalErrorThreshold) {
		return checkNumericalCorrectnessPercentage(EquationParser.buildEquationTreeFromString(equation), values,
				numericalErrorThreshold);
	}

	public static float checkNumericalCorrectnessPercentage(EquationNode equation, double[] values,
			double numericalErrorThreshold) {
		SymbolTable symbolTable = SymbolTable.fromTree(equation);
		SymbolIdentifier[] symbols = symbolTable.getIdentifiersAsArray();
		return checkNumericalCorrectnessPercentage(equation, values, symbols, numericalErrorThreshold);
	}

	/**
	 * Will count the number of successes and divide it by the total number of
	 * combinations tested.
	 * 
	 * @param equation
	 * @param values
	 * @param symbols
	 * @return
	 */
	private static float checkNumericalCorrectnessPercentage(EquationNode equation, double[] values,
			SymbolIdentifier[] symbols, double numericalErrorThreshold) {
		int succeses = 0;
		int valueCnt = values.length;
		int symbolCnt = symbols.length;
		int combinationCnt = (int) FastMath.pow(valueCnt, symbolCnt);
		if (symbolCnt == 0) {
			return checkNumericalCorrectness(equation, values, symbols, numericalErrorThreshold) ? 1 : 0;
		} else {
			ValueLookupTable lookup = new ValueLookupTable();
			for (int i = 0; i < combinationCnt; i++) {
				for (int j = 0; j < symbolCnt; j++) {
					int valueIndex = (int) (i / FastMath.pow(valueCnt, symbolCnt - (j + 1))) % valueCnt;
					lookup.setValueFor(symbols[j].getName(), symbols[j].getIndex(), values[valueIndex]);
				}
				if (checkNumericalCorrectness(equation, lookup, numericalErrorThreshold)) {
					succeses++;
				}
			}
			return succeses / (float) combinationCnt;
		}
	}

	public static double getNumericalDeviation(String equation, double start, double end, double step) {
		return getNumericalDeviation(EquationParser.buildEquationTreeFromString(equation), start, end, step);
	}

	public static double getNumericalDeviation(EquationNode equation, double start, double end, double step) {
		int valueCnt = (int) ((end - start) / step) + 1;
		double[] values = new double[valueCnt];
		for (int i = 0; i < values.length; i++) {
			values[i] = start + i * step;
		}
		return getNumericalDeviation(equation, values);
	}

	public static double getNumericalDeviation(String equation, double[] values) {
		return getNumericalDeviation(EquationParser.buildEquationTreeFromString(equation), values);
	}

	public static double getNumericalDeviation(EquationNode equation, double[] values) {
		SymbolTable symbolTable = SymbolTable.fromTree(equation);
		SymbolIdentifier[] symbols = symbolTable.getIdentifiersAsArray();
		return getNumericalDeviation(equation, values, symbols);
	}

	/**
	 * Will average the deviation over all combinations. Values NaN and infinity are
	 * not added to keep the entire result from becoming NaN or Infinity, however
	 * the total number of combinations used to calculate the average remains
	 * unchanged. This means that a large number of NaNs and/or Infinitys will lead
	 * to a better result. If not a single deviation not equal to NaN or Infinity is
	 * returned, Infinity is returned.
	 * 
	 * @param equation
	 * @param values
	 * @param symbols
	 * @return
	 */
	private static double getNumericalDeviation(EquationNode equation, double[] values, SymbolIdentifier[] symbols) {
		int valueCnt = values.length;
		int symbolCnt = symbols.length;
		int combinationCnt = (int) FastMath.pow(valueCnt, symbolCnt);
		if (symbolCnt == 0) {
			return getNumericalDeviation(equation, new ValueLookupTable());
		} else {
			double totalDeviation = 0;
			boolean resultNan = true; // becomes false if at least one result is not NaN
			ValueLookupTable lookup = new ValueLookupTable();
			for (int i = 0; i < combinationCnt; i++) {
				for (int j = 0; j < symbolCnt; j++) {
					int valueIndex = (int) (i / FastMath.pow(valueCnt, symbolCnt - (j + 1))) % valueCnt;
					lookup.setValueFor(symbols[j].getName(), symbols[j].getIndex(), values[valueIndex]);
				}
				double deviation = getNumericalDeviation(equation, lookup);
				if (!(Double.isNaN(deviation) || Double.isInfinite(deviation))) {
					totalDeviation += getNumericalDeviation(equation, lookup);
					resultNan = false;
				}

			}
			if (resultNan) {
				return Double.POSITIVE_INFINITY;
			} else {
				return totalDeviation / combinationCnt;
			}
		}
	}

	private static double getNumericalDeviation(EquationNode equation, ValueLookupTable lookup) {
		double result = equation.accept(new FindDeviationEquationTreeVisitor(lookup));
		return result;
	}

	/**
	 * 
	 * @param left             The tree on which a match is to be found.
	 * @param right            The other tree, which is the 'pattern' the first tree
	 *                         is compared to.
	 * @param exact            If true, every node type in both trees is checked
	 *                         whether they have the same type (100% structural
	 *                         equality between left and right). if false, then
	 *                         identifier nodes in the tree 'right' match the whole
	 *                         subtree on tree 'left'.
	 * @param checkContent     Check the content of number containing nodes. (int,
	 *                         real and constants)
	 * @param checkIdentifiers If and identifier node is matched, check whether its
	 *                         name is the same. Only has an effect if exact = true.
	 * @return
	 */
	@Deprecated
	public static boolean isStructurallyEqual(IMathParserBaseNode left, IMathParserBaseNode right, boolean exact,
			boolean checkContent, boolean checkIdentifiers) {
		if (!exact) {
			if (right instanceof IdentifierNode) {
				return true;
			}
		} else if (checkIdentifiers) {
			if (left instanceof IdentifierNode && right instanceof IdentifierNode) {
				return ((IdentifierNode) left).getName().equals(((IdentifierNode) right).getName());
			}
		}
		if (checkContent) {
			if (left instanceof RealValuedNode && right instanceof RealValuedNode) {
				CalculateNumericalValueVisitor visitor = new CalculateNumericalValueVisitor();
				return left.accept(visitor).equals(right.accept(visitor));
			}
		}
		if (left.getClass().equals(right.getClass())) {
			if (left.getChildcount() == right.getChildcount()) {
				for (int i = 0; i < left.getChildcount(); i++) {
					IMathParserBaseNode leftChild = left.getChild(i);
					IMathParserBaseNode rightChild = right.getChild(i);
					if (NodeUtils.isAnyNodeType(leftChild, ExpressionNode.class)
							&& NodeUtils.isAnyNodeType(rightChild, ExpressionNode.class)) {
						if (!ExpressionUtil.isStructurallyEqual(leftChild, rightChild, exact, checkContent,
								checkIdentifiers,false)) {
							return false;
						}
					} else {
						if (!isStructurallyEqual(leftChild, rightChild, exact, checkContent, checkIdentifiers)) {
							return false;
						}
					}

				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
