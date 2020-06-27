/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IdentifierNode;

/**
 * Defines the replace rules. a -> b.
 * @author Nils Dralle
 *
 */
public class GeneralTransformationRule extends AbstractTransformationRule {
	private ExpressionNode left;
	private ExpressionNode right;

	/**
	 * @param left the left to set
	 */
	public void setLeft(ExpressionNode left) {
		this.left = left;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(ExpressionNode right) {
		this.right = right;
	}

	/**
	 * If the rule is applied, write identifiers on the right side which do not have
	 * a match on the left.
	 */
	private boolean writeUnknownIdentifiersOnRHS = false;
	/**
	 * Ignore the sign when matching the rule, but correct the sign later on in the
	 * result.
	 */
	private boolean ignoreSigns = false;

	/**
	 * @return the ignoreSigns
	 */
	public boolean isIgnoreSigns() {
		return ignoreSigns;
	}

	/**
	 * @param ignoreSigns the ignoreSigns to set
	 */
	public void setIgnoreSigns(boolean ignoreSigns) {
		this.ignoreSigns = ignoreSigns;
	}

	/**
	 * @return the writeUnknownIdentifiersOnRHS
	 */
	public boolean isWriteUnknownIdentifiersOnRHS() {
		return writeUnknownIdentifiersOnRHS;
	}

	/**
	 * @param writeUnknownIdentifiersOnRHS the writeUnknownIdentifiersOnRHS to set
	 */
	public void setWriteUnknownIdentifiersOnRHS(boolean writeUnknownIdentifiersOnRHS) {
		this.writeUnknownIdentifiersOnRHS = writeUnknownIdentifiersOnRHS;
	}

	private static Logger log = Logger.getLogger(GeneralTransformationRule.class);

	public GeneralTransformationRule(ExpressionNode left, ExpressionNode right) {
		super();
		this.left = left;
		this.right = right;
		ignoreSigns = left.getSign().equals(Sign.POSITIVE);
	}

	public ExpressionNode getLeft() {
		return left;
	}

	public ExpressionNode getRight() {
		return right;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", left, right);

	}

	public boolean isPossible(ExpressionNode expression, boolean checkRecursive) {
		return isPossible(expression, left, ignoreSigns, checkRecursive);
	}

	public static boolean isPossible(ExpressionNode expression, ExpressionNode pattern, boolean ignoreSigns,
			boolean checkRecursive) {
		ExpressionNode patternClone = pattern;
		if (ignoreSigns) {
			try {
				patternClone = pattern.clone();
			} catch (CloneNotSupportedException e) {
			}
			patternClone.setSign(expression.getSign());
		}
		if (ExpressionUtil.isStructurallyEqual(expression, patternClone, false, true, false,true)) {
			return true;
		} else {
			if (checkRecursive) {
				for (int i = 0; i < expression.getChildcount(); i++) {
					if (isPossible(expression.getChild(i), pattern, checkRecursive, ignoreSigns)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	protected boolean isRuleAllowed(Map<SymbolIdentifier, ExpressionNode> replaceMap) {
		return true;
	}

	/**
	 * Transforms an expression by searching for rulePattern within the entire tree
	 * for expression.
	 * 
	 * @param expression
	 * @param rulePattern
	 * @param ruleTarget
	 * @param transformAll Transform all occurrences of rulePattern or only the
	 *                     first one?
	 * @return
	 * @throws Exception
	 */
	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) {
		if (expression == null) {
			return null;
		}
		int transformed = 0;
		try {
			expression = expression.clone();
		} catch (CloneNotSupportedException e) {
		} // clone expression to avoid modifying the input expression instance
		ExpressionNode originExpression = null;
		try {
			originExpression = expression.clone();
		} catch (CloneNotSupportedException e) {
		}
		ExpressionNode newexpression = transformExpression(expression, transformAll);
		if (newexpression != null) {
			expression = newexpression;
			transformed++;
		}
		if (transformed > 0) {
			log.debug(originExpression + " transformed to " + expression + " with rule " + left + " -> " + right);
		}
		return transformed > 0 ? expression : null;
	}

	private ExpressionNode transformExpression(ExpressionNode expression, boolean transformAll) {
		boolean changeSignOnTarget = false;
		if (ignoreSigns&&!expression.getSign().equals(left.getSign())) {
			try {
				expression = expression.clone();
			} catch (CloneNotSupportedException e) {

			}
			expression.setSign(Sign.createOpposite(expression.getSign()));
			changeSignOnTarget = true;
		}
		int transformed = 0;
		if (ExpressionUtil.isStructurallyEqual(expression, left, false, true, false,true)) {
			if (left.getChildcount() == 0) {// special case for transforming root, if the pattern is only one node. So, for a rule like 0 -> -0 or -0 -> 0.
				ExpressionNode newexpression = null;
				try {
					newexpression = transformPrematchedExpression(expression);
				} catch (Exception e) {
					log.debug(String.format("Rewriting %s with rule %s failed", expression, this.toString()), e);
				}
				if (newexpression != null) {
					expression = newexpression;
					transformed++;
					if (!transformAll) {
						if (changeSignOnTarget) {
							expression.setSign(Sign.createOpposite(expression.getSign()));
						}
						return expression;
					}
				}
			} else if (expression.getChildcount() == left.getChildcount()) {
				ExpressionNode newexpression = null;
				try {
					newexpression = transformPrematchedExpression(expression);
				} catch (Exception e) {
					//log.debug(String.format("Rewriting %s with rule %s failed", expression, this.toString()), e);
				}
				if (newexpression != null) {
					expression = newexpression;
					transformed++;
					if (!transformAll) {
						if (changeSignOnTarget) {
							expression.setSign(Sign.createOpposite(expression.getSign()));
						}
						return expression;
					}
				}
			} else if (expression.getChildcount() > left.getChildcount()) {// for handling multinodes
				for (int i = 0; i < expression.getChildcount() - left.getChildcount() + 1; i++) {
					ExpressionNode partialExpressionForCheck = null;
					try {
						partialExpressionForCheck = expression.clone();
					} catch (CloneNotSupportedException e) {
					}
					partialExpressionForCheck.clearChilds();
					int[] childIndices = new int[left.getChildcount()];
					for (int j = 0; j < left.getChildcount(); j++) {
						partialExpressionForCheck.setChild(expression.getChild(i + j), -1);
						childIndices[j] = i + j;
					}
					ExpressionNode newchild = null;
					if (ExpressionUtil.isStructurallyEqual(partialExpressionForCheck, left, false, true, false,false)) {
						try {
							newchild = transformPrematchedExpression(partialExpressionForCheck);
						} catch (Exception e) {
//							log.debug(String.format("Rewriting %s with rule %s failed", partialExpressionForCheck,
//									this.toString()), e);
						}
					}
					if (newchild != null) {
						for (int j = 1; j < childIndices.length; j++) {
							expression.setChild(null, childIndices[j]);
						}
						expression.setChild(newchild, childIndices[0]);
						transformed++;
						if (!transformAll) {
							break;
						}
					}
				}
				List<ExpressionNode> notNullNewExpressionChilds = new ArrayList<ExpressionNode>();
				for (int i = 0; i < expression.getChildcount(); i++) {
					ExpressionNode child = expression.getChild(i);
					if (child != null) {
						notNullNewExpressionChilds.add(child);
					}
				}
				expression.clearChilds();
				for (ExpressionNode expressionNode : notNullNewExpressionChilds) {
					expression.setChild(expressionNode, -1);
				}
				if (!transformAll && transformed > 0) {
					if (changeSignOnTarget) {
						expression.setSign(Sign.createOpposite(expression.getSign()));
					}
					return expression;
				}
			}
		}
		if (changeSignOnTarget) {
			expression.setSign(Sign.createOpposite(expression.getSign()));
		}
		for (int i = 0; i < expression.getChildcount(); i++) {
			ExpressionNode newchild = transformExpression(expression.getChild(i), transformAll);
			if (newchild != null) {
				expression.setChild(newchild, i);
				transformed++;
				if (!transformAll) {
					return expression;
				}
			}
		}
		return transformed > 0 ? expression : null;
	}

	private ExpressionNode transformPrematchedExpression(ExpressionNode expression) {
		Map<SymbolIdentifier, ExpressionNode> replaceMap = matchToRuleIdentifiers(expression);
		if (isRuleAllowed(replaceMap)) {
			ExpressionNode nex = null;
			try {
				nex = replaceIdentifersWithStuff(right.clone(), replaceMap);
			} catch (CloneNotSupportedException e) {

			}
			if (nex == null) {
				throw new RuntimeException(String.format("Replacement operation on %s failed", right));
			}
			return nex;
		}
		return null;
	}

	private ExpressionNode replaceIdentifersWithStuff(ExpressionNode expressionNode,
			Map<SymbolIdentifier, ExpressionNode> replaceMap) {
		if (expressionNode instanceof IdentifierNode) {
			IdentifierNode id = (IdentifierNode) expressionNode;
			SymbolIdentifier identifierName = id.getNameAsSymbolIdentifier();
			if (replaceMap.containsKey(identifierName)) {
				ExpressionNode replacementNode = replaceMap.get(identifierName);
				if (id.getSign().equals(Sign.NEGATIVE)) {
					replacementNode.setSign(Sign.createOpposite(replacementNode.getSign()));
				}
				return replacementNode;
			} else {
				if (writeUnknownIdentifiersOnRHS) {
					IdentifierNode replacementNode = new IdentifierNode(identifierName);
					if (id.getSign().equals(Sign.NEGATIVE)) {
						replacementNode.setSign(Sign.createOpposite(replacementNode.getSign()));
					}
					return replacementNode;
				} else {
					throw new RuntimeException(String.format("No replacement for %s", identifierName));
				}
			}
		} else {
			for (int i = 0; i < expressionNode.getChildcount(); i++) {
				ExpressionNode replacementNode = replaceIdentifersWithStuff(expressionNode.getChild(i), replaceMap);
				if (replacementNode != null) {
					expressionNode.setChild(replacementNode, i);
				}
			}
			return expressionNode;
		}
	}

	private Map<SymbolIdentifier, ExpressionNode> matchToRuleIdentifiers(ExpressionNode partialExpressionForCheck) {
		return matchToRuleIdentifiersStatic(partialExpressionForCheck, left);
	}

	private static Map<SymbolIdentifier, ExpressionNode> matchToRuleIdentifiersStatic(
			ExpressionNode partialExpressionForCheck, ExpressionNode pattern) {
		if (pattern instanceof IdentifierNode) {
			IdentifierNode patternId = (IdentifierNode) pattern;
			ExpressionNode matchedClone = null;
			try {
				matchedClone = partialExpressionForCheck.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			if (patternId.getSign().equals(Sign.NEGATIVE)) {
				matchedClone.setSign(Sign.createOpposite(matchedClone.getSign()));
			}
			SymbolIdentifier identifierName = patternId.getNameAsSymbolIdentifier();
			Map<SymbolIdentifier, ExpressionNode> replaceMap = new HashMap<SymbolIdentifier, ExpressionNode>();
			replaceMap.put(identifierName, matchedClone);
			return replaceMap;
		} else {
			Map<SymbolIdentifier, ExpressionNode> glblReplaceMap = new HashMap<SymbolIdentifier, ExpressionNode>();
			for (int i = 0; i < partialExpressionForCheck.getChildcount(); i++) {
				Map<SymbolIdentifier, ExpressionNode> lclReplaceMap = matchToRuleIdentifiersStatic(
						partialExpressionForCheck.getChild(i), pattern.getChild(i));
				for (SymbolIdentifier iterable_element : lclReplaceMap.keySet()) {
					ExpressionNode foundMatch = lclReplaceMap.get(iterable_element);
					if (glblReplaceMap.containsKey(iterable_element)) {
						ExpressionNode existingMatch = glblReplaceMap.get(iterable_element);
						if (!foundMatch.equals(existingMatch)) {
							throw new RuntimeException(String.format("Conflicting subtrees for %s", iterable_element));
						}
					} else {
						glblReplaceMap.put(iterable_element, foundMatch);
					}
				}
			}
			return glblReplaceMap;
		}
	}

	/**
	 * Transforms an expression by searching for rulePattern within the entire tree
	 * for expression.
	 * 
	 * @param expression
	 * @param rulePattern
	 * @param ruleTarget
	 * @param transformAll Transform all occurrences of rulePattern or only the
	 *                     first one?
	 * @return
	 * @throws Exception
	 */

	@Override
	public ExpressionNode apply(ExpressionNode expression) {
		if (expression == null) {
			return null;
		}
		ExpressionNode transformedExpression = apply(expression, false);
		log.debug(expression + " => " + transformedExpression);
		return transformedExpression;
	}

	@Override
	public boolean isPossible(ExpressionNode expression) {
		return isPossible(expression, true);
	}
}
