/**
 * 
 */
package de.dralle.mathparser.base.service.expression;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.ValueLookupTable;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import io.swagger.model.ConstantValuePreset;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.IdentifierValuePreset;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckRequestIdentifierTestRange;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.SymbolicEquationCheckRequest;
import io.swagger.model.SymbolicEquationCheckResponse;
import io.swagger.model.SymbolicExpressionCheckRequest;
import io.swagger.model.SymbolicExpressionCheckResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class ExpressionSymbolicCheckService implements IExpressionSymbolicCheckService {

	public ExpressionSymbolicCheckService() {

	}

	@Override
	public SymbolicExpressionCheckResponse handle(SymbolicExpressionCheckRequest request,
			HttpServletResponse httpServletResponse) throws MathParserServerException {
		String leftExp=request.getLeftExpression();
		String rightExp = request.getRightExpression();
		ExpressionNode leftExpTree = ExpressionParser.buildExpressionTreeFromString(leftExp);
		ExpressionNode rightExpTree = ExpressionParser.buildExpressionTreeFromString(rightExp);
		if(leftExpTree==null||rightExp==null) {
			throw new MathParserServerException(1, "Parsing problem.");
		}
					boolean result=false;
			try {
				result=SymbolicEqualityUtil.checkSymbolicEquality(leftExpTree,rightExpTree,SymbolicEqualityUtil.buildSimplificationRule());
			}catch(Exception e) {
				throw new MathParserServerException(e,2, e.getMessage());
			}SymbolicExpressionCheckResponse response = new SymbolicExpressionCheckResponse();	
			response.setCode(0);
			response.setMessage(String.format("Success evaluating %s = %s.", leftExp,rightExp));
			response.setResult(result);
			return response;
			
	}

}
