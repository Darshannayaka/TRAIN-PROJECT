package com.ingroinfo.trainProject.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class PassengerDetails extends EntityClass {
private String trainName;
private String schedule;
private String trainFrom;
private String trainTo;
private String departureDate;
private String trainFromTiming;
private String trainToTime;


private String firstName;
private String lastName;
private String aadharNumber;
private String email;
private String age;
private String gender;
private String phone;
private String ticketPrice;


@ManyToOne
private User user;


public String getTrainName() {
	return trainName;
}


public void setTrainName(String trainName) {
	this.trainName = trainName;
}


public String getSchedule() {
	return schedule;
}


public void setSchedule(String schedule) {
	this.schedule = schedule;
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


public String getTrainFromTiming() {
	return trainFromTiming;
}


public void setTrainFromTiming(String trainFromTiming) {
	this.trainFromTiming = trainFromTiming;
}


public String getTrainToTime() {
	return trainToTime;
}


public void setTrainToTime(String trainToTime) {
	this.trainToTime = trainToTime;
}


public String getFirstName() {
	return firstName;
}


public void setFirstName(String firstName) {
	this.firstName = firstName;
}


public String getLastName() {
	return lastName;
}


public void setLastName(String lastName) {
	this.lastName = lastName;
}


public String getAadharNumber() {
	return aadharNumber;
}


public void setAadharNumber(String aadharNumber) {
	this.aadharNumber = aadharNumber;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public String getAge() {
	return age;
}


public void setAge(String age) {
	this.age = age;
}


public String getGender() {
	return gender;
}


public void setGender(String gender) {
	this.gender = gender;
}


public String getPhone() {
	return phone;
}


public void setPhone(String phone) {
	this.phone = phone;
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
