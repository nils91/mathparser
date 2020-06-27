/**
 * 
 */
package de.dralle.mathparser.rules.conditions.interfaces;

import java.util.Map;

import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.rules.GeneralTransformationRule;

/**
 * Base interface for all rule conditions.
 * @author Nils Dralle
 *
 */
public interface ITransformationRuleCondition {
	boolean allowRule(GeneralTransformationRule rule,Map<SymbolIdentifier, ExpressionNode> replaceMap);
}
