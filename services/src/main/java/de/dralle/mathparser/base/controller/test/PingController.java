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
import io.swagger.model.PingRequest;
import io.swagger.model.PingResponse;

@Controller
@RequestMapping(RestCalls.TEST_PING)
public class PingController extends AbstractMathParserServerController<PingRequest, PingResponse> {

	private static Logger log = Logger.getLogger(PingController.class);

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PingResponse ping(@RequestBody PingRequest request, HttpServletResponse httpServletResponse) {
		log.info(request.toString());
		validate(request);
		PingResponse response = new PingResponse();
		response.setCode(0);
		response.setId(request.getId());
		log.info(response.toString());
		return response;
	}

	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	protected PingResponse newResponse(int code, String errorMessage) {
		PingResponse response = new PingResponse();
		response.setCode(code);
		return response;
	}
}
