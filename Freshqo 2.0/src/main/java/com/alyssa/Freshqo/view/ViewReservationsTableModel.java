package com.alyssa.Freshqo.view;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewReservationsTableModel
 * 
 * The table model to view reservations
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

import javax.swing.table.AbstractTableModel;

import com.alyssa.Freshqo.domain.Reservation;

/**
 * ViewReservationsTableModel The table model to view reservations
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 */

public class ViewReservationsTableModel extends AbstractTableModel {
	/**
	 * the names of each column in the table
	 */
	private final String[] tableLayoutListColumns = { "Date", "Time", "Customer Name", "Num. of People", "Table Name",
			"Claimed" };

	/**
	 * the class type for each column
	 */
	private final Class[] columnClasses = { String.class, String.class, int.class, String.class, String.class,
			boolean.class };

	/**
	 * the list of reservations that are to be displayed within the table
	 */
	private List<Reservation> reservationData = new ArrayList<>();

	@Override
	/**
	 * getColumnCount the number of columns in the table
	 * 
	 * @return the number of columns
	 */
	public int getColumnCount() {
		return this.tableLayoutListColumns.length;
	}

	@Override
	/**
	 * getRowCount the number of rows in the table
	 * 
	 * @return the number of rows
	 */
	public int getRowCount() {
		return reservationData.size();
	}

	@Override
	/**
	 * getColumnName
	 * 
	 * @param col the column number
	 * @return the name of the column
	 */
	public String getColumnName(int col) {
		return this.tableLayoutListColumns[col];
	}

	@Override
	/**
	 * getValueAt finds the value at the specific row and column number
	 * 
	 * @param row the row number
	 * @param col the column number
	 * @return the value at the specific row and column
	 */
	public Object getValueAt(int row, int col) {

		Reservation reservation = this.reservationData.get(row);
		switch (col) {
		case 0:
			return reservation.getReservationDateTime().getDate();
		case 1:
			return reservation.getReservationDateTime().getTime();
		case 2:
			return reservation.getCustomer().getName();
		case 3:
			return reservation.getCustomer().getNumPeople();
		case 4:
			return reservation.getTable().getTableName();
		default:
			return reservation.isClaimed();
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
		Reservation reservation = this.reservationData.get(row);
		switch (col) {
		case 0:
			reservation.getReservationDateTime().setDate((String) value);
			break;
		case 1:
			reservation.getReservationDateTime().setTime((String) value);
			break;
		case 2:
			reservation.getCustomer().setName((String) value);
			break;
		case 3:
			reservation.getCustomer().setNumPeople((int) value);
			break;
		case 4:
			reservation.getTable().setTableName((String) value);
			break;
		default:
			reservation.setClaimed((boolean) value);
		}

		fireTableCellUpdated(row, col);
	}

	/**
	 * updateRow when an table is modified, the row must be then updated
	 * 
	 * @param reservation the reservation to place in the table and add to the
	 *                    current list of reservations
	 * @param row         the row that needs to be updated due to a change in the
	 *                    table
	 */
	public void updateRow(Reservation reservation, int row) {
		this.reservationData.set(row, reservation);
		fireTableRowsUpdated(row, row);
	}

	/**
	 * insertRow inserts a row in the table with a table
	 * 
	 * @param position    the position to put the row
	 * @param reservation the reservation to place in the table and add to the
	 *                    current list of reservations
	 */
	public void insertRow(int position, Reservation reservation) {
		this.reservationData.add(reservation);
		fireTableRowsInserted(0, getRowCount());
	}

	/**
	 * addRow adds a row at the bottom of the table with a new reservation
	 * 
	 * @param reservation the reservation to be placed in the table
	 */
	public void addRow(Reservation reservation) {
		insertRow(getRowCount(), reservation);
	}

	/**
	 * addRows adds 2+ rows into the table
	 * 
	 * @param reservationList the list of reservations that are to be put into the table
	 */
	public void addRows(List<Reservation> reservationList) {
		for (Reservation reservation : reservationList) {
			addRow(reservation);
		}
	}

	/**
	 * removeRow removes a specific row in the table
	 * 
	 * @param position the position of the reservation to be removed
	 */
	public void removeRow(int position) {
		this.reservationData.remove(position);
		fireTableRowsDeleted(0, getRowCount());
	}

	/**
	 * getData gets the list of tables
	 * 
	 * @return the list of reservations
	 */
	public List<Reservation> getData() {
		return reservationData;
	}

	/**
	 * setData gets the list of reservations
	 * 
	 * @param reservationData the list of reservations
	 */
	public void setData(List<Reservation> reservationData) {
		this.reservationData = reservationData;
		fireTableRowsInserted(0, getRowCount());
	}

	public void clearAll() {
		for (int i = reservationData.size() - 1; i >= 0 ; i--) {
			removeRow(i);
		}
	}
}
