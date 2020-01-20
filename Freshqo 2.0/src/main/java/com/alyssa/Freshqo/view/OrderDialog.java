package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.Chef;
import com.alyssa.Freshqo.domain.Table;

/**
 * OrderDialog 
 * 
 * The dialog to see tables that can be filled for customers
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class OrderDialog extends JDialog {

	// VARIABLES
	private Restaurant restaurant;
	private JPanel generalTablesPanel;
	private JButton claimReservationButton;
	private JButton findTableButton;
	private JButton viewTableButton;
	private JButton viewWaitingListButton;
	private JButton returnToHomepageButton;
	private RestaurantTablesTableModel restaurantTablesTableModel;
	private JTable restaurantTablesTable;
	private JPanel tablesFoodPanel;
	private ImageIcon homepageBackground;
	private JLabel homepageBackgroundLabel;

	/**
	 * OrderDialog constructor initializes the restaurant and calls the initialize
	 * user interface method
	 * 
	 * @param restaurant the restaurant
	 */
	public OrderDialog(Restaurant restaurant) {
		if (restaurant.getCurrentUser() instanceof Chef) {
			JOptionPane.showMessageDialog(null, "Only a waiter or manager can access the orders button.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.restaurant = restaurant;
		initUI();
	}

	/**
	 * initializes the user interface
	 */
	private void initUI() {

		setModalityType(ModalityType.APPLICATION_MODAL);

		setUndecorated(true); // TODO change to true
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		// Table's Order Panel
		tablesFoodPanel = new JPanel();
		tablesFoodPanel.setLayout(null);

		// General Tables Panel
		generalTablesPanel = new JPanel();
		generalTablesPanel.setLayout(null);

		claimReservationButton = new JButton(new ImageIcon(getClass().getResource("../images/claim reservation button.JPG")));
		claimReservationButton.setBounds(865, 75, 120, 75);
		claimReservationButton.addActionListener(new ButtonListener());
		generalTablesPanel.add(claimReservationButton);

		findTableButton = new JButton(new ImageIcon(getClass().getResource("../images/find a table button.JPG")));
		findTableButton.setBounds(865, 165, 120, 75);
		findTableButton.addActionListener(new ButtonListener());
		generalTablesPanel.add(findTableButton);

		viewTableButton = new JButton(new ImageIcon(getClass().getResource("../images/view table button.JPG")));
		viewTableButton.setBounds(865, 255, 120, 75);
		viewTableButton.addActionListener(new ButtonListener());
		generalTablesPanel.add(viewTableButton);

		viewWaitingListButton = new JButton(new ImageIcon(getClass().getResource("../images/view waiting list button.JPG")));
		viewWaitingListButton.setBounds(865, 345, 120, 75);
		viewWaitingListButton.addActionListener(new ButtonListener());
		generalTablesPanel.add(viewWaitingListButton);

		returnToHomepageButton = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnToHomepageButton.setBounds(865, 435, 120, 75);
		returnToHomepageButton.addActionListener(new ButtonListener());
		generalTablesPanel.add(returnToHomepageButton);

		restaurantTablesTableModel = new RestaurantTablesTableModel();
		restaurantTablesTable = new JTable(restaurantTablesTableModel);
		restaurantTablesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		restaurantTablesTable.setBounds(25, 100, 400, 400);
		restaurantTablesTableModel.addRows(restaurant.getTables());

		JScrollPane tableListScrollPane = new JScrollPane(restaurantTablesTable);
		tableListScrollPane.setBounds(25, 100, 400, 400);
		generalTablesPanel.add(tableListScrollPane);

		getContentPane().add(generalTablesPanel);

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);

		generalTablesPanel.add(homepageBackgroundLabel);
		setVisible(true);
	}

	/**
	 * ButtonListener Performs actions based on specific button
	 * 
	 * @author Alyssa Gao
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
			if (press.getSource() == claimReservationButton) {
				// claim only for the current date
				if (restaurant.getTables().size() == 0) {
					JOptionPane.showMessageDialog(null, "Your restaurant currently has no tables.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (restaurant.findAvailableWaiter() == null) {
					JOptionPane.showMessageDialog(null, "There are no available waiters.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String customerNameUnderReservation = JOptionPane
						.showInputDialog("Please input the name under reservation: ");
				if (customerNameUnderReservation != null) {
					restaurant.claimReservation(customerNameUnderReservation.toUpperCase());
					restaurantTablesTableModel.refresh();
				}
			} else if (press.getSource() == viewTableButton) {
				int selectedRow = restaurantTablesTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please choose a table to view.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (restaurantTablesTable.getValueAt(selectedRow, 1).equals(("Not Occupied"))) {
					JOptionPane.showMessageDialog(null, "This table is currently not occupied.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					AddFoodDialog addFoodDialog = new AddFoodDialog(restaurant, selectedRow);
					restaurantTablesTableModel.refresh();
				}
			} else if (press.getSource() == findTableButton) {
				if (restaurant.getTables().size() == 0) {
					JOptionPane.showMessageDialog(null, "Your restaurant currently has no tables.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				FindTableDialog findTableDialog = new FindTableDialog(restaurant);
				restaurantTablesTableModel.refresh();
			} else if (press.getSource() == viewWaitingListButton) {
				WaitingListDialog waitingListDialog = new WaitingListDialog(restaurant);

			} else if (press.getSource() == returnToHomepageButton) {
				dispose();
			}
		}
	}

	/**
	 * RestaurantTablesTableModel The table model to display tables and the
	 * customers at them
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class RestaurantTablesTableModel extends AbstractTableModel {
		/**
		 * the names of each column in the table
		 */
		private final String[] tableListColumns = { "Table Name", "Occupied" };

		/**
		 * the class type for each column
		 */
		private final Class[] columnClasses = { String.class, String.class };

		/**
		 * the list of recipes that are to be displayed within the table
		 */
		private List<Table> tablesData = new ArrayList<>();

		@Override
		/**
		 * getColumnCount the number of columns in the table
		 * 
		 * @return the number of columns
		 */
		public int getColumnCount() {
			return this.tableListColumns.length;
		}

		@Override
		/**
		 * getRowCount the number of rows in the table
		 * 
		 * @return the number of rows
		 */
		public int getRowCount() {
			return tablesData.size();
		}

		@Override
		/**
		 * getColumnName
		 * 
		 * @param col the column number
		 * @return the name of the column
		 */
		public String getColumnName(int col) {
			return this.tableListColumns[col];
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

			Table table = this.tablesData.get(row);
			switch (col) {
			case 0:
				return table.getTableName();
			default:
				return table.isOccupied() ? "Occupied by " + table.getCustomer().getName() : "Not Occupied";
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

		/**
		 * updateRow when an table is modified, the row must be then updated
		 * 
		 * @param table the table to place in the table and add to the current list of
		 *              tables
		 * @param row   the row that needs to be updated due to a change in the table
		 */
		public void updateRow(Table table, int row) {
			this.tablesData.set(row, table);
			fireTableRowsUpdated(row, row);
		}

		/**
		 * insertRow inserts a row in the table with a table
		 * 
		 * @param position the position to put the row
		 * @param table    the table to place in the table and add to the current list
		 *                 of tables
		 */
		public void insertRow(int position, Table table) {
			this.tablesData.add(table);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * addRow adds a row at the bottom of the table with a new table
		 * 
		 * @param table the table to be placed in the table
		 */
		public void addRow(Table table) {
			insertRow(getRowCount(), table);
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param tableList the list of tables that are to be put into the table
		 */
		public void addRows(List<Table> tableList) {
			for (Table table : tableList) {
				addRow(table);
			}
		}

		/**
		 * removeRow removes a specific row in the table
		 * 
		 * @param position the position of the table to be removed
		 */
		public void removeRow(int position) {
			this.tablesData.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * getData gets the list of tables
		 * 
		 * @return the list of tables
		 */
		public List<Table> getData() {
			return tablesData;
		}

		/**
		 * setData gets the list of tables
		 * 
		 * @param tablesData the list of tables
		 */
		public void setData(List<Table> tablesData) {
			this.tablesData = tablesData;
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * refresh refresh the elements in the table
		 */
		public void refresh() {
			fireTableRowsUpdated(0, getRowCount());
		}

	}
}
