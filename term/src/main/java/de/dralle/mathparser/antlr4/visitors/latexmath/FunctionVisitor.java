/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.BinomFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.DetFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.FractionFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.FuncExprContext;
import de.dralle.generated.antlr4.latexmathParser.FunctionParamContext;
import de.dralle.generated.antlr4.latexmathParser.LbFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.LgFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.LnFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.LogFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.NormalFracContext;
import de.dralle.generated.antlr4.latexmathParser.ProdFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.RtFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.ShortFracContext;
import de.dralle.generated.antlr4.latexmathParser.SqrtFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.SumFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.SumFunctionParamContext;
import de.dralle.generated.antlr4.latexmathParser.TrigFunctionContext;
import de.dralle.generated.antlr4.latexmathParser.TrigFunctionParamContext;
import de.dralle.mathparser.nodes.AcosNode;
import de.dralle.mathparser.nodes.AcotNode;
import de.dralle.mathparser.nodes.AsinNode;
import de.dralle.mathparser.nodes.AtanNode;
import de.dralle.mathparser.nodes.BinomNode;
import de.dralle.mathparser.nodes.CosNode;
import de.dralle.mathparser.nodes.CoshNode;
import de.dralle.mathparser.nodes.CotNode;
import de.dralle.mathparser.nodes.CothNode;
import de.dralle.mathparser.nodes.Degree2RadiansConversionNode;
import de.dralle.mathparser.nodes.DeterminantNode;
import de.dralle.mathparser.nodes.DivisionNode;
import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.FunctionNode;
import de.dralle.mathparser.nodes.LdNode;
import de.dralle.mathparser.nodes.LgNode;
import de.dralle.mathparser.nodes.LnNode;
import de.dralle.mathparser.nodes.LogNode;
import de.dralle.mathparser.nodes.LoopFunctionNode;
import de.dralle.mathparser.nodes.MathmaticalRootNode;
import de.dralle.mathparser.nodes.MatrixNode;
import de.dralle.mathparser.nodes.PowNode;
import de.dralle.mathparser.nodes.ProductNode;
import de.dralle.mathparser.nodes.SinNode;
import de.dralle.mathparser.nodes.SinhNode;
import de.dralle.mathparser.nodes.SqrtNode;
import de.dralle.mathparser.nodes.SummationNode;
import de.dralle.mathparser.nodes.TanNode;
import de.dralle.mathparser.nodes.TanhNode;

/**
 * @author Nils Dralle
 *
 */
public class FunctionVisitor extends latexmathBaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitFuncExpr(FuncExprContext ctx) {
		ExpressionNode fn = ctx.getChild(0).accept(new FunctionVisitor());
		return fn;
	}

	@Override
	public ExpressionNode visitFunctionParam(FunctionParamContext ctx) {
		if (ctx.shortAtom() != null) {
			return ctx.shortAtom().accept(new AtomVisitor());
		}
		if (ctx.braceExpr() != null) {
			return ctx.braceExpr().accept(new PrimaryExprVisitor());
		}
		return null;
	}

	@Override
	public ExpressionNode visitTrigFunctionParam(TrigFunctionParamContext ctx) {
		ExpressionNode param = null;
		if (ctx.shortAtom() != null) {
			param = ctx.shortAtom().accept(new AtomVisitor());
		}
		if (ctx.expr() != null) {
			param = ctx.expr().accept(new ExpressionVisitor());
		}
		if (ctx.DEGREE() != null) {
			return new Degree2RadiansConversionNode(param);
		} else {
			return param;
		}
	}

	@Override
	public ExpressionNode visitTrigFunction(TrigFunctionContext ctx) {
		ExpressionNode powExpr = null;
		if (ctx.caretSubExpr() != null) {
			powExpr = ctx.caretSubExpr().accept(new ExpressionVisitor());
		}
		ExpressionNode functionParam = ctx.trigFunctionParam().accept(this);
		FunctionNode fktNode = null;
		if (ctx.SIN() != null) {
			fktNode = new SinNode();
		} else if (ctx.COS() != null) {
			fktNode = new CosNode();
		} else if (ctx.TAN() != null) {
			fktNode = new TanNode();
		} else if (ctx.COT() != null) {
			fktNode = new CotNode();
		} else if (ctx.ASIN() != null) {
			fktNode = new AsinNode();
		} else if (ctx.ACOS() != null) {
			fktNode = new AcosNode();
		} else if (ctx.ATAN() != null) {
			fktNode = new AtanNode();
		} else if (ctx.ACOT() != null) {
			fktNode = new AcotNode();
		} else if (ctx.SINH() != null) {
			fktNode = new SinhNode();
		} else if (ctx.COSH() != null) {
			fktNode = new CoshNode();
		} else if (ctx.TANH() != null) {
			fktNode = new TanhNode();
		} else if (ctx.COTH() != null) {
			fktNode = new CothNode();
		}
		fktNode.addChild(functionParam);
		ExpressionNode node = null;
		if (powExpr != null) {
			PowNode pnode = new PowNode();
			pnode.setBase(fktNode);
			pnode.setExponent(powExpr);
			node = pnode;
		} else {
			node = fktNode;
		}
		return node;
	}

	@Override
	public ExpressionNode visitBinomFunction(BinomFunctionContext ctx) {
		ExpressionNode n = ctx.functionParam(0).accept(this);
		ExpressionNode k = ctx.functionParam(1).accept(this);
		return new BinomNode(n, k);
	}

	@Override
	public ExpressionNode visitNormalFrac(NormalFracContext ctx) {
		DivisionNode node = new DivisionNode();
		node.setLeftExpression(ctx.functionParam(0).accept(this));
		node.setRightExpression(ctx.functionParam(1).accept(this));
		return node;
	}

	@Override
	public ExpressionNode visitShortFrac(ShortFracContext ctx) {
		DivisionNode node = new DivisionNode();
		node.setLeftExpression(ctx.functionParam(0).accept(this));
		node.setRightExpression(ctx.functionParam(1).accept(this));
		return node;
	}

	@Override
	public ExpressionNode visitSumFunction(SumFunctionContext ctx) {
		LoopFunctionNode node = (LoopFunctionNode) ctx.sumFunctionParam().accept(this); //always be LoopFunctionNode
		return new SummationNode(node.getIdentifierAssignment(), node.getUpperBound(), node.getExpression());
	}

	@Override
	public ExpressionNode visitProdFunction(ProdFunctionContext ctx) {
		LoopFunctionNode node = (LoopFunctionNode) ctx.sumFunctionParam().accept(this); //always be LoopFunctionNode
		return new ProductNode(node.getIdentifierAssignment(), node.getUpperBound(), node.getExpression());
	}
	
	@Override
	public ExpressionNode visitSumFunctionParam(SumFunctionParamContext ctx) {
		EquationNode identAssignNode = ctx.identifierAssignParam().accept(new EquationVisitor());//always an equality node (for now)
		ExpressionNode boundNode = ctx.caretSubExpr().accept(new ExpressionVisitor());
		ExpressionNode actualExpressionNode = ctx.functionParam().accept(this);
		return new LoopFunctionNode(null, (EqualityNode) identAssignNode, boundNode, actualExpressionNode);
	}// Don´t really care bout the function param, object will be disassembled in
		// further parsing anyway

	@Override
	public ExpressionNode visitDetFunction(DetFunctionContext ctx) {
		MatrixNode matrix = ctx.matrix().accept(new MatrixVisitor());
		return new DeterminantNode(matrix);
	}

	@Override
	public ExpressionNode visitSqrtFunction(SqrtFunctionContext ctx) {
		if (ctx.functionParam() != null) {
			ExpressionNode paramExpr = ctx.functionParam().accept(this);
			SqrtNode node = new SqrtNode();
			node.addChild(paramExpr);
			return node;
		}
		return null;
	}

	@Override
	public FunctionNode visitRtFunction(RtFunctionContext ctx) {
		ExpressionNode paramExpr = ctx.functionParam().accept(this);
		ExpressionNode nthParam = ctx.expr().accept(new ExpressionVisitor());
		MathmaticalRootNode node = new MathmaticalRootNode();
		node.addChild(nthParam);
		node.addChild(paramExpr);
		return node;
	}

	@Override
	public FunctionNode visitLbFunction(LbFunctionContext ctx) {
		LdNode thisNode = new LdNode();
		thisNode.addChild(ctx.functionParam().accept(this));
		return thisNode;
	}

	@Override
	public FunctionNode visitLnFunction(LnFunctionContext ctx) {
		LnNode thisNode = new LnNode();
		thisNode.addChild(ctx.functionParam().accept(this));
		return thisNode;
	}

	@Override
	public FunctionNode visitLgFunction(LgFunctionContext ctx) {
		LgNode thisNode = new LgNode();
		thisNode.addChild(ctx.functionParam().accept(this));
		return thisNode;
	}

	@Override
	public FunctionNode visitLogFunction(LogFunctionContext ctx) {
		LogNode thisNode = new LogNode();
		thisNode.addChild(ctx.advancedIndice().accept(new IndiceVisitor()));
		thisNode.addChild(ctx.functionParam().accept(this));
		return thisNode;
	}

	@Override
	public ExpressionNode visitFractionFunction(FractionFunctionContext ctx) {
		DivisionNode node = new DivisionNode();
		node.setLeftExpression(ctx.functionParam(0).accept(this));
		node.setRightExpression(ctx.functionParam(1).accept(this));
		return node;
	}

}
