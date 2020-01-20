package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.time.DayOfWeek;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.MenuItem;
import com.alyssa.Freshqo.domain.TableOrder;
import com.alyssa.Freshqo.domain.TableOrderItem;
import com.alyssa.Freshqo.utils.Utils;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

/**
 * TransactionDialog
 * The dialog to display historical transactions
 * @author Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */
public class TransactionDialog extends JDialog {

	// VARIABLES
	private Restaurant restaurant;
	private JLabel dateLabel;
	private ImageIcon homepageBackground;
	private JPanel panel;
	private JButton returnToHomepageButton;
	private JLabel homepageBackgroundLabel;
	private JTable transactionTable;
	private DatePickerSettings dateSettings;
	private DatePicker datePicker;
	private JButton lookUpButton;
	private JButton resetButton;
	private TransactionLayoutTableModel transactionTableLayout;
	private OrderLayoutTableModel orderLayoutTableLayout;
	private JTable orderTable;
	private JLabel menuItemImageLabel;
	private MenuItem selectedMenuItem;
	private final DecimalFormat currencyFormat = new DecimalFormat("##0.00");

	/**
	 * initializes the restaurant and calls the initialize user interface method
	 * @param restaurant the restaurant
	 */
	public TransactionDialog(Restaurant restaurant) {
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

		panel = new JPanel();
		panel.setLayout(null);

		//Return Button
		returnToHomepageButton = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnToHomepageButton.setBounds(865, 435, 120, 75);
		returnToHomepageButton.addActionListener(new ButtonListener());
		panel.add(returnToHomepageButton);

		//JTable of Transactions
		transactionTableLayout = new TransactionLayoutTableModel();
		transactionTable = new JTable(transactionTableLayout);
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionTable.setBounds(25, 150, 455, 400);
		transactionTable.addMouseListener(new MyMouseListener());
		transactionTableLayout.addRows(restaurant.getHistoricalTransactions());

		JScrollPane transactionListScrollPane = new JScrollPane(transactionTable);
		transactionListScrollPane.setBounds(25, 150, 455, 400);
		panel.add(transactionListScrollPane);

		//JTable for specifics on the order selected
		orderLayoutTableLayout = new OrderLayoutTableModel();
		orderTable = new JTable(orderLayoutTableLayout);
		orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderTable.setBounds(500, 50, 320, 400);
		orderTable.addMouseListener(new MyMouseListener());

		JScrollPane orderListScrollPane = new JScrollPane(orderTable);
		orderListScrollPane.setBounds(500, 50, 320, 350);
		panel.add(orderListScrollPane);

		// Date
		dateLabel = new JLabel("<html>Transaction:<p>Date</html>");
		dateLabel.setBounds(25, 100, 100, 30);
		panel.add(dateLabel);
		dateSettings = new DatePickerSettings();
		dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
		datePicker = new DatePicker(dateSettings);
		dateSettings.setDateRangeLimits(null, null);
		datePicker.setBounds(150, 100, 200, 30);
		datePicker.setDateToToday();
		datePicker.getComponentDateTextField().setEditable(false);
		panel.add(datePicker);

		//Confirm Search Button
		lookUpButton = new JButton(new ImageIcon(getClass().getResource("../images/look up button.JPG")));
		lookUpButton.setBounds(380, 60, 100, 30);
		lookUpButton.addActionListener(new ButtonListener());
		panel.add(lookUpButton);

		//Reset Search Button
		resetButton = new JButton(new ImageIcon(getClass().getResource(("../images/reset button.JPG"))));
		resetButton.setBounds(380, 100, 100, 30);
		resetButton.addActionListener(new ButtonListener());
		panel.add(resetButton);

		//Menu Item Image Box
		ImageIcon originalImage = new ImageIcon(getClass().getResource("../images/original menu item image.JPG"));
		menuItemImageLabel = new JLabel(originalImage);
		menuItemImageLabel.setBounds(500, 420, 150, 150);
		Border recipeImageJLabeborder = BorderFactory.createLineBorder(Color.BLACK, 1);
		menuItemImageLabel.setBorder(recipeImageJLabeborder);
		panel.add(menuItemImageLabel);

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		panel.add(homepageBackgroundLabel);

		getContentPane().add(panel);
		setVisible(true);
	}

	/**
	 * ButtonListener
	 *
	 * Performs actions based on specific button
	 *
	 * @author Zaid Omer
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
			if(press.getSource() == lookUpButton){
				List<TableOrder> transactionsUnderSpecificDate = new ArrayList<>();
				for(int i = 0; i < restaurant.getHistoricalTransactions().size(); i++){
					if(restaurant.getHistoricalTransactions().get(i).getDate().equals("" + Utils.convertToLocalDate(datePicker.getText()))){
						transactionsUnderSpecificDate.add(restaurant.getHistoricalTransactions().get(i));
					}
				}

				transactionTableLayout.clearAll();
				transactionTableLayout.addRows(transactionsUnderSpecificDate);

			}else if(press.getSource() == resetButton){
				transactionTableLayout.clearAll();
				transactionTableLayout.addRows(restaurant.getHistoricalTransactions());
			}else if (press.getSource() == returnToHomepageButton) {
				dispose();
			}
		}
	}

	/**
	 * MyMouseListener
	 *
	 * Performs actions based on specific mouse action
	 *
	 * @author Zaid Omer
	 * @version 1.0
	 * @date June 13, 2019
	 */
	public class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent press) {
			if (press.getClickCount() == 1 && transactionTable.getSelectedRow() != -1) {
				orderLayoutTableLayout.clearAll();
				int row = transactionTable.getSelectedRow();
				String transactinoIDOfSelectedOrder = "" + transactionTable.getValueAt(row, 1);

				for(int i = 0; i < restaurant.getHistoricalTransactions().size(); i++){
					if((restaurant.getHistoricalTransactions().get(i).getTransactionID()).equals(transactinoIDOfSelectedOrder)){
						orderLayoutTableLayout.addRows(restaurant.getHistoricalTransactions().get(i).getOrderItems());
					}
				}
			}else if(press.getClickCount() >= 1 && orderTable.getSelectedRow() != -1){
				for(int i = 0; i < restaurant.getMenu().size(); i++){
					if(orderTable.getValueAt(orderTable.getSelectedRow(), 0).equals(restaurant.getMenu().get(i).getName())){
						selectedMenuItem = restaurant.getMenu().get(i);
					}
				}
				menuItemImageLabel.setIcon(selectedMenuItem.getImage());
			}

		}

		@Override
		public void mousePressed(MouseEvent press) {

		}

		@Override
		public void mouseReleased(MouseEvent press) {

		}

		@Override
		public void mouseEntered(MouseEvent press) {

		}

		@Override
		public void mouseExited(MouseEvent press) {

		}
	}

	/**
	 * TransactionLayoutTableModel
	 *
	 * The table model to display transaactions
	 *
	 * @author Zaid Omer
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class TransactionLayoutTableModel extends AbstractTableModel {
		private final String[] transactionLayoutColumns = {"Date", "Transaction ID", "Total ($)", "Subtotal ($)"};
		private final Class[] columnClasses = { String.class, String.class, String.class , String.class};
		private List<TableOrder> transactionData = new ArrayList<>();

		@Override
		/**
		 * getColumnCount the number of columns in the table
		 *
		 * @return the number of columns
		 */
		public int getColumnCount() {
			return this.transactionLayoutColumns.length;
		}

		@Override
		/**
		 * getRowCount the number of rows in the table
		 *
		 * @return the number of rows
		 */
		public int getRowCount() {
			return transactionData.size();
		}

		@Override
		/**
		 * getColumnName
		 *
		 * @param col the column number
		 * @return the name of the column
		 */
		public String getColumnName(int col) {
			return this.transactionLayoutColumns[col];
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

			TableOrder transaction = this.transactionData.get(row);
			switch (col) {
				case 0:
					return transaction.getDate();
				case 1:
					return transaction.getTransactionID();
				case 2:
					return currencyFormat.format(transaction.getTotal());
				default:
					return currencyFormat.format(transaction.getSubtotal());
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
			TableOrder transaction = this.transactionData.get(row);
			switch (col) {
				case 0:
					transaction.setDate((String) value);
					break;
				case 1:
					transaction.setTotal((double) value);
					break;
				case 2:
					transaction.setTransactionID((String) value);
					break;
				default:
					transaction.setSubtotal((double) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * updateRow when an table is modified, the row must be then updated
		 *
		 * @param transaction the transaction to place in the table and add to the current list
		 *                 of tables
		 * @param row      the row that needs to be updated due to a change in the table
		 */
		public void updateRow(TableOrder transaction, int row) {
			this.transactionData.set(row, transaction);
			fireTableRowsUpdated(row, row);
		}


		/**
		 * insertRow inserts a row in the table with a table
		 *
		 * @param position the position to put the row
		 * @param transaction the food to show on the table
		 */
		public void insertRow(int position, TableOrder transaction) {
			this.transactionData.add(transaction);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * addRow adds a row at the bottom of the table with a new recipe
		 *
		 * @param transaction the food to be placed in the table
		 */
		public void addRow(TableOrder transaction) {
			insertRow(getRowCount(), transaction);
		}

		/**
		 * addRows adds 2+ rows into the table
		 *
		 * @param transactionsList the list of transaction items that are to be put into the table
		 */
		public void addRows(List<TableOrder> transactionsList) {
			for (TableOrder transaction : transactionsList) {
				addRow(transaction);
			}
		}


		/**
		 * removeRow removes a specific row in the table
		 *
		 * @param position the position of the recipe to be removed
		 */
		public void removeRow(int position) {
			this.transactionData.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * getData gets the list of tables
		 *
		 * @return the list of transaction items
		 */
		public List<TableOrder> getData() {
			return transactionData;
		}

		/**
		 * setData gets the list of tables
		 *
		 * @param transactionData the list of transaction items
		 */
		public void setData(List<TableOrder> transactionData) {
			this.transactionData = transactionData;
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * clearData clears all rows from table
		 */
		public void clearAll() {
			for (int i = transactionData.size() - 1; i >= 0 ; i--) {
				removeRow(i);
			}
		}
	}

	/**
	 * OrderLayoutTableModel
	 *
	 * The table model to display order items
	 *
	 * @author Zaid Omer
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class OrderLayoutTableModel extends AbstractTableModel {
		private final String[] orderLayoutColumns = {"Item", "Cost ($)", "Quantity"};
		private final Class[] columnClasses = {String.class, String.class, Integer.class};
		private List<TableOrderItem> orderData = new ArrayList<>();

		@Override
		/**
		 * getColumnCount the number of columns in the table
		 *
		 * @return the number of columns
		 */
		public int getColumnCount() {
			return this.orderLayoutColumns.length;
		}

		@Override
		/**
		 * getRowCount the number of rows in the table
		 *
		 * @return the number of rows
		 */
		public int getRowCount() {
			return orderData.size();
		}

		@Override
		/**
		 * getColumnName
		 *
		 * @param col the column number
		 * @return the name of the column
		 */
		public String getColumnName(int col) {
			return this.orderLayoutColumns[col];
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

			TableOrderItem order = this.orderData.get(row);
			MenuItem menuItem = order.getMenuItem();
			switch (col) {
				case 0:
					return menuItem.getName();
				case 1:
					return  currencyFormat.format(menuItem.getPrice());
				default:
					return order.getQuantity();
			}
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
			TableOrderItem order = this.orderData.get(row);
			MenuItem menuItem = order.getMenuItem();
			switch (col) {
				case 0:
					menuItem.setName((String) value);
					break;
				case 1:
					menuItem.setPrice((double) value);
					break;
				default:
					order.setQuantity((int) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * insertRow inserts a row in the table with a table
		 *
		 * @param position the position to put the row
		 * @param order the food to show on the table
		 */
		public void insertRow(int position, TableOrderItem order) {
			this.orderData.add(order);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * addRow adds a row at the bottom of the table with a new recipe
		 *
		 * @param order the food to be placed in the table
		 */
		public void addRow(TableOrderItem order) {
			insertRow(getRowCount(), order);
		}

		/**
		 * addRows adds 2+ rows into the table
		 *
		 * @param ordersList the list of orders that are to be put into the table
		 */
		public void addRows(List<TableOrderItem> ordersList) {
			for (TableOrderItem orderItem : ordersList) {
				addRow(orderItem);
			}
		}

		/**
		 * removeRow removes a specific row in the table
		 *
		 * @param position the position of the recipe to be removed
		 */
		public void removeRow(int position) {
			this.orderData.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * clearData clears all rows from table
		 */
		public void clearAll() {
			for (int i = orderData.size() - 1; i >= 0 ; i--) {
				removeRow(i);
			}
		}

	}
}