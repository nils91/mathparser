package de.dralle.mathparser.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.dralle.mathparser.base.common.MathParserServerTechnicalException;

public abstract class AbstractMathParserServerController<REQUEST_TYPE, RESPONSE_TYPE> {
	
	@ExceptionHandler(value = { MathParserServerTechnicalException.class })
	public void handleTechnicalException(MathParserServerTechnicalException ex, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		getLogger().warn("Technical exception : " +  ex.getCode()  + " : " + ex.getErrorMessage() , ex);			
		response.setStatus(HttpServletResponse.SC_OK);
		getLogger().info(response.toString());
		pw.write("{\n" + //
				"\t\"code\": "+ ex.getCode() +",\n" + //
				"\t\"returnMessage\": \"" + ex.getErrorMessage() + "\"\n" + //
				"}");
		pw.flush();
	}
	
	@ExceptionHandler(value = { SQLException.class })
	public void handleTechnicalException(SQLException ex, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		getLogger().error("SQLException : " + ex.getMessage(), ex);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		getLogger().info(response.toString());
		pw.write("{\n" + //
				"\t\"code\": 999,\n" + //
				"\t\"returnMessage\": \"Database Error.\"\n" + //
				"}");
		pw.flush();
	}
	
	@ExceptionHandler(value = { RuntimeException.class, ValidationException.class, MethodArgumentNotValidException.class })
	public void handleTechnicalException(RuntimeException ex, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		getLogger().error("RuntimeException : " + ex.getMessage(), ex);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		getLogger().info(response.toString());
		pw.write("{\n" + //
				"\t\"code\": 999,\n" + //
				"\t\"returnMessage\": \"" + ex.getMessage() + "\"\n" + //
				"}");
		pw.flush();
	}
	
	@ExceptionHandler(value = { NotImplementedException.class })
	public void handleNotImplementedException(NotImplementedException ex, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		getLogger().error("NotImplementedException : " + ex.getMessage(), ex);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		getLogger().info(response.toString());
		pw.write("{\n" + //
				"\t\"code\": 999,\n" + //
				"\t\"returnMessage\": \"" + ex.getMessage() + "\"\n" + //
				"}");
		pw.flush();
	}
	
	
	@ExceptionHandler(value = { Exception.class })
	public void handleOtherExceptions(Exception ex, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		getLogger().error("General Exception : " + ex.getMessage(), ex);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		String s = "{\n" + //
				"\t\"code\": 999,\n" + //
				"\t\"returnMessage\": \"" + ex.getMessage() + "\"\n" + //
				"}";
		getLogger().info(s);
		pw.write(s);
		pw.flush();
	}
	/**
	 * Validates a request.
	 * 
	 * @param request Request to be validated.
	 * @return Matching response. Will contain the reason why validation failed.
	 */
	protected RESPONSE_TYPE validate(REQUEST_TYPE request) {
		
		Validator validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<REQUEST_TYPE>> ergebnis = validator.validate(request);
		if (!ergebnis.isEmpty()) {
			String message = "";
			for (ConstraintViolation<REQUEST_TYPE> constraintViolation : ergebnis) {
				message += constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage() + "  - ";
			}
			getLogger().debug(message.toString());
			RESPONSE_TYPE response = newResponse(99, message);
			getLogger().info(response.toString());
			return response;
		}
		return null;
	}
	
	private static ArrayList<String> requests = new ArrayList<String>();
	protected RESPONSE_TYPE checkForDuplicateRequest(REQUEST_TYPE request) {
		if (requests.contains(request.toString())) { 
			String message = "DuplicateRequest : " + request.toString();
			
			getLogger().debug(message.toString());
			RESPONSE_TYPE response = newResponse(99, message);
			getLogger().info(response.toString());
			return response;
		} else {
			requests.add(request.toString());
			getLogger().debug("Requests count :" + requests.size());
		}
		return null;
	}
	
	protected void removeDuplicateRequest(REQUEST_TYPE request) {
		getLogger().debug("Requests count prior deletion :" + requests.size());
		requests.remove(request.toString());
		getLogger().debug("Requests count after deletion:" + requests.size());
	}
	
	protected abstract Logger getLogger();
	
	protected abstract RESPONSE_TYPE newResponse(int code, String message);
	
}
