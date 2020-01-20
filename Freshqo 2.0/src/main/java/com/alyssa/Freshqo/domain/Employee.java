package com.alyssa.Freshqo.domain;

import java.io.Serializable;

/**
 * Employee 
 * 
 * Abstract class for an employee
 * 
 * @author Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */
public abstract class Employee implements Serializable {

	// VARIABLES
	private String name;
	private double pay;
	private String userID;
	private String password;
	private String dateHired;
	private String email;
	private String SINNumber;
	private String position;

	/**
	 * Employee constructor - assigns all values to variables
	 * 
	 * @param name      employee name
	 * @param pay       employee pay
	 * @param userID    employee username for login
	 * @param password  employee password to login
	 * @param dateHired date employee is hired
	 * @param email     employee email
	 * @param SINNumber employee Social Insurance Number
	 * @param position  specific employee position
	 */
	public Employee(String name, double pay, String userID, String password, String dateHired, String email,
			String SINNumber, String position) {
		this.name = name;
		this.pay = pay;
		this.userID = userID;
		this.password = password;
		this.dateHired = dateHired;
		this.email = email;
		this.SINNumber = SINNumber;
		this.setPosition(position);

	}

	/**
	 * Name getter
	 * 
	 * @return String the name of the employee
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter
	 * 
	 * @param name value of employee name to set variable to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Pay getter
	 * 
	 * @return double - the hourly pay of the employee
	 */
	public double getPay() {
		return pay;
	}

	/**
	 * Pay setter
	 * 
	 * @param pay the new value to set the pay variable to
	 */
	public void setPay(double pay) {
		this.pay = pay;
	}

	/**
	 * UserID getter
	 * 
	 * @return String - the employee's userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * UserID setter
	 * 
	 * @param userID the value to set the userID to
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * password getter
	 * 
	 * @return String - the employee's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * password setter
	 * 
	 * @param password the new value to set the employee password to
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * DateHired getter
	 * 
	 * @return String - the date the employee was hired
	 */
	public String getDateHired() {
		return dateHired;
	}

	/**
	 * DateHired setter
	 * 
	 * @param dateHired the value to set the dateHired variable to
	 */
	public void setDateHired(String dateHired) {
		this.dateHired = dateHired;
	}

	/**
	 * Email getter
	 * 
	 * @return String - the employee's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Email setter
	 * 
	 * @param email the value to set the email variable to
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * SINNumber getter
	 * 
	 * @return String - the employee's SIN Number
	 */
	public String getSINNumber() {
		return SINNumber;
	}

	/**
	 * SINNumber setter
	 * 
	 * @param SINNumber the value to set the SINNumber value to
	 */
	public void setSINNumber(String SINNumber) {
		this.SINNumber = SINNumber;
	}

	/**
	 * Position getter
	 * 
	 * @return String the employee's specific position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Position setter
	 * 
	 * @param position the value to set position variable to
	 */
	public void setPosition(String position) {
		this.position = position;
	}

}
