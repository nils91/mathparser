/**
 * 
 */
package de.dralle.mathparser;

import java.util.Map;

import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.rules.GeneralTransformationRule;

/**
 * Provides utility methods which are used in the application of rewrite rules.
 * 
 * Most of the contents of this class have been moved to AbstractTransformatioonRule.
 * 
 * @author Nils Dralle
 *
 */
@Deprecated
public class TransformationUtil {

	/**
	 * Might not be accurate if pattern contains more than one occurrence of an
	 * identifier.
	 * 
	 * @param expression
	 * @param pattern
	 * @param checkRecursive
	 * @return
	 */
	public static boolean isTransformationPossible(ExpressionNode expression, GeneralTransformationRule rule,
			boolean checkRecursive) {
		if (rule == null) {
			return false;
		}
		return rule.isPossible(expression, checkRecursive);
	}

	


	public static boolean isTransformationPossible(String expression, String rule, boolean checkRecursive) {
		return isTransformationPossible(ExpressionParser.buildExpressionTreeFromString(expression),
				TransformationRuleParser.buildTransformationRuleFromString(rule), checkRecursive);
	}
	/**
	 * Tries to replace the identifiers in expression with the nodes specified in
	 * identifierReplacements. Node: This method modifies the parameter expression.
	 * Use replaceIdentifier, if you don't want this.
	 */
	public static IMathParserBaseNode replaceIdentifiersDirect(IMathParserBaseNode expression,
			Map<String, IMathParserBaseNode> identifierReplacements) {
		if (expression instanceof IdentifierNode) {
			String idName = ((IdentifierNode) expression).getName();
			if (identifierReplacements.containsKey(idName)) {
				return identifierReplacements.get(idName);
			} else {
				throw new RuntimeException("No replacement for " + idName);
			}
		} else if (expression.getChildcount() > 0) {
			for (int i = 0; i < expression.getChildcount(); i++) {
				IMathParserBaseNode replacement = replaceIdentifiersDirect(expression.getChild(i),
						identifierReplacements);
				if (replacement != null) {
					expression.setChild(replacement, i);
				}
			}
		}
		return expression;
	}

	/**
	 * Tries to replace the identifiers in expression with the nodes specified in
	 * identifierReplacements.
	 * 
	 */
	public static IMathParserBaseNode replaceIdentifiers(IMathParserBaseNode expression,
			Map<String, IMathParserBaseNode> identifierReplacements) {
		try {
			for (String identifier : identifierReplacements.keySet()) {
				IMathParserBaseNode subtreeClone = identifierReplacements.get(identifier).clone();
				identifierReplacements.put(identifier, subtreeClone);
			}
			return replaceIdentifiersDirect(expression.clone(), identifierReplacements);
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Replacing not possible", e);
		}
	}

	/**
	 * Tries to replace the identifiers in expression with the nodes specified in
	 * identifierReplacements.
	 * 
	 */
	public static IMathParserBaseNode replaceIdentifiers(String expression,
			Map<String, IMathParserBaseNode> identifierReplacements) {
		return replaceIdentifiers(ExpressionParser.buildExpressionTreeFromString(expression), identifierReplacements);
	}

	/**
	 * getTransformDirection is used to try and find a transformDirection. If that
	 * also returns Direction.Bidirectional, Direction.Left2Right is used instead.
	 * By default only the first occurrence of rulePattern found is transformed.
	 * 
	 * @param expression
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	public static IMathParserBaseNode transformExpression(ExpressionNode expression, GeneralTransformationRule rule)
			throws Exception {
		return rule.apply(expression, false);
	}

	/**
	 * getTransformDirection is used to try and find a transformDirection. If that
	 * also returns Direction.Bidirectional, Direction.Left2Right is used instead.
	 * 
	 * @param expression
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	public static IMathParserBaseNode transformExpression(ExpressionNode expression, GeneralTransformationRule rule,
			boolean transformAll) throws Exception {
		return rule.apply(expression, transformAll);
	}

	/**
	 * getTransformDirection is used to try and find a transformDirection. If that
	 * also returns Direction.Bidirectional, Direction.Left2Right is used instead.
	 * By default only the first occurrence of rulePattern found is transformed.
	 * 
	 * @param expression
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	public static IMathParserBaseNode transformExpression(String expression, String rule) throws Exception {
		return transformExpression(expression, rule, false);
	}

	/**
	 * getTransformDirection is used to try and find a transformDirection. If that
	 * also returns Direction.Bidirectional, Direction.Left2Right is used instead.
	 * 
	 * @param expression
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	public static IMathParserBaseNode transformExpression(String expression, String rule, boolean transformAll)
			throws Exception {
		return transformExpression(ExpressionParser.buildExpressionTreeFromString(expression),
				TransformationRuleParser.buildTransformationRuleFromString(rule), transformAll);
	}
}
