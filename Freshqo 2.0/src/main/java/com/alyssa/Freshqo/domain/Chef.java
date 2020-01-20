package com.alyssa.Freshqo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Chef 
 * 
 * A class to represent the chef-type employee
 * 
 * @author Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */
public class Chef extends Employee implements Serializable {

	// VARIABLES
	private TableOrderItem currentOrderItem;
	private List<TableOrderItem> completedOrders;

	/**
	 * initializes the Chef, adds all required variables values
	 * 
	 * @param name      the chef's name
	 * @param pay       the chef's pay
	 * @param userID    the chef's username to login
	 * @param password  the chef's password to login
	 * @param dateHired the date the chef was hired
	 * @param email     the chef's email
	 * @param SINNumber the chef's Social Insurance Number
	 * @param position  the chef's specific position
	 */
	public Chef(String name, double pay, String userID, String password, String dateHired, String email,
			String SINNumber, String position) {
		super(name, pay, userID, password, dateHired, email, SINNumber, position);
		currentOrderItem = null;
		completedOrders = new ArrayList<>();
	}

	/**
	 * get the chef's current order being cooked
	 * 
	 * @return TableOrderItem the order being cooked
	 */
	public TableOrderItem getCurrentOrderItem() {
		return currentOrderItem;
	}

	/**
	 * set the chef's current order to be cooked
	 * 
	 * @param currentOrderItem the new order the chef is currently making
	 */
	public void setCurrentOrderItem(TableOrderItem currentOrderItem) {
		this.currentOrderItem = currentOrderItem;
	}

	/**
	 * completed orders getter
	 * 
	 * @return TableOrderItem array list of all TableOrderItems completed
	 */
	public List<TableOrderItem> getCompletedOrders() {
		return completedOrders;
	}

	/**
	 * completed orders setters
	 * 
	 * @param completedOrders
	 */
	public void setCompletedOrders(List<TableOrderItem> completedOrders) {
		this.completedOrders = completedOrders;
	}

}