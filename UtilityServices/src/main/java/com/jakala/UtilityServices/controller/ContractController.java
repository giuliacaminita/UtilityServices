package com.jakala.UtilityServices.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jakala.UtilityServices.model.Contract;
import com.jakala.UtilityServices.model.Customer;
import com.jakala.UtilityServices.repository.ContractRepository;
import com.jakala.UtilityServices.repository.CustomerRepository;

@RestController
@RequestMapping("utilityservice")
public class ContractController 
{
	@Autowired
	CustomerRepository repo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@PostMapping("/addcontract/{id}")
	public ResponseEntity<Contract> addContract(@PathVariable("id") int id, @RequestBody Contract c) 
	{
	    Optional<Customer> customerOptional = repo.findById(id);

	    if (customerOptional.isPresent()) 
	    {
	        Customer customer = customerOptional.get();
	        c.setCustomer(customer);

	        if (!c.isValid()) 
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        

	        customer.addContract(c);
	        contractRepo.save(c); 

	        return new ResponseEntity<>(c, HttpStatus.OK);
	    } 
	    else
	    	
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);	    
	}
	
	@GetMapping("/{name}/contracts")
	public ResponseEntity<List<Contract>> getContracts(@PathVariable("name")String name)
	{
		Customer c= repo.findByName(name);
		
		List<Contract> contracts= c.getContracts(); 
		
		if (contracts.isEmpty())
			 
	        return ResponseEntity.noContent().build();
		else 
	        return ResponseEntity.ok().body(contracts);
		
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<List<Contract>> searchContracts
	(
	    @RequestParam(name = "customerName", required = false) String customerName,
	    @RequestParam(name = "startDate", required = false) LocalDate startDate,
	    @RequestParam(name = "userType", required = false) String userType,
	    @RequestParam(name = "type", required = false) String type
	) 
	{
	    Set<Contract> contractsSet = new HashSet<>(); 

	    if (customerName != null) 
	    {
	        Customer customer = repo.findByName(customerName);
	        if (customer != null) 
	        {
	            List<Contract> contractsByName = customer.getContracts();
	            contractsSet.addAll(contractsByName); 
	        }
	    }

	    List<Contract> filteredContracts = contractRepo.findByFilters(startDate, userType, type);
	    contractsSet.addAll(filteredContracts); 

	    List<Contract> uniqueContracts = new ArrayList<>(contractsSet); 

	    if (uniqueContracts.isEmpty()) 
	        return ResponseEntity.noContent().build();
	    else 
	        return ResponseEntity.ok().body(uniqueContracts);
	    
	}
}