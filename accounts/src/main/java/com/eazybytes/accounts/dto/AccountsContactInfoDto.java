package com.eazybytes.accounts.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConfigurationProperties(prefix = "accounts")
//public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
//
//}

// To load the latest properties, declare properties in class as instance variable
@ConfigurationProperties(prefix = "accounts")
public class AccountsContactInfoDto { 
	
	private String message; 
	private Map<String, String> contactDetails;
	private List<String> onCallSupport;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(Map<String, String> contactDetails) {
		this.contactDetails = contactDetails;
	}
	public List<String> getOnCallSupport() {
		return onCallSupport;
	}
	public void setOnCallSupport(List<String> onCallSupport) {
		this.onCallSupport = onCallSupport;
	}
	
	

}
