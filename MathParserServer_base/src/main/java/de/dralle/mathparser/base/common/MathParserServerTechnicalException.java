package de.dralle.mathparser.base.common;

public class MathParserServerTechnicalException extends RuntimeException {

	private Integer code;
	private String errorMessage;

	public MathParserServerTechnicalException(Throwable cause, Integer code, String errorMessage) {
		super(errorMessage,cause);
		this.code = code;
		this.errorMessage = errorMessage;
	}
	
	public MathParserServerTechnicalException(Integer code, String errorMessage) {
		super(errorMessage);
		this.code = code;
		this.errorMessage = errorMessage;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
