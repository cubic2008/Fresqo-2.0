package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.alyssa.Freshqo.domain.Customer;

/**
 * WaitingListDialog
 * 
 * The dialog to display waiting list
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class WaitingListDialog extends JDialog {

	// VARIABLES
	private JPanel panel;
	private Restaurant restaurant;
	private ImageIcon background;
	private WaitingListTableModel waitingListTableModel;
	private JTable waitingListTable;
	private JButton deleteButton;

	
	/**
	 * initializes restaurant and user interface
	 * @param restaurant
	 */
	public WaitingListDialog(Restaurant restaurant) {
		this.restaurant = restaurant;
		initUI();
	}

	/**
	 * initializes user interface
	 */
	private void initUI() {

		setModalityType(ModalityType.APPLICATION_MODAL);

		setUndecorated(false);
		setSize(200, 300);
		setLocationRelativeTo(null);
		setResizable(false);

		panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel);
		
		waitingListTableModel = new WaitingListTableModel();
		waitingListTable = new JTable(waitingListTableModel);
		waitingListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		waitingListTable.setBounds(25, 20, 150, 150);

		
		List<Customer> customerList = new ArrayList<>();
		Iterator<Customer> customerIterator = restaurant.getWaitingList().iterator();
		while ( customerIterator.hasNext() ) {
		customerList.add( customerIterator.next() );
		}
		waitingListTableModel.addRows(customerList);

		JScrollPane tableListScrollPane = new JScrollPane(waitingListTable);
		tableListScrollPane.setBounds(25, 20, 150, 150);
		panel.add(tableListScrollPane);
		
		deleteButton = new JButton(new ImageIcon(getClass().getResource("../images/delete button.JPG")));
		deleteButton.setBounds(40, 200, 120, 50);
		panel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			/**
			 * actionPerformed Invoked when an action occurs
			 *
			 * @param press the action that occurs
			 */
			public void actionPerformed(ActionEvent press) {
				int selectedRow = waitingListTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null,
							"Please choose a customer to remove from the waiting list.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				waitingListTableModel.removeRow(selectedRow);
				restaurant.getWaitingList().dequeue(customerList.get(selectedRow));
				customerList.remove(selectedRow);
				

			}
		});


		// background image
		background = new ImageIcon(getClass().getResource("../images/small dialog background.JPG"));
		JLabel backgroundLabel = new JLabel(background);
		backgroundLabel.setBounds(0, 0, 200, 300);
		panel.add(backgroundLabel);

		setVisible(true);
	}

	/**
	 * WaitingListTableModel
	 * 
	 * The table model for waiting list
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class WaitingListTableModel extends AbstractTableModel {
		/**
		 * the names of each column in the table
		 */
		private final String[] tableLayoutListColumns = { "Customer Name", "Num. of People" };

		/**
		 * the class type for each column
		 */
		private final Class[] columnClasses = { String.class, int.class };

		/**
		 * the list of recipes that are to be displayed within the table
		 */
		private List<Customer> waitingListData = new ArrayList<>();

		@Override
		/**
		 * the number of columns in the table
		 * 
		 * @return the number of columns
		 */
		public int getColumnCount() {
			return this.tableLayoutListColumns.length;
		}

		@Override
		/**
		 * the number of rows in the table
		 * 
		 * @return the number of rows
		 */
		public int getRowCount() {
			return waitingListData.size();
		}

		@Override
		/**
		 * gets column name
		 * 
		 * @param col the column number
		 * @return the name of the column
		 */
		public String getColumnName(int col) {
			return this.tableLayoutListColumns[col];
		}

		@Override
		/**
		 * finds the value at the specific row and column number
		 * 
		 * @param row the row number
		 * @param col the column number
		 * @return the value at the specific row and column
		 */
		public Object getValueAt(int row, int col) {

			Customer customer = this.waitingListData.get(row);
			switch (col) {
			case 0:
				return customer.getName();
			default:
				return customer.getNumPeople();
			}
		}

		@Override
		/**
		 * finds the class type for a specific column
		 * 
		 * @param col the column number
		 * @return the class type for the specific column
		 */
		public Class<?> getColumnClass(int col) {
			return this.columnClasses[col];
		}

		@Override
		/**
		 * checks if the user can edit the cell
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
		 * sets a value at the specific row and column
		 * 
		 * @param value the value to be set
		 * @param row   the row number
		 * @param col   the column number
		 */
		public void setValueAt(Object value, int row, int col) {
			Customer customer = this.waitingListData.get(row);
			switch (col) {
			case 0:
				customer.setName((String) value);
				break;
			case 1:
				customer.setNumPeople((int) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * when an table is modified, the row must be then updated
		 * 
		 * @param customer the customer to place in the table and add to the current
		 *                 list of tables
		 * @param row      the row that needs to be updated due to a change in the table
		 */
		public void updateRow(Customer customer, int row) {
			this.waitingListData.set(row, customer);
			fireTableRowsUpdated(row, row);
		}

		/**
		 * inserts a row in the table with a table
		 * 
		 * @param position the position to put the row
		 * @param customer the customer to place in the table and add to the current
		 *                 list of tables
		 */
		public void insertRow(int position, Customer customer) {
			this.waitingListData.add(customer);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * adds a row at the bottom of the table with a new recipe
		 * 
		 * @param customer the customer to be placed in the table
		 */
		public void addRow(Customer customer) {
			insertRow(getRowCount(), customer);
		}

		/**
		 * adds 2+ rows into the table
		 * 
		 * @param waitingListData the list of customers that are to be put into the table
		 */
		public void addRows(List<Customer> waitingListData) {
			for (Customer customer : waitingListData) {
				addRow(customer);
			}
		}

		/**
		 * removes a specific row in the table
		 * 
		 * @param position the position of the customer to be removed
		 */
		public void removeRow(int position) {
			this.waitingListData.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

	}

}
