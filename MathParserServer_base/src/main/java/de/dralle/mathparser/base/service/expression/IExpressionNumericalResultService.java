/**
 * 
 */
package de.dralle.mathparser.base.service.expression;

import javax.servlet.http.HttpServletResponse;

import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.IMathParserService;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.NumericalResultRequest;
import io.swagger.model.NumericalResultResponse;

/**
 * @author Nils Dralle
 *
 */
public interface IExpressionNumericalResultService extends IMathParserService<NumericalResultRequest,NumericalResultResponse>{

	@Override
	NumericalResultResponse handle(NumericalResultRequest request, HttpServletResponse httpServletResponse) throws MathParserServerException;


}
