package com.example.currencyfactorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.example.currencyfactorms.model.Currency;

@Component
public interface CurrencyRepository extends JpaRepository<Currency, Integer>{
	
	   List<Currency> findByCountryCode(String countryCode);
	
	
	  @Modifying(clearAutomatically = true)	  
	  @Query("update Currency c set c.conversionFactor=?1 where c.countryCode=?2") 
	  int updateConversionFactor(double conversionFactor, String countryCode);

}
