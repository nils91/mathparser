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
import de.dralle.mathparser.base.service.equation.IEquationSyntaxCheckService;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.MessageEchoRequest;
import io.swagger.model.MessageEchoResponse;

@Controller
@RequestMapping(RestCalls.EQUATION_SYNTAX)
public class EquationSyntaxCheckController extends AbstractMathParserServerController<EquationSyntaxCheckRequest, EquationSyntaxCheckResponse> {

	private static Logger log = Logger.getLogger(EquationSyntaxCheckController.class);

	@Autowired
	private IEquationSyntaxCheckService service;
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EquationSyntaxCheckResponse checkEquationSyntax(@RequestBody EquationSyntaxCheckRequest request, HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		EquationSyntaxCheckResponse response = validate(request);
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
	protected EquationSyntaxCheckResponse newResponse(int code, String errorMessage) {
		EquationSyntaxCheckResponse response = new EquationSyntaxCheckResponse();
		response.setCode(code);
		response.setError(errorMessage);
		return response;
	}
}
