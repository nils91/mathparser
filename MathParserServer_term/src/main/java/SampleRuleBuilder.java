import java.util.Map;

import de.dralle.mathparser.Sign;
import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.rules.AbstractTransformationRule;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;
import de.dralle.mathparser.rules.conditions.interfaces.ITransformationRuleCondition;

/**
 * Do not use.
 * @author Nils Dralle
 *
 */
public class SampleRuleBuilder {

	void buildUnconditionalRule() {
		AbstractTransformationRule rule=TransformationRuleBuilder.buildRuleFromString("a*1 -> a");
	}
	
	void buildConditionalRule() {
		AbstractTransformationRule rule=TransformationRuleBuilder.buildRuleFromString("a^{-b} -> \frac{1}{a^b}",new ITransformationRuleCondition() {			
			@Override
			public boolean allowRule(GeneralTransformationRule rule, Map<SymbolIdentifier, ExpressionNode> replaceMap) {
				return replaceMap.get(new SymbolIdentifier("b")).getSign()==Sign.POSITIVE;
			}
		});
	}

}
