package de.dralle.mathparser.base.common;

public class MathParserServerException extends Exception {

	private Integer code;
	private String errorMessage;

	public MathParserServerException(Throwable cause, Integer code, String errorMessage) {
		super(errorMessage,cause);
		this.code = code;
		this.errorMessage = errorMessage;
	}
	
	public MathParserServerException(Integer code, String errorMessage) {
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
