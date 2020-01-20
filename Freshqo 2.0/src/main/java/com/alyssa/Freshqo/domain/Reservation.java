package com.alyssa.Freshqo.domain;

import java.io.Serializable;

/**
 * Reservation 
 * 
 * The template to make a reservation
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class Reservation implements Serializable {

	// Variables
	private Table table;
	private Customer customer;
	private ReservationDateTime reservationDateTimePeriod;
	private boolean claimed;

	/**
	 * initializes table, customer, reservation date time period and sets claimed as
	 * false
	 * 
	 * @param table                     the table
	 * @param name                      customer's name
	 * @param numPeople                 the number of people
	 * @param reservationDateTimePeriod the rservation date and time
	 */
	public Reservation(Table table, String name, int numPeople, ReservationDateTime reservationDateTimePeriod) {
		this.table = table;
		this.customer = new Customer(name, numPeople);
		this.reservationDateTimePeriod = reservationDateTimePeriod;
		claimed = false;
	}

	/**
	 * getTable gets table
	 * 
	 * @return table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * setTable sets table
	 * 
	 * @param table the table
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * getCustomer gets customer
	 * 
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * setCustomer sets customer
	 * 
	 * @param customer the customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * getReservationDateTime gets reservation date and time
	 * 
	 * @return reservation date and time
	 */
	public ReservationDateTime getReservationDateTime() {
		return reservationDateTimePeriod;
	}

	/**
	 * setReservationDateTime sets reservation date and time
	 * 
	 * @param reservationDateTimePeriod reservation date and time
	 */
	public void setReservationDateTime(ReservationDateTime reservationDateTimePeriod) {
		this.reservationDateTimePeriod = reservationDateTimePeriod;
	}

	/**
	 * isClaimed check if table is claimed
	 * 
	 * @return true if table is claimed
	 */
	public boolean isClaimed() {
		return claimed;
	}

	/**
	 * setClaimed sets table as claimed
	 * 
	 * @param claimed if table is claimed or not
	 */
	public void setClaimed(boolean claimed) {
		this.claimed = claimed;
	}
}
