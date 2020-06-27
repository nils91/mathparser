/**
 * 
 */
package de.dralle.mathparser.base.service.equation;

import javax.servlet.http.HttpServletResponse;

import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.IMathParserService;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.SymbolicEquationCheckRequest;
import io.swagger.model.SymbolicEquationCheckResponse;

/**
 * @author Nils Dralle
 *
 */
public interface IEquationSymbolicCheckService
		extends IMathParserService<SymbolicEquationCheckRequest, SymbolicEquationCheckResponse> {
	@Override
	SymbolicEquationCheckResponse handle(SymbolicEquationCheckRequest request, HttpServletResponse httpServletResponse)
			throws MathParserServerException;

}
