package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.Customer;
import com.alyssa.Freshqo.domain.Table;
import com.alyssa.Freshqo.domain.TableOrder;
import com.alyssa.Freshqo.domain.Waiter;

/**
 * FindTableDialog 
 * 
 * The dialog used to find a table
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class FindTableDialog extends JDialog {

	// VARIABLES
	private Restaurant restaurant;
	private JPanel panel;
	private ImageIcon background;

	private JTextField customerNameTextField;
	private JSpinner numOfPeopleSpinner;
	private JButton findAvailableTableButton;

	/**
	 * initializes the restaurant and calls the initialize user interface method
	 * 
	 * @param restaurant the restaurant
	 */
	public FindTableDialog(Restaurant restaurant) {
		this.restaurant = restaurant;
		initUI();
	}

	/**
	 * initializes the user interface
	 */
	private void initUI() {

		setModalityType(ModalityType.APPLICATION_MODAL);

		setUndecorated(false); // TODO change to true
		setSize(200, 300);
		setLocationRelativeTo(null);
		setResizable(false);

		panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel);

		JLabel customerNameLabel = new JLabel("Customer Name: ");
		customerNameLabel.setBounds(10, 50, 100, 30);
		panel.add(customerNameLabel);

		customerNameTextField = new JTextField();
		customerNameTextField.setBounds(10, 80, 175, 30);
		panel.add(customerNameTextField);

		JLabel numOfPeopleLabel = new JLabel("Number of People:");
		numOfPeopleLabel.setBounds(10, 120, 125, 30);
		panel.add(numOfPeopleLabel);

		SpinnerModel numOfPeopleSpinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
		numOfPeopleSpinner = new JSpinner(numOfPeopleSpinnerModel);
		numOfPeopleSpinner.setBounds(135, 120, 50, 30);
		panel.add(numOfPeopleSpinner);

		findAvailableTableButton = new JButton(new ImageIcon(getClass().getResource("../images/find table button.JPG")));
		findAvailableTableButton.setBounds(40, 200, 120, 50);
		panel.add(findAvailableTableButton);
		findAvailableTableButton.addActionListener(new ActionListener() {
			@Override
			/**
			 * actionPerformed Invoked when an action occurs
			 * 
			 * @param e the action that occurs
			 */
			public void actionPerformed(ActionEvent e) {
				Customer customer = new Customer(customerNameTextField.getText(), (int) numOfPeopleSpinner.getValue());
				Table availableTable = restaurant.findAvailableTableForWalkInCustomer(customer);
				if (availableTable == null) {
					restaurant.getWaitingList().enqueue(customer);
					JOptionPane.showMessageDialog(null, "Customer has been added to the waiting list");
				} else {
					Waiter waiter = restaurant.findAvailableWaiter();
					if (waiter == null) {
						JOptionPane.showMessageDialog(null, "There are no available waiters.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						JOptionPane.showMessageDialog(null, "Customer has been successfully placed at Table "
								+ availableTable.getTableName() + ". They will be served by " + waiter.getName());
						availableTable.setCustomer(customer);
						availableTable.setOccupied(true);
						availableTable.setCurrentAssignedWaiter(waiter);
						availableTable.setCurrentOrder(new TableOrder(availableTable));
						availableTable.getCurrentOrder().setWaiter(waiter);
						waiter.getAssignedTables().add(availableTable);
					}
				}
				dispose();

			}
		});

		// background image
		background = new ImageIcon(getClass().getResource("../images/small dialog background.JPG"));
		JLabel backgroundLabel = new JLabel(background);
		backgroundLabel.setBounds(0, 0, 200, 300);
		panel.add(backgroundLabel);

		setVisible(true);
	}
}
