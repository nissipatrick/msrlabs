package com.example.convertcurrencyms.model;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class CurrencyResponse {

	
private static final long serialVersionUID = 1L;
	
	private double convertedamount;
	private String statusMessage;
	private String countryCode;
	private double amount;
	private double conversionfactor;
	private String errorMessage;
	
	public CurrencyResponse() {
		super();
	}
	
	public CurrencyResponse(String statusMessage, String errorMessage, double amount) {
		super();
		this.statusMessage = statusMessage;
		this.setErrorMessage(errorMessage);
		this.convertedamount = amount;
	}
	
	
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	

	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public double getConvertedamount() {
		return convertedamount;
	}

	public void setConvertedamount(double convertedamount) {
		this.convertedamount = convertedamount;
	}

	public double getConversionfactor() {
		return conversionfactor;
	}

	public void setConversionfactor(double conversionfactor) {
		this.conversionfactor = conversionfactor;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
