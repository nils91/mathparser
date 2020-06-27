package de.dralle.mathparser.base.controller.equation;

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
import de.dralle.mathparser.base.service.equation.IEquationTransformCheckService;
import io.swagger.model.EquationTransformCheckRequest;
import io.swagger.model.EquationTransformCheckResponse;
import io.swagger.model.SymbolicEquationCheckRequest;
import io.swagger.model.SymbolicEquationCheckResponse;

@Controller
@RequestMapping(RestCalls.EQUATION_CHECK_TRANSFORM)
public class EquationTransformCheckController
		extends AbstractMathParserServerController<EquationTransformCheckRequest, EquationTransformCheckResponse> {

	private static Logger log = Logger.getLogger(EquationTransformCheckController.class);
	@Autowired
	private IEquationTransformCheckService service;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EquationTransformCheckResponse checkEquality(@RequestBody EquationTransformCheckRequest request,
			HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		EquationTransformCheckResponse response = validate(request);
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
	protected EquationTransformCheckResponse newResponse(int code, String errorMessage) {
		EquationTransformCheckResponse response = new EquationTransformCheckResponse();
		response.setCode(code);
		response.setMessage(errorMessage);
		return response;
	}
}
