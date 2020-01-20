package com.alyssa.Freshqo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.alyssa.Freshqo.data.CustomerQueue;
import com.alyssa.Freshqo.data.Queue;
import com.alyssa.Freshqo.domain.Customer;
import com.alyssa.Freshqo.domain.Employee;
import com.alyssa.Freshqo.domain.MenuItem;
import com.alyssa.Freshqo.domain.Reservation;
import com.alyssa.Freshqo.domain.Table;
import com.alyssa.Freshqo.domain.TableOrder;
import com.alyssa.Freshqo.domain.TableOrderItem;

/**
 * Dao 
 * 
 * Data Access Object interface that defines database operations
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public interface Dao {
	
	/**
	 * clearData clear all database data.
	 */
	public void clearData() throws SQLException;
	
	/**
	 * saveEmployees save a list of employees to database.
	 *  @param employees a list of employees to be saved to database.
	 *  @exception SQLException when encountered a database error
	 */
	public void saveEmployees ( List<Employee> employees ) throws SQLException;

	/**
	 * Save reservation book to database
	 * @param reservationBook a list of Reservation objects that are expected to save to database
	 * @throws SQLException when encountered a database error
	 */
	public void saveReservationBook(List<Reservation> reservationBook) throws SQLException;

	/**
	 * Save restaurant tables to database
	 * @param tables a list of Table objects that are expected to save to database
	 * @throws SQLException when encountered a database error
	 */
	public void saveTables(List<Table> tables) throws SQLException;

	/**
	 * Save the waiting list to database
	 * @param waitingList a list of Customer objects that are in the waiting list and are expected to save to database
	 * @throws SQLException when encountered a database error
	 */
	public void saveWaitingList(CustomerQueue<Customer> waitingList) throws SQLException;

	/**
	 * Save restaurant menu to database
	 * @param menu a list of MenuItem objects that are expected to save to database
	 * @throws SQLException when encountered a database error
	 */
	public void saveMenu(List<MenuItem> menu) throws SQLException, IOException;

//	public void saveKitchenOrders(Queue<TableOrderItem> kitchenOrders) throws SQLException;

	/**
	 * Save historical transactions (table orders and order items) to database
	 * @param menu a list of TableOrder objects that are completed (i.e., paid) and expected to save to database
	 * @throws SQLException when encountered a database error
	 */
	public void saveHistoricalTransactions(List<TableOrder> historicalTransactions) throws SQLException;

	/**
	 * Retrieve all employee records from the database
	 * @return a list of Employee objects that are retrieved from the database
	 * @throws SQLException when encountered a database error
	 */
	public List<Employee> loadEmployees() throws SQLException;

	/**
	 * Retrieve all menu item records from the database
	 * @return a list of MenuItem objects that are retrieved from the database
	 * @throws SQLException when encountered a database error
	 */
	public List<MenuItem> loadMenu() throws SQLException, IOException;

	/**
	 * Retrieve all customer records from the database
	 * @return a list of Customer objects that are retrieved from the database
	 * @throws SQLException when encountered a database error
	 */
	public List<Customer> loadCustomers() throws SQLException;

	/**
	 * Retrieve all table records from the database
	 * @return a list of Table objects that are retrieved from the database
	 * @throws SQLException when encountered a database error
	 */
	public List<Table> loadTables(List<Customer> customers, List<Employee> employees) throws SQLException;

	/**
	 * Retrieve all reservation records from the database
	 * @return a list of Reservation objects that are retrieved from the database
	 * @throws SQLException when encountered a database error
	 */
	public List<Reservation> loadReservation(List<Table> tables, List<Customer> customers) throws SQLException;

	/**
	 * Retrieve all table order records from the database. It includes unfinished orders, kitchen orders and historical orders.
	 * @return a two lists of TableOrder objects that are retrieved from the database. The two lists are contained in a Map<String, List<TableOrder>> object,
	 * with keys of "NON-HIST" and "HIST". Returned TableOrder list contains associated TableOrderItem list.
	 * @throws SQLException when encountered a database error
	 */
	public Map<String, List<TableOrder>> loadTableOrders(List<Employee> employees, List<Table> tables, List<MenuItem> menu, Queue<TableOrderItem> kitchenOrders) throws SQLException;

}
