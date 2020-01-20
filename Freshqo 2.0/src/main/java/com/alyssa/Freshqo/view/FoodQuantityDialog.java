package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.alyssa.Freshqo.domain.MenuItem;
import com.alyssa.Freshqo.domain.TableOrder;
import com.alyssa.Freshqo.domain.TableOrderItem;
import com.alyssa.Freshqo.domain.Waiter;

/**
 * FoodQuantityDialog 
 * 
 * The Dialog used to select food quantity
 * 
 * @author Alyssa Gao && Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */
public class FoodQuantityDialog extends JDialog {

	// VARIABLES
	private TableOrderItem tableOrderItem;
	private TableOrder tableOrder;
	private JPanel panel;
	private ImageIcon background;
	private JLabel itemImage;

	private JSpinner quantitySpinner;
	private JButton orderButton;

	/**
	 * FoodQuantityDialog constructor
	 * 
	 * @param menuItem   the menu item ordered
	 * @param waiter     the waiter serving this order
	 * @param tableOrder the table order variable
	 */
	public FoodQuantityDialog(MenuItem menuItem, Waiter waiter, TableOrder tableOrder) {
		this.tableOrderItem = new TableOrderItem(menuItem, waiter, tableOrder);
		this.tableOrder = tableOrder;
		initUI();
	}

	/**
	 * initializes the user interface
	 */
	private void initUI() {

		setModalityType(ModalityType.APPLICATION_MODAL);

		setUndecorated(false); // TODO change to true
		setSize(200, 370);
		setLocationRelativeTo(null);
		setResizable(false);

		panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel);
		
		JLabel itemImage = new JLabel(this.tableOrderItem.getMenuItem().getImage() );
		itemImage.setBounds(10, 10, 175, 175);
		panel.add(itemImage);

		JLabel quantityLabel = new JLabel("Quantity of Item: ");
		quantityLabel.setBounds(10, 190, 125, 30);
		panel.add(quantityLabel);

		SpinnerModel quantitySpinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
		quantitySpinner = new JSpinner(quantitySpinnerModel);
		quantitySpinner.setBounds(135, 190, 50, 30);
		panel.add(quantitySpinner);

		orderButton = new JButton(new ImageIcon(getClass().getResource("../images/order button.JPG")));
		orderButton.setBounds(40, 270, 120, 50);
		panel.add(orderButton);
		orderButton.addActionListener(new ActionListener() {
			@Override
			/**
			 * actionPerformed Invoked when an action occurs
			 *
			 * @param press the action that occurs
			 */
			public void actionPerformed(ActionEvent press) {

				if (press.getSource() == orderButton) {
					int quantity = (int) quantitySpinner.getValue();
					// checks if item has been ordered before and not fired
					// if so, add to previous item and update quantity

					boolean orderBefore = false;
					for (int i = 0; i < tableOrder.getOrderItems().size(); i++) {
						if ((tableOrder.getOrderItems().get(i).getMenuItem().equals(tableOrderItem.getMenuItem()))
								&& (!tableOrder.getOrderItems().get(i).isFired())) {
							tableOrder.getOrderItems().get(i)
									.setQuantity(tableOrder.getOrderItems().get(i).getQuantity() + quantity);
							orderBefore = true;
						}
					}
					if (!orderBefore) {
						tableOrderItem.setQuantity(quantity);
						tableOrder.getOrderItems().add(tableOrderItem);
					}
					tableOrder
							.setSubtotal(tableOrder.getSubtotal() + quantity * tableOrderItem.getMenuItem().getPrice());
					tableOrder.setTotal(tableOrder.getSubtotal() * 1.13);
				}
				dispose();

			}
		});

		// background image
		background = new ImageIcon(getClass().getResource("../images/small dialog background.JPG"));
		JLabel backgroundLabel = new JLabel(background);
		backgroundLabel.setBounds(0, 0, 200, 370);
		panel.add(backgroundLabel);

		setVisible(true);
	}

}
