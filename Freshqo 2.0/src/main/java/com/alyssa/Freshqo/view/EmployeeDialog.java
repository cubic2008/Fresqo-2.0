package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.Chef;
import com.alyssa.Freshqo.domain.Employee;
import com.alyssa.Freshqo.domain.Manager;
import com.alyssa.Freshqo.domain.Waiter;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 * EmployeeDialog 
 * 
 * A File to Create the Employee Dialog Pop-up
 * 
 * @author Alyssa Gao && Zaid Omer
 * @version June 13, 2019
 */
public class EmployeeDialog extends JDialog {
	private Restaurant restaurant;
	private JPanel viewEmployeesPanel;
	private JPanel addEmployeesPanel;
	private JTextField employeeNameTextField;
	private JTextField payTextField;
	private JTextField userIDTextField;
	private JPasswordField passwordTextField;
	private JButton createEmployee;
	private JTextField emailTextField;
	private JTextField SINNumberTextField;
	private DatePicker datePicker;
	private JRadioButton chefRadioButton;
	private JRadioButton waiterRadioButton;
	private JRadioButton managerRadioButton;
	private boolean numeric = true;
	private ViewEmployeesTableModel viewEmployeesTableModel;
	private JTable viewEmployeesTable;
	private JButton searchButton;
	private JButton returnToHomeButton1;
	private JButton returnToHomeButton2;
	private JRadioButton searchChefRadioButton;
	private JRadioButton searchWaiterRadioButton;
	private JRadioButton searchManagerRadioButton;
	private JButton deleteEmployeeButton;
	private ImageIcon homepageBackground;
	private ImageIcon homepageBackground2;
	private JLabel homepageBackgroundLabel;
	private JLabel homepageBackgroundLabel2;

	/**
	 * initializes the restaurant and calls the initialize user interface method
	 * 
	 * @param restaurant the restaurant
	 */
	public EmployeeDialog(Restaurant restaurant) {
		if (!(restaurant.getCurrentUser() instanceof Manager)) {
			JOptionPane.showMessageDialog(null, "Only a manager can access the employee button.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.restaurant = restaurant;
		initUI();
	}

	/**
	 * initializes the user interface
	 */
	public void initUI() {

		// The Following up until indicated written by Zaid Omer
		setModalityType(ModalityType.APPLICATION_MODAL);

		setUndecorated(true);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		viewEmployeesPanel = new JPanel();
		viewEmployeesPanel.setLayout(null);

		addEmployeesPanel = new JPanel();
		addEmployeesPanel.setLayout(null);

		// Tabs
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Add Employee", null, addEmployeesPanel);
		tabbedPane.addTab("View Employees", null, viewEmployeesPanel);

		// Add Employee Panel Setup

		// Employee Name
		JLabel employeeNameLabel = new JLabel("Employee Name:");
		employeeNameLabel.setBounds(25, 100, 200, 30);
		addEmployeesPanel.add(employeeNameLabel);

		employeeNameTextField = new JTextField();
		employeeNameTextField.setBounds(150, 100, 200, 30);
		addEmployeesPanel.add(employeeNameTextField);

		// Employee Pay
		JLabel payLabel = new JLabel("Pay (Hourly Rate $):");
		payLabel.setBounds(25, 150, 200, 30);
		addEmployeesPanel.add(payLabel);

		payTextField = new JTextField();
		payTextField.setBounds(150, 150, 200, 30);
		addEmployeesPanel.add(payTextField);

		// Employee userID
		JLabel userIDLabel = new JLabel("User ID:");
		userIDLabel.setBounds(25, 200, 200, 30);
		addEmployeesPanel.add(userIDLabel);

		userIDTextField = new JTextField();
		userIDTextField.setBounds(150, 200, 200, 30);
		addEmployeesPanel.add(userIDTextField);

		// Enter user password
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(25, 250, 200, 30);
		addEmployeesPanel.add(passwordLabel);

		passwordTextField = new JPasswordField(20);
		passwordTextField.setBounds(150, 250, 200, 30);
		addEmployeesPanel.add(passwordTextField);

		// Type of Employee
		JLabel employeeTypeLabel = new JLabel("Type of Employee: ");
		employeeTypeLabel.setBounds(25, 300, 200, 30);
		addEmployeesPanel.add(employeeTypeLabel);

		chefRadioButton = new JRadioButton("Chef", true);
		chefRadioButton.setBounds(150, 300, 200, 30);
		addEmployeesPanel.add(chefRadioButton);

		waiterRadioButton = new JRadioButton("Waiter");
		waiterRadioButton.setBounds(150, 330, 200, 30);
		addEmployeesPanel.add(waiterRadioButton);

		managerRadioButton = new JRadioButton("Manager");
		managerRadioButton.setBounds(150, 360, 200, 30);
		addEmployeesPanel.add(managerRadioButton);

		ButtonGroup employeeTypeGroup = new ButtonGroup();
		employeeTypeGroup.add(chefRadioButton);
		employeeTypeGroup.add(waiterRadioButton);
		employeeTypeGroup.add(managerRadioButton);

		// Date Added
		JLabel dateLabel = new JLabel("Date Hired:");
		dateLabel.setBounds(400, 100, 200, 30);
		addEmployeesPanel.add(dateLabel);

		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
		datePicker = new DatePicker(dateSettings);
		datePicker.setBounds(525, 100, 200, 30);
		datePicker.setDateToToday();
		datePicker.getComponentDateTextField().setEditable(false);
		addEmployeesPanel.add(datePicker);

		// Email
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(400, 150, 200, 30);
		addEmployeesPanel.add(emailLabel);

		emailTextField = new JTextField();
		emailTextField.setBounds(525, 150, 200, 30);
		addEmployeesPanel.add(emailTextField);

		// SIN Number
		JLabel SINNumberLabel = new JLabel("SIN Number:");
		SINNumberLabel.setBounds(400, 200, 200, 30);
		addEmployeesPanel.add(SINNumberLabel);

		SINNumberTextField = new JTextField();
		SINNumberTextField.setBounds(525, 200, 200, 30);
		addEmployeesPanel.add(SINNumberTextField);

		// Submit data, make employee
		createEmployee = new JButton(new ImageIcon(getClass().getResource("../images/add employee button.JPG")));
		createEmployee.setBounds(865, 75, 120, 75);
		createEmployee.addActionListener(new ButtonListener());
		addEmployeesPanel.add(createEmployee);

		returnToHomeButton1 = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnToHomeButton1.setBounds(865, 435, 120, 75);
		returnToHomeButton1.addActionListener(new ButtonListener());
		addEmployeesPanel.add(returnToHomeButton1);

		// ========== View Employees Panel Setup =============== By: Alyssa Gao
		searchChefRadioButton = new JRadioButton("View Chefs", true);
		searchChefRadioButton.setBounds(150, 200, 200, 30);
		viewEmployeesPanel.add(searchChefRadioButton);

		searchWaiterRadioButton = new JRadioButton("View Waiters");
		searchWaiterRadioButton.setBounds(150, 230, 200, 30);
		viewEmployeesPanel.add(searchWaiterRadioButton);

		searchManagerRadioButton = new JRadioButton("View Managers");
		searchManagerRadioButton.setBounds(150, 260, 200, 30);
		viewEmployeesPanel.add(searchManagerRadioButton);

		ButtonGroup searchEmployeeTypeGroup = new ButtonGroup();
		employeeTypeGroup.add(searchChefRadioButton);
		employeeTypeGroup.add(searchWaiterRadioButton);
		employeeTypeGroup.add(searchManagerRadioButton);

		searchButton = new JButton(new ImageIcon(getClass().getResource("../images/search employee button.JPG")));
		searchButton.setBounds(865, 75, 120, 75);
		searchButton.addActionListener(new ButtonListener());
		viewEmployeesPanel.add(searchButton);

		deleteEmployeeButton = new JButton(new ImageIcon(getClass().getResource("../images/delete employee button.JPG")));
		deleteEmployeeButton.setBounds(865, 165, 120, 75);
		deleteEmployeeButton.addActionListener(new ButtonListener());
		viewEmployeesPanel.add(deleteEmployeeButton);

		viewEmployeesTableModel = new ViewEmployeesTableModel();
		viewEmployeesTable = new JTable(viewEmployeesTableModel);
		viewEmployeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		viewEmployeesTable.setBounds(400, 50, 400, 400);
		viewEmployeesTableModel.addRows(restaurant.getEmployees());

		JScrollPane tableListScrollPane = new JScrollPane(viewEmployeesTable);
		tableListScrollPane.setBounds(400, 50, 400, 400);
		viewEmployeesPanel.add(tableListScrollPane);
		viewEmployeesTable.setVisible(true);

		returnToHomeButton2 = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnToHomeButton2.setBounds(865, 435, 120, 75);
		returnToHomeButton2.addActionListener(new ButtonListener());
		viewEmployeesPanel.add(returnToHomeButton2);

		// background images
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		addEmployeesPanel.add(homepageBackgroundLabel);

		homepageBackground2 = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel2 = new JLabel(homepageBackground2);
		homepageBackgroundLabel2.setBounds(0, 0, 1000, 600);
		viewEmployeesPanel.add(homepageBackgroundLabel2);

		// Set Visible
		getContentPane().add(tabbedPane);
		setVisible(true);
	}

	/**
	 * ButtonListener Performs actions based on specific button
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
			if (press.getSource() == createEmployee) {
				if ((employeeNameTextField.getText().isEmpty()) || (payTextField.getText().isEmpty())
						|| (userIDTextField.getText().isEmpty()) || (passwordTextField.getText().isEmpty())
						|| (datePicker.getText().isEmpty()) || (emailTextField.getText().isEmpty())
						|| (SINNumberTextField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Please enter all required info.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					for (int i = 0; i < restaurant.getEmployees().size(); i++) {
						if (restaurant.getEmployees().get(i).getUserID().equals((userIDTextField.getText()))) {
							JOptionPane.showMessageDialog(null, "There is already an employee with that user ID",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}

					try {
						double pay = Double.parseDouble(payTextField.getText());
					} catch (NumberFormatException excption) {
						JOptionPane.showMessageDialog(null, "Please enter a numeric value for pay.", "Error",
								JOptionPane.ERROR_MESSAGE);
						numeric = false;
					}

					if ((restaurant.getEmployees().size() == 0) && (!managerRadioButton.isSelected())) {
						JOptionPane.showMessageDialog(null,
								"Please first create a manager before creating other employees.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					if (numeric) {
						String employeeName = employeeNameTextField.getText().toUpperCase();
						double pay = Double.parseDouble(payTextField.getText());
						String userID = userIDTextField.getText();
						String password = new String(passwordTextField.getPassword());

						String dateHired = datePicker.getText();
						String email = emailTextField.getText();
						String SINNumber = SINNumberTextField.getText();

						if (chefRadioButton.isSelected()) {
							Employee newChef = new Chef(employeeName, pay, userID, password, dateHired, email,
									SINNumber, "Chef");
							restaurant.addEmployee(newChef);
							viewEmployeesTableModel.addRow(newChef);

						} else if (waiterRadioButton.isSelected()) {
							Employee newWaiter = new Waiter(employeeName, pay, userID, password, dateHired, email,
									SINNumber, "Waiter");
							restaurant.addEmployee(newWaiter);
							viewEmployeesTableModel.addRow(newWaiter);
						} else {
							Employee newManager = new Manager(employeeName, pay, userID, password, dateHired, email,
									SINNumber, "Manager");
							restaurant.addEmployee(newManager);
							viewEmployeesTableModel.addRow(newManager);
						}
						JOptionPane.showMessageDialog(null, employeeName + " has been added.");
					}
				}
			} else if (press.getSource() == searchButton) {
				if (searchWaiterRadioButton.isSelected()) {
					viewEmployeesTableModel.clearAll();
					viewEmployeesTableModel.addWaiterRows((restaurant.getWaiters()));
				} else if (searchChefRadioButton.isSelected()) {
					viewEmployeesTableModel.clearAll();
					viewEmployeesTableModel.addChefRows((restaurant.getChefs()));
				} else if (searchManagerRadioButton.isSelected()) {
					viewEmployeesTableModel.clearAll();
					viewEmployeesTableModel.addManagerRows((restaurant.getManagers()));
				} else {
					viewEmployeesTableModel.clearAll();
					viewEmployeesTableModel.addRows((restaurant.getEmployees()));
				}
			} else if (press.getSource() == deleteEmployeeButton) {
				int selectedRow = viewEmployeesTable.getSelectedRow();
				Employee employee = null;
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please choose an employee to delete.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (int i = 0; i < restaurant.getEmployees().size(); i++) {
					if (restaurant.getEmployees().get(i).getUserID()
							.equals(viewEmployeesTableModel.getValueAt(selectedRow, 0))) {
						employee = restaurant.getEmployees().get(i);
					}
				}

				if ((employee instanceof Manager) && (restaurant.getManagers().size() == 1)) {
					JOptionPane.showMessageDialog(null, "You must have at least one maanger at all times.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				viewEmployeesTableModel.removeRow(selectedRow);
				restaurant.getEmployees().remove(employee);

			} else if ((press.getSource() == returnToHomeButton1) || (press.getSource() == returnToHomeButton2)) {
				dispose();
			}
		}
	}

	/**
	 * ViewEmployeesTableModel The table model to display employees
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class ViewEmployeesTableModel extends AbstractTableModel {
		/**
		 * the names of each column in the table
		 */
		private final String[] tableLayoutListColumns = { "User ID", "Name", "Position" };

		/**
		 * the class type for each column
		 */
		private final Class[] columnClasses = { String.class, String.class, String.class };

		/**
		 * the list of tables that are to be displayed within the table
		 */
		private List<Employee> employees = new ArrayList<>();

		@Override
		/**
		 * getColumnCount the number of columns in the table
		 * 
		 * @return the number of columns
		 */
		public int getColumnCount() {
			return this.tableLayoutListColumns.length;
		}

		@Override
		/**
		 * getRowCount the number of rows in the table
		 * 
		 * @return the number of rows
		 */
		public int getRowCount() {
			return employees.size();
		}

		@Override
		/**
		 * getColumnName
		 * 
		 * @param col the column number
		 * @return the name of the column
		 */
		public String getColumnName(int col) {
			return this.tableLayoutListColumns[col];
		}

		@Override
		/**
		 * getValueAt finds the value at the specific row and column number
		 * 
		 * @param row the row number
		 * @param col the column number
		 * @return the value at the specific row and column
		 */
		public Object getValueAt(int row, int col) {

			Employee employee = this.employees.get(row);
			switch (col) {
			case 0:
				return employee.getUserID();
			case 1:
				return employee.getName();
			default:
				return employee.getPosition();
			}
		}

		@Override
		/**
		 * getColumnClass finds the class type for a specific column
		 * 
		 * @param col the column number
		 * @return the class type for the specific column
		 */
		public Class<?> getColumnClass(int col) {
			return this.columnClasses[col];
		}

		@Override
		/**
		 * isCellEditable checks if the user can edit the cell
		 * 
		 * @param row the row number
		 * @param col the column number
		 * @return whether or not the cell is editable
		 */
		public boolean isCellEditable(int row, int col) {
			return false;
		}

		@Override
		/**
		 * setValueAt sets a value at the specific row and column
		 * 
		 * @param value the value to be set
		 * @param row   the row number
		 * @param col   the column number
		 */
		public void setValueAt(Object value, int row, int col) {
			Employee employee = this.employees.get(row);
			switch (col) {
			case 0:
				employee.setUserID((String) value);
			case 1:
				employee.setName((String) value);
				break;
			default:
				employee.setPosition((String) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * updateRow when an table is modified, the row must be then updated
		 * 
		 * @param employee the employee to place in the table and add to the current
		 *                 list of employees
		 * @param row      the row that needs to be updated due to a change in the table
		 */
		public void updateRow(Employee employee, int row) {
			this.employees.set(row, employee);
			fireTableRowsUpdated(row, row);
		}

		/**
		 * insertRow inserts a row in the table with a table
		 * 
		 * @param position the position to put the row
		 * @param employee the employee to place in the table and add to the current
		 *                 list of employees
		 */
		public void insertRow(int position, Employee employee) {
			this.employees.add(employee);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * addRow adds a row at the bottom of the table
		 * 
		 * @param employee the employee to be placed in the table
		 */
		public void addRow(Employee employee) {
			insertRow(getRowCount(), employee);
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param employeeList the list of employees that are to be put into the table
		 */
		public void addRows(List<Employee> employeeList) {
			for (Employee employee : employeeList) {
				addRow(employee);
			}
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param chefList the list of chefs that are to be put into the table
		 */
		public void addChefRows(List<Chef> chefList) {
			for (Employee employee : chefList) {
				addRow(employee);
			}
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param waiterList the list of waiters that are to be put into the table
		 */
		public void addWaiterRows(List<Waiter> waiterList) {
			for (Employee employee : waiterList) {
				addRow(employee);
			}
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param managerList the list of managers that are to be put into the table
		 */
		public void addManagerRows(List<Manager> managerList) {
			for (Employee employee : managerList) {
				addRow(employee);
			}
		}

		/**
		 * removeRow removes a specific row in the table
		 * 
		 * @param position the position of the employee to be removed
		 */
		public void removeRow(int position) {
			this.employees.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * getData gets the list of tables
		 * 
		 * @return the list of employees
		 */
		public List<Employee> getData() {
			return employees;
		}

		/**
		 * setData gets the list of employees
		 * 
		 * @param employees the list of employees
		 */
		public void setData(List<Employee> employees) {
			this.employees = employees;
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * clearAll clears all rows on the table
		 */
		public void clearAll() {
			for (int i = employees.size() - 1; i >= 0; i--) {
				removeRow(i);
			}
		}
	}

}
