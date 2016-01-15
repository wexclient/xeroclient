package com.livngroup.gds.xero.response;

import org.springframework.http.HttpStatus;

public class XeroCallResponse<T> implements IXeroResponce {
	
	private Boolean ok;
	private String message;
	private HttpStatus status;
	private T result;
	
	public XeroCallResponse(Boolean ok, String message, HttpStatus status, T result) {
		this.ok = ok;
		this.message = message;
		this.status = status;
		this.result = result;
	}
	
	public static XeroCallResponse<Object> forFailure() {
		return new XeroCallResponse<Object>(false, FAILURE, HttpStatus.I_AM_A_TEAPOT, null);
	}
	
	public static XeroCallResponse<Object> forSuccess() {
		return new XeroCallResponse<Object>(true, SUCCESS, HttpStatus.OK, null);
	}
	
	public static <T> XeroCallResponse<T> forSuccess(T result) {
		return new XeroCallResponse<T>(true, SUCCESS, HttpStatus.OK, result);
	}

	@Override
	public Boolean getOk() {
		return ok;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}

	public T getResult() {
		return result;
	}

}
