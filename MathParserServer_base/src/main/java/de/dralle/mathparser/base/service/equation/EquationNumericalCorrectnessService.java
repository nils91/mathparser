/**
 * 
 */
package de.dralle.mathparser.base.service.equation;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.ValueLookupTable;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import io.swagger.model.ConstantValuePreset;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.IdentifierValuePreset;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckRequestIdentifierTestRange;
import io.swagger.model.NumericalCheckResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class EquationNumericalCorrectnessService implements IEquationNumericalCorrectnessService {

	public EquationNumericalCorrectnessService() {

	}

	@Override
	public NumericalCheckResponse handle(NumericalCheckRequest request, HttpServletResponse httpServletResponse)
			throws MathParserServerException {
		List<ConstantValuePreset> constantValuePresets = request.getConstants();
		ValueLookupTable vt = new ValueLookupTable();		
		for (ConstantValuePreset constantValuePreset : constantValuePresets) {
			ConstantNode parsedConstant = null;
			try {
				parsedConstant = (ConstantNode) ExpressionParser
						.buildExpressionTreeFromString(constantValuePreset.getName());
			} catch (Exception e) {
				throw new MathParserServerException(e, 2, e.getMessage());
			}
			vt.setValueFor(parsedConstant.getConstant(), constantValuePreset.getIndex(), constantValuePreset.getValue());
		}
		NumericalCheckResponse response = new NumericalCheckResponse();
		EquationNode equation = null;
		try {
			equation = EquationParser.buildEquationTreeFromString(request.getEquation());
		} catch (Exception e) {
			throw new MathParserServerException(e, 1, e.getMessage());
		}
		if (equation == null) {
			throw new MathParserServerException(1, "");
		}
		double min = 0;
		double max = 0;
		double step = 0;
		double numericalErrorThreshold = 0;
		NumericalCheckRequestIdentifierTestRange testRange = request.getIdentifierTestRange();
		if (testRange != null) {
			min = testRange.getMin().doubleValue();
			max = testRange.getMax().doubleValue();
			step = testRange.getStep().doubleValue();
		}
		if (request.getNumericalErrorThreshold() != null) {
			numericalErrorThreshold = request.getNumericalErrorThreshold();
		}

		if (min >= max) {
			throw new MathParserServerException(3, "");
		}
		if (step <= 0) {
			throw new MathParserServerException(3, "");
		}
		try {
			response.setResult(new Double(NumericalEquationUtil.checkNumericalCorrectnessPercentage(equation, min, max, step,
					numericalErrorThreshold)));
			response.setCode(0);
			return response;
		} catch (Exception e) {
			throw new MathParserServerException(e, 4, e.getMessage());
		}
	}

}
