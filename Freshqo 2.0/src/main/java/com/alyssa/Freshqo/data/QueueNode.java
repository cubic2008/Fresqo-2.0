package com.alyssa.Freshqo.data;

import java.io.Serializable;

/**
 * QueueNode 
 * 
 * A File outlining the node for a queue
 * 
 * @author Zaid Omer
 * @version June 13, 2019
 */
class QueueNode<T> implements Serializable {
	private T item;
	private QueueNode<T> next;

	/**
	 * QueueNode constructor
	 * 
	 * @param item of type T the data to add to the queue node
	 */
	public QueueNode(T item) {
		this.item = item;
		this.next = null;
	}

	/**
	 * QueueNode constructor
	 * 
	 * @param item, T type, the data to add to the queue node
	 * @param next, QueueNode, the next node in the queue
	 */
	public QueueNode(T item, QueueNode<T> next) {
		this.item = item;
		this.next = next;
	}

	// Getters

	/**
	 * Next node getter
	 * 
	 * @return next, the next node of type QueueNode
	 */
	public QueueNode<T> getNext() {
		return next;
	}

	/**
	 * item getter
	 * 
	 * @return item, the item in the node of type T
	 */
	public T getItem() {
		return item;
	}

	// Setters

	/**
	 * Next node setter
	 * 
	 * @param next, of type QueueNode
	 */
	public void setNext(QueueNode<T> next) {
		this.next = next;
	}

	/**
	 * item setter
	 * 
	 * @param item, the item to change to, of type T
	 */
	public void setItem(T item) {
		this.item = item;
	}
}