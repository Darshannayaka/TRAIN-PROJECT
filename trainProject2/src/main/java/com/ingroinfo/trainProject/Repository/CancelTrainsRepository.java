package com.ingroinfo.trainProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ingroinfo.trainProject.entities.CancelTrains;

public interface CancelTrainsRepository extends JpaRepository<CancelTrains, Long> {

	
	@Query("from CancelTrains as p where p.user.id=:userId")
	public List<CancelTrains> findCancelTrainsBooking(@Param("userId") long userId);
}
