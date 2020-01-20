package com.alyssa.Freshqo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Waiter
 * 
 * The template for a waiter
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class Waiter extends Employee implements Serializable {
	
	//Variables	
	private List<Table> assignedTables;

	/**
	 * 
	 * @param name the waiter's name
	 * @param pay the waiter's pay rate
	 * @param userID the waiter's user ID
	 * @param password the waiter's password	
	 * @param dateHired the waiter's hire date
	 * @param email the waiter's email
	 * @param SINNumber the waiter's SIN number
	 * @param position the waiter's position
	 */
	public Waiter(String name, double pay, String userID, String password, String dateHired, String email, String SINNumber, String position) {
		super(name, pay, userID, password, dateHired, email, SINNumber, position );
		assignedTables = new ArrayList<>();
	}

	/**
	 * gets the waiter's assigned tables
	 * 
	 * @return List<Table> the list of assigned tables
	 */
	public List<Table> getAssignedTables() {
		return assignedTables;
	}

	/**
	 * sets the assigned tables
	 * 
	 * @param assignedTables the list of assigned tables
	 */
	public void setAssignedTables(List<Table> assignedTables) {
		this.assignedTables = assignedTables;
	}
}
