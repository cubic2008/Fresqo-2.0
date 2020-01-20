package com.alyssa.Freshqo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Configuration
 * 
 * Preload and maintain configuration parameters.
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */
public class Configuration {
	
	public static final String CONFIGURATION_FILENAME = "restaurant.properties";
	
	public static final String CONFIG_DB_DRIVER_CLASS_NAME_KEY = "DB.DriverClassName";
	public static final String CONFIG_DB_URL_KEY = "DB.Url";
	public static final String CONFIG_DB_USERNAME_KEY = "DB.Username";
	public static final String CONFIG_DB_PASSWORD_KEY = "DB.Password";
	
	public static String DB_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost/fresqo_db";
	public static String DB_USERNAME = "root";
	public static String DB_PASSWORD = "";
	
	protected static Properties _configuration = new Properties();
	
	{
		try {
			Properties configuration = new Properties();
			configuration.load(new FileInputStream(CONFIGURATION_FILENAME));
			DB_DRIVER_CLASS_NAME = configuration.getProperty( CONFIG_DB_DRIVER_CLASS_NAME_KEY );
			DB_URL = configuration.getProperty( CONFIG_DB_URL_KEY );
			DB_USERNAME = configuration.getProperty( CONFIG_DB_USERNAME_KEY );
			DB_PASSWORD = configuration.getProperty( CONFIG_DB_PASSWORD_KEY );
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"The configuration file does not exist or is corrupt. The default value will be used.", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

}
