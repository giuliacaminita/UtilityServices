package com.jakala.UtilityServices.model;

import java.time.LocalDate;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Contract 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private static final String[] ALLOWED_TYPES = {"gas", "electricity", "gas and electricity"};
	private static final String[] ALLOWED_USER_TYPES = {"private", "business"};

	private String type;
	private String user;
	private LocalDate start;
	
	@ManyToOne
	@JoinColumn(name="customerID")
	@JsonBackReference
	private Customer customer;

	public int getId() {
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
	    if (Arrays.asList(ALLOWED_TYPES).contains(type)) 
	        this.type = type;
	    else 
	        throw new IllegalArgumentException("Tipo non valido per il contratto");
	    
	}

	public String getUser() 
	{
		return user;
	}

	public void setUser(String userType) 
	{
	    if (Arrays.asList(ALLOWED_USER_TYPES).contains(userType)) 
	    
	        this.user = userType;
	    else 
	        throw new IllegalArgumentException("Tipo di utente non valido per il contratto");
	    
	}

	public LocalDate getStart() 
	{
		return start;
	}

	public void setStart(LocalDate startDate)
	{
		this.start = startDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public boolean isValid()
	{
		return 	type!=null 					&& 
				!type.isBlank()				&&
				user!=null 					&& 
				!user.isBlank()				&&
				start!=null 				;
				
	}

}
