package com.alyssa.Freshqo.domain;

import java.io.Serializable;

/**
 * TableOrderItem
 * 
 * The dialog used to select menu items for a table's order
 * 
 * @author Alyssa Gao && Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */

public class TableOrderItem implements Serializable {

	// Variables
	private MenuItem menuItem;
	private int quantity;
	private Waiter servedByWaiter;
	private Chef preparedByChef;
	private TableOrder tableOrder;
	private boolean fired;
	private boolean servedToCustomer;

	/**
	 * initializes most variables
	 * 
	 * @param menuItem       the menu itme
	 * @param servedByWaiter the waiter
	 * @param tableOrder     the table order
	 */
	public TableOrderItem(MenuItem menuItem, Waiter servedByWaiter, TableOrder tableOrder) {
		this.menuItem = menuItem;
		this.servedByWaiter = servedByWaiter;
		this.tableOrder = tableOrder;
		this.quantity = 0;
		fired = false;
		servedToCustomer = false;
	}

	/**
	 * getMenuItem gets menu item
	 * 
	 * @return the menu item
	 */
	public MenuItem getMenuItem() {
		return menuItem;
	}

	/**
	 * setMenuItem sets menu item
	 * 
	 * @param menuItem the menu item
	 */
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	/**
	 * getQuantity gets quantity
	 * 
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * setQuantity sets quantity
	 * 
	 * @param sets the quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * getServedByWaiter gets waiter serving table
	 * 
	 * @return the waiter
	 */
	public Waiter getServedByWaiter() {
		return servedByWaiter;
	}

	/**
	 * setServedByWaiter sets waiter serving table
	 * 
	 * @param waiter the waiter
	 */
	public void setServedByWaiter(Waiter servedByWaiter) {
		this.servedByWaiter = servedByWaiter;
	}

	/**
	 * getPreparedByChef gets chef making dish
	 * 
	 * @return the chef
	 */
	public Chef getPreparedByChef() {
		return preparedByChef;
	}

	/**
	 * setPreparedByChef sets chef making dish
	 * 
	 * @param chef the chef
	 */
	public void setPreparedByChef(Chef preparedByChef) {
		this.preparedByChef = preparedByChef;
	}

	/**
	 * getTableOrder gets table order
	 * 
	 * @return the table order
	 */
	public TableOrder getTableOrder() {
		return tableOrder;
	}

	/**
	 * setTableOrder sets table order
	 * 
	 * @param tableOrder the table order
	 */
	public void setTableOrder(TableOrder tableOrder) {
		this.tableOrder = tableOrder;
	}

	/**
	 * isFired checks if dish is fired
	 * 
	 * @return true if dish has been fired
	 */
	public boolean isFired() {
		return fired;
	}

	/**
	 * setFired sets item as fired or not
	 * 
	 * @param fired value of whether item is fired or not
	 */
	public void setFired(boolean fired) {
		this.fired = fired;
	}

	/**
	 * isServedToCustomer checks if dish is served
	 * 
	 * @return true if dish has been served
	 */
	public boolean isServedToCustomer() {
		return servedToCustomer;
	}

	/**
	 * setServedToCustomer sets item as served or not
	 * 
	 * @param served value of whether item is served or not
	 */
	public void setServedToCustomer(boolean servedToCustomer) {
		this.servedToCustomer = servedToCustomer;
	}
}
