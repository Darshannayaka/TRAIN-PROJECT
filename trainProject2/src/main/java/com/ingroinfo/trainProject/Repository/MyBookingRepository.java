package com.ingroinfo.trainProject.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ingroinfo.trainProject.entities.MyOrder;
import com.ingroinfo.trainProject.entities.PassengerDetails;

public interface MyBookingRepository extends JpaRepository<MyOrder, Long> {

	@Query("from MyOrder as p where p.user.id=:userId")
	public List<MyOrder> findMyOrderByUser(@Param("userId") long userId);
	

	
	public MyOrder findByOrderId(String OrderId);


//
//





}
