package com.example.currencyfactorms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.currencyfactorms.model.Currency;
import com.example.currencyfactorms.model.CurrencyResponse;
import com.example.currencyfactorms.model.InputRequest;
import com.example.currencyfactorms.model.StatusResponse;
import com.example.currencyfactorms.repo.CurrencyRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class CurrencyFactorService {

	@Autowired
	CurrencyRepository repo;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Autowired
	LoadBalancerClient loadBalancerClient;
	
	

	@Autowired
	private RestTemplate lbrestTemplate;
	
	//feign
	@Autowired
	Convertcurrencyms convertcurrencyms;
	
	public List<Currency> getAllConversionFactors(){
		
		 return repo.findAll();		
		
	}
	
	public List<Currency> getConversionfactorByCountry(String CountryCode) {
		
		System.out.println("Country code" + CountryCode);
		return repo.findByCountryCode(CountryCode);		
		
		
	}
	
	public CurrencyResponse fetchCurrencyFactor(InputRequest request) {
		
		CurrencyResponse response = new CurrencyResponse();
		
		List<Currency> currencylist =null;
		currencylist=repo.findByCountryCode(request.getCountryCode());
		
		if(!currencylist.isEmpty())
		for(Currency c:currencylist) {
			
			double conversionFactor = c.getConversionFactor();
			double amount = request.getAmount();
			System.out.println("conversion Factor is: " + conversionFactor);
			System.out.println("amount is: " + amount);
			double convertedAmount = amount * conversionFactor;
			System.out.println("converted Amount is: " + convertedAmount);
			convertedAmount = (double) Math.round(convertedAmount * 100d) / 100d;
			response.setAmount(amount);
			response.setConversionfactor(conversionFactor);
			response.setConvertedamount(convertedAmount);
			response.setStatusMessage("Conversion Factor found");			
		}
		else {
			response.setErrorMessage("Conversion Factor not Found!!");
		}
		
		return response;
	}
	
	
	@Transactional
	public StatusResponse addConversionFactor(InputRequest request) {
		
		StatusResponse response = new StatusResponse();
		
		List<Currency> existingList = repo.findByCountryCode(request.getCountryCode());	
		
		if(!existingList.isEmpty())
		{
			response.setErrorMessage("Country code present already");
		}
		else {
			System.out.println("country code - Conversion factor " + request.getCountryCode() + "--" + request.getConversionFactor());
			Currency p = new Currency(request.getCountryCode(), request.getConversionFactor());
			
			System.out.println("Id value" + p.getId());
			repo.save(p);
			response.setStatusMessage("Country Code - Conversion factor added");
		}
		
		return response;
		
	}
	
	
	  @Transactional 
	  public StatusResponse updateCurrencyFactor(InputRequest request)
	  {
	  
		  StatusResponse response = new StatusResponse();
	  
		  int status = repo.updateConversionFactor(request.getConversionFactor(),request.getCountryCode()); 
		  System.out.println("update status " + status);
		  response.setStatusMessage("Conversion Factor updated");
	  
		  return response; 
	  }
	  
	  
	  //Discovery client
	  public CurrencyResponse fetchCurrencyFactorsd(InputRequest req) {
		  InputRequest request= createInputrequest(req);
		  List<ServiceInstance> serviceInstances = discoveryClient.getInstances("convertcurrencyms");
		  if (serviceInstances != null && serviceInstances.size() > 0) {
			  for (ServiceInstance instance : serviceInstances) {
					System.out.println(instance.getHost() + ":" + instance.getPort());
				}
			  ServiceInstance instance = serviceInstances.get(0);
			  String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/convertcurrencyms/getcurrency";
			  System.out.println("Calling URL:" + url);
			  RestTemplate restTemplate = new RestTemplate();
			  HttpEntity<InputRequest> dRequest = new HttpEntity<InputRequest>(request);
			  CurrencyResponse dResponse = restTemplate.postForEntity(url, dRequest, CurrencyResponse.class).getBody();
			  return dResponse;
		  }
		  return null;
		}
	 
	  
	  //loadbalancer
	  public CurrencyResponse fetchCurrencyFactorloadbalancer(InputRequest req) {
		  	InputRequest request= createInputrequest(req);
		  	ServiceInstance serviceInstances = loadBalancerClient.choose("convertcurrencyms");
		  	if (serviceInstances != null) {
		  		String url = "http://" + serviceInstances.getHost() + ":" + serviceInstances.getPort() + "/convertcurrencyms/getcurrency";
		  		System.out.println("LB Calling URL:" + url);
		  		RestTemplate restTemplate = new RestTemplate();

				HttpEntity<InputRequest> dRequest = new HttpEntity<InputRequest>(request);
				CurrencyResponse response = restTemplate.postForEntity(url, dRequest, CurrencyResponse.class).getBody();
				return response;
		  	}
		  	return null;
	 }
	  
	  
	  //loadbalancer rest template
	  public CurrencyResponse fetchCurrencyFactorloadbalancerrt(InputRequest req) {
		  	InputRequest request= createInputrequest(req);
		  	HttpEntity<InputRequest> dRequest = new HttpEntity<InputRequest>(request);
		  	CurrencyResponse response = lbrestTemplate.postForEntity("http://convertcurrencyms/getcurrency", dRequest, CurrencyResponse.class).getBody();
				return response;
		  	
	 }
	  
	  @HystrixCommand(fallbackMethod = "fetchCurrencyfallback")
	  public CurrencyResponse fetchCurrencyFactorhystrix(InputRequest req) {
		  	InputRequest request= createInputrequest(req);
		  	HttpEntity<InputRequest> dRequest = new HttpEntity<InputRequest>(request);
		  	CurrencyResponse response = lbrestTemplate.postForEntity("http://convertcurrencyms/getcurrency", dRequest, CurrencyResponse.class).getBody();
				return response;
		  	
	 }
	  
	
	   public CurrencyResponse fetchCurrencyfallback(InputRequest req) {
			CurrencyResponse curresponse= new CurrencyResponse("Calculation not done",null,0.0);
			return curresponse;
		}


	  
	  //feign
	  public CurrencyResponse fetchCurrencyFactorfeign(InputRequest req) {
		  	InputRequest request= createInputrequest(req);
			CurrencyResponse response = convertcurrencyms.getComputedCurrency(request);
			return response;
	 }
				
		

	private InputRequest createInputrequest(InputRequest req) {
		List<Currency> currencylist =null;
		currencylist=repo.findByCountryCode(req.getCountryCode());
		if(!currencylist.isEmpty()) {
			for(Currency c:currencylist) {
				
				double conversionFactor = c.getConversionFactor();
				req.setConversionFactor(conversionFactor);
			}
		}
		return req;
	}  
	
	
	 
	 
}
