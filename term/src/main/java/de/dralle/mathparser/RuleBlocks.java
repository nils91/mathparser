package de.dralle.mathparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.rules.AbstractTransformationRule;
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.RepeatableRuleBlock;
import de.dralle.mathparser.rules.TransformationRuleBuilder;
import de.dralle.mathparser.rules.conditions.interfaces.ITransformationRuleCondition;
/**
 * This class provides some static methods, that assemble sets of rewrite rules into list.
 * 
 * @author Nils Dralle
 *
 */
public class RuleBlocks {

	private static final class PositiveRuleCondition implements ITransformationRuleCondition {
		private String[] variables;

		public PositiveRuleCondition(String... variables) {
			this.variables = variables;
		}

		@Override
		public boolean allowRule(GeneralTransformationRule rule, Map<SymbolIdentifier, ExpressionNode> replaceMap) {
			for (String variable : variables) {
				ExpressionNode expression = replaceMap.get(new SymbolIdentifier(variable, null));
				if (expression.getSign().equals(Sign.NEGATIVE)) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * @param tmp
	 */
	@Deprecated
	public static List<GeneralTransformationRule> buildFactorReductionRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("2*a->a+a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("3*a->2*a+a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*2->a+a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*3->a*2+a"));
		// tmp.add(TransformationRuleParser.buildTransformationRuleFromString("b*a<->(b-1)*a+a"));

		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^2->a*a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^3->a^2*a"));
		// tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b->a^(b-1)*a"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildNegativeSignReplacementRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-(a+b)->-a-b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a-b->a+-b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a--b->a+b"));
		// tmp.add(TransformationRuleParser.buildTransformationRuleFromString("--a->a"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildFractionRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();

		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^{-1}->\\frac{1}{a}"));

//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\frac{1}{a^b}->a^{-b}"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\frac{a}{b}->a*b^{-1}"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\frac{a}{b^c}->a*b^{-c}"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^{-b}->\\frac{1}{a^b}"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("a^{-b}->\\frac{1}{a^b}",
				new PositiveRuleCondition("b")));

		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*b^{-1}->\\frac{a}{b}"));

//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*b^{-c}->\\frac{a}{b^c}"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("a*b^{-c}->\\frac{a}{b^c}",
				new PositiveRuleCondition("c")));

		tmp.add(TransformationRuleParser
				.buildTransformationRuleFromString("\\frac{a}{b}*\\frac{a}{c}->\\frac{a}{b*c}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\frac{a}{\\frac{b}{c}}->\\frac{a*c}{b}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\frac{a*b}{a*c}->\\frac{b}{c}"));
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{ax+x^2}{x}->a+x"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{x^2+ax}{x}->x+a"));
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{ax+bx^2}{x}->a+bx"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{bx^2+ax}{x}->bx+a"));
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{xa+x^2}{x}->a+x"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{x^2+xa}{x}->x+a"));
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{xa+x^2*b}{x}->a+bx"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{x^2*b+xa}{x}->bx+a"));

//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,c)+frac(b,c)->frac(a+b,c)"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,b)*frac(c,d)->frac(a*c,b*d)"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,b)/frac(c,d)->frac(a*d,b*c)"));
		
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a*b)/a->b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(b*a)/a->b"));
		
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(ac+bc)/c->a+b"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(1+a)/a->1/a+1"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+1)/a->1+1/a"));
		
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+bx^2)/x->a/x+bx"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(bx^2+a)/x->a/x+bx"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(x^2b+a)/x->a/x+bx"));
		
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildLogarithmRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();

		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\ln{a}->\\log_{\\epsilon}{a}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\lg{a}->\\log_{10}{a}"));

		tmp.add(TransformationRuleParser
				.buildTransformationRuleFromString("\\log_{a}{b*c}->\\log_{a}{b}+\\log_{a}{c}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\log_{a}{b}*c->\\log_{a}{b^c}"));

		tmp.add(TransformationRuleParser
				.buildTransformationRuleFromString("\\log_{a}{\\frac{b}{c}}->\\log_{a}{b}+{-1}*\\log_{a}{c}"));

		tmp.add(TransformationRuleParser
				.buildTransformationRuleFromString("\\log_{a}{\\sqrt[b]{c}}->\\frac{1}{b}*\\log_{a}{c}"));

		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildTriginometicRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-\\sin{x}->\\sin{-x}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-\\tan{x}->\\tan{-x}"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\cos{-x}->\\cos{x}",new PositiveRuleCondition("x")));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("-\\cot{x}->\\cot{-x}"));
		
		//tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(\\sin{x})^{a}->\\sin^{a}{x}")); //already dealt with by parser
		//tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(\\cos{x})^{a}->\\cos^{a}{x}"));
		//tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(\\tan{x})^{a}->\\tan^{a}{x}"));
		
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("1+-\\sin^2{x}->\\cos^2{x}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("1+-\\cos^2{x}->\\sin^2{x}"));
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{\\cos^2{x}}{\\sin^2{x}}->\\cot^2{x}"));		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{\\sin^2{x}}{\\cos^2{x}}->\\tan^2{x}"));	
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{1}{\\cot^2{x}}->\\tan^2{x}"));		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{1}{\\tan^2{x}}->\\cot^2{x}"));
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{1}{\\tan^2{x}+1}->\\cos^2{x}"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{\\cot^2{x}}{\\cot^2{x}+1}->\\cos^2{x}"));
		
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{1}{\\cot^2{x}+1}->\\sin^2{x}"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("\\frac{\\tan^2{x}}{\\tan^2{x}+1}->\\sin^2{x}"));
		
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildSqrtRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\sqrt{b}->\\sqrt[2]{b}"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("b^{\\frac{c}{a}}->\\sqrt[a]{b^c}",
				new PositiveRuleCondition("c")));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("b^{\\frac{c}{a}}->\\frac{1}{\\sqrt[a]{b^{-c}}} "));

		tmp.add(TransformationRuleParser
				.buildTransformationRuleFromString("\\sqrt[a]{b^{-1*c}}->\\frac{1}{\\sqrt[a]{b^c}}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\sqrt[a]{\\sqrt[b]{c}}->\\sqrt[a*b]{c}"));
		tmp.add(TransformationRuleParser
				.buildTransformationRuleFromString("\\sqrt[a]{b}*\\sqrt[a]{c}->\\sqrt[a]{b*c}"));
		tmp.add(TransformationRuleParser
				.buildTransformationRuleFromString("\\frac{\\sqrt[a]{b}}{\\sqrt[a]{c}}->\\sqrt[a]{\\frac{b}{c}}"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildPowRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*a->a^2"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("b*a*a->b*a^2"));

		// build compositeRule for next rule....
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b*a->a^{b+1}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b*a^c->a^{b+c}"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^{b^{c}}->a^{b*c}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a^{b})^{c}->a^{b*c}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\frac{a^b}{a^c}->a^{(b-c)}"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a*b)^c->a^c*b^c"));

//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b^c->a^c*b^c"));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("(-a)^2->a^2",new PositiveRuleCondition("a")));
		
		return tmp;
	}

	public static List<AbstractTransformationRule> buildMultiplicationRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*(-b)->-(a*b)"));

		tmp.add(TransformationRuleBuilder.buildRuleFromString("a*(-b)->-(a*b)", new PositiveRuleCondition("a", "b")));
		tmp.add(TransformationRuleBuilder.buildRuleFromString("-(a)*b->-(a*b)", new PositiveRuleCondition("a", "b")));
		// tmp.add(TransformationRuleBuilder.buildRuleFromString("-(a*(-b))->(a*b)",new
		// PositiveRuleCondition("a","b")));

		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+a->a*2"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*b+a->a*(b+1)"));

		return tmp;
	}

//	/**
//	 * @param tmp
//	 */
//	public static List<AbstractTransformationRule> buildSumupRules() {
//		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
//		tmp.add(TransformationRuleBuilder.buildCommonFactorsRuleForNumbers());
//		return tmp;
//	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildReplaceWithConstantFactorRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("0*a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*0->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-0->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^0->1"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+-a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-1*a+a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a-a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+\\frac{1}{a}->1"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a/a->1"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildDistributiveRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a(b+c)->ab+ac"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(b+c)a->ba+ca"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)(c+d)->ac+ad+bc+bd"));
		
		//Following two rules are workarounds, proper solution would probably be a new transformation rule to handle this case
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a(b+c+d)->ab+ac+ad"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(b+c+d)a->ba+ca+da"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildBinomialRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)^2->a^2+2ab+b^2"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a-b)^2->a^2-2ab+b^2"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)(a-b)->a^2-b^2"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	public static List<AbstractTransformationRule> buildNeutralElementRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("1*a->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*1->a"));
//		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-(a*1)->-a")); //doesnt work cause -- gets reduced to nothing by parser

		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a/1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("0+a->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+0->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a*b)/b->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a/b)*b->a"));

		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\ln{\\epsilon^a}->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\epsilon^{\\log_{\\epsilon}{a}}->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("b^{\\log_{b}{a}}->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("\\log_{b}{b^a}->a"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	@Deprecated
	public static List<AbstractTransformationRule> buildAssociativeRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+(b+c)->(a+b)+c"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*(b*c)->(a*b)*c"));
		return tmp;
	}

	public static AbstractTransformationRule[] ruleListToArray(List<AbstractTransformationRule> rules) {
		return rules.toArray(new AbstractTransformationRule[rules.size()]);
	}

	public static AbstractTransformationRule combineRules(AbstractTransformationRule... rules) {
		return combineRules(rules, true, false);
	}

	public static AbstractTransformationRule combineRules(AbstractTransformationRule[] rules,
			boolean ignoreNotPossibleRules, boolean repeatable) {
		if (repeatable) {
			RepeatableRuleBlock combinedRule = new RepeatableRuleBlock(rules);
			combinedRule.setIgnoreNotPossibleRules(ignoreNotPossibleRules);
			return combinedRule;
		} else {
			CompositeTransformationRule combinedRule = new CompositeTransformationRule(Arrays.asList(rules));
			combinedRule.setIgnoreNotPossibleRules(ignoreNotPossibleRules);
			return combinedRule;
		}
	}

}
