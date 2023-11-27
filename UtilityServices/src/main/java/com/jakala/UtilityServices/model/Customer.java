package com.jakala.UtilityServices.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@OneToMany(mappedBy="customer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Contract> contracts;

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public List<Contract> getContracts() 
	{
		return contracts;
	}

	public void setContracts(List<Contract> contracts) 
	{
		this.contracts = contracts;
	}
	
	public void addContract(Contract contract)
	{
		contract.setCustomer(this);
		contracts.add(contract);
	}
}
