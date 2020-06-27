/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.AbsoluteExprContext;
import de.dralle.generated.antlr4.latexmathParser.BraceExprContext;
import de.dralle.generated.antlr4.latexmathParser.ParenthesisExprContext;
import de.dralle.generated.antlr4.latexmathParser.PrimaryContext;
import de.dralle.mathparser.nodes.AbsoluteNode;
import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * @author Nils Dralle
 *
 */
public class PrimaryExprVisitor extends latexmathBaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitPrimary(PrimaryContext ctx) {
		if (ctx.parenthesisExpr() != null) {
			return ctx.parenthesisExpr().accept(new PrimaryExprVisitor());
		}
		if (ctx.braceExpr() != null) {
			return ctx.braceExpr().accept(new PrimaryExprVisitor());
		}
		if (ctx.absoluteExpr() != null) {
			return ctx.absoluteExpr().accept(new PrimaryExprVisitor());
		}
		if (ctx.funcExpr() != null) {
			return ctx.funcExpr().accept(new FunctionVisitor());
		}
		if (ctx.atom() != null) {
			return ctx.atom().accept(new AtomVisitor());
		}
		return null;
	}

	@Override
	public ExpressionNode visitAbsoluteExpr(AbsoluteExprContext ctx) {
		if (ctx.getChildCount() == 0) {
			return null;
		} else if (ctx.getChildCount() == 3) {
			if (ctx.expr() != null) {
				ExpressionNode unaryNode = ctx.expr().accept(new ExpressionVisitor());
				AbsoluteNode thisNode = new AbsoluteNode();
				thisNode.addChild(unaryNode);
				return thisNode;
			}
		}
		return null;
	}

	@Override
	public ExpressionNode visitBraceExpr(BraceExprContext ctx) {
		if (ctx.getChildCount() == 0) {
			return null;
		} else if (ctx.getChildCount() == 3) {
			if (ctx.expr() != null) {
				ExpressionNode unaryNode = ctx.expr().accept(new ExpressionVisitor());
				return unaryNode;
			}
		}
		return null;
	}

	@Override
	public ExpressionNode visitParenthesisExpr(ParenthesisExprContext ctx) {
		if (ctx.getChildCount() == 0) {
			return null;
		} else if (ctx.getChildCount() == 3) {
			if (ctx.expr() != null) {
				ExpressionNode unaryNode = ctx.expr().accept(new ExpressionVisitor());
				return unaryNode;
			}
		}
		return null;
	}
}
