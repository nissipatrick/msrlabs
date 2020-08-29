package com.example.currencyfactorms.service;

import org.springframework.stereotype.Component;

import com.example.currencyfactorms.model.CurrencyResponse;
import com.example.currencyfactorms.model.InputRequest;


@Component
public class Convertcurrencymsfallback implements Convertcurrencyms{

	@Override
	public CurrencyResponse getComputedCurrency(InputRequest request) {
		
		return new CurrencyResponse("Calculation not done",null,0.0);
	}

}
