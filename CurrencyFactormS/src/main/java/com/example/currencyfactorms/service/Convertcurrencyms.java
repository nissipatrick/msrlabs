package com.example.currencyfactorms.service;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.currencyfactorms.model.CurrencyResponse;
import com.example.currencyfactorms.model.InputRequest;


@FeignClient(name="convertcurrencyms",  fallback=Convertcurrencymsfallback.class)

public interface Convertcurrencyms {
	
	@RequestMapping(value = "/getcurrency", method = RequestMethod.POST)
	public CurrencyResponse getComputedCurrency(@RequestBody InputRequest request);

}
