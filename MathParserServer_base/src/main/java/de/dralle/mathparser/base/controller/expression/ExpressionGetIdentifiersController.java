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
import de.dralle.mathparser.base.service.expression.IExpressionGetIdentifiersService;
import de.dralle.mathparser.base.service.expression.IExpressionSyntaxCheckService;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.ExpressionIdentifiersRequest;
import io.swagger.model.ExpressionIdentifiersResponse;
import io.swagger.model.ExpressionSyntaxCheckRequest;
import io.swagger.model.ExpressionSyntaxCheckResponse;
import io.swagger.model.MessageEchoRequest;
import io.swagger.model.MessageEchoResponse;

@Controller
@RequestMapping(RestCalls.EXP_ID)
public class ExpressionGetIdentifiersController extends AbstractMathParserServerController<ExpressionIdentifiersRequest, ExpressionIdentifiersResponse> {

	private static Logger log = Logger.getLogger(ExpressionGetIdentifiersController.class);
@Autowired
private IExpressionGetIdentifiersService service;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ExpressionIdentifiersResponse getIdentifiers(@RequestBody ExpressionIdentifiersRequest request, HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		ExpressionIdentifiersResponse response = validate(request);
	if(response==null) {
		try {
			response=service.handle(request,httpServletResponse);
		} catch (MathParserServerException e) {
			response=newResponse(e.getCode(), e.getMessage());
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
	protected ExpressionIdentifiersResponse newResponse(int code, String errorMessage) {
		ExpressionIdentifiersResponse response = new ExpressionIdentifiersResponse();
		response.setCode(code);
		return response;
	}
}
