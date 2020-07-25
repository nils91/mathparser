package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

public class EquationTransformationTests {

	
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	@Test
	public void testEquationTransformation() {
		String startString = "2x+3=12";
		String targetString = "2x=9";
		String ruleString = "f->f-3";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	
	
	@Test
	public void testEquationTransformation2() {
		String startString = "2x=9";
		String targetString = "x=4.5";
		String ruleString = "f->f/2";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	
	@Test
	public void testEquationTransformation3() {
		String startString = "x+3=8";
		String targetString = "x=5";
		String ruleString = "f->f-3";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation4() {
		String startString = "2x=10";
		String targetString = "x=5";
		String ruleString = "f->f \\div 2";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation5() {
		String startString = "x-8=9";
		String targetString = "x=17";
		String ruleString = "f->f+8";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation6() {
		String startString = "\\frac{a}{2}+3=4";
		String targetString = "a+6=8";
		String ruleString = "f->f*2";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation6_2() {
		String startString = "a+6=8";
		String targetString = "a=2";
		String ruleString = "f->f-6";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation6__2() {
		String startString = "\\frac{a}{2}+3=4";
		String targetString = "\\frac{a}{2}=1";
		String ruleString = "f->f-3";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation6_2_2() {
		String startString = "\\frac{a}{2}=1";
		String targetString = "a=2";
		String ruleString = "f->f*2";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	@Disabled
	public void testEquationTransformation__2() {
		String startString = "2x+3=12";
		String targetString = "x+1.5=6";
		String ruleString = "f->f/2";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation2__2() {
		String startString = "x+1.5=6";
		String targetString = "x=4.5";
		String ruleString = "f->f-1.5";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7() {
		String startString = "\\frac{(x+5)^2-25}{x}=13";
		String targetString = "(x+5)^2-25=13x";
		String ruleString = "f->f*x";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7_2() {
		String startString = "(x+5)^2-25=13x";
		String targetString = "x^2+10x+5^2-25=13x";
		String ruleString = "f->f+0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7_3() {
		String startString = "x^2+10x+5^2-25=13x";
		String targetString = "x^2+10x=13x";
		String ruleString = "f->f+0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7_4() {
		String startString = "x^2+10x=13x";
		String targetString = "x^2=3x";
		String ruleString = "f->f-10x";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7_5() {
		String startString = "x*x=3x";
		String targetString = "x=3";
		String ruleString = "f->f/x";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	@Disabled
	public void testEquationTransformation7_5_a2() {
		String startString = "x^2=3x";
		String targetString = "x=3";
		String ruleString = "f->f/x";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7__2() {
		String startString = "\\frac{(x+5)^2-25}{x}=13";
		String targetString = "\\frac{(x^2+10x+5^2)-25}{x}=13";
		String ruleString = "f->f+0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7_2__2() {
		String startString = "\\frac{(x^2+10x+5^2)-25}{x}=13";
		String targetString = "\\frac{x^2+10x}{x}=13";
		String ruleString = "f->f+0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7_3__2() {
		String startString = "\\frac{x^2+10x}{x}=13";
		String targetString = "x+10=13";
		String ruleString = "f->f+0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation7_4__2() {
		String startString = "x+10=13";
		String targetString = "x=3";
		String ruleString = "f->f-10";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation8() {
		String startString = "(5x+3x)=6";
		String targetString = "(5+3)x=6";
		String ruleString = "f->f+0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation8_2() {
		String startString = "(5+3)x=6";
		String targetString = "8x=6";
		String ruleString = "f->f+0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation8_3() {
		String startString = "8x=6";
		String targetString = "x=\\frac{6}{8}";
		String ruleString = "f->f \\div 8";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	@Test
	public void testEquationTransformation8_4() {
		String startString = "x=\\frac{6}{8}";
		String targetString = "x=0.75";
		String ruleString = "f->f + 0";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
}
