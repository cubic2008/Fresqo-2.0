package com.alyssa.Freshqo.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.alyssa.Freshqo.Restaurant;
import com.alyssa.Freshqo.domain.Chef;
import com.alyssa.Freshqo.domain.Reservation;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 * KitchenDialog 
 * 
 * The Dialog for the Kitchen to view orders to cook
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class ReservationBookDialog extends JDialog {

	// Variables
	private Restaurant restaurant;
	private JPanel panel;
	private DatePicker datePicker;
	private JLabel dateLabel;
	private DatePickerSettings dateSettings;
	private ImageIcon homepageBackground;
	private JLabel homepageBackgroundLabel;
	private JButton lookUpButton;
	private JButton addReservationButton;
	private JButton cancelReservationButton;
	private JButton returnToHomepageButton;
	private JRadioButton searchByDateButton;
	private JRadioButton searchByNameButton;
	private JLabel nameLabel;
	private JTextField searchNameTextField;
	private ViewReservationsTableModel viewReservationsTableModel;
	private JTable viewReservationsTable;

	/**
	 * constructor to initialize restaurant and user interface
	 * @param restaurant
	 */
	public ReservationBookDialog(Restaurant restaurant) {
		if (restaurant.getCurrentUser() instanceof Chef) {
			JOptionPane.showMessageDialog(null, "Only a manager or waiter can access the reservation button.", "Error",
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

		// Date
		dateLabel = new JLabel("Reservation Date:");
		dateLabel.setBounds(25, 100, 100, 30);
		panel.add(dateLabel);
		dateSettings = new DatePickerSettings();
		dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
		datePicker = new DatePicker(dateSettings);
		dateSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusDays(60));
		datePicker.setBounds(150, 100, 200, 30);
		datePicker.setDateToToday();
		datePicker.getComponentDateTextField().setEditable(false);
		panel.add(datePicker);

		// Name
		nameLabel = new JLabel("Reservation Name:");
		nameLabel.setBounds(25, 100, 120, 30);
		panel.add(nameLabel);

		searchNameTextField = new JTextField();
		searchNameTextField.setBounds(150, 100, 200, 30);
		panel.add(searchNameTextField);

		lookUpButton = new JButton(new ImageIcon(getClass().getResource("../images/look up button.JPG")));
		lookUpButton.setBounds(380, 100, 100, 30);
		lookUpButton.addActionListener(new ButtonListener());
		panel.add(lookUpButton);

		addReservationButton = new JButton(new ImageIcon(getClass().getResource("../images/add reservation button.JPG")));
		addReservationButton.setBounds(865, 75, 120, 75);
		addReservationButton.addActionListener(new ButtonListener());
		panel.add(addReservationButton);

		cancelReservationButton = new JButton(new ImageIcon(getClass().getResource("../images/cancel reservation button.JPG")));
		cancelReservationButton.setBounds(865, 165, 120, 75);
		cancelReservationButton.addActionListener(new ButtonListener());
		panel.add(cancelReservationButton);

		returnToHomepageButton = new JButton(new ImageIcon(getClass().getResource("../images/return home button.JPG")));
		returnToHomepageButton.setBounds(865, 435, 120, 75);
		returnToHomepageButton.addActionListener(new ButtonListener());
		panel.add(returnToHomepageButton);

		searchByDateButton = new JRadioButton("Search by Date", true);
		searchByDateButton.setBounds(25, 60, 150, 30);
		panel.add(searchByDateButton);

		searchByNameButton = new JRadioButton("Search by Name", false);
		searchByNameButton.setBounds(200, 60, 150, 30);
		panel.add(searchByNameButton);

		ButtonGroup searchButtonGroup = new ButtonGroup();
		searchButtonGroup.add(searchByDateButton);
		searchButtonGroup.add(searchByNameButton);

		displaySearchByDateCriteriaComponents();

		searchByDateButton.addItemListener(new RadioListener());
		searchByNameButton.addItemListener(new RadioListener());

		viewReservationsTableModel = new ViewReservationsTableModel();
		viewReservationsTable = new JTable(viewReservationsTableModel);
		viewReservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		viewReservationsTable.setBounds(25, 150, 700, 400);
		viewReservationsTableModel.addRows(restaurant.getReservationBook());

		JScrollPane tableListScrollPane = new JScrollPane(viewReservationsTable);
		tableListScrollPane.setBounds(25, 150, 700, 400);
		panel.add(tableListScrollPane);

		// background image
		homepageBackground = new ImageIcon(getClass().getResource("../images/freshqo background.JPG"));
		homepageBackgroundLabel = new JLabel(homepageBackground);
		homepageBackgroundLabel.setBounds(0, 0, 1000, 600);
		panel.add(homepageBackgroundLabel);

		setVisible(true);
		nameLabel.setVisible(false);
		searchNameTextField.setVisible(false);
	}

	/**
	 * If user selects to search by date, then certain things are now set as visible
	 * and vice versa
	 */
	private void displaySearchByDateCriteriaComponents() {
		dateLabel.setVisible(true);
		datePicker.setVisible(true);
		nameLabel.setVisible(false);
		searchNameTextField.setVisible(false);
	}

	/**
	 * If user selects to search by name, then certain things are now set as visible
	 * and vice versa
	 */
	private void displaySearchByNameCriteriaComponents() {
		dateLabel.setVisible(false);
		datePicker.setVisible(false);
		nameLabel.setVisible(true);
		searchNameTextField.setVisible(true);
	}

	/**
	 * RadioListener Performs an action based on the radio button
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class RadioListener implements ItemListener {

		/**
		 * actionPerformed performs the action that is needed to be performed from
		 * clicking a button
		 *
		 * @param press used to determine which button is pressed
		 */
		public void itemStateChanged(ItemEvent e) {
			if ((e.getSource() == searchByDateButton) && (e.getStateChange() == ItemEvent.SELECTED)) {
				displaySearchByDateCriteriaComponents();

			} else if ((e.getSource() == searchByNameButton) && (e.getStateChange() == ItemEvent.SELECTED)) {
				displaySearchByNameCriteriaComponents();
			}

		}
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
			if (press.getSource() == lookUpButton) {

				if (searchByDateButton.isSelected()) {
					List<Reservation> reservationsUnderSpecifiedDate = new ArrayList<>();
					for (int i = 0; i < restaurant.getReservationBook().size(); i++) {
						if (restaurant.getReservationBook().get(i).getReservationDateTime().getDate()
								.equals(datePicker.getText())) {
							reservationsUnderSpecifiedDate.add(restaurant.getReservationBook().get(i));
						}
					}

					viewReservationsTableModel.clearAll();
					viewReservationsTableModel.addRows(reservationsUnderSpecifiedDate);
				} else {
					if (searchNameTextField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					List<Reservation> reservationsUnderSpecifiedName = new ArrayList<>();
					for (int i = 0; i < restaurant.getReservationBook().size(); i++) {
						if (restaurant.getReservationBook().get(i).getCustomer().getName()
								.indexOf(searchNameTextField.getText().toUpperCase()) >= 0) {
							reservationsUnderSpecifiedName.add(restaurant.getReservationBook().get(i));
						}
					}

					viewReservationsTableModel.clearAll();
					viewReservationsTableModel.addRows(reservationsUnderSpecifiedName);
				}

			} else if (press.getSource() == cancelReservationButton) {
				int selectedRow = viewReservationsTable.getSelectedRow();
				Reservation reservation = null;
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please choose a reservation to cancel.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				for (int i = 0; i < restaurant.getReservationBook().size(); i++) {
					if ((restaurant.getReservationBook().get(i).getReservationDateTime().getDate()
							.equals(viewReservationsTableModel.getValueAt(selectedRow, 0)))
							&& (restaurant.getReservationBook().get(i).getReservationDateTime().getTime()
									.equals(viewReservationsTableModel.getValueAt(selectedRow, 1)))) {
						reservation = restaurant.getReservationBook().get(i);
					}
				}

				viewReservationsTableModel.removeRow(selectedRow);
				restaurant.getReservationBook().remove(reservation);

			} else if (press.getSource() == addReservationButton) {
				AddReservationDialog addReservationDialog = new AddReservationDialog(restaurant,
						viewReservationsTableModel);
			} else if (press.getSource() == returnToHomepageButton) {
				dispose();
			}
		}
	}
}