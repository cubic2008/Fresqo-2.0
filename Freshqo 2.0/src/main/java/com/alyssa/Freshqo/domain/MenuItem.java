package com.alyssa.Freshqo.domain;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.DecimalFormat;

import javax.swing.Icon;

/**
 * MenuItem 
 * 
 * The template to make a menu item
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class MenuItem implements Serializable {

	// VARIABLES
	private String name;
	private String price;
	private String description;
	private Icon image;
	private String category;
	private final DecimalFormat currencyFormat = new DecimalFormat("##0.00");

	/**
	 * MenuItem constructor Initializes the menu item assigning all appropriate
	 * values
	 * 
	 * @param name        the dish's name
	 * @param price       the price of the item
	 * @param description the description about the dish
	 * @param image       the image of the dish
	 * @param category    the food category (appetizers, entrees, desserts,
	 *                    beverages)
	 */
	public MenuItem(String name, double price, String description, Icon image, String category) {
		this.name = name.toUpperCase();
		this.price = currencyFormat.format(price);
		this.description = description;
		this.image = image;
		this.category = category;
	}

	/**
	 * getName gets name
	 * @return the menu's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setName sets name
	 * @param name the menu's name
	 */
	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	/**
	 * getPrice gets price in double
	 * @return the menu's price
	 */
	public double getPrice() {
		return Double.parseDouble(price);
	}

	/**
	 * getPrice gets price in string
	 * @return the menu's price
	 */
	public String getPriceFormatted() {
		return price;
	}

	/**
	 * setPrice sets price in double
	 * @param price the menu's price
	 */
	public void setPrice(double price) {
		this.price = currencyFormat.format(price);
		;
	}

	/**
	 * getImage gets image
	 * @return the menu's image
	 */
	public Icon getImage() {
		return image;
	}

	/**
	 * setImage sets image
	 * @param image the menu's image
	 */
	public void setImage(Icon image) {
		this.image = image;
	}

	/**
	 * getCategory gets the item's category
	 * @return the item's category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * setCategory sets the item's category
	 * @param category the item's category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	private byte[] internalIconImage;

	public byte[] getInternalIconImage() {
		return internalIconImage;
	}

	public void setInternalIconImage(byte[] internalIconImage) {
		this.internalIconImage = internalIconImage;
	}

}
