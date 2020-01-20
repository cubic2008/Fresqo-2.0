package com.alyssa.Freshqo.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.alyssa.Freshqo.utils.Utils;

/**
 * ReservationDateTime 
 * 
 * The template for a reservation's date and time
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class ReservationDateTime implements Serializable {

	// Variables
	private String date;
	private String time;

	/**
	 * initializes date and time
	 * 
	 * @param date the date
	 * @param time the time
	 */
	public ReservationDateTime(String date, String time) {
		this.date = date;
		this.time = time;
	}

	/**
	 * getDate gets date
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * setDate sets date
	 * 
	 * @param date the date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * getTime gets time
	 * 
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * setTime sets time
	 * 
	 * @param time the time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * getLocalDate gets local date
	 * 
	 * @return local date
	 */
	public LocalDate getLocalDate() {
		return Utils.convertToLocalDate(this.date);
	}

	/**
	 * getLocalTime gets local time
	 * 
	 * @return local time
	 */
	public LocalTime getLocalTime() {
		return Utils.convertToLocalTime(this.time);
	}

	/**
	 * getSecuredTimePeriodFrom gets the time two hours before
	 * 
	 * @return local time two hours before
	 */
	public LocalTime getSecuredTimePeriodFrom() {
		LocalTime localTime = getLocalTime();
		int hour = localTime.getHour() - 2;
		if (hour < 0) {
			hour = 0; // we will not adjust the date as the restaurant will not across the midnight.
		}
		return LocalTime.of(hour, localTime.getMinute());
	}

	/**
	 * getSecuredTimePeriodTo gets the time two hours after
	 * 
	 * @return local time two hours after
	 */
	public LocalTime getSecuredTimePeriodTo() {
		LocalTime localTime = getLocalTime();
		int hour = localTime.getHour() + 2;
		if (hour > 23) {
			hour = 23; // we will not adjust the date as the restaurant will not across the midnight.
		}
		return LocalTime.of(hour, localTime.getMinute());
	}
}
