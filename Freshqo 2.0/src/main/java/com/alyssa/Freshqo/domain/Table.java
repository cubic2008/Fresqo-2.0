package com.alyssa.Freshqo.domain;

import java.io.Serializable;

/**
 * AddFoodDialog
 * 
 * The dialog used to select menu items for a table's order
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class Table implements Serializable {

	// Variables
	private String tableName;
	private int numSeats;
	private Customer customer;
	private TableOrder currentOrder;
	private boolean canBeReserved;
	private Waiter currentAssignedWaiter;
	private boolean occupied;

	/**
	 * initializes all table's variables
	 * 
	 * @param tableName     table name
	 * @param numSeats      number of seats
	 * @param canBeReserved if table can be reserved
	 */
	public Table(String tableName, int numSeats, boolean canBeReserved) {
		currentOrder = new TableOrder(this);
		this.tableName = tableName.toUpperCase();
		this.numSeats = numSeats;
		this.canBeReserved = canBeReserved;
		this.customer = null;
//		this.currentOrder = null;
		this.currentAssignedWaiter = null;
	}

	/**
	 * isOccupied checks if table is occupied
	 * 
	 * @return true if table is occupied
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * setOccupied sets table as occupied or not
	 * 
	 * @param occupied if table is occupied
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	/**
	 * getTableName gets table name
	 * 
	 * @return table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * setTableName sets table name
	 * 
	 * @param tableNum table name
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * getNumSeats gets number of seats
	 * 
	 * @return number of seats
	 */
	public int getNumSeats() {
		return numSeats;
	}

	/**
	 * setNumSeats sets number of seats
	 * 
	 * @return number of seats
	 */
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
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
	 * getCurrentOrder get current order
	 * 
	 * @return current order
	 */
	public TableOrder getCurrentOrder() {
		return currentOrder;
	}

	/**
	 * setCurrentOrder sets current order
	 * 
	 * @param currentOrder the current order
	 */
	public void setCurrentOrder(TableOrder currentOrder) {
		this.currentOrder = currentOrder;
	}

	/**
	 * canBeReserved checks if table can be reserved
	 * 
	 * @return true if table can be reserved
	 */
	public boolean canBeReserved() {
		return canBeReserved;
	}

	/**
	 * setCanBeReserved sets table as reserved or not
	 * 
	 * @return true if table can be reserved
	 */
	public void setCanBeReserved(boolean canBeReserved) {
		this.canBeReserved = canBeReserved;
	}

	/**
	 * getCurrentAssignedWaiter gets currently assigned waiter
	 * 
	 * @return the currently assigned waiter to the table
	 */
	public Waiter getCurrentAssignedWaiter() {
		return currentAssignedWaiter;
	}

	/**
	 * setCurrentAssignedWaiter sets currently assigned waiter
	 * 
	 * @param currentAssignedWaiter the currently assigned waiter for the table
	 */
	public void setCurrentAssignedWaiter(Waiter currentAssignedWaiter) {
		this.currentAssignedWaiter = currentAssignedWaiter;
	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", numSeats=" + numSeats + ", customer=" + customer + ", currentOrder="
				+ currentOrder + ", canBeReserved=" + canBeReserved + ", currentAssignedWaiter=" + currentAssignedWaiter
				+ ", occupied=" + occupied + "]";
	}
	
}
