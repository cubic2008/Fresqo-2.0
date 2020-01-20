package com.alyssa.Freshqo.domain;

import java.io.Serializable;

/**
 * Customer 
 * 
 * The class to hold all customer information
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class Customer implements Serializable {

	// VARIABLES
	private String name;
	private int numPeople;

	/**
	 * initializes the customer object, assigns values to name and numPeople
	 * 
	 * @param name      the name of the customer
	 * @param numPeople the number of people in the party
	 */
	public Customer(String name, int numPeople) {
		this.name = name.toUpperCase();
		this.numPeople = numPeople;
	}

	/**
	 * customer name getter
	 * 
	 * @return String the customer's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * customer name setter
	 * 
	 * @param name the new name of the customer to assign
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * numPoeple getter
	 * 
	 * @return int the number of people at the table
	 */
	public int getNumPeople() {
		return numPeople;
	}

	/**
	 * numPeople setter
	 * 
	 * @param numPeople the new value for the number of people in the table
	 */
	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}
}
