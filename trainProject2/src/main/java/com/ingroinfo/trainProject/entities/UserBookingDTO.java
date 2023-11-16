package com.ingroinfo.trainProject.entities;

public class UserBookingDTO {

	private PassengerDetails passengerDetails;
	private MyOrder myBookTicket;
	


	public PassengerDetails getPassengerDetails() {
		return passengerDetails;
	}


	public void setPassengerDetails(PassengerDetails passengerDetails) {
		this.passengerDetails = passengerDetails;
	}


	public MyOrder getMyBookTicket() {
		return myBookTicket;
	}


	public void setMyBookTicket(MyOrder myBookTicket) {
		this.myBookTicket = myBookTicket;
	}
	
	
	
}
