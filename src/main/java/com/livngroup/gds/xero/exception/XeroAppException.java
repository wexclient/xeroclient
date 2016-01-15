package com.livngroup.gds.xero.exception;

import com.livngroup.gds.xero.response.XeroErrorResponse;

public class XeroAppException extends Exception implements XeroException {

	@Override
	public XeroErrorResponse getErrorResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}
