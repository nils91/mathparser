/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.AdvancedIndiceContext;
import de.dralle.generated.antlr4.latexmathParser.IndiceContext;
import de.dralle.generated.antlr4.latexmathParser.NumericIndiceContext;
import de.dralle.generated.antlr4.latexmathParser.SimpleIndiceContext;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.TextNode;

/**
 * @author Nils Dralle
 *
 */
public class IndiceVisitor extends latexmathBaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitIndice(IndiceContext ctx) {
		if(ctx.simpleIndice()!=null) {
			return ctx.simpleIndice().accept(this);
		}if(ctx.advancedIndice()!=null) {
			return ctx.advancedIndice().accept(this);
		}
		return null;
	}

	@Override
	public ExpressionNode visitSimpleIndice(SimpleIndiceContext ctx) {
		if(ctx.numericIndice()!=null) {
			return ctx.numericIndice().accept(this);
		}
		return null;
	}

	@Override
	public ExpressionNode visitNumericIndice(NumericIndiceContext ctx) {
		if(ctx.shortNumber()!=null) {
			return new TextNode(ctx.shortNumber().getText());
		}if(ctx.integer()!=null) {
			return new TextNode(ctx.integer().getText());
		}return null;
	}

	@Override
	public ExpressionNode visitAdvancedIndice(AdvancedIndiceContext ctx) {
		if(ctx.shortAtom()!=null) {
			return ctx.shortAtom().accept(new AtomVisitor());
		}if(ctx.expr()!=null) {
			return ctx.expr().accept(new ExpressionVisitor());
		}return null;
	}

	

}
