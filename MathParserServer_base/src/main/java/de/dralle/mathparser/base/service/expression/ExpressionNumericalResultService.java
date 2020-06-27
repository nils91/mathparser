/**
 * 
 */
package de.dralle.mathparser.base.service.expression;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.ValueLookupTable;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.expression.IExpressionSyntaxCheckService;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.visitors.CalculateNumericalValueVisitor;
import io.swagger.model.ConstantValuePreset;
import io.swagger.model.ExpressionSyntaxCheckRequest;
import io.swagger.model.ExpressionSyntaxCheckResponse;
import io.swagger.model.IdentifierValuePreset;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.NumericalResultRequest;
import io.swagger.model.NumericalResultResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class ExpressionNumericalResultService implements IExpressionNumericalResultService {

	public ExpressionNumericalResultService() {

	}

	@Override
	public NumericalResultResponse handle(NumericalResultRequest request, HttpServletResponse httpServletResponse)
			throws MathParserServerException {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		List<IdentifierValuePreset> identifierValuePresets = request.getIdentifiers();
		List<ConstantValuePreset> constantValuePresets = request.getConstants();
		ValueLookupTable vt = new ValueLookupTable();
		for (IdentifierValuePreset identifierValuePreset : identifierValuePresets) {
			vt.setValueFor(identifierValuePreset.getName(), identifierValuePreset.getIndex(),
					identifierValuePreset.getValue());
		}
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
		NumericalResultResponse response = new NumericalResultResponse();
		ExpressionNode expression = null;
		try {
			expression = ExpressionParser.buildExpressionTreeFromString(request.getExpression());
		} catch (Exception e) {
			throw new MathParserServerException(e, 1, e.getMessage());
		}
		try {
			response.setResult(expression.accept(new CalculateNumericalValueVisitor(vt)));
			response.setCode(0);
		} catch (Exception e) {
			throw new MathParserServerException(e, 3, e.getMessage());
		}
		return response;
	}

}
