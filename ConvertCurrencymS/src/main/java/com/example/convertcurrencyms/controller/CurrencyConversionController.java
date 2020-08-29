package com.example.convertcurrencyms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.convertcurrencyms.model.CurrencyResponse;
import com.example.convertcurrencyms.model.InputRequest;
import com.example.convertcurrencyms.service.CurrencyService;

@RestController
@RequestMapping("/convertcurrencyms")

public class CurrencyConversionController {

	@Autowired
	CurrencyService service;
	
	@RequestMapping(value = "/getcurrency", method = RequestMethod.POST)
	public CurrencyResponse getComputedCurrency(@RequestBody InputRequest request) {
		
			
		return service.getConvertedCurrency(request);
		 
	}

}
