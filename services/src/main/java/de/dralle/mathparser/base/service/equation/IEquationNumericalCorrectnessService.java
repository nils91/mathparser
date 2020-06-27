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

/**
 * @author Nils Dralle
 *
 */
public interface IEquationNumericalCorrectnessService extends IMathParserService<NumericalCheckRequest, NumericalCheckResponse>{
@Override
	NumericalCheckResponse handle(NumericalCheckRequest request, HttpServletResponse httpServletResponse) throws MathParserServerException;

}
