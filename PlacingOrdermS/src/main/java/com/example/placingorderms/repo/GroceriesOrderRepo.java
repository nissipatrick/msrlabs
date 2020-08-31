package com.example.placingorderms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.example.placingorderms.model.GroceriesOrderInventory;

@Component
public interface GroceriesOrderRepo extends JpaRepository<GroceriesOrderInventory, Integer>{

}
