package de.dralle.mathparser.tests;

import java.util.Collection;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.rules.GeneralTransformationRule;

/**
 * Some old test, ignore
 * 
 * @author Nils Dralle
 *
 */

@Deprecated
class StructuralTransformationTest {

	
	
	@Test
	@Disabled
	void test() {
		String sourceString1 = "(x+y)^2+(x+y)*z";
		String sourceString2 = "x(x+2y+z)+y(y+z)";
		
		String rule1String = "(a+b)²->a²+2ab+b²";
		String rule2String = "(a+b)c->ac+bc";
		String rule3String = "c(a+b)->ac+bc";
		String rule4String = "a^2->a*a";
		String rule5String = "2*a*b->a*b + a*b";

		String rule6String = "a*b->b*a";
		String rule7String = "a+(b+c)->(a+b)+c";
		
		
		HashMap<String,GeneralTransformationRule> rulesForSimplification = new HashMap<String,GeneralTransformationRule>();
		HashMap<String,GeneralTransformationRule> rulesForOrder = new HashMap<String,GeneralTransformationRule>();
		
		rulesForSimplification.put("rule1", TransformationRuleParser.buildTransformationRuleFromString(rule1String));
		rulesForSimplification.put("rule2", TransformationRuleParser.buildTransformationRuleFromString(rule2String));
		rulesForSimplification.put("rule3", TransformationRuleParser.buildTransformationRuleFromString(rule3String));
		rulesForSimplification.put("rule4", TransformationRuleParser.buildTransformationRuleFromString(rule4String));
		rulesForSimplification.put("rule5", TransformationRuleParser.buildTransformationRuleFromString(rule5String));
		rulesForOrder.put("rule6", TransformationRuleParser.buildTransformationRuleFromString(rule6String));
		rulesForOrder.put("rule7", TransformationRuleParser.buildTransformationRuleFromString(rule7String));
		 Collection<GeneralTransformationRule> valuesSimplification = rulesForSimplification.values();
		 Collection<GeneralTransformationRule> valuesOrder = rulesForOrder.values();
		 
		 ExpressionNode transformation1 = ExpressionParser.buildExpressionTreeFromString(sourceString1);
		 ExpressionNode transformation2 = ExpressionParser.buildExpressionTreeFromString(sourceString2);
		 ExpressionNode transformationnew1=null;
		 ExpressionNode transformationnew2=null;
		 try {
		 do {
			for (GeneralTransformationRule transformationRule : valuesSimplification) {					
					transformationnew1 = transformationRule.apply(transformation1, false);
					transformationnew2 = transformationRule.apply(transformation2, false);
					System.out.println(transformationRule.getLeft() + "  <=>   " + transformationRule.getRight());
					System.out.println(transformationnew1 + " <-> " + transformationnew2);			
			}
			for (GeneralTransformationRule transformationRule : valuesOrder) {					
				transformationnew1 = transformationRule.apply(transformation1, false);
				transformationnew2 = transformationRule.apply(transformation2, false);
				System.out.println(transformationRule.getLeft() + "  <=>   " + transformationRule.getRight());
				System.out.println(transformationnew1 + " <-> " + transformationnew2);			
			}
			System.out.println("next Iteration");
		} while (!ExpressionUtil.isStructurallyEqual(transformation1, transformation2, true, true, true,false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			ExpressionNode sourceExpression = ExpressionParser.buildExpressionTreeFromString(sourceString);
//			TransformationRule rule1 = TransformationRuleParser.buildTransformationRuleFromString(rule1String);
//			TransformationRule rule2 = TransformationRuleParser.buildTransformationRuleFromString(rule2String);
//			TransformationRule rule3 = TransformationRuleParser.buildTransformationRuleFromString(rule3String);
//			
//			IMathParserBaseNode transformation1 = TransformationUtil.transformExpression(sourceExpression, rule1, Direction.Left2Right);
//			IMathParserBaseNode transformation2 = TransformationUtil.transformExpression(transformation1, rule2, Direction.Left2Right);
//			IMathParserBaseNode transformation3 = TransformationUtil.transformExpression(transformation2, rule3, Direction.Left2Right);
//			
//			ExpressionNode destinationExpression = ExpressionParser.buildExpressionTreeFromString(destString);
//			assertTrue(ExpressionUtil.isStructurallyEqual(transformation3, destinationExpression, true, true, true));
//		} catch (Exception e) {
//			fail();
//		}
	}
	
	
	HashMap<String, GeneralTransformationRule> getRules(String ... ruleStrings ){
		return new HashMap<>();
	}
	
}
