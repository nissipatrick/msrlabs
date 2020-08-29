package com.example.currencyfactorms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
	@SequenceGenerator(name="seq", initialValue=1, allocationSize=1000)
	public class Currency {
		
		@Id
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
		private Integer id;
		private String countryCode;
		private double conversionFactor;
		public Currency() {
			super();
		}
		
		
		public Currency(String countryCode, double conversionFactor) {
			super();
			this.countryCode = countryCode;
			this.conversionFactor = conversionFactor;
		}


		public Currency(Integer id, String countryCode, double conversionFactor) {
			super();
			this.id = id;
			this.countryCode = countryCode;
			this.conversionFactor = conversionFactor;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
		public double getConversionFactor() {
			return conversionFactor;
		}
		public void setConversionFactor(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}
		
}
