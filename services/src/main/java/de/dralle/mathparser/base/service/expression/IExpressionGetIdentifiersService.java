/**
 * 
 */
package de.dralle.mathparser.base.service.expression;

import javax.servlet.http.HttpServletResponse;

import de.dralle.mathparser.base.service.IMathParserService;
import io.swagger.model.ExpressionIdentifiersRequest;
import io.swagger.model.ExpressionIdentifiersResponse;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.NumericalResultRequest;
import io.swagger.model.NumericalResultResponse;

/**
 * @author Nils Dralle
 *
 */
public interface IExpressionGetIdentifiersService extends IMathParserService<ExpressionIdentifiersRequest,ExpressionIdentifiersResponse>{


}
