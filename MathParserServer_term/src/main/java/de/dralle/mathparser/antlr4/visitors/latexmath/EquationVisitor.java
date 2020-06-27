/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;


import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.EquationContext;
import de.dralle.generated.antlr4.latexmathParser.ExprContext;
import de.dralle.generated.antlr4.latexmathParser.IdentifierAssignContext;
import de.dralle.generated.antlr4.latexmathParser.IdentifierAssignParamContext;
import de.dralle.generated.antlr4.latexmathParser.RelopContext;
import de.dralle.mathparser.RelationOperator;
import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.EquationNode;

/**
 * 
 * @author Nils Dralle
 *Visitor for the equation node in the parse tree. Example: x=y.
 */
public class EquationVisitor extends latexmathBaseVisitor<EquationNode> {	

	@Override
	public EquationNode visitEquation(EquationContext ctx) {
		RelopContext relopChild = ctx.relop();
		RelationOperator operator = relopChild.accept(new EquationRelopVisitor());
		EquationNode node = EquationNode.buildNode(operator);
		 ExprContext leftExpression = ctx.expr(0);
		 ExprContext rightExpression = ctx.expr(1);
		node.setLeftExpression(leftExpression.accept(new ExpressionVisitor()));
		node.setRightExpression(rightExpression.accept(new ExpressionVisitor()));
		return node;
	}

	@Override
	public EquationNode visitIdentifierAssignParam(IdentifierAssignParamContext ctx) {
		if(ctx.identifierAssign()!=null) {
			return ctx.identifierAssign().accept(this);
		}return null;
	}

	@Override
	public EquationNode visitIdentifierAssign(IdentifierAssignContext ctx) {
		EqualityNode eq=new EqualityNode();
		eq.setLeftExpression(ctx.identifier().accept(new AtomVisitor()));
		eq.setRightExpression(ctx.expr().accept(new ExpressionVisitor()));
		return eq;
	}
}
