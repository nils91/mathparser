/**
 * 
 */
package de.dralle.mathparser;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.rules.AbstractTransformationRule;

/**
 * @author Nils Dralle
 *
 */
@Deprecated
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("(a+b)²+(c+d)(e+f)");
		//ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("a*a+a*2*b+b*b+ce+cf+de+df");
		ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("(a+b)²+a(a+b)+b(a+b)");
		ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2*a^2+4ab+2*b^2");
		List<AbstractTransformationRule> simplificationRules = buildSimplificationRules();
		ex1=simplify(ex1, simplificationRules);
		ex2=simplify(ex2, simplificationRules);
		System.out.println(ex1);
		System.out.println(ex2);
	}

	/**
	 * @param expression
	 * @param simplificationRules
	 * @return 
	 */
	private static ExpressionNode simplify(ExpressionNode expression, List<AbstractTransformationRule> simplificationRules) {
		try {
			expression=expression.clone();
		} catch (CloneNotSupportedException e1) {
		}
		boolean simplificationStop=false;
		while(!simplificationStop) {
			simplificationStop=true;
			for (AbstractTransformationRule transformationRule : simplificationRules) {
				if(transformationRule.isPossible(expression)) {
					IMathParserBaseNode newex1=null;
					try {
						newex1 = transformationRule.apply(expression);
					} catch (Exception e) {
					}
					if(newex1!=null) {
						simplificationStop=false;
						expression=(ExpressionNode) newex1;
					}					
				}
			}
			
		}
		return expression;
	}

	private static List<AbstractTransformationRule> buildSimplificationRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		// gp
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*0->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a/1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*2->a+a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-0->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a-b->a+-b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^0->1"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^2->a*a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^3->a^2*a"));
		// tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b->a^(b-1)*a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a--b->a+b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("--a->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b*a^c->a^(b+c)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b/a^c->a^(b-c)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^c*b^c->(a*b)^c"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b^c->a^(b*c)"));
		// associative
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+(b+c)->(a+b)+c"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*(b*c)->(a*b)*c"));
		// binom
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)²->a²+2ab+b²"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a-b)²->a²-2ab+b²"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)(a-b)->a²-b²"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a-b)(a+b)->a²-b²"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)(a+-b)->a²-b²"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+-b)(a+b)->a²-b²"));
		// distributive
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a(b+c)->ab+ac"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(b+c)a->ba+ca"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)(c+d)->ac+ad+bc+bd"));
		// neutral
	//	tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+0->a"));
		// inverse
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+-a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+frac(1,a)->1"));
		// fractions
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,c)+frac(b,c)->frac(a+b,c)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,b)*frac(c,d)->frac(a*c,b*d)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,b)/frac(c,d)->frac(a,b)*frac(d,c)"));
		return tmp;
	}

}
