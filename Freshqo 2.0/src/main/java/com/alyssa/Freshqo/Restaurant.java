package com.alyssa.Freshqo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alyssa.Freshqo.dao.DaoImpl;
import com.alyssa.Freshqo.data.CustomerQueue;
import com.alyssa.Freshqo.data.DoublyLinkedList;
import com.alyssa.Freshqo.data.Queue;
import com.alyssa.Freshqo.domain.Chef;
import com.alyssa.Freshqo.domain.Customer;
import com.alyssa.Freshqo.domain.Employee;
import com.alyssa.Freshqo.domain.Manager;
import com.alyssa.Freshqo.domain.MenuItem;
import com.alyssa.Freshqo.domain.Reservation;
import com.alyssa.Freshqo.domain.ReservationDateTime;
import com.alyssa.Freshqo.domain.Table;
import com.alyssa.Freshqo.domain.TableOrder;
import com.alyssa.Freshqo.domain.TableOrderItem;
import com.alyssa.Freshqo.domain.Waiter;
import com.alyssa.Freshqo.utils.Utils;
import com.alyssa.Freshqo.view.EmployeeDialog;
import com.alyssa.Freshqo.view.KitchenDialog;
import com.alyssa.Freshqo.view.LoginFrame;
import com.alyssa.Freshqo.view.MenuDialog;
import com.alyssa.Freshqo.view.OrderDialog;
import com.alyssa.Freshqo.view.ReportingDialog;
import com.alyssa.Freshqo.view.ReservationBookDialog;
import com.alyssa.Freshqo.view.SetupDialog;
import com.alyssa.Freshqo.view.TransactionDialog;

/**
 * Restaurant 
 * 
 * The main class that stores all variables from reservations,
 * tables, menu, waiting list, etc.
 * 
 * @author Alyssa Gao && Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */

public class Restaurant extends JFrame {

	// Variables
	private List<Table> tables = new DoublyLinkedList<>();
	private List<Reservation> reservationBook = new DoublyLinkedList<>();
	private List<MenuItem> menu = new DoublyLinkedList<>();
	private CustomerQueue<Customer> waitingList = new CustomerQueue<>();
	private Queue<TableOrderItem> kitchenOrders = new Queue<>();
	private List<Employee> employees = new DoublyLinkedList<>();
	private List<TableOrder> historicalTransactions = new DoublyLinkedList<>();
	private Employee currentUser;
	private Restaurant self = this;
	private JPanel mainPanel;
	private JButton orderButton;
	private JButton transactionButton;
	private JButton kitchenButton;
	private JButton reservationButton;
	private JButton reportingButton;
	private JButton menuButton;
	private JButton setupButton;
	private JButton employeeButton;
	private ImageIcon homepageBackground;
	private JLabel homepageBackgroundLabel;
	private ImageIcon homepageBackgroundLocked;
	private JLabel homepageBackgroundLabelLocked;
	private JButton openFileButton;
	private JButton saveFileButton;
	private JButton logoutButton;
	private JButton loginButton;
	private JButton closeFileButton;
	private JLabel employeeNameLabel;

	/**
	 * main the entry point (main method) of the restaurant management program
	 * 
	 * @param args command-line argument. It is not used in the app.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Restaurant restaurant = new Restaurant();
				restaurant.setVisible(true);
			}
		});
	}

	/**
	 * initializes user interface
	 */
	public Restaurant() {
		initUI();
	}

	/**
	 * initializes the user interface
	 */
	private void initUI() {

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		getContentPane().add(mainPanel);

		orderButton = new JButton(new ImageIcon(getClass().getResource("images/orders button.JPG")));
		orderButton.setBounds(50, 150, 125, 125);
		orderButton.addActionListener(new ButtonListener());
		mainPanel.add(orderButton);

		transactionButton = new JButton(new ImageIcon(getClass().getResource("images/transactions button.JPG")));
		transactionButton.setBounds(200, 150, 125, 125);
		transactionButton.addActionListener(new ButtonListener());
		mainPanel.add(transactionButton);

		kitchenButton = new JButton(new ImageIcon(getClass().getResource("images/kitchen button.JPG")));
		kitchenButton.setBounds(350, 150, 125, 125);
		kitchenButton.addActionListener(new ButtonListener());
		mainPanel.add(kitchenButton);

		menuButton = new JButton(new ImageIcon(getClass().getResource("images/menu button.JPG")));
		menuButton.setBounds(500, 150, 125, 125);
		menuButton.addActionListener(new ButtonListener());
		mainPanel.add(menuButton);

		reservationButton = new JButton(new ImageIcon(getClass().getResource("images/reservations button.JPG")));
		reservationButton.setBounds(50, 300, 125, 125);
		reservationButton.addActionListener(new ButtonListener());
		mainPanel.add(reservationButton);

		employeeButton = new JButton(new ImageIcon(getClass().getResource("images/employee button.JPG")));
		employeeButton.setBounds(200, 300, 125, 125);
		employeeButton.addActionListener(new ButtonListener());
		mainPanel.add(employeeButton);

		reportingButton = new JButton(new ImageIcon(getClass().getResource("images/reporting button.JPG")));
		reportingButton.setBounds(350, 300, 125, 125);
		reportingButton.addActionListener(new ButtonListener());
		mainPanel.add(reportingButton);

		setupButton = new JButton(new ImageIcon(getClass().getResource("images/setup button.JPG")));
		setupButton.setBounds(500, 300, 125, 125);
		setupButton.addActionListener(new ButtonListener());
		mainPanel.add(setupButton);

		loginButton = new JButton(new ImageIcon(getClass().getResource("images/homepage login button.JPG")));
		loginButton.setBounds(865, 75, 120, 75);
		loginButton.addActionListener(new ButtonListener());
		mainPanel.add(loginButton);

		logoutButton = new JButton(new ImageIcon(getClass().getResource("images/logout button.JPG")));
		logoutButton.setBounds(865, 75, 120, 75);
		logoutButton.addActionListener(new ButtonListener());
		mainPanel.add(logoutButton);

//		openFileButton = new JButton(new ImageIcon(getClass().getResource("images/open file button.JPG")));
		openFileButton = new JButton(new ImageIcon(getClass().getResource("images/load data button.JPG")));
		openFileButton.setBounds(865, 165, 120, 75);
		openFileButton.addActionListener(new ButtonListener());
		mainPanel.add(openFileButton);

//		saveFileButton = new JButton(new ImageIcon(getClass().getResource("images/save file button.JPG")));
		saveFileButton = new JButton(new ImageIcon(getClass().getResource("images/save data button.JPG")));
		saveFileButton.setBounds(865, 255, 120, 75);
		saveFileButton.addActionListener(new ButtonListener());
		mainPanel.add(saveFileButton);

		closeFileButton = new JButton(new ImageIcon(getClass().getResource("images/close file button.JPG")));
		closeFileButton.setBounds(865, 345, 120, 75);
		closeFileButton.addActionListener(new ButtonListener());
		mainPanel.add(closeFileButton);

		// background image when logged out
		homepageBackgroundLocked = new ImageIcon(getClass().getResource("images/freshqo homepage locked.JPG"));
		homepageBackgroundLabelLocked = new JLabel(homepageBackgroundLocked);
		homepageBackgroundLabelLocked.setBounds(0, 0, 1000, 600);
		mainPanel.add(homepageBackgroundLabelLocked);

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("images/freshqo homepage.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		mainPanel.add(homepageBackgroundLabel);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				closeApplication();
			}
		});

		loadConfigurationAndData();
		login();

		disableButtons();

		// icon image and size
		setDefaultLookAndFeelDecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/freshqo icon.JPG")));
		setTitle("Freshqo Management");
		setSize(1000, 600);
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	}

	/**
	 * closeApplication Asks if the user is sure they want to close the program. If
	 * yes, it will automatically save the data.
	 */
	protected void closeApplication() {
		int ret = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to close the application?",
				"Close Application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (ret == JOptionPane.YES_OPTION) {
			saveConfigurationAndData();
			System.exit(0);
		}
	}

	/**
	 * openDataFile Opens user's own file explorer and allows user to choose a data
	 * file the file to be opened that was previously saved from before
	 */
	protected void openDataFile() {
		JFileChooser fileopen = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Application data files", ".fqo");
		fileopen.addChoosableFileFilter(filter);

		int ret = fileopen.showDialog(mainPanel, "Open file");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fileopen.getSelectedFile();
			try {
				loadDataFile(file);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "The file name could not be found.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;

			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "The file cannot be used with this program.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}

	/**
	 * reloadDataFromDB load the data from the database.
	 */
	protected void reloadDataFromDB ( ) {
		int ret = JOptionPane.showConfirmDialog(mainPanel, "Loading data will wipe out all changes that you have made since your started the application. Are you sure you want to load the data now?",
				"Load Data", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (ret == JOptionPane.YES_OPTION) {
			try {
				loadDataFromDB( );
			} catch (SQLException|IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "There is an error in loading data from the database. Please contact administrator.", "Error", JOptionPane.ERROR_MESSAGE);			
			}
		}
	}

	/**
	 * loadDataFile load the RecipeDatabase from the file specified by the filename.
	 * 
	 * @param filename the file name of the database file.
	 * @throws FileNotFoundException  if the file cannot be found
	 * @throws IOException            if there is an error in reading the file
	 * @throws ClassNotFoundException the RecipeDatabase class cannot be found.
	 * @throws ClassCastException     the data stored in the file is not a
	 *                                RecipeDatabase object
	 */
	private void loadDataFile(String filename)
			throws FileNotFoundException, IOException, ClassNotFoundException, ClassCastException {
		loadDataFile(new File(filename));
	}

	/**
	 * loadDataFile load the RecipeDatabase from the database, or specified file if database is not available.
	 * 
	 * @param file the File object defining the database file.
	 * @throws FileNotFoundException  if the file cannot be found
	 * @throws IOException            if there is an error in reading the file
	 * @throws ClassNotFoundException the RecipeDatabase class cannot be found.
	 * @throws ClassCastException     the data stored in the file is not a
	 *                                RecipeDatabase object
	 */
	@SuppressWarnings("unchecked")
	private void loadDataFile(File file)
			throws FileNotFoundException, IOException, ClassNotFoundException, ClassCastException {
		this.configuration.setProperty("database.filename", file.getAbsolutePath());
		
		try {
			this.loadDataFromDB();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is an error in loading data from the database. Please contact administrator. "
					+ "Meanwhile, as a backup step, the data will be loaded from the last known local back.", "Error",
					JOptionPane.ERROR_MESSAGE);
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				tables = (List<Table>) ois.readObject();
				reservationBook = (List<Reservation>) ois.readObject();
				menu = (List<MenuItem>) ois.readObject();
				waitingList = (CustomerQueue<Customer>) ois.readObject();
				employees = (DoublyLinkedList<Employee>) ois.readObject();
				historicalTransactions = (List<TableOrder>) ois.readObject();
				kitchenOrders = (Queue<TableOrderItem>) ois.readObject();
			}
		}

	}

	/**
	 * loadDataFromDB load the RecipeDatabase from the database.
	 * 
	 * @throws SQLException     there is a database error occurred.
	 * @throws IOException      there is an I/O error occurred.
	 */
	private void loadDataFromDB() throws SQLException, IOException {
		
		this.employees = DaoImpl.getInstance().loadEmployees();
		this.menu = DaoImpl.getInstance().loadMenu();
		List<Customer> customers = DaoImpl.getInstance().loadCustomers();
		this.tables = DaoImpl.getInstance().loadTables(customers, this.employees);
		this.reservationBook = DaoImpl.getInstance().loadReservation(this.tables, customers);
		Map<String, List<TableOrder>> allTableOrders = DaoImpl.getInstance().loadTableOrders(this.employees, this.tables, this.menu, this.kitchenOrders );
		this.historicalTransactions = allTableOrders.get( "HIST" );
	}

	/**
	 * saveDataFile save data file saves the file under its own unique extension
	 */
	protected void saveDataFile() {
		JFileChooser filesave = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Application data files", ".fqo");
		filesave.addChoosableFileFilter(filter);

		int ret = filesave.showDialog(mainPanel, "Save file");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = filesave.getSelectedFile();
			saveDataFile(file);
		}
	}

	/**
	 * saveDataFile converts the String filename into a file
	 * 
	 * @param filename the name of the file to be saved
	 */
	private void saveDataFile(String filename) {
		saveDataFile(new File(filename));
	}

	/**
	 * saveDataFile saves the data into a file
	 * 
	 * @param file the file which will hold the saved data
	 */
	private void saveDataFile(File file) {
		this.configuration.setProperty("database.filename", file.getAbsolutePath());
		try {
			DaoImpl.getInstance().clearData();
			DaoImpl.getInstance().saveEmployees( employees );
			DaoImpl.getInstance().saveMenu ( menu );
			DaoImpl.getInstance().saveTables ( tables );
			DaoImpl.getInstance().saveReservationBook ( reservationBook );
			DaoImpl.getInstance().saveWaitingList ( waitingList );
//			DaoImpl.getInstance().saveKitchenOrders ( kitchenOrders );
			DaoImpl.getInstance().saveHistoricalTransactions ( historicalTransactions );
		} catch (IOException|SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is an error in saving data to database. Please connect your adminstrator. As a back, the data is also save to a local file. Please contact supporting team to restore from the local backup.", "Error",
			JOptionPane.ERROR_MESSAGE);
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(tables);
			oos.writeObject(reservationBook);
			oos.writeObject(menu);
			oos.writeObject(waitingList);
			oos.writeObject(employees);
			oos.writeObject(historicalTransactions);
			oos.writeObject(kitchenOrders);
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "The file cannot be used with this program.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * saveDataFile saves the data to database
	 * 
	 */
	private void saveDataToDB ( ) {
		try {
			DaoImpl.getInstance().clearData();
			DaoImpl.getInstance().saveEmployees( employees );
			DaoImpl.getInstance().saveMenu ( menu );
			DaoImpl.getInstance().saveTables ( tables );
			DaoImpl.getInstance().saveReservationBook ( reservationBook );
			DaoImpl.getInstance().saveWaitingList ( waitingList );
//			DaoImpl.getInstance().saveKitchenOrders ( kitchenOrders );
			DaoImpl.getInstance().saveHistoricalTransactions ( historicalTransactions );
		} catch (IOException|SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is an error in saving data to database. Please connect your adminstrator.", "Error",
			JOptionPane.ERROR_MESSAGE);
		}
	}

	protected Properties configuration = new Properties();
	public static final String CONFIGURATION_FILENAME = "restaurant.properties";
	public static final String DEFAULT_DATABASE_FILENAME = "restaurant management.fqo";

	/**
	 * saveConfigurationAndData saves configuration and data under the file
	 */
	protected void saveConfigurationAndData() {

		String dbFilename = this.configuration.getProperty("database.filename", DEFAULT_DATABASE_FILENAME);
//		this.saveDataFile(dbFilename);
		this.saveDataToDB();
		this.configuration.setProperty("database.filename", dbFilename);
		try {
			this.configuration.store(new FileOutputStream(CONFIGURATION_FILENAME), "");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The file type cannot be used to save your data.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * loadConfigurationAndData loads configuration and data from a saved file
	 */
	protected void loadConfigurationAndData() {
		try {
			this.configuration.load(new FileInputStream(CONFIGURATION_FILENAME));
			String dbFilename = this.configuration.getProperty("database.filename", DEFAULT_DATABASE_FILENAME);
//			try {
//				this.loadDataFile(dbFilename);
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null,
//						"The data file cannot be open or does not exist. A default one will be created on the exit of the application.",
//						"Warning", JOptionPane.WARNING_MESSAGE);
//			} catch (ClassNotFoundException | ClassCastException e) {
//				JOptionPane.showMessageDialog(null,
//						"The data file is corrupt and hence cannot be used. A new data file will be created on the exit of the application.",
//						"Warning", JOptionPane.WARNING_MESSAGE);
//			}
			try {
				loadDataFromDB( );
			} catch (SQLException|IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "There is an error in loading data from the database. Please contact administrator. Default data will be created.", "Warning", JOptionPane.WARNING_MESSAGE);			
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"The configuration file does not exist or is corrupt. The default value will be used.", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * getMenu gets the menu
	 * 
	 * @return the menu
	 */
	public List<MenuItem> getMenu() {
		return menu;
	}

	/**
	 * getAppetizerMenu gets the appetizer menu
	 * 
	 * @return the appetizer menu
	 */
	public List<MenuItem> getAppetizerMenu() {
		List<MenuItem> appetizerMenu = new ArrayList<>();
		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).getCategory().equals("Appetizer")) {
				appetizerMenu.add(menu.get(i));
			}
		}
		return appetizerMenu;
	}

	/**
	 * getEntreeMenu gets the entree menu
	 * 
	 * @return the entree menu
	 */
	public List<MenuItem> getEntreeMenu() {
		List<MenuItem> entreeMenu = new ArrayList<>();
		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).getCategory().equals("Entree")) {
				entreeMenu.add(menu.get(i));
			}
		}
		return entreeMenu;
	}

	/**
	 * getDessertMenu gets the desserts menu
	 * 
	 * @return the desserts menu
	 */
	public List<MenuItem> getDessertMenu() {
		List<MenuItem> dessertMenu = new ArrayList<>();
		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).getCategory().equals("Dessert")) {
				dessertMenu.add(menu.get(i));
			}
		}
		return dessertMenu;
	}

	/**
	 * getBeverageMenu gets the beverage menu
	 * 
	 * @return the beverage menu
	 */
	public List<MenuItem> getBeverageMenu() {
		List<MenuItem> beverageMenu = new ArrayList<>();
		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).getCategory().equals("Beverage")) {
				beverageMenu.add(menu.get(i));
			}
		}
		return beverageMenu;
	}

	/**
	 * getReservationBook gets the reservation book
	 * 
	 * @return the reservation book
	 */
	public List<Reservation> getReservationBook() {
		return reservationBook;
	}

	/**
	 * getWaiters gets the list of waiters
	 * 
	 * @return the list of waiters
	 */
	public List<Waiter> getWaiters() {
		List<Waiter> waiters = new DoublyLinkedList<>();
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i) instanceof Waiter) {
				waiters.add((Waiter) employees.get(i));
			}
		}

		return waiters;
	}

	/**
	 * getChefs gets the list of chefs
	 * 
	 * @return the list of chefs
	 */
	public List<Chef> getChefs() {
		List<Chef> chefs = new DoublyLinkedList<>();
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i) instanceof Chef) {
				chefs.add((Chef) employees.get(i));
			}
		}
		return chefs;
	}

	/**
	 * getManagers gets the list of managers
	 * 
	 * @return the list of managers
	 */
	public List<Manager> getManagers() {
		List<Manager> managers = new DoublyLinkedList<>();
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i) instanceof Manager) {
				managers.add((Manager) employees.get(i));
			}
		}
		return managers;
	}

	/**
	 * getTables gets the list of tables
	 * 
	 * @return the list of tables
	 */
	public List<Table> getTables() {
		return tables;
	}

	/**
	 * getReservableTables gets the list of reservable tables
	 * 
	 * @return the list of reservable tables
	 */
	public List<Table> getReservableTables() {
		List<Table> reservableTables = new ArrayList<>();
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).canBeReserved()) {
				reservableTables.add(tables.get(i));
			}
		}
		return reservableTables;
	}

	/**
	 * getReservableTables gets the list of reservable tables for time period and
	 * number of people
	 * 
	 * @param numPeople         the number of people
	 * @param reserveTimePeriod the date and time of the reservation
	 * @return the list of reservable tables tables
	 */
	public List<Table> getReservableTables(int numPeople, ReservationDateTime reserveTimePeriod) {
		List<Table> reservableTables = new ArrayList<>();
		for (int i = 0; i < tables.size(); i++) {
			if ((tables.get(i).canBeReserved()) && (tables.get(i).getNumSeats() >= numPeople)) {
				if ((!reserveTimePeriod.getSecuredTimePeriodFrom().isBefore(LocalTime.now()))
						|| (!tables.get(i).isOccupied())) {
					reservableTables.add(tables.get(i));
				}
			}
		}
		return reservableTables;
	}

	/**
	 * findAvailableTableForReservation findsa available tables for reservations
	 * 
	 * @param numPeople         the number of people
	 * @param reserveTimePeriod the reservation's time and date
	 * @return the list of available tables
	 */
	public List<Table> findAvailableTableForReservation(int numPeople, ReservationDateTime reserveTimePeriod) {
		// save reserve tables to an array
		// check date and save "already made" reservations to an array
		// check each reservation to see if its within the time range
		// if not, remove table from reserve tables

		List<Table> availableTableForReservation = getReservableTables(numPeople, reserveTimePeriod);
		List<Reservation> savedReservationsForDate = new ArrayList<>();

		for (int i = 0; i < reservationBook.size(); i++) {
			if (reservationBook.get(i).getReservationDateTime().getLocalDate()
					.equals(Utils.convertToLocalDate(reserveTimePeriod.getDate()))) {
				savedReservationsForDate.add(reservationBook.get(i));
			}
		}
		for (int i = 0; i < savedReservationsForDate.size(); i++) {
			if ((!savedReservationsForDate.get(i).isClaimed())
					&& (availableTableForReservation.contains(savedReservationsForDate.get(i).getTable()))) {
				if ((savedReservationsForDate.get(i).getReservationDateTime().getSecuredTimePeriodFrom()
						.isBefore(reserveTimePeriod.getLocalTime()))
						&& (savedReservationsForDate.get(i).getReservationDateTime().getSecuredTimePeriodTo()
								.isAfter(reserveTimePeriod.getLocalTime()))) {
					availableTableForReservation.remove(savedReservationsForDate.get(i).getTable());
				}

			}
		}
		return availableTableForReservation;
	}

	/**
	 * bookReservation books reservation
	 * 
	 * @param reservation the reservation to be added to the reservation book
	 */
	public void bookReservation(Reservation reservation) {
		reservationBook.add(reservation);
		JOptionPane.showMessageDialog(null, "Reservation has been added.");
	}

	/**
	 * claimReservation claims reservation
	 * 
	 * @param customerName the customer name
	 */
	public void claimReservation(String customerName) {
		boolean found = false;
		Reservation reservation = null;
		Table reservedTable = null;
		Waiter waiter = findAvailableWaiter();
		for (int i = 0; i < reservationBook.size(); i++) {
			if ((!reservationBook.get(i).isClaimed())
					&& (reservationBook.get(i).getCustomer().getName().equals(customerName))) {
				found = true;
				reservation = reservationBook.get(i);
				break;
			}
		}
		if (!found) {
			JOptionPane.showMessageDialog(null, "There is no reservation under that name", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Reservation has been claimed by " + customerName + " for "
				+ reservation.getReservationDateTime().getLocalTime());

		reservation.setClaimed(true);
		reservedTable = reservation.getTable();
		reservedTable.setCustomer(reservation.getCustomer());
		reservedTable.setOccupied(true);
		reservedTable.setCurrentAssignedWaiter(waiter);
		reservedTable.setCurrentOrder(new TableOrder(reservedTable));
		reservedTable.getCurrentOrder().setWaiter(waiter);
		waiter.getAssignedTables().add(reservedTable);

		JOptionPane.showMessageDialog(null, "They have been successfully placed at Table "
				+ reservedTable.getTableName() + ". They will be served by " + waiter.getName());

	}

	/**
	 * addEmployee adds an employee
	 * 
	 * @param employee the employee to be added
	 */
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	/**
	 * getEmployees gets employees
	 * 
	 * @return the list of employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}

	/**
	 * getCurrentUser gets the current user
	 * 
	 * @return the employee currently logged in
	 */
	public Employee getCurrentUser() {
		return currentUser;
	}

	/**
	 * setCurrentUser sets the current user
	 * 
	 * @param currentUser the employee currently logged in
	 */
	public void setCurrentUser(Employee currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * findAvailableTableForWalkInCustomer find an available table for walk-in
	 * customer
	 * 
	 * @param customer the customer to be placed at the table if found
	 * @return a table that the customer can be seated at
	 */
	public Table findAvailableTableForWalkInCustomer(Customer customer) {

		List<Table> availableTables = new ArrayList<>();

		for (int i = 0; i < tables.size(); i++) {
			if (!tables.get(i).isOccupied()) {
				availableTables.add(tables.get(i));
			}
		}

		for (int i = 0; i < reservationBook.size(); i++) {
			if ((reservationBook.get(i).getReservationDateTime().getLocalDate().equals(LocalDate.now()))
					&& (LocalTime.now()
							.isBefore(reservationBook.get(i).getReservationDateTime().getSecuredTimePeriodTo()))
					&& (LocalTime.now()
							.isAfter(reservationBook.get(i).getReservationDateTime().getSecuredTimePeriodFrom()))) {
				availableTables.remove(reservationBook.get(i).getTable());
			}
		}

		if (availableTables.size() > 0) {
			return getAppropriateTable(availableTables, customer.getNumPeople());
		} else {
			return null;
		}
	}

	/**
	 * checkWaitingList checks the waiting list to see if someone can now be seated
	 * at a table
	 */
	public void checkWaitingList() {
		if ((waitingList.size() == 0) || (getWaiters().size() == 0)) {
			return;
		}

		for (int i = 0; i < tables.size(); i++) {
			if (!tables.get(i).isOccupied()) {
				Table table = tables.get(i);
				int numSeats = table.getNumSeats();
				Customer customer = waitingList.dequeue(numSeats);
				tables.get(i).setCustomer(customer);
				if (tables.get(i).getCustomer() != null) {
					JOptionPane.showMessageDialog(null,
							"From the waiting list, " + customer.getName() + " has been placed at a table.");
					table.setOccupied(true);
					Waiter waiter = findAvailableWaiter();
					table.setCurrentAssignedWaiter(waiter);
					table.setCurrentOrder(new TableOrder(table));
					table.getCurrentOrder().setWaiter(waiter);
					waiter.getAssignedTables().add(table);
				}
			}
		}
	}

	/**
	 * login setups login and asks user to create a default password in the
	 * beginning
	 */
	public void login() {
		if (employees.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Welcome to Freshqo! As there are currently no employees added, there will be an already set username and password to login.");
			JOptionPane.showMessageDialog(null,
					"Once logged in, please add a manager to the database before creating any other employees.");
			String newPassword = JOptionPane.showInputDialog(
					"A default user is created for you. UserID: manager. Please enter a new password.");
			if (newPassword != null) {
				Manager newManager = new Manager("MANAGER", 0.0, "manager", newPassword, "", "", "", "Manager");
				addEmployee(newManager);
				LoginFrame loginFrame = new LoginFrame(self);
			}
		}
		LoginFrame loginFrame = new LoginFrame(self);
	}

	/**
	 * initializeSuccessfulLogin initializes successful login
	 */
	public void initializeSuccessfulLogin() {
		if (currentUser != null) {
			homepageBackgroundLabelLocked.setVisible(false);

			employeeNameLabel = new JLabel("Hello " + currentUser.getName());
			employeeNameLabel.setBounds(700, 15, 200, 30);
			employeeNameLabel.setForeground(Color.white);
			employeeNameLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
			mainPanel.add(employeeNameLabel);
			mainPanel.add(homepageBackgroundLabel);
			enableButtons();
		}
	}

	/**
	 * logout logs current user out
	 */
	private void logout() {
		mainPanel.remove(employeeNameLabel);
		mainPanel.remove(homepageBackgroundLabel);
		homepageBackgroundLabelLocked.setVisible(true);
		disableButtons();
		currentUser = null;
	}

	/**
	 * when logged in, main buttons become visible
	 */
	private void enableButtons() {

		orderButton.setVisible(true);
		transactionButton.setVisible(true);
		kitchenButton.setVisible(true);
		menuButton.setVisible(true);
		reservationButton.setVisible(true);
		employeeButton.setVisible(true);
		reportingButton.setVisible(true);
		setupButton.setVisible(true);
		loginButton.setVisible(false);
		logoutButton.setVisible(true);

	}

	/**
	 * when logged out, main buttons become invisible
	 */
	private void disableButtons() {
		orderButton.setVisible(false);
		transactionButton.setVisible(false);
		kitchenButton.setVisible(false);
		menuButton.setVisible(false);
		reservationButton.setVisible(false);
		employeeButton.setVisible(false);
		reportingButton.setVisible(false);
		setupButton.setVisible(false);
		loginButton.setVisible(true);
		logoutButton.setVisible(false);

	}

	/**
	 * getAppropriateTable gets the table that has a number of seats closest to
	 * number of people
	 * 
	 * @param availableTables the list of available table
	 * @param numPeople       the number of people
	 * @return the best table for the customer
	 */
	private Table getAppropriateTable(List<Table> availableTables, int numPeople) {
		int minDifference = Integer.MAX_VALUE;
		Table bestTable = null;
		for (int i = 0; i < availableTables.size(); i++) {
			if (availableTables.get(i).getNumSeats() - numPeople >= 0
					&& availableTables.get(i).getNumSeats() - numPeople < minDifference) {
				bestTable = availableTables.get(i);
				minDifference = availableTables.get(i).getNumSeats();
			}
		}
		return bestTable;
	}

	/**
	 * getWaitingList ge4ts waiting list
	 * 
	 * @return the waiting list
	 */
	public CustomerQueue<Customer> getWaitingList() {
		return waitingList;
	}

	/**
	 * findAvailableWaiter finds available waiters ensures that each waiter has
	 * about the same number of tables
	 * 
	 * @return the waiter for the table
	 */
	public Waiter findAvailableWaiter() {
		int minTables = Integer.MAX_VALUE;
		Waiter waiter = null;
		for (int i = 0; i < getWaiters().size(); i++) {
			if (getWaiters().get(i).getAssignedTables().size() < minTables) {
				waiter = getWaiters().get(i);
				minTables = getWaiters().get(i).getAssignedTables().size();
			}
		}
		return waiter;
	}

	/**
	 * getTotalSales gets total sales
	 * 
	 * @return the total sales
	 */
	public double getTotalSales() {
		double totalSales = 0;
		for (int i = 0; i < historicalTransactions.size(); i++) {
			totalSales = historicalTransactions.get(i).getTotal();
		}
		return totalSales;
	}

	/**
	 * getTotalSeats gets total seats
	 * 
	 * @return the total number of seats
	 */
	public int getTotalSeats() {
		int totalSeats = 0;
		for (int i = 0; i < tables.size(); i++) {
			totalSeats = tables.get(i).getNumSeats();
		}
		return totalSeats;
	}

	/**
	 * getHistoricalTransactions gets historical transactions
	 * 
	 * @return the historical transactions
	 */
	public List<TableOrder> getHistoricalTransactions() {
		return historicalTransactions;
	}

	/**
	 * setHistoricalTransactions sets historical transactions
	 * 
	 * @param historicalTransactions the historical transactions
	 */
	public void setHistoricalTransactions(List<TableOrder> historicalTransactions) {
		this.historicalTransactions = historicalTransactions;
	}

	/**
	 * getKitchenOrders gets kitchen orders
	 * 
	 * @return the kitchen orders
	 */
	public Queue<TableOrderItem> getKitchenOrders() {
		return kitchenOrders;
	}

	/**
	 * setKitchenOrders sets kitchen orders
	 * 
	 * @param kitchenOrders the kitchen orders
	 */
	public void setKitchenOrders(Queue<TableOrderItem> kitchenOrders) {
		this.kitchenOrders = kitchenOrders;
	}

	/**
	 * Button Listener Performs Action Based On Specific Button
	 * 
	 * @author Zaid Omer && Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class ButtonListener implements ActionListener {

		/**
		 * actionPerformed performs the action that is needed to be performed from
		 * clicking a button
		 * 
		 * @param press used to determine which button is pressed
		 */
		public void actionPerformed(ActionEvent press) {
			if (press.getSource() == orderButton) {
				if (waitingList.size() > 0) {
					checkWaitingList();
				}
				OrderDialog orderDialog = new OrderDialog(self);
			} else if (press.getSource() == transactionButton) {
				TransactionDialog transactionDialog = new TransactionDialog(self);
			} else if (press.getSource() == kitchenButton) {
				KitchenDialog kitchenDialog = new KitchenDialog(self);

			} else if (press.getSource() == menuButton) {
				MenuDialog menuDialog = new MenuDialog(self);

			} else if (press.getSource() == setupButton) {
				SetupDialog setupDialog = new SetupDialog(self);

			} else if (press.getSource() == reservationButton) {
				ReservationBookDialog reservationBookDialog = new ReservationBookDialog(self);

			} else if (press.getSource() == employeeButton) {
				if (getManagers().size() == 0) {
					JOptionPane.showMessageDialog(null,
							"You must first create a manager before creating other employees.");
				}
				EmployeeDialog employeeDialog = new EmployeeDialog(self);

			} else if (press.getSource() == reportingButton) {
				ReportingDialog reportingDialog = new ReportingDialog(self);

			} else if (press.getSource() == logoutButton) {
				logout();
			} else if (press.getSource() == openFileButton) {
//				openDataFile();
				reloadDataFromDB();
			} else if (press.getSource() == saveFileButton) {
//				saveDataFile();
				saveDataToDB();
			} else if (press.getSource() == closeFileButton) {
				closeApplication();
			} else if (press.getSource() == loginButton) {
				login();
			}
		}

	}

}
