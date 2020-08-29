package com.example.currencyfactorms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.currencyfactorms.exception.GenericException;
import com.example.currencyfactorms.model.Currency;
import com.example.currencyfactorms.model.CurrencyResponse;
import com.example.currencyfactorms.model.InputRequest;
import com.example.currencyfactorms.model.StatusResponse;
import com.example.currencyfactorms.service.CurrencyFactorService;

@RestController
@RefreshScope
@RequestMapping("/currencyFactormS")
public class CurrencyFactorController {
	
	@Autowired
	CurrencyFactorService service;
	
	@GetMapping("/conversionfactor")
	public List<Currency> getAllConversionfactors(){
		
		return service.getAllConversionFactors();
		
	}
	
	@GetMapping("/conversionfactor/{CountryCode}")
	public ResponseEntity<List<Currency>> getconversionfactors(@PathVariable String CountryCode) {
		List<Currency> c = service.getConversionfactorByCountry(CountryCode);
		if (!c.isEmpty()) {
			return new ResponseEntity<List<Currency>>(c,HttpStatus.FOUND);
		} else {
			throw new GenericException("Country code not found");
		}
	}
	
	@RequestMapping(value="/addconversionfactor",method = RequestMethod.POST)
	public StatusResponse addConversionFactor(@RequestBody InputRequest request) {
		
		StatusResponse response=null;
		response = service.addConversionFactor(request);		
		return response;
			
		
	}
	
	
	  @RequestMapping(value="/updateconversionfactor",method = RequestMethod.POST)
	  public StatusResponse updateConversionFactor(@RequestBody InputRequest request)
	  {
	  
		  StatusResponse response=null; 
		  response = service.updateCurrencyFactor(request); 
		  return response;
	  
	  
	  }
	  
	  @RequestMapping(value="/fetchconversionfactor",method = RequestMethod.POST)
	  public CurrencyResponse fetchConversionFactor(@RequestBody InputRequest request)
	  {
	  
		  CurrencyResponse response=null; 
		  response = service.fetchCurrencyFactor(request); 
		  return response;
	  }
	  
	  @RequestMapping(value="/fetchconversionfactorsd",method = RequestMethod.POST)
	  public CurrencyResponse fetchConversionFactorsd(@RequestBody InputRequest request)
	  {
	  
		  CurrencyResponse response=null; 
		  response = service.fetchCurrencyFactorsd(request); 
		  return response;
	  }
	  
	  @RequestMapping(value="/conversionfactorfeign",method = RequestMethod.POST)
	  public CurrencyResponse conversionfactorfeign(@RequestBody InputRequest request)
	  {
	  
		  CurrencyResponse response=null; 
		  response = service.fetchCurrencyFactorfeign(request); 
		  return response;
	  }
	  
	  
	  @RequestMapping(value="/conversionfactorlb",method = RequestMethod.POST)
	  public CurrencyResponse conversionfactorlb(@RequestBody InputRequest request)
	  {
	  
		  CurrencyResponse response=null; 
		  response = service.fetchCurrencyFactorloadbalancer(request); 
		  return response;
	  }
	  
	  @RequestMapping(value="/conversionfactorlbrt",method = RequestMethod.POST)
	  public CurrencyResponse conversionfactorlbrt(@RequestBody InputRequest request)
	  {
	  
		  CurrencyResponse response=null; 
		  response = service.fetchCurrencyFactorloadbalancerrt(request); 
		  return response;
	  }
	  
	  @RequestMapping(value="/conversionfactorhystrix",method = RequestMethod.POST)
	  public CurrencyResponse conversionfactorhystrix(@RequestBody InputRequest request)
	  {
	  
		  CurrencyResponse response=null; 
		  response = service.fetchCurrencyFactorhystrix(request); 
		  return response;
	  }
	  
}

