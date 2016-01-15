package com.livngroup.gds.xero.exception;

import org.apache.commons.lang3.StringUtils;

import com.livngroup.gds.xero.response.XeroErrorResponse;

public class XeroRuntimeException extends RuntimeException implements XeroException {

	private static final long serialVersionUID = 1L;

	private XeroErrorResponse errorResponse;

	public XeroRuntimeException(Throwable cause, XeroErrorResponse errorResponse) {
		super(XeroException.defaultIfNull(errorResponse).getMessage(), cause);
		this.errorResponse = XeroException.defaultIfNull(errorResponse);
	}

	public XeroRuntimeException(XeroErrorResponse errorResponse) {
		super(XeroException.defaultIfNull(errorResponse).getMessage());
		this.errorResponse = XeroException.defaultIfNull(errorResponse);
	}

	@Override
	public XeroErrorResponse getErrorResponse() {
		return errorResponse;
	}

	@Override
	public String getMessage() {
		return errorResponse.toString() + ": " +  StringUtils.trimToEmpty(super.getMessage());
	}

}
