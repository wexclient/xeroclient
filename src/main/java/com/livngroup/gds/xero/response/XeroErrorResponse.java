package com.livngroup.gds.xero.response;

import org.springframework.http.HttpStatus;

public class XeroErrorResponse implements IXeroResponce {
	
	private String message;
	private HttpStatus status;
	private String code;
	private String description;
	
	public XeroErrorResponse() {
		this.message = FAILURE;
		this.status = HttpStatus.I_AM_A_TEAPOT;
		this.code = HttpStatus.I_AM_A_TEAPOT + "-0000";
	}
	
	public XeroErrorResponse(HttpStatus status, String code, String description) {
		this.message = FAILURE;
		this.status = status;
		this.code = code;
		this.description = description;
	}

	public XeroErrorResponse(HttpStatus status, String description) {
		this(status, status.value() + "-0000", description);
	}

	@Override
	public Boolean getOk() {
		return Boolean.FALSE;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getDecription() {
		return description;
	}

}
