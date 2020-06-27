/**
 * 
 */
package de.dralle.mathparser.visitors;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.util.FastMath;

import de.dralle.mathparser.Constants;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.ValueLookupTable;
import de.dralle.mathparser.nodes.AbsoluteNode;
import de.dralle.mathparser.nodes.AcosNode;
import de.dralle.mathparser.nodes.AcotNode;
import de.dralle.mathparser.nodes.AsinNode;
import de.dralle.mathparser.nodes.AtanNode;
import de.dralle.mathparser.nodes.BinomNode;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.CosNode;
import de.dralle.mathparser.nodes.CoshNode;
import de.dralle.mathparser.nodes.CotNode;
import de.dralle.mathparser.nodes.CothNode;
import de.dralle.mathparser.nodes.Degree2RadiansConversionNode;
import de.dralle.mathparser.nodes.DeterminantNode;
import de.dralle.mathparser.nodes.DivisionNode;
import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.FactorialNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.LdNode;
import de.dralle.mathparser.nodes.LgNode;
import de.dralle.mathparser.nodes.LnNode;
import de.dralle.mathparser.nodes.LogNode;
import de.dralle.mathparser.nodes.MathmaticalRootNode;
import de.dralle.mathparser.nodes.MatrixNode;
import de.dralle.mathparser.nodes.ModuloNode;
import de.dralle.mathparser.nodes.MultiAddNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.NumberNode;
import de.dralle.mathparser.nodes.PowNode;
import de.dralle.mathparser.nodes.ProductNode;
import de.dralle.mathparser.nodes.SinNode;
import de.dralle.mathparser.nodes.SinhNode;
import de.dralle.mathparser.nodes.SqrtNode;
import de.dralle.mathparser.nodes.SubtractNode;
import de.dralle.mathparser.nodes.SummationNode;
import de.dralle.mathparser.nodes.TanNode;
import de.dralle.mathparser.nodes.TanhNode;
import de.dralle.mathparser.nodes.TextNode;

/**
 * Numerical evaluation
 * 
 * @author Nils Dralle
 *
 */
public class CalculateNumericalValueVisitor extends ExpressionTreeBaseVisitor<Double> {

	private ValueLookupTable valueLookupTable=null;

	
	public CalculateNumericalValueVisitor(ValueLookupTable valueLookupTable) {
		super();
		this.valueLookupTable = valueLookupTable;
	}

	public CalculateNumericalValueVisitor() {
		super();
	}
	
	@Override
	protected Double handleSign(Double value,ExpressionNode node) {
		return node.getSign().equals(Sign.NEGATIVE)?-value:value;
	}

	@Override
	protected Double visitNode(AcosNode node) {
		return FastMath.acos(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(MultiAddNode node) {
		double value = 0;
		for (int i = 0; i < node.getChildcount(); i++) {
			value += node.getChild(i).accept(this);
		}
		return value;
	}

	@Override
	protected Double visitNode(MultiMultiplyNode node) {
		double value = 1;
		for (int i = 0; i < node.getChildcount(); i++) {
			value *= node.getChild(i).accept(this);
		}
		return value;
	}

	@Override
	protected Double visitNode(AsinNode node) {
		return FastMath.asin(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(AtanNode node) {
		return FastMath.atan(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(CosNode node) {
		return FastMath.cos(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(Degree2RadiansConversionNode node) {
		return FastMath.toRadians(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(DivisionNode node) {
		double left = node.getLeftExpression().accept(this);
		double right = node.getRightExpression().accept(this);
		return left / right;
	}

	@Override
	protected Double visitNode(FactorialNode node) {
		Double value = node.getChild(0).accept(this);
		return CombinatoricsUtils.factorialDouble(value.intValue());
	}

	@Override
	protected Double visitNode(IdentifierNode node) {
		if (valueLookupTable == null) {
			throw new RuntimeException("No value table given");
		}
		String identifier = node.getName();
		if(node.hasIndice()) {
			if(node.hasSimpleTextualNumericIndice()) {
				TextNode indiceNode=(TextNode) node.getIndice();
				String indice = indiceNode.getContent();
				return valueLookupTable.getValueFor(identifier,indice);
			}else if(node.hasAdvancedIndice()) {
				ExpressionNode indiceNode = node.getIndice();
				String indice=""+indiceNode.accept(this).intValue();
				return valueLookupTable.getValueFor(identifier,indice);
			}
		}else {
			return (valueLookupTable.getValueFor(identifier));
		}
		return null;
	}

	@Override
	protected Double visitNode(ConstantNode node) {
		if (valueLookupTable == null) {
			throw new RuntimeException("No value table given");
		}
		Constants constant = node.getConstant();
		if(node.hasIndice()) {
			if(node.hasSimpleTextualNumericIndice()) {
				TextNode indiceNode=(TextNode) node.getIndice();
				String indice = indiceNode.getContent();
				return valueLookupTable.getValueFor(constant,indice);
			}else if(node.hasAdvancedIndice()) {
				ExpressionNode indiceNode = node.getIndice();
				String indice=""+indiceNode.accept(this).intValue();
				return valueLookupTable.getValueFor(constant,indice);
			}
		}else {
			return (valueLookupTable.getValueFor(constant));
		}
		return null;
	}

	@Override
	protected Double visitNode(NumberNode node) {
		return node.getValue().doubleValue();
	}

	@Override
	protected Double visitNode(LdNode node) {
		return FastMath.log(2,node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(LnNode node) {
		return FastMath.log(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(LgNode node) {
		return FastMath.log10(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(LogNode node) {
		double left = node.getLeftExpression().accept(this);
		double right = node.getRightExpression().accept(this);
		return FastMath.log(left,right);
	}

	private double calculateNthRoot(double value, double n) {
		return FastMath.pow(value, 1 / n);
	}

	@Override
	protected Double visitNode(MathmaticalRootNode node) {
		double left = node.getLeftExpression().accept(this);
		double right = node.getRightExpression().accept(this);
		return calculateNthRoot(right,left);
	}

	@Override
	protected Double visitNode(ModuloNode node) {
		double left = node.getLeftExpression().accept(this);
		double right = node.getRightExpression().accept(this);
		return left % right;
	}
	@Override
	protected Double visitNode(PowNode node) {
		double left = node.getLeftExpression().accept(this);
		double right = node.getRightExpression().accept(this);
		return FastMath.pow(left,right);
	}

	@Override
	protected Double visitNode(SinNode node) {
		return FastMath.sin(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(SqrtNode node) {
		return FastMath.sqrt(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(SubtractNode node) {
		double left = node.getLeftExpression().accept(this);
		double right = node.getRightExpression().accept(this);
		return left - right;
	}

	@Override
	protected Double visitNode(TanNode node) {
		return FastMath.tan(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(AbsoluteNode node) {
		return FastMath.abs(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(BinomNode node) {
		Double n = node.getN().accept(this);
		Double k = node.getK().accept(this);
		try {
			return CombinatoricsUtils.binomialCoefficientDouble(n.intValue(),k.intValue());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Double visitNode(AcotNode node) {
		return 1.0 / FastMath.atan(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(CoshNode node) {
		return FastMath.cosh(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(CotNode node) {
		return 1.0 / FastMath.tan(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(CothNode node) {
		return 1.0 / FastMath.tanh(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(DeterminantNode node) {
		MatrixNode maT = node.getChildMatrix();
		double[][] matrix = new double[maT.getMatrixWidth()][maT.getMatrixHeight()];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = (maT.getElement(i, j).accept(this));
			}
		}
		Array2DRowRealMatrix acmMatrix = new Array2DRowRealMatrix(matrix);
		return (new LUDecomposition(acmMatrix).getDeterminant());
	}

	@Override
	protected Double visitNode(ProductNode node) {
		EqualityNode identAssign = node.getIdentifierAssignment();
		IdentifierNode runningIdentifier = (IdentifierNode) identAssign.getLeftExpression();
		TextNode runningIdentifierIndice=null;
		if(runningIdentifier.hasIndice()) {
			runningIdentifierIndice=(TextNode) runningIdentifier.getIndice();
		}
		ExpressionNode lowerBound = identAssign.getRightExpression();
		ExpressionNode upperBound = node.getUpperBound();
		Double lowerBoundValue = lowerBound.accept(this);
		Double upperBoundValue = upperBound.accept(this);
		ValueLookupTable scopedValueTable = null;
		if(valueLookupTable!=null) {
			try {
				scopedValueTable=valueLookupTable.clone();
			} catch (CloneNotSupportedException e) {				
			}
		}else {
			scopedValueTable=new ValueLookupTable();
		}
		CalculateNumericalValueVisitor childValueCalculator = new CalculateNumericalValueVisitor(scopedValueTable);
		double fullValue=1;
		for (int i = lowerBoundValue.intValue(); i <= upperBoundValue.intValue(); i++) {
			if(runningIdentifierIndice==null) {
				scopedValueTable.setValueFor(runningIdentifier.getName(), i);
			}else {
				scopedValueTable.setValueFor(runningIdentifier.getName(), runningIdentifierIndice.getContent(), i);
			}
			ExpressionNode expression = node.getExpression();
			Double expressionValue = expression.accept(childValueCalculator);
			fullValue*=expressionValue;
		}
		return fullValue;
	}

	@Override
	protected Double visitNode(SummationNode node) {
		EqualityNode identAssign = node.getIdentifierAssignment();
		IdentifierNode runningIdentifier = (IdentifierNode) identAssign.getLeftExpression();
		TextNode runningIdentifierIndice=null;
		if(runningIdentifier.hasIndice()) {
			runningIdentifierIndice=(TextNode) runningIdentifier.getIndice();
		}
		ExpressionNode lowerBound = identAssign.getRightExpression();
		ExpressionNode upperBound = node.getUpperBound();
		Double lowerBoundValue = lowerBound.accept(this);
		Double upperBoundValue = upperBound.accept(this);
		ValueLookupTable scopedValueTable = null;
		if(valueLookupTable!=null) {
			try {
				scopedValueTable=valueLookupTable.clone();
			} catch (CloneNotSupportedException e) {				
			}
		}else {
			scopedValueTable=new ValueLookupTable();
		}
		CalculateNumericalValueVisitor childValueCalculator = new CalculateNumericalValueVisitor(scopedValueTable);
		double fullValue=0;
		for (int i = lowerBoundValue.intValue(); i <= upperBoundValue.intValue(); i++) {
			if(runningIdentifierIndice==null) {
				scopedValueTable.setValueFor(runningIdentifier.getName(), i);
			}else {
				scopedValueTable.setValueFor(runningIdentifier.getName(), runningIdentifierIndice.getContent(), i);
			}
			ExpressionNode expression = node.getExpression();
			Double expressionValue = expression.accept(childValueCalculator);
			fullValue+=expressionValue;
		}
		return fullValue;
	}

	@Override
	protected Double visitNode(SinhNode node) {
		return FastMath.sinh(node.getChild(0).accept(this));
	}

	@Override
	protected Double visitNode(TanhNode node) {
		return FastMath.tanh(node.getChild(0).accept(this));
	}

}
