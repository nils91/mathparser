/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.AdditiveExprContext;
import de.dralle.generated.antlr4.latexmathParser.CaretSubExprContext;
import de.dralle.generated.antlr4.latexmathParser.ExprContext;
import de.dralle.generated.antlr4.latexmathParser.FactorialExprContext;
import de.dralle.generated.antlr4.latexmathParser.ImpMulExprContext;
import de.dralle.generated.antlr4.latexmathParser.MultiplicativeExprContext;
import de.dralle.generated.antlr4.latexmathParser.PowerExprContext;
import de.dralle.generated.antlr4.latexmathParser.ShortFracExprContext;
import de.dralle.generated.antlr4.latexmathParser.SignedFactorialExprContext;
import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.Operator;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.nodes.BinaryExpressionNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.FactorialNode;
import de.dralle.mathparser.nodes.MultiNode;
import de.dralle.mathparser.nodes.PowNode;

/**
 * @author Nils Dralle
 *
 */
public class ExpressionVisitor extends latexmathBaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitExpr(ExprContext ctx) {// has one child

		if (ctx.additiveExpr() != null) {
			return ctx.additiveExpr().accept(new ExpressionVisitor());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public ExpressionNode visitAdditiveExpr(AdditiveExprContext ctx) {
		if (ctx.getChildCount() == 0) {
			return null;
		} else if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(new ExpressionVisitor());
		} else if (ctx.getChildCount() == 3) {
			ExpressionNode left = ctx.getChild(0).accept(new ExpressionVisitor());
			ExpressionNode right = ctx.getChild(2).accept(new ExpressionVisitor());
			ExpressionNode thisNode = null;
			if (ctx.PLUS() != null) {
				thisNode = NodeUtils.buildNode(Operator.ADD);
			} else if (ctx.MINUS() != null) {
				thisNode = NodeUtils.buildNode(Operator.SUBTRACT);
			}
			thisNode=assignBinaryChilds(thisNode, left, right);
			return thisNode;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public ExpressionNode visitMultiplicativeExpr(MultiplicativeExprContext ctx) {
		if (ctx.getChildCount() == 0) {
			return null;
		} else if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(new ExpressionVisitor());
		} else if (ctx.getChildCount() == 3) {
			ExpressionNode left = ctx.getChild(0).accept(new ExpressionVisitor());
			ExpressionNode right = ctx.getChild(2).accept(new ExpressionVisitor());
			ExpressionNode thisNode = null;
			if (ctx.TIMES() != null) {
				thisNode = NodeUtils.buildNode(Operator.MULTIPLY);
			} else if (ctx.DIV() != null) {
				thisNode = NodeUtils.buildNode(Operator.DIV);
			} else if (ctx.MODULO() != null) {
				thisNode = NodeUtils.buildNode(Operator.MODULO);
			}
			thisNode=assignBinaryChilds(thisNode, left, right);
			return thisNode;
		}
		return null;
	}

	/**
	 * @param node
	 * @param left
	 * @param right
	 * @return 
	 */
	private ExpressionNode assignBinaryChilds(ExpressionNode node, ExpressionNode left, ExpressionNode right) {
		if (node instanceof BinaryExpressionNode) {
			((BinaryExpressionNode) node).setLeftExpression(left);
			((BinaryExpressionNode) node).setRightExpression(right);
		}
		if(node instanceof MultiNode) {
			((MultiNode) node).setChild(left,-1);
			((MultiNode) node).setChild(right,-1);
		}
		return node;
	}

	@Override
	public ExpressionNode visitShortFracExpr(ShortFracExprContext ctx) {
		if (ctx.impMulExpr() != null) {
			return ctx.impMulExpr().accept(this);
		}
		if (ctx.shortFrac() != null) {
			return (ctx.shortFrac().accept(new FunctionVisitor()));
		}
		return null;
	}

	@Override
	public ExpressionNode visitImpMulExpr(ImpMulExprContext ctx) {
		if (ctx.getChildCount() == 0) {
			return null;
		} else if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(new ExpressionVisitor());
		} else {
			ExpressionNode left = ctx.getChild(0).accept(new ExpressionVisitor());
			ExpressionNode right = ctx.getChild(1).accept(new ExpressionVisitor());
			ExpressionNode thisNode = NodeUtils.buildNode(Operator.MULTIPLY);
			thisNode=assignBinaryChilds(thisNode, left, right);
			return thisNode;
		}
	}

	@Override
	public ExpressionNode visitFactorialExpr(FactorialExprContext ctx) {
		if (ctx.FACTORIAL() != null) {
			if (ctx.powerExpr() != null) {
				return new FactorialNode(ctx.powerExpr().accept(new ExpressionVisitor()));
			}
			if (ctx.factorialExpr() != null) {
				return new FactorialNode(ctx.factorialExpr().accept(new ExpressionVisitor()));
			}
		} else {
			if (ctx.powerExpr() != null) {
				return ctx.powerExpr().accept(new ExpressionVisitor());
			}
			if (ctx.factorialExpr() != null) {
				return ctx.factorialExpr().accept(new ExpressionVisitor());
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public ExpressionNode visitPowerExpr(PowerExprContext ctx) {
		ExpressionNode base = ctx.primary().accept(new PrimaryExprVisitor());
		ExpressionNode exponent = null;
		if (ctx.caretSubExpr() != null) {
			exponent = ctx.caretSubExpr().accept(this);
			PowNode pow = new PowNode();
			pow.setBase(base);
			pow.setExponent(exponent);
			return pow;
		}
		return base;
	}

	@Override
	public ExpressionNode visitCaretSubExpr(CaretSubExprContext ctx) {
		return ctx.functionParam().accept(new FunctionVisitor());
	}

	@Override
	public ExpressionNode visitSignedFactorialExpr(SignedFactorialExprContext ctx) {
		if (ctx.MINUS() != null) {
			ExpressionNode sfe = ctx.signedFactorialExpr().accept(new ExpressionVisitor());
			sfe.setSign(sfe.getSign().equals(Sign.POSITIVE) ? Sign.NEGATIVE : Sign.POSITIVE);
			return sfe;
		}
		if (ctx.factorialExpr() != null) {
			return ctx.factorialExpr().accept(new ExpressionVisitor());
		}
		if (ctx.signedFactorialExpr() != null) {
			return ctx.signedFactorialExpr().accept(new ExpressionVisitor());
		}
		return null;
	}

}
