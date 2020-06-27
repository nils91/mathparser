/**
 * 
 */
package de.dralle.mathparser.base.service.expression;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.expression.IExpressionSyntaxCheckService;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import io.swagger.model.ExpressionSyntaxCheckRequest;
import io.swagger.model.ExpressionSyntaxCheckResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class ExpressionSyntaxCheckService implements IExpressionSyntaxCheckService{	
	
	public ExpressionSyntaxCheckService() {
	
	}
	
	@Override
	public ExpressionSyntaxCheckResponse handle(ExpressionSyntaxCheckRequest request,
			HttpServletResponse httpServletResponse) throws MathParserServerException {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionSyntaxCheckResponse response = new ExpressionSyntaxCheckResponse();
		try{
			response.setExpression(ExpressionParser.checkExpressionSyntaxThrowException(request.getExpression()));
			response.setCode(0);
		}catch(Exception e) {
			throw new MathParserServerException(e, 1, e.getMessage());
		}
		return response;
	}

}
