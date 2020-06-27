/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import java.util.ArrayList;
import java.util.List;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.ExprContext;
import de.dralle.generated.antlr4.latexmathParser.MatrixLineContext;
import de.dralle.mathparser.nodes.ExpressionNode;

/**
 * @author Nils Dralle
 *
 */
public class MatrixLineVisitor extends latexmathBaseVisitor<List<ExpressionNode>>{

	@Override
	public List<ExpressionNode> visitMatrixLine(MatrixLineContext ctx) {
		List<ExpressionNode> elements=new ArrayList<ExpressionNode>();
		ExpressionVisitor exprVisitor = new ExpressionVisitor();
		for (ExprContext ex : ctx.expr()) {
			elements.add(ex.accept(new ExpressionVisitor()));
		}
		return elements;
	}

	

}

