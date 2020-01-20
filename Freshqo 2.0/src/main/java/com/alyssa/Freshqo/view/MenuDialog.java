package com.alyssa.Freshqo.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.Manager;
import com.alyssa.Freshqo.domain.MenuItem;
import com.alyssa.Freshqo.utils.Utils;

/**
 * MenuDialog 
 * 
 * The dialog to add items to the menu
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class MenuDialog extends JDialog {

	// VARIABLES
	private Restaurant restaurant;
	private JTextField menuItemNameTextField;
	private JFormattedTextField menuItemPriceTextField;
	private JTextArea menuItemDescriptionTextArea;
	private JLabel menuItemImageLabel;
	private byte[] menuItemImageBytes;
	private JComboBox menuItemCategoryChoice;
	private JButton loadImageButton;
	private JButton addItemButton;
	private JButton deleteItemButton;
	private JButton returnToHomepageButton;
	private JPanel panel;
	private ImageIcon homepageBackground;
	private JLabel homepageBackgroundLabel;
	private MenuTableModel menuTableModel;
	private JTable menuTable;

	/**
	 * MenuDialog constructor initializes the restaurant and calls the initialize
	 * user interface method
	 * 
	 * @param restaurant the restaurant
	 */
	public MenuDialog(Restaurant restaurant) {
		if (!(restaurant.getCurrentUser() instanceof Manager)) {
			JOptionPane.showMessageDialog(null, "Only a manager can access and use the setup button.", "Error",
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

		setUndecorated(true);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel);

		// Formatter to ensure user does not enter non-numerical values
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		NumberFormatter numFormatter = new NumberFormatter(decimalFormat);
		numFormatter.setAllowsInvalid(false); // specifies that invalid characters are not allowed

		ImageIcon originalImage = new ImageIcon(getClass().getResource("../images/original menu item image.JPG"));
		menuItemImageLabel = new JLabel(originalImage);
		menuItemImageLabel.setBounds(50, 75, 150, 150);
		Border recipeImageJLabeborder = BorderFactory.createLineBorder(Color.BLACK, 1);
		menuItemImageLabel.setBorder(recipeImageJLabeborder);

		loadImageButton = new JButton(new ImageIcon(getClass().getResource("../images/load image button.JPG")));
		loadImageButton.setBounds(225, 135, 80, 50);
		loadImageButton.addActionListener(new ButtonListener());

		JLabel menuItemNameLabel = new JLabel("Item Name:");
		menuItemNameLabel.setBounds(50, 250, 100, 30);
		menuItemNameTextField = new JTextField();
		menuItemNameTextField.setBounds(175, 250, 200, 30);

		JLabel menuItemPriceLabel = new JLabel("Item Price:");
		menuItemPriceLabel.setBounds(50, 300, 100, 30);
		menuItemPriceTextField = new JFormattedTextField(numFormatter);
		menuItemPriceTextField.setBounds(175, 300, 200, 30);

		JLabel menuItemDescriptionLabel = new JLabel("Item Description:");
		menuItemDescriptionLabel.setBounds(50, 350, 100, 30);
		JScrollPane menuItemDescriptionScrollPane = new JScrollPane();
		menuItemDescriptionTextArea = new JTextArea();
		menuItemDescriptionTextArea.setBounds(175, 350, 200, 75);
		menuItemDescriptionTextArea.setLineWrap(true);
		menuItemDescriptionTextArea.setWrapStyleWord(true);
		menuItemDescriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel menuItemCategoryLabel = new JLabel("Item Category");
		menuItemCategoryLabel.setBounds(50, 450, 100, 30);
		String[] menuItemCategoryOptions = { "Appetizer", "Entree", "Dessert", "Beverage" };
		menuItemCategoryChoice = new JComboBox(menuItemCategoryOptions);
		menuItemCategoryChoice.setBounds(175, 450, 200, 30);

		addItemButton = new JButton(new ImageIcon(getClass().getResource("../images/add item button.JPG")));
		addItemButton.setBounds(865, 75, 120, 75);
		addItemButton.addActionListener(new ButtonListener());

		deleteItemButton = new JButton(new ImageIcon(getClass().getResource("../images/delete item button.JPG")));
		deleteItemButton.setBounds(865, 165, 120, 75);
		deleteItemButton.addActionListener(new ButtonListener());

		returnToHomepageButton = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnToHomepageButton.setBounds(865, 435, 120, 75);
		returnToHomepageButton.addActionListener(new ButtonListener());

		menuTableModel = new MenuTableModel();
		menuTable = new JTable(menuTableModel);
		menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menuTable.setBounds(500, 100, 300, 400);
		menuTableModel.addRows(restaurant.getMenu());

		JScrollPane tableListScrollPane = new JScrollPane(menuTable);
		tableListScrollPane.setBounds(500, 100, 300, 400);

		panel.add(menuItemNameLabel);
		panel.add(menuItemNameTextField);
		panel.add(menuItemPriceLabel);
		panel.add(menuItemPriceTextField);
		panel.add(menuItemDescriptionLabel);
		panel.add(menuItemCategoryLabel);
		panel.add(menuItemCategoryChoice);
		panel.add(menuItemImageLabel);
		panel.add(loadImageButton);
		panel.add(addItemButton);
		panel.add(deleteItemButton);
		panel.add(returnToHomepageButton);
		panel.add(tableListScrollPane);

		menuItemDescriptionScrollPane.getViewport().add(this.menuItemDescriptionTextArea);
		menuItemDescriptionScrollPane.setBounds(175, 350, 200, 75);
		panel.add(menuItemDescriptionScrollPane);

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		panel.add(homepageBackgroundLabel);

		setVisible(true);
	}

	/**
	 * ButtonListener inner class that checks which button was pressed and executes
	 * the corresponding action
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @since May 5, 2019
	 */
	class ButtonListener implements ActionListener {

		/**
		 * actionPerformed performs the action that is needed to be performed from
		 * clicking a button
		 * 
		 * @param press used to determine which button is pressed
		 */
		public void actionPerformed(ActionEvent press) {
			if (press.getSource() == loadImageButton) {
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Image files", ".jpg");
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(panel, "Open file");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileopen.getSelectedFile();
					try {
						BufferedImage img = ImageIO.read(file);
						if (img == null) {
							JOptionPane.showMessageDialog(null, "Invalid image file.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						Image dimg = img.getScaledInstance(175, 175, Image.SCALE_SMOOTH);
						menuItemImageLabel.setIcon(new ImageIcon(dimg));
						menuItemImageBytes = Utils.getContent(file);
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error in loading image.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			} else if (press.getSource() == deleteItemButton) {
				int selectedRow = menuTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please select a menu item.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				menuTableModel.removeRow(selectedRow);
				restaurant.getMenu().remove(selectedRow);

			} else if (press.getSource() == returnToHomepageButton) {
				dispose();
			} else if (press.getSource() == addItemButton) {
				for (int i = 0; i < restaurant.getMenu().size(); i++) {
					if ((restaurant.getMenu().get(i).getName())
							.equals((menuItemNameTextField.getText().toUpperCase()))) {
						JOptionPane.showMessageDialog(null, "You already have added an item with the same name.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				if ((menuItemNameTextField.getText().equals("")) || (menuItemPriceTextField.getText().equals(""))
						|| (menuItemDescriptionTextArea.getText().equals(""))
						|| (menuItemImageLabel.getIcon() == null)) {
					JOptionPane.showMessageDialog(null, "You are still missing values in your item.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				MenuItem menuItem = new MenuItem(menuItemNameTextField.getText().toUpperCase(),
						(double) Double.parseDouble(menuItemPriceTextField.getText()),
						menuItemDescriptionTextArea.getText(), menuItemImageLabel.getIcon(),
						menuItemCategoryChoice.getSelectedItem().toString());
				menuItem.setInternalIconImage(menuItemImageBytes);
				restaurant.getMenu().add(menuItem);
				menuTableModel.addRow(menuItem);

			}
		}
	}

	/**
	 * MenuTableModel The table model to display the menu items
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class MenuTableModel extends AbstractTableModel {
		private final String[] foodLayoutListColumns = { "Dish", "Price", "Category" };
		private final Class[] columnClasses = { String.class, String.class, String.class };
		private List<MenuItem> menu = new ArrayList<>();

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
			return menu.size();
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

			MenuItem menuItem = this.menu.get(row);
			switch (col) {
			case 0:
				return menuItem.getName();
			// case 2:
			case 1:
				return menuItem.getPriceFormatted();
			default:
				return menuItem.getCategory();
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
			MenuItem menuItem = this.menu.get(row);
			switch (col) {
			case 0:
				menuItem.setName((String) value);
				break;
			case 1:
				menuItem.setPrice((double) value);
				break;
			default:
				menuItem.setCategory((String) value);
			}

			fireTableCellUpdated(row, col);
		}

		/**
		 * updateRow when an table is modified, the row must be then updated
		 *
		 * @param menuItem the menu item to place in the table and add to the current
		 *                 list of menu items
		 * @param row      the row that needs to be updated due to a change in the table
		 */
		public void updateRow(MenuItem menuItem, int row) {
			this.menu.set(row, menuItem);
			fireTableRowsUpdated(row, row);
		}

		/**
		 * insertRow inserts a row in the table with a table
		 *
		 * @param position the position to put the row
		 * @param menuItem the menu item to show on the table
		 */
		public void insertRow(int position, MenuItem menuItem) {
			this.menu.add(menuItem);
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
		 * @param position the position of the menu item to be removed
		 */
		public void removeRow(int position) {
			this.menu.remove(position);
			fireTableRowsDeleted(0, getRowCount());
		}

		/**
		 * getData gets the list of tables
		 *
		 * @return the list of menu items
		 */
		public List<MenuItem> getData() {
			return menu;
		}

		/**
		 * setData gets the list of tables
		 *
		 * @param foodData the list of menu item
		 */
		public void setData(List<MenuItem> foodData) {
			this.menu = foodData;
			fireTableRowsInserted(0, getRowCount());
		}
	}
}
