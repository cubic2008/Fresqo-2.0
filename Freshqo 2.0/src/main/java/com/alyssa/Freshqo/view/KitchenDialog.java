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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.Chef;
import com.alyssa.Freshqo.domain.TableOrderItem;

/**
 * KitchenDialog 
 * 
 * The Dialog for the Kitchen to view orders to cook
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class KitchenDialog extends JDialog {

	// VARIABLES
	private Restaurant restaurant;
	private Chef chef;
	private JPanel panel;
	private JButton nextOrderButton;
	private JButton returnToHomepageButton;
	private JButton finishButton;
	private JTable incompleteOrdersTable;
	private KitchenOrdersTableModel incompleteOrdersTableModel;
	private JTable completedOrdersTable;
	private KitchenOrdersTableModel completeOrdersTableModel;
	private JTextField itemNameTextField;
	private JTextField quantityTextField;
	private JLabel itemImage;
	private ImageIcon homepageBackground;
	private JLabel homepageBackgroundLabel;

	/**
	 * KitchenDialog constructor initializes the restaurant, the chef and calls the
	 * initialize user interface method
	 * 
	 * @param restaurant the restaurant
	 */

	public KitchenDialog(Restaurant restaurant) {
		if (!(restaurant.getCurrentUser() instanceof Chef)) {
			JOptionPane.showMessageDialog(null, "Only a chef can access and use the kitchen button.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.restaurant = restaurant;
		this.chef = (Chef) restaurant.getCurrentUser();
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

		panel = new JPanel();
		panel.setLayout(null);

		nextOrderButton = new JButton(new ImageIcon(getClass().getResource("../images/next order button.JPG")));
		nextOrderButton.setBounds(865, 75, 120, 75);
		nextOrderButton.addActionListener(new ButtonListener());
		panel.add(nextOrderButton);

		returnToHomepageButton = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnToHomepageButton.setBounds(865, 435, 120, 75);
		returnToHomepageButton.addActionListener(new ButtonListener());
		panel.add(returnToHomepageButton);

		finishButton = new JButton(new ImageIcon(getClass().getResource("../images/finished button.JPG")));
		finishButton.setBounds(600, 430, 120, 50);
		finishButton.addActionListener(new ButtonListener());
		panel.add(finishButton);

		List<TableOrderItem> kitchenOrders = new ArrayList<>();
		Iterator<TableOrderItem> kitcherOrderIterator = restaurant.getKitchenOrders().iterator();
		while (kitcherOrderIterator.hasNext()) {
			kitchenOrders.add(kitcherOrderIterator.next());
		}

		incompleteOrdersTableModel = new KitchenOrdersTableModel();
		incompleteOrdersTable = new JTable(incompleteOrdersTableModel);
		incompleteOrdersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		incompleteOrdersTable.setBounds(25, 100, 200, 400);
		incompleteOrdersTableModel.addRows(kitchenOrders);

		JScrollPane incompleteOrdersScrollPane = new JScrollPane(incompleteOrdersTable);
		incompleteOrdersScrollPane.setBounds(25, 100, 200, 400);
		panel.add(incompleteOrdersScrollPane);

		completeOrdersTableModel = new KitchenOrdersTableModel();
		completedOrdersTable = new JTable(completeOrdersTableModel);
		completedOrdersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		completedOrdersTable.setBounds(300, 100, 200, 400);
		completeOrdersTableModel.addRows(chef.getCompletedOrders());

		JScrollPane completeOrdersScrollPane = new JScrollPane(completedOrdersTable);
		completeOrdersScrollPane.setBounds(300, 100, 200, 400);
		panel.add(completeOrdersScrollPane);

		JLabel incompleteOrdersLabel = new JLabel("Incomplete Orders");
		incompleteOrdersLabel.setBounds(65, 65, 150, 30);
		panel.add(incompleteOrdersLabel);

		JLabel completedOrdersLabel = new JLabel("Your Completed Orders");
		completedOrdersLabel.setBounds(350, 65, 150, 30);
		panel.add(completedOrdersLabel);

		JLabel currentOrderLabel = new JLabel("Current Order");
		currentOrderLabel.setBounds(600, 115, 150, 30);
		panel.add(currentOrderLabel);

		JLabel itemNameLabel = new JLabel("Item: ");
		itemNameLabel.setBounds(550, 160, 50, 30);
		panel.add(itemNameLabel);

		JLabel quantityLabel = new JLabel("Quantity: ");
		quantityLabel.setBounds(550, 200, 50, 30);
		panel.add(quantityLabel);

		itemImage = new JLabel();

		if (chef.getCurrentOrderItem() != null) {
			itemNameTextField = new JTextField(chef.getCurrentOrderItem().getMenuItem().getName());
			quantityTextField = new JTextField(Integer.toString(chef.getCurrentOrderItem().getQuantity()));
			itemImage.setIcon(chef.getCurrentOrderItem().getMenuItem().getImage());
			nextOrderButton.setVisible(false);
		} else {
			itemNameTextField = new JTextField("");
			quantityTextField = new JTextField("");
			finishButton.setVisible(false);
		}
		itemNameTextField.setBounds(610, 160, 100, 30);
		itemNameTextField.setEditable(false);
		panel.add(itemNameTextField);
		quantityTextField.setBounds(610, 200, 50, 30);
		quantityTextField.setEditable(false);
		panel.add(quantityTextField);
		itemImage.setBounds(580, 230, 175, 175);
		panel.add(itemImage);

		getContentPane().add(panel);

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		panel.add(homepageBackgroundLabel);

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
			if (press.getSource() == nextOrderButton) {
				if (restaurant.getKitchenOrders().size() > 0 && chef.getCurrentOrderItem() == null) {
					// Remove from pending kitchen orders and table
					// Place order item as chef's current order item
					TableOrderItem orderItem = restaurant.getKitchenOrders().dequeue();
					chef.setCurrentOrderItem(orderItem);
					incompleteOrdersTableModel.removeRow(0);
					itemNameTextField.setText(orderItem.getMenuItem().getName());
					quantityTextField.setText(Integer.toString(orderItem.getQuantity()));
					itemImage.setIcon(orderItem.getMenuItem().getImage());
					finishButton.setVisible(true);
					nextOrderButton.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "There are no items that need to be made.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if (press.getSource() == finishButton) {
				TableOrderItem finishedOrderItem = chef.getCurrentOrderItem();
				chef.getCompletedOrders().add(finishedOrderItem);
				completeOrdersTableModel.addRow(finishedOrderItem);
				finishedOrderItem.setServedToCustomer(true);
				chef.setCurrentOrderItem(null);
				itemNameTextField.setText("");
				quantityTextField.setText("");
				itemImage.setIcon(null);

				finishButton.setVisible(false);
				nextOrderButton.setVisible(true);

			} else if (press.getSource() == returnToHomepageButton) {
				dispose();
			}
		}
	}

	/**
	 * KitchenOrdersTableModel The table model to display kitchen orders
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class KitchenOrdersTableModel extends AbstractTableModel {
		private final String[] foodLayoutListColumns = { "Dish", "Quantity" };
		private final Class[] columnClasses = { String.class, int.class };
		private List<TableOrderItem> tableOrderItems = new ArrayList<>();

		@Override
		/**
		 * getColumnCount the number of columns in the table
		 *
		 * @return the number of columns
		 */
		public int getColumnCount() {
			return this.foodLayoutListColumns.length;
		}

		@Override
		/**
		 * getRowCount the number of rows in the table
		 *
		 * @return the number of rows
		 */
		public int getRowCount() {
			return tableOrderItems.size();
		}

		@Override
		/**
		 * getColumnName
		 *
		 * @param col the column number
		 * @return the name of the column
		 */
		public String getColumnName(int col) {
			return this.foodLayoutListColumns[col];
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

			TableOrderItem tableOrderItem = this.tableOrderItems.get(row);
			switch (col) {
			case 0:
				return tableOrderItem.getMenuItem().getName();
			default:
				return tableOrderItem.getQuantity();
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
			TableOrderItem tableOrderItem = this.tableOrderItems.get(row);
			switch (col) {
			case 0:
				tableOrderItem.getMenuItem().setName((String) value);
				break;
			default:
				tableOrderItem.setQuantity((int) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * updateRow when an table is modified, the row must be then updated
		 *
		 * @param tableOrderItem the recipe to place in the table and add to the current
		 *                       list of tables
		 * @param row            the row that needs to be updated due to a change in the
		 *                       table
		 */
		public void updateRow(TableOrderItem tableOrderItem, int row) {
			this.tableOrderItems.set(row, tableOrderItem);
			fireTableRowsUpdated(row, row);
		}

		/**
		 * insertRow inserts a row in the table with a table
		 *
		 * @param position       the position to put the row
		 * @param tableOrderItem the food to show on the table
		 */
		public void insertRow(int position, TableOrderItem tableOrderItem) {
			this.tableOrderItems.add(tableOrderItem);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * addRow adds a row at the bottom of the table with a new recipe
		 *
		 * @param tableOrderItem the food to be placed in the table
		 */
		public void addRow(TableOrderItem tableOrderItem) {
			insertRow(getRowCount(), tableOrderItem);
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param tableOrderItemList the list of menu items that are to be put into the
		 *                           table
		 */
		public void addRows(List<TableOrderItem> tableOrderItemList) {
			for (TableOrderItem tableOrderItem : tableOrderItemList) {
				addRow(tableOrderItem);
			}
		}

		/**
		 * removeRow removes a specific row in the table
		 *
		 * @param position the position of the recipe to be removed
		 */
		public void removeRow(int position) {
			this.tableOrderItems.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * getData gets the list of tables
		 *
		 * @return the list of menu items
		 */
		public List<TableOrderItem> getData() {
			return tableOrderItems;
		}

		/**
		 * setData gets the list of tables
		 *
		 * @param orderedItems the list of food items
		 */
		public void setData(List<TableOrderItem> orderedItems) {
			this.tableOrderItems = orderedItems;
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * clearAll clears all rows in the table
		 */
		public void clearAll() {
			for (int i = tableOrderItems.size() - 1; i >= 0; i--) {
				removeRow(i);
			}
		}
	}
}
