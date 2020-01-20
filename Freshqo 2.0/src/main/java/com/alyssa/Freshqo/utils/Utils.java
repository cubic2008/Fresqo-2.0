package com.alyssa.Freshqo.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Utils
 * 
 * Formats time and date
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class Utils {

	// Variables
	public static String DateFormatPattern = "MMMM d, yyyy";
	public static String TimeFormatPattern = "h:ma";

	/**
	 * gets the date formatted
	 * 
	 * @return the date formatted
	 */
	public static DateTimeFormatter getDateFormatter() {
		return DateTimeFormatter.ofPattern(DateFormatPattern);
	}

	/**
	 * gets the list of dates formatted
	 * 
	 * @return the list of dates formatted
	 */
	public static ArrayList<DateTimeFormatter> getDateFormatters() {
		ArrayList<DateTimeFormatter> dateFormatters = new ArrayList<>();
		dateFormatters.add(getDateFormatter());
		return dateFormatters;
	}

	/**
	 * gets the time formatted
	 * 
	 * @return the time formatted
	 */
	public static DateTimeFormatter getTimeFormatter() {
		return DateTimeFormatter.ofPattern(TimeFormatPattern);
	}

	/**
	 * gets the list of times formatted
	 * 
	 * @return the list of times formatted
	 */
	public static List<DateTimeFormatter> getTimeFormatters() {
		List<DateTimeFormatter> dateFormatters = new ArrayList<>();
		dateFormatters.add(getTimeFormatter());
		return dateFormatters;
	}

	/**
	 * converts a string to local date
	 * 
	 * @param dateString the date in string format
	 * @return the date in LocalDate format
	 */
	public static LocalDate convertToLocalDate(String dateString) {
		return LocalDate.parse(dateString, getDateFormatter());
	}

	/**
	 * converts a string to local time
	 * 
	 * @param timeString the date in string format
	 * @return the time in LocalTime format
	 */
	public static LocalTime convertToLocalTime(String timeString) {
		return LocalTime.parse(timeString.toUpperCase(), DateTimeFormatter.ofPattern(TimeFormatPattern));
	}

//	public static byte[] icon2Bytes(BufferedImage bufferedImage) throws IOException {
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ImageIO.write(bufferedImage, "JPG", baos);
//		byte[] dataToEncode = baos.toByteArray();
//		byte[] base64Data = Base64.getEncoder().encode(dataToEncode);
//		return base64Data;
//	}

	public static byte[] getContent(File file) throws IOException {
		try ( FileInputStream fis = new FileInputStream ( file ) ) {
			byte[] fileContent = new byte [ (int) file.length() ];
			fis.read(fileContent);
			return fileContent;
		}
	}

}