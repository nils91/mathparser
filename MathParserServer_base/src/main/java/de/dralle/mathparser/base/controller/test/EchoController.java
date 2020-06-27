package de.dralle.mathparser.base.controller.test;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.dralle.mathparser.base.common.RestCalls;
import de.dralle.mathparser.base.controller.AbstractMathParserServerController;
import io.swagger.model.MessageEchoRequest;
import io.swagger.model.MessageEchoResponse;

@Controller
@RequestMapping(RestCalls.TEST_ECHO)
public class EchoController extends AbstractMathParserServerController<MessageEchoRequest, MessageEchoResponse> {

	private static Logger log = Logger.getLogger(EchoController.class);

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public MessageEchoResponse echo(@RequestBody MessageEchoRequest request, HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		MessageEchoResponse response = newResponse(0, request.getMessage());
		MessageEchoResponse validationResult = validate(request);
		if(validationResult!=null) {
			response=validationResult;
		}
		log.info(response.toString());
		return response;
	}

	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	protected MessageEchoResponse newResponse(int code, String errorMessage) {
		MessageEchoResponse response = new MessageEchoResponse();
		response.setCode(code);
		response.setMessage(errorMessage);
		return response;
	}
}
