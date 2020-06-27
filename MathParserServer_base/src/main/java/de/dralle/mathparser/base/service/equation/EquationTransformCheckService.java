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
import io.swagger.model.EquationTransformCheckRequest;
import io.swagger.model.EquationTransformCheckResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class EquationTransformCheckService implements IEquationTransformCheckService {

	private static Logger log = Logger.getLogger(EquationTransformCheckService.class);

	@Override
	public EquationTransformCheckResponse handle(EquationTransformCheckRequest request,
			HttpServletResponse httpServletResponse) throws MathParserServerException {
		String startEquationString = request.getStartEquation();
		String targetEquationString = request.getTargetEquation();
		String ruleString = request.getRule();
		EquationNode startEquationTree = EquationParser.buildEquationTreeFromString(startEquationString);
		if (startEquationTree == null) {
			throw new MathParserServerException(1, "Start equation parsing problem");
		}
		EquationNode targetEquationTree = EquationParser.buildEquationTreeFromString(targetEquationString);
		if (targetEquationTree == null) {
			throw new MathParserServerException(2, "Target equation parsing problem");
		}
		AbstractTransformationRule ruleTree = null;
		if (ruleString != null) {
			ruleTree = TransformationRuleParser.buildTransformationRuleFromString(ruleString);
			if (ruleTree == null) {
				throw new MathParserServerException(3, "Rule parsing problem");
			}
		}
		boolean result = false;
		try {
			result = SymbolicEqualityUtil.checkEquivalizationTransform(startEquationTree, targetEquationTree, ruleTree);
		} catch (Exception e) {
			throw new MathParserServerException(4, e.getMessage());
		}
		EquationTransformCheckResponse response = new EquationTransformCheckResponse();
		response.setCode(0);
		response.setResult(result);
		return response;
	}

}
