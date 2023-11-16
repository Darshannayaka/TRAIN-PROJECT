package com.ingroinfo.trainProject.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ingroinfo.trainProject.entities.MyOrder;
import com.ingroinfo.trainProject.entities.PassengerDetails;

public interface PassengerDetailsRepository extends JpaRepository<PassengerDetails, Long> {

	@Query("from PassengerDetails as p where p.user.id=:userId")
	public List<PassengerDetails> findPassengerDetailsByUser(@Param("userId") long userId);
	
	@Query("SELECT b from PassengerDetails b where b.user.id=:userId AND b.id=:id")
	public List<PassengerDetails> findPassengerDetailsByUsers(@Param("userId") long userId,@Param("id") long id);	
	
	 PassengerDetails findById(long id); 
	 
}
