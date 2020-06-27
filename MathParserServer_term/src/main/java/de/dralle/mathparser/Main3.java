package de.dralle.mathparser;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.SubtractNode;
import de.dralle.mathparser.rules.AbstractTransformationRule;
import de.dralle.mathparser.rules.AdditionReordering;
import de.dralle.mathparser.rules.CombineAddNodes;
import de.dralle.mathparser.rules.CombineMultiplyNodes;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.MultiplicationReordering;
@Deprecated
public class Main3 {

	static List<GeneralTransformationRule> powRules = new ArrayList<GeneralTransformationRule>();
	static List<GeneralTransformationRule> excludeRules = new ArrayList<GeneralTransformationRule>();
	static List<GeneralTransformationRule> basicRules = new ArrayList<GeneralTransformationRule>();
	
	static ExpressionNode ex1 = ExpressionParser.buildExpressionTreeFromString("(a+b)²+a(a+b)+b(a+b)");
	static ExpressionNode ex2 = ExpressionParser.buildExpressionTreeFromString("2*a^2+2ab+2ab+2*b^2");
	
	public static void main(String[] args) {

		SubtractNode ex3 = new SubtractNode();
		ex3.setLeftExpression(ex1);
		ex3.setRightExpression(ex2);
		System.out.println(ex3);
		ExpressionNode ex4 = normalizeExpression(ex3);// sort
		ExpressionNode zero = new IntegerNode(0);
		ExpressionNode oldexpression = null;
		System.out.println(ex4);
		int tries = 0;
		do {
			try {
				oldexpression = ex4.clone();
			} catch (CloneNotSupportedException e) {
			}
			System.out.println(ex4);
			ex4 = normalizeExpression(ex4);// sort
			System.out.println(ex4);
			ex4 = maximumReduce(ex4, buildRules(), 100);
			System.out.println(ex4);
			tries++;
		} while (!ExpressionUtil.isStructurallyEqual(ex4, oldexpression, true, true, true,false) && tries < 100);
		System.out.println(ex4);
	}
	
	private static ExpressionNode maximumReduce(ExpressionNode expression, List<AbstractTransformationRule> list,
			int maxDistance) {
		try {
			expression = expression.clone();
		} catch (CloneNotSupportedException e1) {
		}
		boolean noChange = true;
		int distance=0;
		do {
			noChange = true;
			ExpressionNode oldexpression = null;
			try {
				oldexpression = expression.clone();
			} catch (CloneNotSupportedException e) {
			}
			for (AbstractTransformationRule rule : list) {
				if (rule.isPossible(expression)) {
					try {
						expression = rule.apply(expression, true);
					} catch (Exception e) {					
					}
					noChange=ExpressionUtil.isStructurallyEqual(expression, oldexpression, true, true, true,false);
				}
			}
			distance++;
			System.out.println(distance);
		} while (!noChange&&distance<maxDistance);
		return expression;
	}

	public static ExpressionNode normalizeExpression(ExpressionNode expression) {
		List<AbstractTransformationRule> normalizeRules = new ArrayList<AbstractTransformationRule>();
		normalizeRules.add(new CombineAddNodes());
		normalizeRules.add(new CombineMultiplyNodes());
		normalizeRules.add(new AdditionReordering());
		normalizeRules.add(new MultiplicationReordering());
//		normalizeRules.add(new DecombineMultiAddNode());
//		normalizeRules.add(new DecombineMultiMultiplyNode());

		for (AbstractTransformationRule abstractTransformationRule : normalizeRules) {
			if (abstractTransformationRule.isPossible(expression)) {
				ExpressionNode transformed = null;
				try {
					transformed = abstractTransformationRule.apply(expression.clone(), true);
					System.out.println(transformed);
				} catch (Exception e) {

				}
				if (transformed != null) {
					expression = transformed;
				}
			}
		}
		return expression;
	}

	private static boolean checkSymbolicalEquality(ExpressionNode startExpression, ExpressionNode targetExpression,
			List<AbstractTransformationRule> simplificationRules, int maxDistance) {
		try {
			startExpression = normalizeExpression(startExpression.clone());
		} catch (CloneNotSupportedException e1) {
		}
		boolean newSolutionFound = false;
		boolean equalityReached = false;
		int distance = 0;
		List<ExpressionNode> all = new ArrayList<ExpressionNode>();
		List<ExpressionNode> lastWave = new ArrayList<ExpressionNode>();
		List<ExpressionNode> nextWave = new ArrayList<ExpressionNode>();
		all.add(startExpression);
		nextWave.add(startExpression);
		do {
			lastWave = nextWave;
			newSolutionFound = false;
			List<ExpressionNode> newExpressions = new ArrayList<ExpressionNode>();
			System.out.println("Wave size: " + lastWave.size());
			for (ExpressionNode expression : lastWave) {
				for (AbstractTransformationRule rule : simplificationRules) {
					ExpressionNode transformedLocal = applyTransformation(expression, rule);
					if(transformedLocal!=null) {
					newExpressions.addAll(checkForNewExpression(normalizeExpression(transformedLocal), newExpressions));}
				}
			}
			nextWave = checkForNewExpressions(newExpressions, all);
			newSolutionFound = nextWave.size() > 0;
			distance++;
			equalityReached = solutionFound(targetExpression, nextWave);
			all.addAll(nextWave);
			System.out.println(distance);
		} while (!equalityReached && newSolutionFound && distance < maxDistance);
		return equalityReached;
	}

	private static boolean solutionFound(ExpressionNode expression, List<ExpressionNode> potentialMatches) {
		for (ExpressionNode expressionNode : potentialMatches) {
			if (ExpressionUtil.isStructurallyEqual(expressionNode, expression, true, true, true,false)) {
				return true;
			}
		}
		return false;
	}

	private static List<ExpressionNode> checkForNewExpressions(List<ExpressionNode> newExpressions,
			List<ExpressionNode> existingExpressionToCheckAgainst) {
		List<ExpressionNode> tmpList = new ArrayList<ExpressionNode>();
		for (ExpressionNode expressionNode : newExpressions) {
			boolean isKnown = false;
			for (ExpressionNode existingSolution : existingExpressionToCheckAgainst) {
				if (ExpressionUtil.isStructurallyEqual(expressionNode, existingSolution, true, true, true,false)) {
					isKnown = true;
					break;
				}
			}
			if (!isKnown) {
				tmpList.add(expressionNode);
			}
		}
		return tmpList;
	}
	private static List<ExpressionNode> checkForNewExpression(ExpressionNode newExpression,
			List<ExpressionNode> existingExpressionToCheckAgainst) {
		List<ExpressionNode> tmpList = new ArrayList<ExpressionNode>();
			boolean isKnown = false;
			for (ExpressionNode existingSolution : existingExpressionToCheckAgainst) {
				if (ExpressionUtil.isStructurallyEqual(newExpression, existingSolution, true, true, true,false)) {
					isKnown = true;
					break;
				}
			}
			if (!isKnown) {
				tmpList.add(newExpression);
			}
		
		return tmpList;
	}
	
	private static ExpressionNode applyTransformation(ExpressionNode expression,
			AbstractTransformationRule rule) {
		List<ExpressionNode> transformedLocal = new ArrayList<ExpressionNode>();

		ExpressionNode transformedTmp = null;
		if (rule.isPossible(expression)) {
			try {
				transformedTmp = rule.apply(expression, true);
//				System.out
//						.println(rule + " has been applied to " + expression + " and the result is " + transformedTmp);
			} catch (Exception e) {
			}
		}
		

		return transformedTmp;

	}

	private static List<AbstractTransformationRule> buildRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();

		// tmp.addAll(buildCommutativeRules());
		tmp.addAll(buildReplaceWithConstantFactorRules());
		tmp.addAll(buildNeutralElementRules());
		tmp.addAll(buildFactorReductionRules());
		tmp.addAll(buildNegativeSignReplacementRules());
		tmp.addAll(buildPowRules());
		//tmp.addAll(buildAssociativeRules());
		tmp.addAll(buildBinomialRules());
		tmp.addAll(buildDistributiveRules());
		tmp.addAll(buildFractionRules());
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildFactorReductionRules() {
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
	private static List<GeneralTransformationRule> buildNegativeSignReplacementRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a-b->a+(-1*b)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-(x+y)->x+-y"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a--b->a+b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("--a->a"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<AbstractTransformationRule> buildFractionRules() {
		List<AbstractTransformationRule> tmp = new ArrayList<AbstractTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,c)+frac(b,c)->frac(a+b,c)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,b)*frac(c,d)->frac(a*c,b*d)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("frac(a,b)/frac(c,d)->frac(a*d,b*c)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a*b)/a->b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(b*a)/a->b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(ac+bc)/c->a+b"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(1+a)/a->1/a+1"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+1)/a->1+1/a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+bx²)/x->a/x+bx"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(bx²+a)/x->a/x+bx"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(x²b+a)/x->a/x+bx"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildPowRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b*a^c->a^(b+c)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^b/a^c->a^(b-c)"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^c*b^c->(a*b)^c"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^{b^c}->a^{(b*c)}"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildReplaceWithConstantFactorRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("0*a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-0->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^0->1"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+-1*a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("-1*a+a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a-a->0"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+frac(1,a)->1"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a/a->1"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildDistributiveRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a(b+c)->ab+ac"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(b+c)a->ba+ca"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)(c+d)->ac+ad+bc+bd"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildBinomialRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)²->a²+2ab+b²"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a-b)²->a²-2ab+b²"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("(a+b)(a-b)->a²-b²"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildNeutralElementRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("1*a->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a/1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a^1->a"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("0+a->a"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildAssociativeRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+(b+c)->(a+b)+c"));
		tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*(b*c)->(a*b)*c"));
		return tmp;
	}

	/**
	 * @param tmp
	 */
	private static List<GeneralTransformationRule> buildCommutativeRules() {
		List<GeneralTransformationRule> tmp = new ArrayList<GeneralTransformationRule>();
		// tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a*b->b*a"));
		// tmp.add(TransformationRuleParser.buildTransformationRuleFromString("a+b->b+a"));
		return tmp;
	}

}
