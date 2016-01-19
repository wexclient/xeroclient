package com.livngroup.gds.xero.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectifier.xeroclient.XeroApiException;
import com.connectifier.xeroclient.XeroClient;
import com.livngroup.gds.xero.exception.XeroAppException;
import com.livngroup.gds.xero.exception.XeroException;
import com.livngroup.gds.xero.exception.XeroRuntimeException;
import com.livngroup.gds.xero.response.XeroErrorResponse;


public abstract class XeroController {

	final protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected XeroClient xeroClient;
	
	protected void assertNotNull(String paramName, Object paramValue) throws XeroRuntimeException {
    	if (paramValue == null) {
    		throw exceptionForAssertion(paramName, paramValue, "must not be null");
    	}
    }
	
	protected void assertNotEmpty(String paramName, String paramValue) throws XeroRuntimeException {
    	if (StringUtils.isBlank(paramValue)) {
    		throw exceptionForAssertion(paramName, paramValue, "must not be empty");
    	}
    }
	
	protected void assertEmpty(String paramName, String paramValue) throws XeroRuntimeException {
    	if (StringUtils.isNotBlank(paramValue)) {
    		throw exceptionForAssertion(paramName, paramValue, "must be empty");
    	}
    }

	private XeroRuntimeException exceptionForAssertion(String paramName, Object paramValue, String messagePostfix) {
		return new XeroRuntimeException(new XeroErrorResponse(
				HttpStatus.NOT_ACCEPTABLE, 
				HttpStatus.NOT_ACCEPTABLE.toString() + "-" + paramName,
				"Parameter: " + paramName + "=[" + paramValue + "] " + messagePostfix));
	}
	
	@ExceptionHandler({XeroRuntimeException.class,XeroAppException.class})
	public @ResponseBody ResponseEntity<XeroErrorResponse> handleRuntimeException(XeroException exception) {
		logger.error("Exception cought: " + exception.getMessage(), exception);
	    return new ResponseEntity<XeroErrorResponse>(exception.getErrorResponse(), exception.getErrorResponse().getStatus());
	}

	@ExceptionHandler({XeroApiException.class})
	public @ResponseBody ResponseEntity<XeroErrorResponse> handleRuntimeException(XeroApiException exception) {
		logger.error("Exception cought: " + exception.getMessage(), exception);
		HttpStatus status = HttpStatus.valueOf(exception.getResponseCode());
	    return new ResponseEntity<XeroErrorResponse>(
	    		new XeroErrorResponse(status, exception.getMessage()), status);
	}

}
