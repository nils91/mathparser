/**
 * 
 */
package de.dralle.mathparser.base.service;

import javax.servlet.http.HttpServletResponse;

import de.dralle.mathparser.base.common.MathParserServerException;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;

/**
 * @author Nils Dralle
 *
 */
public interface IMathParserService<REQUEST_TYPE,RESPONSE_TYPE>{

	RESPONSE_TYPE handle(REQUEST_TYPE request, HttpServletResponse httpServletResponse) throws MathParserServerException;

}
