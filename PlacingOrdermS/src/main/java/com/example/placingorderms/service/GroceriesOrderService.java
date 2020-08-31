package com.example.placingorderms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.placingorderms.model.GroceriesOrderInventory;
import com.example.placingorderms.repo.GroceriesOrderRepo;

@Service
public class GroceriesOrderService {

	
	@Autowired
	GroceriesOrderRepo repo;
	
	

	public Integer placeOrder(String prdName, Double quantity) {
		GroceriesOrderInventory goi = new GroceriesOrderInventory();
		goi.setPrdName(prdName);
		goi.setQuantity(quantity);
		goi = repo.save(goi);
		return goi.getId();
	}

}
