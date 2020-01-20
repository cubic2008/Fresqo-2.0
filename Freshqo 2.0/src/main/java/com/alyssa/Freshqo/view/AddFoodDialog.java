package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.MenuItem;
import com.alyssa.Freshqo.domain.Table;
import com.alyssa.Freshqo.domain.TableOrder;
import com.alyssa.Freshqo.domain.TableOrderItem;
import com.alyssa.Freshqo.domain.Waiter;
import com.alyssa.Freshqo.utils.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * AddFoodDialog 
 * 
 * The dialog used to select menu items for a table's order
 * 
 * @author Alyssa Gao && Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */

public class AddFoodDialog extends JDialog {

	// VARIABLES
	private Restaurant restaurant;
	private ImageIcon homepageBackground;
	private String tableName;
	private String customerName;
	private int tableSize;
	private Waiter waiter;
	private JLabel tableInfoLabel;
	private JPanel tablesFoodPanel;
	private JButton returnToHomepageButton;
	private JButton fireOrderButton;
	private JButton payButton;
	private JButton releaseTableButton;
	private JButton deleteItemButton;
	private JLabel homepageBackgroundLabel;
	private JLabel subTotalLabel;
	private JLabel totalPriceLabel;
	private JTextField subTotalTextField;
	private JTextField totalPriceTextField;
	private JLabel appetizersLabel;
	private JLabel entreesLabel;
	private JLabel dessertsLabel;
	private JLabel beveragesLabel;
	private JTable appetizersTable;
	private JTable entreesTable;
	private JTable dessertsTable;
	private JTable beveragesTable;
	private FoodLayoutTableModel appetizerLayoutTableModel;
	private OrderedItemsTableModel orderedItemsTableModel;
	private JTable orderedItemsTable;
	private MenuItem appetizerOrdered;
	private MenuItem entreeOrdered;
	private MenuItem dessertOrdered;
	private MenuItem beverageOrdered;
	private List<Table> tables;
	private Table table;
	private TableOrder currentOrder;
	private final DecimalFormat currencyFormat = new DecimalFormat("##0.00");

	/**
	 * Initializes restaurant, calls the UI method, and uses the selected row from
	 * previous dialog to find the table this order is for
	 * 
	 * @param restaurant
	 * @param selectedRow
	 * @author Zaid Omer && Alyssa Gao
	 */
	public AddFoodDialog(Restaurant restaurant, int selectedRow) {
		if (!(restaurant.getCurrentUser() instanceof Waiter)) {
			JOptionPane.showMessageDialog(null, "Only a waiter can view tables and add orders.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.restaurant = restaurant;
		this.tables = restaurant.getTables();
		table = tables.get(selectedRow);
		this.currentOrder = table.getCurrentOrder();
		
		if (restaurant.getCurrentUser() != table.getCurrentAssignedWaiter()) {
			JOptionPane.showMessageDialog(null, "Only the assigned waiter can serve and take orders.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		initUI();
	}

	/**
	 * initializes the user interface
	 * 
	 * @author Zaid Omer && Alyssa Gao
	 */
	public void initUI() {

		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true); // TODO change to true
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		tablesFoodPanel = new JPanel();
		tablesFoodPanel.setLayout(null);

		// Fire Order Button
		fireOrderButton = new JButton(new ImageIcon(getClass().getResource("../images/fire order button.JPG")));
		fireOrderButton.setBounds(865, 75, 120, 75);
		fireOrderButton.addActionListener(new ButtonListener());
		tablesFoodPanel.add(fireOrderButton);

		// Pay Button
		payButton = new JButton(new ImageIcon(getClass().getResource("../images/pay button.JPG")));
		payButton.setBounds(865, 165, 120, 75);
		payButton.addActionListener(new ButtonListener());
		tablesFoodPanel.add(payButton);

		// Release Table Button
		releaseTableButton = new JButton(new ImageIcon(getClass().getResource("../images/release table button.JPG")));
		releaseTableButton.setBounds(865, 255, 120, 75);
		releaseTableButton.addActionListener(new ButtonListener());
		tablesFoodPanel.add(releaseTableButton);

		// Return to Home Button
		returnToHomepageButton = new JButton(new ImageIcon(getClass().getResource("../images/return button.JPG")));
		returnToHomepageButton.setBounds(865, 435, 120, 75);
		returnToHomepageButton.addActionListener(new ButtonListener());
		tablesFoodPanel.add(returnToHomepageButton);

		// Delete Item Button
		deleteItemButton = new JButton(new ImageIcon(getClass().getResource("../images/delete button.JPG")));
		deleteItemButton.setBounds(590, 410, 120, 50);
		deleteItemButton.addActionListener(new ButtonListener());
		tablesFoodPanel.add(deleteItemButton);

		// Basic Table Information Label
		tableName = table.getTableName();
		customerName = table.getCustomer().getName();
		tableSize = table.getNumSeats();
		waiter = table.getCurrentAssignedWaiter();
		tableInfoLabel = new JLabel("<html>Table " + tableName + "<p>" + "Customer: " + customerName + "<p>"
				+ "Table Size: " + tableSize + "<p>" + "Served by: " + waiter.getName() + "</html>");
		tableInfoLabel.setBounds(25, -200, 200, 600);
		tablesFoodPanel.add(tableInfoLabel);
		tablesFoodPanel.add(returnToHomepageButton);

		// Price Labels
		subTotalLabel = new JLabel("Subtotal: ");
		subTotalLabel.setBounds(500, 500, 100, 30);
		tablesFoodPanel.add(subTotalLabel);

		totalPriceLabel = new JLabel("Total Price: ");
		totalPriceLabel.setBounds(500, 530, 100, 30);
		tablesFoodPanel.add(totalPriceLabel);

		// Price Text Fields
		subTotalTextField = new JTextField(currencyFormat.format(currentOrder.getSubtotal()));
		subTotalTextField.setBounds(600, 500, 100, 30);
		subTotalTextField.setEditable(false);
		tablesFoodPanel.add(subTotalTextField);

		totalPriceTextField = new JTextField(currencyFormat.format(currentOrder.getTotal()));
		totalPriceTextField.setBounds(600, 530, 100, 30);
		totalPriceTextField.setEditable(false);
		tablesFoodPanel.add(totalPriceTextField);

		tablesFoodPanel.setVisible(true);

		// Appetizer Options
		appetizersLabel = new JLabel("Appetizers");
		appetizersLabel.setBounds(25, 140, 200, 20);
		tablesFoodPanel.add(appetizersLabel);
		appetizerLayoutTableModel = new FoodLayoutTableModel();
		appetizersTable = new JTable(appetizerLayoutTableModel);
		appetizersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		appetizersTable.setBounds(25, 160, 200, 200);
		appetizerLayoutTableModel.addRows(restaurant.getAppetizerMenu());
		appetizersTable.addMouseListener(new MyMouseListener());

		JScrollPane tableListScrollPane = new JScrollPane(appetizersTable);
		tableListScrollPane.setBounds(25, 160, 200, 200);
		tablesFoodPanel.add(tableListScrollPane);

		// Entree Options
		entreesLabel = new JLabel("Entrees");
		entreesLabel.setBounds(25, 370, 200, 20);
		tablesFoodPanel.add(entreesLabel);

		FoodLayoutTableModel entreeLayoutTableModel = new FoodLayoutTableModel();
		entreesTable = new JTable(entreeLayoutTableModel);
		entreesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entreesTable.setBounds(25, 390, 200, 200);
		entreeLayoutTableModel.addRows(restaurant.getEntreeMenu());
		entreesTable.addMouseListener(new MyMouseListener());

		JScrollPane entreeTableListScrollPane = new JScrollPane(entreesTable);
		entreeTableListScrollPane.setBounds(25, 390, 200, 200);
		tablesFoodPanel.add(entreeTableListScrollPane);

		// Dessert Options
		dessertsLabel = new JLabel("Desserts");
		dessertsLabel.setBounds(250, 140, 200, 20);
		tablesFoodPanel.add(dessertsLabel);
		FoodLayoutTableModel dessertLayoutTableModel = new FoodLayoutTableModel();
		dessertsTable = new JTable(dessertLayoutTableModel);
		dessertsTable.setBounds(250, 160, 200, 200);
		dessertLayoutTableModel.addRows(restaurant.getDessertMenu());
		dessertsTable.addMouseListener(new MyMouseListener());

		JScrollPane dessertTableListScrollPane = new JScrollPane(dessertsTable);
		dessertTableListScrollPane.setBounds(250, 160, 200, 200);
		tablesFoodPanel.add(dessertTableListScrollPane);

		// Beverages Options
		beveragesLabel = new JLabel("Beverages");
		beveragesLabel.setBounds(250, 370, 200, 20);
		tablesFoodPanel.add(beveragesLabel);
		FoodLayoutTableModel beverageLayoutTableModel = new FoodLayoutTableModel();
		beveragesTable = new JTable(beverageLayoutTableModel);
		beveragesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		beveragesTable.setBounds(250, 390, 200, 200);
		beverageLayoutTableModel.addRows(restaurant.getBeverageMenu());
		beveragesTable.addMouseListener(new MyMouseListener());

		JScrollPane beverageTableListScrollPane = new JScrollPane(beveragesTable);
		beverageTableListScrollPane.setBounds(250, 390, 200, 200);
		tablesFoodPanel.add(beverageTableListScrollPane);

		// Table to display current ordered items
		orderedItemsTableModel = new OrderedItemsTableModel();
		orderedItemsTable = new JTable(orderedItemsTableModel);
		orderedItemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderedItemsTable.setBounds(500, 100, 300, 300);
		orderedItemsTableModel.addRows(currentOrder.getOrderItems());

		JScrollPane ordersListScrollPane = new JScrollPane(orderedItemsTable);
		ordersListScrollPane.setBounds(500, 100, 300, 300);
		tablesFoodPanel.add(ordersListScrollPane);

		if (currentOrder.hasPaid()) {
			payButton.setVisible(false);
			deleteItemButton.setVisible(false);
		}

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		tablesFoodPanel.add(homepageBackgroundLabel);

		getContentPane().add(tablesFoodPanel);
		setVisible(true);
	}

	/**
	 * changeCost decreases total and subtotal based on the item selected Used when
	 * item is removed from the order
	 * 
	 * @param tableOrderItem
	 * @author Alyssa Gao
	 */
	public void changeCost(TableOrderItem tableOrderItem) {
		currentOrder.setSubtotal(
				currentOrder.getSubtotal() - tableOrderItem.getMenuItem().getPrice() * tableOrderItem.getQuantity());
		currentOrder.setTotal(currentOrder.getSubtotal() * 1.13);
	}

	/**
	 * refreshCosts refreshes costs in text field when changes occur
	 * 
	 * @author Alyssa Gao
	 */
	public void refreshCosts() {
		subTotalTextField.setText(currencyFormat.format(currentOrder.getSubtotal()));
		totalPriceTextField.setText(currencyFormat.format(currentOrder.getTotal()));
	}

	/**
	 * Generate invoice in PDF format.
	 * 
	 * @param tableOrder the TableOrder object to generate the invoice PDF file.
	 * @param file       the File object to save the invoice
	 * @author Alyssa Gao
	 */
	public void generateInvoice(TableOrder tableOrder, File file) {

		Document document = new Document();

		try (FileOutputStream fos = new FileOutputStream(file)) {

			PdfWriter.getInstance(document, fos);

			// open
			document.open();

			Image img = Image.getInstance(getClass().getResource("../images/freshqo logo - small.JPG"));

			document.add(img);

			Font titleFont = new Font();
			titleFont.setStyle(Font.BOLD);
			titleFont.setSize(18);

			Paragraph p = new Paragraph();
			p.add("Freshqo Restaurant - Invoice\n\n");
			p.setAlignment(Element.ALIGN_CENTER);
			p.setFont(titleFont);

			document.add(p);

			Font infoFont = new Font();
			infoFont.setStyle(Font.NORMAL);
			infoFont.setSize(12);

			Paragraph p2 = new Paragraph();
			p2.add("Customer Name: " + tableOrder.getTable().getCustomer().getName() + "\n");
			p2.add("# of people: " + tableOrder.getTable().getCustomer().getNumPeople() + "\n");
			p2.add("Table: " + tableOrder.getTable().getTableName() + "\n");
			p2.add("Date & Time: " + Utils.getDateFormatter().format(LocalDate.now()) + " "
					+ Utils.getTimeFormatter().format(LocalTime.now()) + "\n");
			p2.add("---------------------------------------------------------------------------------------------------\n");
			p2.setFont(infoFont);

			document.add(p2);

			Font itemFont = new Font();
			itemFont.setStyle(Font.NORMAL);
			itemFont.setSize(10);
			Paragraph p3 = new Paragraph();
			for (TableOrderItem tableOrderItem : tableOrder.getOrderItems()) {
				double price = tableOrderItem.getMenuItem().getPrice() * tableOrderItem.getQuantity();
				p3.add(tableOrderItem.getMenuItem().getName() + ":   $" + price + " ( $"
						+ tableOrderItem.getMenuItem().getPriceFormatted() + " @ " + tableOrderItem.getQuantity()
						+ " )\n");
			}
			p3.setFont(itemFont);

			document.add(p3);

			Paragraph p6 = new Paragraph();
			p6.add("---------------------------------------------------------------------------------------------------\n");
			p6.add("Sub-total: $" + currencyFormat.format(tableOrder.getSubtotal()) + "\n");
			p6.add("Tax: $" + (currencyFormat.format(tableOrder.getTotal() - tableOrder.getSubtotal())) + "\n");
			p6.add("Total: $" + currencyFormat.format(tableOrder.getTotal()) + "\n");

			Font totalFont = new Font();
			totalFont.setStyle(Font.BOLD);
			totalFont.setSize(12);
			p6.setFont(totalFont);

			document.add(p6);

			document.close();

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Button Listener Performs Action Based On Specific Button
	 * 
	 * @author Zaid Omer && Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent press) {
			if (press.getSource() == returnToHomepageButton) {
				dispose();
			} else if (press.getSource() == fireOrderButton) {
				for (int i = 0; i < currentOrder.getOrderItems().size(); i++) {
					if (!currentOrder.getOrderItems().get(i).isFired()) {
						restaurant.getKitchenOrders().enqueue(currentOrder.getOrderItems().get(i));

						// Add today's date to transaction
						String pattern = "yyyy-MM-dd";
						SimpleDateFormat dateFormatting = new SimpleDateFormat(pattern);
						String todaysDate = dateFormatting.format(new Date());
						currentOrder.setDate(todaysDate);

						currentOrder.getOrderItems().get(i).setFired(true);
						String transactionID = currentOrder.generateTransactionID();
						currentOrder.setTransactionID(transactionID);
					}
				}
				orderedItemsTableModel.clearAll();
				orderedItemsTableModel.addRows(currentOrder.getOrderItems());
				JOptionPane.showMessageDialog(null, "The order has been sent to the kitchen.");


			} else if (press.getSource() == payButton) {
				// add to past transactions
				// print receipt (PDF)
				for (int i = 0; i < currentOrder.getOrderItems().size(); i++) {
					if (currentOrder.getOrderItems().get(i).isServedToCustomer() == false) {
						JOptionPane.showMessageDialog(null, "Not all items have been served to the customer yet.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				restaurant.getHistoricalTransactions().add(currentOrder);
				currentOrder.setPaid(true);
				payButton.setVisible(false);
				deleteItemButton.setVisible(false);

				int ret = JOptionPane.showConfirmDialog(null, "Would you like to generate an invoice?",
						"Generate Invoice", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					JFileChooser filesave = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter("Invoice file", "pdf");
					filesave.addChoosableFileFilter(filter);

					ret = filesave.showDialog(null, "Save invoice file");
					if (ret == JFileChooser.APPROVE_OPTION) {
						File file = filesave.getSelectedFile();
						generateInvoice(currentOrder, file);
					}
				}

			} else if (press.getSource() == releaseTableButton) {
				for (int i = 0; i < currentOrder.getOrderItems().size(); i++) {
					if (!currentOrder.getOrderItems().get(i).isFired()) {
						JOptionPane.showMessageDialog(null,
								"Please remove all items that will not be fired to the kitchen.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else if ((currentOrder.getOrderItems().get(i).isFired())
							&& (currentOrder.getOrderItems().get(i).isServedToCustomer() == false)) {
						JOptionPane.showMessageDialog(null, "Not all items have been served to the customer yet.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						if (!currentOrder.hasPaid()) {
							JOptionPane.showMessageDialog(null,
									"Items that have been served to the customer have not been paid yet.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}

				int response = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to release this table and set as unoccupied now?", "Release Table",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					table.setOccupied(false);
					table.getCurrentAssignedWaiter().getAssignedTables().remove(table);
					table.setCurrentAssignedWaiter(null);
					if (restaurant.getWaitingList().size() > 0) {
						restaurant.checkWaitingList();
					}
					dispose();
				}

			} else if (press.getSource() == deleteItemButton) {
				int selectedRow = orderedItemsTable.getSelectedRow();

				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please choose an item to delete.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (currentOrder.getOrderItems().get(selectedRow).isFired()) {
					JOptionPane.showMessageDialog(null, "Item order has already been sent to the kitchen.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				changeCost(currentOrder.getOrderItems().get(selectedRow));
				currentOrder.getOrderItems().remove(selectedRow);
				orderedItemsTableModel.clearAll();
				orderedItemsTableModel.addRows(currentOrder.getOrderItems());
				refreshCosts();
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
			if (currentOrder.hasPaid()) {
				JOptionPane.showMessageDialog(null, "Customer has now paid. You can't order any more items.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if ((press.getClickCount() == 2) && (appetizersTable.getSelectedRow() != -1)) {
				appetizersTable = (JTable) press.getSource();
				int row = appetizersTable.getSelectedRow();
				appetizerOrdered = restaurant.getAppetizerMenu().get(row);
				FoodQuantityDialog appetizerQuantityDialog = new FoodQuantityDialog(appetizerOrdered, waiter,
						currentOrder);

			} else if ((press.getClickCount() == 2) && (entreesTable.getSelectedRow() != -1)) {
				entreesTable = (JTable) press.getSource();
				int row = entreesTable.getSelectedRow();
				entreeOrdered = restaurant.getEntreeMenu().get(row);
				FoodQuantityDialog entreeQuantityDialog = new FoodQuantityDialog(entreeOrdered, waiter, currentOrder);

			} else if ((press.getClickCount() == 2) && (dessertsTable.getSelectedRow() != -1)) {
				dessertsTable = (JTable) press.getSource();
				int row = dessertsTable.getSelectedRow();
				dessertOrdered = restaurant.getDessertMenu().get(row);
				FoodQuantityDialog dessertQuantityDialog = new FoodQuantityDialog(dessertOrdered, waiter, currentOrder);

			} else if ((press.getClickCount() == 2) && (beveragesTable.getSelectedRow() != -1)) {
				beveragesTable = (JTable) press.getSource();
				int row = beveragesTable.getSelectedRow();
				beverageOrdered = restaurant.getBeverageMenu().get(row);
				FoodQuantityDialog beverageQuantityDialog = new FoodQuantityDialog(beverageOrdered, waiter,
						currentOrder);
			}

			appetizersTable.clearSelection();
			entreesTable.clearSelection();
			dessertsTable.clearSelection();
			beveragesTable.clearSelection();

			orderedItemsTableModel.clearAll();
			orderedItemsTableModel.addRows(currentOrder.getOrderItems());

			refreshCosts();
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
	 * FoodLayoutTableModel The table model to display food items
	 * 
	 * @author Zaid Omer
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class FoodLayoutTableModel extends AbstractTableModel {
		private final String[] foodLayoutListColumns = { "Dish", "Price" };
		private final Class[] columnClasses = { String.class, String.class };
		private List<MenuItem> foodData = new ArrayList<>();

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
			return foodData.size();
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

			MenuItem menuItem = this.foodData.get(row);
			switch (col) {
			case 0:
				return menuItem.getName();
			// case 2:
			default:
				return menuItem.getPriceFormatted();
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
			MenuItem menuItem = this.foodData.get(row);
			switch (col) {
			case 0:
				menuItem.setName((String) value);
				break;
			default:
				menuItem.setPrice((double) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * updateRow when an table is modified, the row must be then updated
		 *
		 * @param menuItem the menu item to place in the table and add to the current list
		 *                 of menu items
		 * @param row      the row that needs to be updated due to a change in the table
		 */
		public void updateRow(MenuItem menuItem, int row) {
			this.foodData.set(row, menuItem);
			fireTableRowsUpdated(row, row);
		}

		/**
		 * insertRow inserts a row in the table with a table
		 *
		 * @param position the position to put the row
		 * @param menuItem the menu item to show on the table
		 */
		public void insertRow(int position, MenuItem menuItem) {
			this.foodData.add(menuItem);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * addRow adds a row at the bottom of the table with a new menu item
		 *
		 * @param menuItem the menu item to be placed in the table
		 */
		public void addRow(MenuItem menuItem) {
			insertRow(getRowCount(), menuItem);
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param menuItemList the list of menu items that are to be put into the table
		 */
		public void addRows(List<MenuItem> menuItemList) {
			for (MenuItem menuItem : menuItemList) {
				addRow(menuItem);
			}
		}

		/**
		 * removeRow removes a specific row in the table
		 *
		 * @param position the position of the recipe to be removed
		 */
		public void removeRow(int position) {
			this.foodData.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * getData gets the list of tables
		 *
		 * @return the list of menu items
		 */
		public List<MenuItem> getData() {
			return foodData;
		}

		/**
		 * setData gets the list of menu items
		 *
		 * @param foodData the list of menu item
		 */
		public void setData(List<MenuItem> foodData) {
			this.foodData = foodData;
			fireTableRowsInserted(0, getRowCount());
		}
	}

	/**
	 * OrderedItemsTableModel The table model to display ordered items
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class OrderedItemsTableModel extends AbstractTableModel {
		private final String[] foodLayoutListColumns = { "Dish", "Quantity", "Fired", "Served" };
		private final Class[] columnClasses = { String.class, int.class, boolean.class, boolean.class };
		private List<TableOrderItem> orderedItems = new ArrayList<>();

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
			return orderedItems.size();
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

			TableOrderItem tableOrderItem = this.orderedItems.get(row);
			switch (col) {
			case 0:
				return tableOrderItem.getMenuItem().getName();
			case 1:
				return tableOrderItem.getQuantity();
			case 2:
				return tableOrderItem.isFired();
			// case 2:
			default:
				return tableOrderItem.isServedToCustomer();
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
			TableOrderItem tableOrderItem = this.orderedItems.get(row);
			switch (col) {
			case 0:
				tableOrderItem.getMenuItem().setName((String) value);
				break;
			case 1:
				tableOrderItem.setQuantity((int) value);
				break;
			case 2:
				tableOrderItem.setFired((boolean) value);
				break;
			default:
				tableOrderItem.setServedToCustomer((boolean) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * updateRow when an table is modified, the row must be then updated
		 *
		 * @param tableOrderItem the table order item to place in the table and add to the current
		 *                       list of table order items
		 * @param row            the row that needs to be updated due to a change in the
		 *                       table
		 */
		public void updateRow(TableOrderItem tableOrderItem, int row) {
			this.orderedItems.set(row, tableOrderItem);
			fireTableRowsUpdated(row, row);
		}

		/**
		 * insertRow inserts a row in the table with a table
		 *
		 * @param position the position to put the row
		 * @param tableOrderItem the table order item to show on the table
		 */
		public void insertRow(int position, TableOrderItem tableOrderItem) {
			this.orderedItems.add(tableOrderItem);
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * addRow adds a row at the bottom of the table with a new recipe
		 *
		 * @param tableOrderItem the table order item to be placed in the table
		 */
		public void addRow(TableOrderItem tableOrderItem) {
			insertRow(getRowCount(), tableOrderItem);
		}

		/**
		 * addRows adds 2+ rows into the table
		 * 
		 * @param tableOrderItemList the list of table order item that are to be put into the table
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
			this.orderedItems.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * getData gets the list of table order items
		 *
		 * @return the list of table order items
		 */
		public List<TableOrderItem> getData() {
			return orderedItems;
		}

		/**
		 * setData gets the list of table order items
		 *
		 * @param foodData the list of ftable order items
		 */
		public void setData(List<TableOrderItem> orderedItems) {
			this.orderedItems = orderedItems;
			fireTableRowsInserted(0, getRowCount());
		}

		/**
		 * clearAll clears the table
		 */
		public void clearAll() {
			for (int i = orderedItems.size() - 1; i >= 0; i--) {
				removeRow(i);
			}
		}
	}
}
