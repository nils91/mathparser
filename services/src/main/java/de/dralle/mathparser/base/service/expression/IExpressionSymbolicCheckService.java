/**
 * 
 */
package de.dralle.mathparser.base.service.expression;

import javax.servlet.http.HttpServletResponse;

import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.IMathParserService;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.SymbolicEquationCheckRequest;
import io.swagger.model.SymbolicEquationCheckResponse;
import io.swagger.model.SymbolicExpressionCheckRequest;
import io.swagger.model.SymbolicExpressionCheckResponse;

/**
 * @author Nils Dralle
 *
 */
public interface IExpressionSymbolicCheckService
		extends IMathParserService<SymbolicExpressionCheckRequest,SymbolicExpressionCheckResponse> {
	@Override
	SymbolicExpressionCheckResponse handle(SymbolicExpressionCheckRequest request, HttpServletResponse httpServletResponse)
			throws MathParserServerException;

}
