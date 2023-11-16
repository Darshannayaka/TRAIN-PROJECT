package com.ingroinfo.trainProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ingroinfo.trainProject.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.email =:email")
	  
	public User getUserByUserEmail(@Param("email") String email);


	
	boolean existsByEmail(String email);

}
