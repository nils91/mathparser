/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.Map;

import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.rules.conditions.interfaces.ITransformationRuleCondition;

/**
 * Add a condition to a (replace)rule.
 * @author Nils Dralle
 *
 */
public class ConditionalTransformationRule extends GeneralTransformationRule {
	private ITransformationRuleCondition con;
	/**
	 * @return the con
	 */
	public ITransformationRuleCondition getCondition() {
		return con;
	}

	/**
	 * @param con the con to set
	 */
	public void setCondition(ITransformationRuleCondition con) {
		this.con = con;
	}

	/**
	 * @param left
	 * @param right
	 */
	public ConditionalTransformationRule(ExpressionNode left, ExpressionNode right,ITransformationRuleCondition con) {
		super(left, right);
		this.con=con;
	}
	public ConditionalTransformationRule(GeneralTransformationRule rule,ITransformationRuleCondition con) {
		super(rule.getLeft(), rule.getRight());
		this.con=con;
	}

	@Override
	protected boolean isRuleAllowed(Map<SymbolIdentifier, ExpressionNode> replaceMap) {
		if(con!=null) {
			return con.allowRule(this, replaceMap);
		}else {
			throw new RuntimeException(String.format("No condition associated with conditional rule %s", toString()));
		}
	}

}
