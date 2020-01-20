package com.alyssa.Freshqo.domain;

/**
 * Manager 
 * 
 * The manager employee class
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class Manager extends Employee {

	/**
	 * Manager constructor initialize the manager with all required values
	 * 
	 * @param name      the name of the manager
	 * @param pay       the pay of the manager
	 * @param userID    the username for the manager for login
	 * @param password  the manager's password for the login
	 * @param dateHired the date manager is hired
	 * @param email     the manager's email
	 * @param SINNumber the manager's Social Insurance NUmber
	 * @param position  the manager's specific position at the restaurant
	 */
	public Manager(String name, double pay, String userID, String password, String dateHired, String email,
			String SINNumber, String position) {
		super(name, pay, userID, password, dateHired, email, SINNumber, position);
	}

}
