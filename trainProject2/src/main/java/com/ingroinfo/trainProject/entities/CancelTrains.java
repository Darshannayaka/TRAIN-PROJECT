package com.ingroinfo.trainProject.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CancelBooking")
public class CancelTrains extends EntityClass{

	private String firstName;
	private String email;
	private String phone;

	private String trainName;
	private String trainFrom;
	private String trainTo;
	private String departureDate;
	private String ticketPrice;
	
	@ManyToOne
	private User user;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getTrainFrom() {
		return trainFrom;
	}
	public void setTrainFrom(String trainFrom) {
		this.trainFrom = trainFrom;
	}
	public String getTrainTo() {
		return trainTo;
	}
	public void setTrainTo(String trainTo) {
		this.trainTo = trainTo;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
}
