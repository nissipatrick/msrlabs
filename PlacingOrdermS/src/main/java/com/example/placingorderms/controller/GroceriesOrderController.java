package com.example.placingorderms.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.placingorderms.model.GroceriesOrderInventory;
import com.example.placingorderms.repo.GroceriesOrderRepo;
import com.example.placingorderms.service.GroceriesOrderService;

@RestController
@CrossOrigin(origins = "*")
public class GroceriesOrderController {

	
	@Autowired
	GroceriesOrderService service;
	


	@Autowired
	GroceriesOrderRepo repo;
	
	
	@GetMapping(path="/placingorderms/add/{prdName}/{quantity}", produces = "application/json")
	public Integer addnewOrder(@PathVariable String prdName, @PathVariable Double quantity) {
		Integer id=service.placeOrder(prdName,quantity);
		return id;
	}
	

	@GetMapping(path="/placingorderms/add/{prdName}/{quantity}/{userToken}", produces = "application/json")
	public String placeorder(@PathVariable String prdName, @PathVariable Double quantity,@PathVariable String userToken) throws SerialException, SQLException {
		if(userToken != null) {
			System.out.println("userToken"+userToken);
			String[] strArr = userToken.split(":TTL:");
			String dateTokenCreated = strArr[1];
			if(dateTokenCreated != null) {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				Date date1;
				try {

					date1 = format.parse(dateTokenCreated);
					Date date2 = format.parse(LocalTime.now().toString());
					long difference = date2.getTime() - date1.getTime();
					if(difference > 60000) {
						return "Please click on Navigate to Login Page.<button onClick=\"logout()\">Navigate to Login Page</button>";
					}

					System.out.println("difference="+difference);

				} catch (ParseException e) {
					e.printStackTrace();
				}

			}

		}
		GroceriesOrderInventory goi = new GroceriesOrderInventory();
		goi.setPrdName(prdName);
		goi.setQuantity(quantity);
		goi.setUserToken(new javax.sql.rowset.serial.SerialClob(userToken.toCharArray()));
		goi = repo.save(goi);
		return "Your order has been placed successfully with Order Id :" + goi.getId();
	}
	
	
	@GetMapping(path="/GroceriesOrderInventory/orders", produces = "application/json")
	public List<GroceriesOrderInventory> getAllOrders() {
		return repo.findAll();
	}
	


	@PostMapping(path="/placingorderms/add")
	public HttpStatus addConversionFactorPost(@RequestBody GroceriesOrderInventory goi) {
		repo.save(goi);
		return HttpStatus.CREATED;
	}

}
