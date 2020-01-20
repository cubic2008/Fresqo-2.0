package com.alyssa.Freshqo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * TableOrder
 * 
 * The template for a table order
 * 
 * @author Alyssa Gao && Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */

public class TableOrder implements Serializable {

	// Variables
	private String transactionID;
	private Table table;
	private List<TableOrderItem> orderItems;
	private Waiter waiter;
	private String date = "";
	private double subtotal;
	private double total;
	private boolean paid;

	/**
	 * initializes all variables for table
	 * 
	 * @param table the table
	 */
	public TableOrder(Table table) {
		this.table = table;
		this.orderItems = new ArrayList<>();
		this.waiter = null;
		this.setSubtotal(0);
		this.setTotal(0);
		this.paid = false;
		this.transactionID = generateTransactionID();
	}

	/**
	 * generateTransactionID generates transaction id
	 * 
	 * @return transaction id
	 */
	public String generateTransactionID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * getTransactionID gets transaction ID
	 * 
	 * @return transaction ID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * setTransactionID sets transaction ID
	 * 
	 * @param transactionID the transaction ID
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * getDate gets the date
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * setDate sets the date
	 * 
	 * @param date the date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * getOrderItems gets list of order items
	 * 
	 * @return the list of order items for the table
	 */
	public List<TableOrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * setOrderItems sets the list of order items
	 * 
	 * @param orderItems the list of order items for the table
	 */
	public void setOrderItems(List<TableOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * getTable gets the table
	 * 
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * setTable sets the table
	 * 
	 * @param table the table
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * getWaiter gets the waiter
	 * 
	 * @return the waiter
	 */
	public Waiter getWaiter() {
		return waiter;
	}

	/**
	 * setWaiter sets the waiter
	 * 
	 * @param waiter the waiter
	 */
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	/**
	 * getSubtotal gets subtotal
	 * 
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * setSubtotal sets subtotal
	 * 
	 * @param subtotal the subtotal
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * getTotal gets total
	 * 
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * setTotal sets total
	 * 
	 * @param total the total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * hasPaid checks if customer has paid
	 * 
	 * @return true if customer has paid
	 */
	public boolean hasPaid() {
		return paid;
	}

	/**
	 * setPaid sets paid as true if customer has paid
	 * 
	 * @param paid if customer has paid or not
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
}
