package de.dralle.mathparser.base.controller.expression;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.common.RestCalls;
import de.dralle.mathparser.base.controller.AbstractMathParserServerController;
import de.dralle.mathparser.base.service.equation.IEquationSymbolicCheckService;
import de.dralle.mathparser.base.service.expression.IExpressionSymbolicCheckService;
import io.swagger.model.SymbolicEquationCheckRequest;
import io.swagger.model.SymbolicEquationCheckResponse;
import io.swagger.model.SymbolicExpressionCheckRequest;
import io.swagger.model.SymbolicExpressionCheckResponse;

@Controller
@RequestMapping(RestCalls.SYM_EXPRESSION_CORRECTNESS)
public class ExpressionSymbolicEqualityController
		extends AbstractMathParserServerController<SymbolicExpressionCheckRequest, SymbolicExpressionCheckResponse> {

	private static Logger log = Logger.getLogger(ExpressionSymbolicEqualityController.class);
	@Autowired
	private IExpressionSymbolicCheckService service;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SymbolicExpressionCheckResponse checkEquality(@RequestBody SymbolicExpressionCheckRequest request,
			HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		SymbolicExpressionCheckResponse response = validate(request);
		if (response == null) {
			try {
				response = service.handle(request, httpServletResponse);
			} catch (MathParserServerException e) {
				response = newResponse(e.getCode(), e.getErrorMessage());
			}
		}
		log.info(response.toString());
		return response;
	}

	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	protected SymbolicExpressionCheckResponse newResponse(int code, String errorMessage) {
		SymbolicExpressionCheckResponse response = new SymbolicExpressionCheckResponse();
		response.setCode(code);
		response.setMessage(errorMessage);
		return response;
	}
}
