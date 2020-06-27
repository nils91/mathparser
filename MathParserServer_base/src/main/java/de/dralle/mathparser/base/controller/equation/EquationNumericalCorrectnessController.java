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
import de.dralle.mathparser.base.service.equation.IEquationNumericalCorrectnessService;
import de.dralle.mathparser.base.service.expression.IExpressionGetConstantsService;
import de.dralle.mathparser.base.service.expression.IExpressionSyntaxCheckService;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.ExpressionConstantsRequest;
import io.swagger.model.ExpressionConstantsResponse;
import io.swagger.model.ExpressionSyntaxCheckRequest;
import io.swagger.model.ExpressionSyntaxCheckResponse;
import io.swagger.model.MessageEchoRequest;
import io.swagger.model.MessageEchoResponse;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;

@Controller
@RequestMapping(RestCalls.NUM_EQUATION_CORRECTNESS)
public class EquationNumericalCorrectnessController extends AbstractMathParserServerController<NumericalCheckRequest, NumericalCheckResponse> {

	private static Logger log = Logger.getLogger(EquationNumericalCorrectnessController.class);
@Autowired
private IEquationNumericalCorrectnessService service;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public NumericalCheckResponse checkNumericalCorrectness(@RequestBody NumericalCheckRequest request, HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		NumericalCheckResponse response = validate(request);
	if(response==null) {
		try {
			response=service.handle(request,httpServletResponse);
		} catch (MathParserServerException e) {
			response=newResponse(e.getCode(), e.getErrorMessage());
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
	protected NumericalCheckResponse newResponse(int code, String errorMessage) {
		NumericalCheckResponse response = new NumericalCheckResponse();
		response.setCode(code);
		response.setMessage(errorMessage);
		return response;
	}
}
