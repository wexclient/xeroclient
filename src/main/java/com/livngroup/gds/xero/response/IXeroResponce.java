package com.livngroup.gds.xero.response;

import org.springframework.http.HttpStatus;

public interface IXeroResponce {

	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";

	public Boolean getOk();
	public String getMessage();
	public HttpStatus getStatus();

}
