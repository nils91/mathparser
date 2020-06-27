/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.Transformation_ruleContext;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.rules.GeneralTransformationRule;

/**
 * @author Nils Dralle
 *
 */
public class TransformationRuleVisitor extends latexmathBaseVisitor<GeneralTransformationRule> { //TODO: rebuild

	@Override
	public GeneralTransformationRule visitTransformation_rule(Transformation_ruleContext ctx) {
		if(ctx.getChildCount()==0) {
			return null;
		}else {
			ExpressionNode left = ctx.expr(0).accept(new ExpressionVisitor());
			ExpressionNode right = ctx.expr(1).accept(new ExpressionVisitor());
			return new GeneralTransformationRule(left, right);
		}
	}

}
