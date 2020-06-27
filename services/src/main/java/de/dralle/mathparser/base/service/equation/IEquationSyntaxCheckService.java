/**
 * 
 */
package de.dralle.mathparser.base.service.equation;

import javax.servlet.http.HttpServletResponse;

import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.IMathParserService;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;

/**
 * @author Nils Dralle
 *
 */
public interface IEquationSyntaxCheckService extends IMathParserService<EquationSyntaxCheckRequest, EquationSyntaxCheckResponse>{
@Override
	EquationSyntaxCheckResponse handle(EquationSyntaxCheckRequest request, HttpServletResponse httpServletResponse) throws MathParserServerException;

}
