package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.AbstractTableModel;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.Manager;
import com.alyssa.Freshqo.domain.Reservation;
import com.alyssa.Freshqo.domain.Table;

/**
 * SetupDialog 
 * 
 * The Dialog to setup tables and number of seats/table
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class SetupDialog extends JDialog {
	
	//Variables
	private Restaurant restaurant;
	private List<Table> tables;
	private List<Reservation> reservationBook;
	private String numPeople;
	private boolean canBeReserved;
	private JPanel tableManagementPanel;
	private JSpinner numSeatsSpinner;
	private JTextField tableNameTextField;
	private JCheckBox canBeReservedCheckBox;
	private JButton addTableButton;
	private JButton deleteTableButton;
	private JButton returnButton;
	private TableLayoutTableModel tableLayoutTableModel;
	private JTable tableLayoutTable;
	private ImageIcon homepageBackground;
	private JLabel homepageBackgroundLabel;

	/**
	 * constructor to initialize restaurant, tables, reservation book and user interface
	 * @param restaurant
	 */
	public SetupDialog(Restaurant restaurant) {
		if (!(restaurant.getCurrentUser() instanceof Manager)) {
			JOptionPane.showMessageDialog(null, "Only a manager can access and use the setup button.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.restaurant = restaurant;
		tables = restaurant.getTables();
		reservationBook = restaurant.getReservationBook();
		initUI();
	}

	/**
	 * initializes the user interface
	 */
	private void initUI() {

		setModalityType(ModalityType.APPLICATION_MODAL);

		setUndecorated(true);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		tableManagementPanel = new JPanel();
		tableManagementPanel.setLayout(null);
		getContentPane().add(tableManagementPanel);

		// Add Table Panel
		JLabel tableNameLabel = new JLabel("Table Name");
		tableNameLabel.setBounds(25, 150, 300, 30);
		tableNameTextField = new JTextField();
		tableNameTextField.setBounds(150, 150, 300, 30);

		JLabel numSeatsLabel = new JLabel("Number of Seats:");
		numSeatsLabel.setBounds(25, 200, 300, 30);

		tableManagementPanel.add(tableNameLabel);
		tableManagementPanel.add(tableNameTextField);
		tableManagementPanel.add(numSeatsLabel);

		SpinnerModel tableSpinnerModel = new SpinnerNumberModel(2, 2, 10, 2);
		numSeatsSpinner = new JSpinner(tableSpinnerModel);
		numSeatsSpinner.setBounds(150, 200, 50, 30);
		tableManagementPanel.add(numSeatsSpinner);

		canBeReservedCheckBox = new JCheckBox("Reservable", false);
		canBeReservedCheckBox.setBounds(25, 250, 100, 30);
		tableManagementPanel.add(canBeReservedCheckBox);

		addTableButton = new JButton(new ImageIcon(getClass().getResource("../images/add table button.JPG")));
		addTableButton.setBounds(865, 75, 120, 75);
		addTableButton.addActionListener(new ButtonListener());
		tableManagementPanel.add(addTableButton);

		tableLayoutTableModel = new TableLayoutTableModel();
		tableLayoutTable = new JTable(tableLayoutTableModel);
		tableLayoutTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableLayoutTable.setBounds(500, 100, 300, 400);
		tableLayoutTableModel.addRows(tables);

		JScrollPane tableListScrollPane = new JScrollPane(tableLayoutTable);
		tableListScrollPane.setBounds(500, 100, 300, 400);
		tableManagementPanel.add(tableListScrollPane);

		deleteTableButton = new JButton(new ImageIcon(getClass().getResource("../images/delete table button.JPG")));
		deleteTableButton.setBounds(865, 165, 120, 75);
		deleteTableButton.addActionListener(new ButtonListener());
		tableManagementPanel.add(deleteTableButton);

		returnButton = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnButton.setBounds(865, 435, 120, 75);
		returnButton.addActionListener(new ButtonListener());
		tableManagementPanel.add(returnButton);

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		tableManagementPanel.add(homepageBackgroundLabel);
		
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
			if (press.getSource() == addTableButton) {
				String tableName = tableNameTextField.getText().replaceAll(" ", "");
				if (tableName.equals("")) {
					JOptionPane.showMessageDialog(null, "Please input a table name.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (int i = 0; i < tables.size(); i++) {
					if (tables.get(i).getTableName().equals(tableName.toUpperCase())) {
						JOptionPane.showMessageDialog(null, "There is already a table with this name.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				Table table = new Table(tableName, (int) numSeatsSpinner.getValue(),
						canBeReservedCheckBox.isSelected());
				tables.add(table);
				tableLayoutTableModel.addRow(table);

				if (restaurant.getWaitingList().size() > 0) {
					restaurant.checkWaitingList();
				}
			} else if (press.getSource() == deleteTableButton) {
				int selectedRow = tableLayoutTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please choose a table to delete.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					if (tables.get(selectedRow).isOccupied()) {
						JOptionPane.showMessageDialog(null,
								"This table is currently occupied. You can only remove the table if it is unoccupied.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// check that there was no previous reservation with the table
					for (int i = 0; i < reservationBook.size(); i++) {
						if (reservationBook.get(i).getTable() == tables.get(selectedRow)) {
							JOptionPane.showMessageDialog(null,
									"This table is currently saved for a reservation. You can only remove it once the reservation is cleared and it is not being occupied.",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					tableLayoutTableModel.removeRow(selectedRow);
					tables.remove(selectedRow);
				}
			} else if (press.getSource() == returnButton) {
				dispose();
			}
		}
	}

	/**
	 * TableLayoutTableModel The table model to display tables
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class TableLayoutTableModel extends AbstractTableModel {
		/**
		 * the names of each column in the table
		 */
		private final String[] tableLayoutListColumns = { "Table Name", "Num. of Seats", "Reservable" };

		/**
		 * the class type for each column
		 */
		private final Class[] columnClasses = { String.class, int.class, boolean.class };

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
			return this.tableLayoutListColumns.length;
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
			return this.tableLayoutListColumns[col];
		}

		@Override
		/**
		 * getValueAt finds the value at the specific row and column number *
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
			case 1:
				return table.getNumSeats();
			// case 2:
			default:
				return table.canBeReserved();
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
			Table table = this.tablesData.get(row);
			switch (col) {
			case 0:
				table.setTableName((String) value);
				break;
			case 1:
				table.setNumSeats((int) value);
				// case 2:
			default:
				table.setCanBeReserved((boolean) value);
			}

			fireTableCellUpdated(row, col);
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
		 * addRow adds a row at the bottom of the table with a new recipe
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
		 * @param position the position of the recipe to be removed
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
		 * @param data the list of tables
		 */
		public void setData(List<Table> tablesData) {
			this.tablesData = tablesData;
			fireTableRowsInserted(0, getRowCount());
		}
	}
}
