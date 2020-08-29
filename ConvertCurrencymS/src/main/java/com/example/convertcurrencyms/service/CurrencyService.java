package com.example.convertcurrencyms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.convertcurrencyms.model.CurrencyResponse;
import com.example.convertcurrencyms.model.InputRequest;

@Service
public class CurrencyService {
	
private static Logger log = LoggerFactory.getLogger(CurrencyService.class);
	
	
	
	
	public CurrencyResponse getConvertedCurrency(InputRequest request) {
		
		log.info("getConvertedCurrency");
		CurrencyResponse response=new CurrencyResponse();
		double conversionFactor = request.getConversionFactor();
		double amount = request.getAmount();
		System.out.println("conversion Factor is: " + conversionFactor);
		System.out.println("amount is: " + amount);
		double convertedAmount = amount * conversionFactor;
		System.out.println("converted Amount is: " + convertedAmount);
		convertedAmount = (double) Math.round(convertedAmount * 100d) / 100d;
		response.setAmount(amount);
		response.setConversionfactor(conversionFactor);
		response.setConvertedamount(convertedAmount);
		response.setStatusMessage("Converted Amount for country Code " + request.getCountryCode() +"is"+convertedAmount);		
		
		//response= curfactorms.getConversionFactor(request);		
		
		return response;
		
	}


}
