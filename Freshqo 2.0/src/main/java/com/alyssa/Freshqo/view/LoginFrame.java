package com.alyssa.Freshqo.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alyssa.Freshqo.Restaurant;

/**
 * LoginFrame 
 * 
 * The Login Frame
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class LoginFrame extends JFrame {

	// Variables
	private Restaurant restaurant;
	private JPanel panel;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private ImageIcon background;

	/**
	 * LoginFrame constructor initializes the restaurant and calls the initialize
	 * user interface method
	 * 
	 * @param restaurant the restaurant
	 */
	public LoginFrame(Restaurant restaurant) {
		this.restaurant = restaurant;
		initUI();
	}

	/**
	 * initializes the user interface
	 */
	private void initUI() {

		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../images/freshqo icon.JPG")));
		setName("Login");
		setSize(200, 300);
		setLocationRelativeTo(null);
		setResizable(false);

		panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel);

		JLabel usernameLabel = new JLabel("Username: ");
		usernameLabel.setBounds(10, 100, 75, 30);
		panel.add(usernameLabel);

		usernameTextField = new JTextField("manager"); // TODO: change to blank
		usernameTextField.setBounds(100, 100, 75, 30);
		panel.add(usernameTextField);

		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(10, 150, 75, 30);
		panel.add(passwordLabel);

		passwordField = new JPasswordField(""); // TODO: change to blank
		passwordField.setBounds(100, 150, 75, 30);
		panel.add(passwordField);

		loginButton = new JButton(new ImageIcon(getClass().getResource("../images/login button.JPG")));
		loginButton.setBounds(40, 200, 120, 40);
		panel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			@Override
			/**
			 * actionPerformed Invoked when an action occurs
			 * 
			 * @param e the action that occurs
			 */
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < restaurant.getEmployees().size(); i++) {
					if ((restaurant.getEmployees().get(i).getUserID().equals(usernameTextField.getText()))
							&& (restaurant.getEmployees().get(i).getPassword()
									.equals(new String(passwordField.getPassword())))) {
						restaurant.setCurrentUser(restaurant.getEmployees().get(i));
						JOptionPane.showMessageDialog(null, "Login successful.");
						restaurant.initializeSuccessfulLogin();
						dispose();
						return;
					}

				}

				JOptionPane.showMessageDialog(null, "Login failed.", "Error", JOptionPane.ERROR_MESSAGE);
				return;

			}
		});

		background = new ImageIcon(getClass().getResource("../images/small dialog background.JPG"));
		JLabel backgroundLabel = new JLabel(background);
		backgroundLabel.setBounds(0, 0, 200, 300);
		panel.add(backgroundLabel);
		
		setVisible(true);
	}

}
