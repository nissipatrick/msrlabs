package com.example.convertcurrencyms.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class CResponse {

private static final long serialVersionUID = 1L;
	
	private String statusMessage;
	private String errorMessage;
	private double amount;	
	
	
	
	public CResponse() {
		super();
	}
	
	public CResponse(String statusMessage, String errorMessage, double amount) {
		super();
		this.statusMessage = statusMessage;
		this.errorMessage = errorMessage;
		this.amount = amount;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
