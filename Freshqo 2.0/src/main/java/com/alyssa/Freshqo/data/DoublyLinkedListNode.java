package com.alyssa.Freshqo.data;

import java.io.Serializable;

/**
 * DoublyLinkedListNode 
 * 
 * A File outlining the node for a doubly linked list
 * 
 * @author Zaid Omer
 * @version June 13, 2019
 */
class DoublyLinkedListNode<T> implements Serializable {
	private T item;
	private DoublyLinkedListNode<T> next;
	private DoublyLinkedListNode<T> previous;

	/**
	 * DoublyLinkedListNode constructor
	 * 
	 * @param item of type T the data to add to the DoublyLinkedList node
	 */
	public DoublyLinkedListNode(T item) {
		this.item = item;
		this.next = null;
	}

	/**
	 * DoublyLinkedListNode constructor
	 * 
	 * @param item , T type, the data to add to the linked list node
	 * @param next , DoublyLinkedListNode, the next node in the linked list
	 */
	public DoublyLinkedListNode(T item, DoublyLinkedListNode<T> next) {
		this.item = item;
		this.next = next;
	}

	/**
	 * DoublyLinkedListNode constructor
	 * 
	 * @param item     , T type, the data to add to the linked list node
	 * @param next     , DoublyLinkedListNode, the next node in the linked list
	 * @param previous , DoublyLinkedListNode, the previous node in the linked list
	 */
	public DoublyLinkedListNode(T item, DoublyLinkedListNode<T> next, DoublyLinkedListNode<T> previous) {
		this.item = item;
		this.next = next;
		this.previous = previous;
	}

	// Getters

	/**
	 * Next node getter
	 * 
	 * @return next, the next node of type DoublyLinkedListNode
	 */
	public DoublyLinkedListNode<T> getNext() {
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

	/**
	 * previous node getter
	 * 
	 * @return previous, the previous node in the doubly linked list
	 */
	public DoublyLinkedListNode<T> getPrevious() {
		return previous;
	}

	// Setters

	/**
	 * Next node setter
	 * 
	 * @param next, of type DoublyLinkedListNode
	 */
	public void setNext(DoublyLinkedListNode<T> next) {
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

	/**
	 * previous node setter
	 * 
	 * @param previous , the previous node, of type DoublyLinkedListNode
	 */
	public void setPrevious(DoublyLinkedListNode<T> previous) {
		this.previous = previous;
	}
}