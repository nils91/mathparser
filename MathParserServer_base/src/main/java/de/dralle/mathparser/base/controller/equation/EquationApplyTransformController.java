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
import de.dralle.mathparser.base.service.equation.IEquationApplyTransformService;
import de.dralle.mathparser.base.service.equation.IEquationSymbolicCheckService;
import io.swagger.model.EquationTransformRequest;
import io.swagger.model.EquationTransformResponse;
import io.swagger.model.SymbolicEquationCheckRequest;
import io.swagger.model.SymbolicEquationCheckResponse;

@Controller
@RequestMapping(RestCalls.EQUATION_APPLY_TRANSFORM)
public class EquationApplyTransformController
		extends AbstractMathParserServerController<EquationTransformRequest, EquationTransformResponse> {

	private static Logger log = Logger.getLogger(EquationApplyTransformController.class);
	@Autowired
	private IEquationApplyTransformService service;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EquationTransformResponse checkEquality(@RequestBody EquationTransformRequest request,
			HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		EquationTransformResponse response = validate(request);
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
	protected EquationTransformResponse newResponse(int code, String errorMessage) {
		EquationTransformResponse response = new EquationTransformResponse();
		response.setCode(code);
		response.setMessage(errorMessage);
		return response;
	}
}
