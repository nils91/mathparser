/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.RelopContext;
import de.dralle.mathparser.RelationOperator;

/**
 * @author Nils Dralle
 *Visitor for the relation operator in an equation.
 */
public class EquationRelopVisitor extends latexmathBaseVisitor<RelationOperator> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public RelationOperator visitRelop(RelopContext ctx) {
		if(ctx.EQ()!=null) {
			return RelationOperator.EQ;
		}else if(ctx.GT()!=null) {
			return RelationOperator.GT;
		}else if(ctx.LT()!=null) {
			return RelationOperator.LT;
		}else if(ctx.GE()!=null) {
			return RelationOperator.GE;
		}else if(ctx.LE()!=null) {
			return RelationOperator.LE;
		}else if(ctx.NE()!=null) {
			return RelationOperator.NE;
		}
		return null;
	}
}
