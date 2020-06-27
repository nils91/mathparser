/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.nodes.AbsoluteNode;
import de.dralle.mathparser.nodes.AcosNode;
import de.dralle.mathparser.nodes.AcotNode;
import de.dralle.mathparser.nodes.AsinNode;
import de.dralle.mathparser.nodes.AtanNode;
import de.dralle.mathparser.nodes.AtomNode;
import de.dralle.mathparser.nodes.AutomaticConversionNode;
import de.dralle.mathparser.nodes.BinaryExpressionNode;
import de.dralle.mathparser.nodes.BinomNode;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.CosNode;
import de.dralle.mathparser.nodes.CoshNode;
import de.dralle.mathparser.nodes.CotNode;
import de.dralle.mathparser.nodes.CothNode;
import de.dralle.mathparser.nodes.Degree2RadiansConversionNode;
import de.dralle.mathparser.nodes.DeterminantNode;
import de.dralle.mathparser.nodes.DivisionNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.FactorialNode;
import de.dralle.mathparser.nodes.FunctionNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.LdNode;
import de.dralle.mathparser.nodes.LgNode;
import de.dralle.mathparser.nodes.LnNode;
import de.dralle.mathparser.nodes.LogNode;
import de.dralle.mathparser.nodes.LoopFunctionNode;
import de.dralle.mathparser.nodes.MathmaticalRootNode;
import de.dralle.mathparser.nodes.MatrixNode;
import de.dralle.mathparser.nodes.ModuloNode;
import de.dralle.mathparser.nodes.MultiAddNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.NumberNode;
import de.dralle.mathparser.nodes.PowNode;
import de.dralle.mathparser.nodes.ProductNode;
import de.dralle.mathparser.nodes.RealNode;
import de.dralle.mathparser.nodes.RealValuedNode;
import de.dralle.mathparser.nodes.SinNode;
import de.dralle.mathparser.nodes.SinhNode;
import de.dralle.mathparser.nodes.SqrtNode;
import de.dralle.mathparser.nodes.SubtractNode;
import de.dralle.mathparser.nodes.SummationNode;
import de.dralle.mathparser.nodes.TanNode;
import de.dralle.mathparser.nodes.TanhNode;
import de.dralle.mathparser.nodes.TextNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public abstract class ExpressionTreeBaseVisitor<T> extends MathParserBaseTreeVisitor<T> {

	@Override
	public T visit(IMathParserBaseNode node) {
		T value = null;
		if (node instanceof AbsoluteNode) {
			if (value == null)
				value = visitNode((AbsoluteNode) node);
		}
		if (node instanceof AcosNode) {
			if (value == null)
				value = visitNode((AcosNode) node);
		}
		if (node instanceof AcotNode) {
			if (value == null)
				value = visitNode((AcotNode) node);
		}
		if (node instanceof AsinNode) {
			if (value == null)
				value = visitNode((AsinNode) node);
		}
		if (node instanceof AtanNode) {
			if (value == null)
				value = visitNode((AtanNode) node);
		}
		if (node instanceof AtomNode) {
			if (value == null)
				value = visitNode((AtomNode) node);
		}
		if (node instanceof AutomaticConversionNode) {
			if (value == null)
				value = visitNode((AutomaticConversionNode) node);
		}
		if (node instanceof BinaryExpressionNode) {
			if (value == null)
				value = visitNode((BinaryExpressionNode) node);
		}
		if (node instanceof BinomNode) {
			if (value == null)
				value = visitNode((BinomNode) node);
		}
		if (node instanceof ConstantNode) {
			if (value == null)
				value = visitNode((ConstantNode) node);
		}
		if (node instanceof CosNode) {
			if (value == null)
				value = visitNode((CosNode) node);
		}
		if (node instanceof CoshNode) {
			if (value == null)
				value = visitNode((CoshNode) node);
		}
		if (node instanceof CotNode) {
			if (value == null)
				value = visitNode((CotNode) node);
		}
		if (node instanceof CothNode) {
			if (value == null)
				value = visitNode((CothNode) node);
		}
		if (node instanceof Degree2RadiansConversionNode) {
			if (value == null)
				value = visitNode((Degree2RadiansConversionNode) node);
		}
		if (node instanceof DeterminantNode) {
			if (value == null)
				value = visitNode((DeterminantNode) node);
		}
		if (node instanceof DivisionNode) {
			if (value == null)
				value = visitNode((DivisionNode) node);
		}
		if (node instanceof ExpressionNode) {
			if (value == null)
				value = visitNode((ExpressionNode) node);
		}
		if (node instanceof FactorialNode) {
			if (value == null)
				value = visitNode((FactorialNode) node);
		}
		if (node instanceof FunctionNode) {
			if (value == null)
				value = visitNode((FunctionNode) node);
		}
		if (node instanceof IdentifierNode) {
			if (value == null)
				value = visitNode((IdentifierNode) node);
		}
		if (node instanceof IntegerNode) {
			if (value == null)
				value = visitNode((IntegerNode) node);
		}
		if (node instanceof LdNode) {
			if (value == null)
				value = visitNode((LdNode) node);
		}
		if (node instanceof LnNode) {
			if (value == null)
				value = visitNode((LnNode) node);
		}
		if (node instanceof LgNode) {
			if (value == null)
				value = visitNode((LgNode) node);
		}
		if (node instanceof LogNode) {
			if (value == null)
				value = visitNode((LogNode) node);
		}
		if (node instanceof LoopFunctionNode) {
			if (value == null)
				value = visitNode((LoopFunctionNode) node);
		}
		if (node instanceof MathmaticalRootNode) {
			if (value == null)
				value = visitNode((MathmaticalRootNode) node);
		}
		if (node instanceof MatrixNode) {
			if (value == null)
				value = visitNode((MatrixNode) node);
		}
		if (node instanceof ModuloNode) {
			if (value == null)
				value = visitNode((ModuloNode) node);
		}
		if (node instanceof NumberNode) {
			if (value == null)
				value = visitNode((NumberNode) node);
		}
		if (node instanceof PowNode) {
			if (value == null)
				value = visitNode((PowNode) node);
		}
		if (node instanceof ProductNode) {
			if (value == null)
				value = visitNode((ProductNode) node);
		}
		if (node instanceof SummationNode) {
			if (value == null)
				value = visitNode((SummationNode) node);
		}
		if (node instanceof RealNode) {
			if (value == null)
				value = visitNode((RealNode) node);
		}
		if (node instanceof RealValuedNode) {
			if (value == null)
				value = visitNode((RealValuedNode) node);
		}
		if (node instanceof SinNode) {
			if (value == null)
				value = visitNode((SinNode) node);
		}
		if (node instanceof SinhNode) {
			if (value == null)
				value = visitNode((SinhNode) node);
		}
		if (node instanceof SqrtNode) {
			if (value == null)
				value = visitNode((SqrtNode) node);
		}
		if (node instanceof SubtractNode) {
			if (value == null)
				value = visitNode((SubtractNode) node);
		}
		if (node instanceof TanNode) {
			if (value == null)
				value = visitNode((TanNode) node);
		}
		if (node instanceof TanhNode) {
			if (value == null)
				value = visitNode((TanhNode) node);
		}
		if (node instanceof TextNode) {
			if (value == null)
				value = visitNode((TextNode) node);
		}
		if (node instanceof MultiAddNode) {
			if (value == null) {
				value = visitNode((MultiAddNode) node);
			}
		}
		if (node instanceof MultiMultiplyNode) {
			if (value == null) {
				value = visitNode((MultiMultiplyNode) node);
			}
		}
		if (node instanceof ExpressionNode) {
			return handleSign(value, (ExpressionNode) node);
		} else {
			return value;
		}
	}

	protected T handleSign(T value, ExpressionNode node) {
		return value;
	}

	protected T visitNode(ExpressionNode node) {

		return null;
	}

	protected T visitNode(BinaryExpressionNode node) {

		return null;
	}

	protected T visitNode(BinomNode node) {

		return null;
	}

	protected T visitNode(FunctionNode node) {

		return null;
	}

	protected T visitNode(AutomaticConversionNode node) {

		return null;
	}

	protected T visitNode(AcosNode node) {

		return null;
	}

	protected T visitNode(AcotNode node) {

		return null;
	}

	protected T visitNode(AsinNode node) {

		return null;
	}

	protected T visitNode(AtanNode node) {

		return null;
	}

	protected T visitNode(ConstantNode node) {

		return null;
	}

	protected T visitNode(AtomNode node) {

		return null;
	}

	protected T visitNode(NumberNode node) {

		return null;
	}

	protected T visitNode(CosNode node) {

		return null;
	}

	protected T visitNode(CoshNode node) {

		return null;
	}

	protected T visitNode(CotNode node) {

		return null;
	}

	protected T visitNode(CothNode node) {

		return null;
	}

	protected T visitNode(Degree2RadiansConversionNode node) {

		return null;
	}

	protected T visitNode(DeterminantNode node) {

		return null;
	}

	protected T visitNode(DivisionNode node) {

		return null;
	}

	protected T visitNode(FactorialNode node) {

		return null;
	}

	protected T visitNode(IdentifierNode node) {

		return null;
	}

	protected T visitNode(IntegerNode node) {

		return null;
	}

	protected T visitNode(LdNode node) {

		return null;
	}

	protected T visitNode(LnNode node) {

		return null;
	}

	protected T visitNode(LgNode node) {

		return null;
	}

	protected T visitNode(LogNode node) {

		return null;
	}

	protected T visitNode(LoopFunctionNode node) {

		return null;
	}

	protected T visitNode(MatrixNode node) {

		return null;
	}

	protected T visitNode(MathmaticalRootNode node) {

		return null;
	}

	protected T visitNode(ModuloNode node) {

		return null;
	}

	protected T visitNode(PowNode node) {
		return null;
	}

	protected T visitNode(ProductNode node) {
		return null;
	}

	protected T visitNode(SummationNode node) {
		return null;
	}

	protected T visitNode(RealNode node) {
		return null;
	}

	protected T visitNode(RealValuedNode node) {
		return null;
	}

	protected T visitNode(SinNode node) {
		return null;
	}

	protected T visitNode(SinhNode node) {
		return null;
	}

	protected T visitNode(SqrtNode node) {

		return null;
	}

	protected T visitNode(SubtractNode node) {

		return null;
	}

	protected T visitNode(TanNode node) {
		return null;
	}

	protected T visitNode(TanhNode node) {
		return null;
	}

	protected T visitNode(TextNode node) {

		return null;
	}

	protected T visitNode(AbsoluteNode node) {
		return null;
	}

	protected T visitNode(MultiAddNode node) {
		return null;
	}

	protected T visitNode(MultiMultiplyNode node) {
		return null;
	}

}
