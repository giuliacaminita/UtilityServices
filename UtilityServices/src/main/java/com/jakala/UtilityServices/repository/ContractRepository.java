package com.jakala.UtilityServices.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jakala.UtilityServices.model.Contract;

public interface ContractRepository extends CrudRepository<Contract,Integer>
{

	 @Query(
			 "SELECT c FROM Contract c WHERE " +
	         "(:startDate IS NULL OR c.start >= :startDate) " +
	         "AND (:userType IS NULL OR c.user = :userType) " +
	         "AND (:type IS NULL OR c.type = :type)"
	         )
	    List<Contract> findByFilters
	    (
	            @Param("startDate") LocalDate startDate,
	            @Param("userType") String userType,
	            @Param("type") String type
	    );
	
}
