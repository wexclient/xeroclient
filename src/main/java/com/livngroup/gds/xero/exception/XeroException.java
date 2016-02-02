package com.livngroup.gds.xero.exception;

import java.util.Optional;

import com.livngroup.gds.xero.response.XeroErrorResponse;

public interface XeroException {

	public String MESSAGE_DEFAULT = "Default error";
	
	public String MESSAGE_REMOTE_CALL_ERROR = "XERO has RMI exception. It could be caused by Server side and network";
	public String MESSAGE_INVALID_CREDENTIALS = "Invalid credentials";
	
 	public XeroErrorResponse getErrorResponse();
 	
 	public String getMessage();

	public static XeroErrorResponse defaultIfNull(XeroErrorResponse errorResponse) {
		return Optional.ofNullable(errorResponse).orElse(new XeroErrorResponse());
	}
	
}
