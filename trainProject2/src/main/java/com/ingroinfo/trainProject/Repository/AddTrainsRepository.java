package com.ingroinfo.trainProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.ingroinfo.trainProject.entities.AddTrains;

public interface AddTrainsRepository extends JpaRepository<AddTrains, Long> {
	
	@Query("from AddTrains where trainFrom=:trainFrom and trainTo=:trainTo and departureDate=:departureDate")
	List<AddTrains> findTrains(@Param("trainFrom")String trainFrom,@Param("trainTo") String trainTo,@Param("departureDate") String departureDate);

//
	boolean existsByMessage(String message);
//

	
	
//	boolean existsByEmail(String email);
	/*
	 * @Query("from AddTrains where trainFrom=:trainFrom and trainTo=:train")
	 * List<AddTrains> findTrains(@RequestParam("trainFrom") String trainFrom,
	 * String trainTo, String departureDate,@)
	 */

}
