/**
 * 
 */
package de.dralle.mathparser.base.service.expression
;

import javax.servlet.http.HttpServletResponse;

import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.IMathParserService;
import io.swagger.model.ExpressionSyntaxCheckRequest;
import io.swagger.model.ExpressionSyntaxCheckResponse;

/**
 * @author Nils Dralle
 *
 */
public interface IExpressionSyntaxCheckService extends IMathParserService<ExpressionSyntaxCheckRequest, ExpressionSyntaxCheckResponse>{

	@Override
	ExpressionSyntaxCheckResponse handle(ExpressionSyntaxCheckRequest request, HttpServletResponse httpServletResponse) throws MathParserServerException;

}
