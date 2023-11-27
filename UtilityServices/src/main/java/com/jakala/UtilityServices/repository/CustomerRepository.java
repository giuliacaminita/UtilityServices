package com.jakala.UtilityServices.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.jakala.UtilityServices.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer>
{

	Customer findByName(String name);

}
