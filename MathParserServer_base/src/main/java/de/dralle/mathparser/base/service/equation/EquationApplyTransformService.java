/**
 * 
 */
package de.dralle.mathparser.base.service.equation;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.rules.AbstractTransformationRule;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.visitors.EquationTreeLatexFormatter;
import io.swagger.model.EquationTransformRequest;
import io.swagger.model.EquationTransformResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class EquationApplyTransformService implements IEquationApplyTransformService {
	private static Logger log = Logger.getLogger(EquationApplyTransformService.class);

	@Override
	public EquationTransformResponse handle(EquationTransformRequest request, HttpServletResponse httpServletResponse)
			throws MathParserServerException {
		String equationString = request.getEquation();
		String ruleString = request.getRule();
		EquationNode equationTree = EquationParser.buildEquationTreeFromString(equationString);
		if (equationTree == null) {
			throw new MathParserServerException(1, "Equation parsing problem");
		}
		AbstractTransformationRule ruleTree = null;
		if (ruleString != null) {
			ruleTree = TransformationRuleParser.buildTransformationRuleFromString(ruleString);
			if (ruleTree == null) {
				throw new MathParserServerException(2, "Rule parsing problem");
			}
		}
		EquationNode transformedEquationNode = null;
		if (ruleTree != null) {
			try {
				transformedEquationNode = SymbolicEqualityUtil.equivalizationTransform(equationTree, ruleTree);
			} catch (Exception e) {
				throw new MathParserServerException(e, 3, e.getMessage());
			}
			if (transformedEquationNode == null) {
				throw new MathParserServerException(3, "Problem while applying rule");
			}
		} else {
			transformedEquationNode = equationTree;
		}
		EquationTransformResponse response = new EquationTransformResponse();
		response.setCode(0);
		response.setResult(transformedEquationNode.accept(new EquationTreeLatexFormatter()));
		return response;
	}

}
