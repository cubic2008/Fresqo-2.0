package com.alyssa.Freshqo.data;

import java.io.Serializable;
import java.util.AbstractList;

/**
 * DoublyLinkedList 
 * 
 * A File to make the Doubly Linked List
 * 
 * @author Zaid Omer
 * @version June 13, 2019
 */
public class DoublyLinkedList<T> extends AbstractList<T> implements Serializable {

	// VARIABLES
	private DoublyLinkedListNode<T> head;

	@Override
	/**
	 * add item to doubly linked list
	 * 
	 * @param item , the item to add, of type T
	 */
	public boolean add(T item) {
		DoublyLinkedListNode<T> nodeToAdd = new DoublyLinkedListNode<T>(item);
		boolean added = false;
		if (head == null) {
			head = nodeToAdd;
			added = true;
		} else {
			DoublyLinkedListNode<T> tempNode = head;
			while (tempNode.getNext() != null) {
				tempNode = tempNode.getNext();
			}
			nodeToAdd.setPrevious(tempNode);
			tempNode.setNext(nodeToAdd);
			added = true;
		}
		return added;
	}

	@Override
	/**
	 * remove removes item from doubly linked list
	 * 
	 * @param item the item to remove
	 * @return call to removeItem method (actually removes it)
	 */
	public boolean remove(Object item) {
		return removeItem((T) item);
	}

	/**
	 * remove remove item from doubly linked list given an index
	 * 
	 * @param index the index of the item to remove
	 * @return T the item that was removed
	 */
	@Override
	public T remove(int index) {
		T item = get(index);
		this.remove(item);
		return item;
	}

	/**
	 * to remove item from doubly linked list
	 * 
	 * @param itemToRemove , the item to remove
	 */
	public boolean removeItem(T itemToRemove) {
		DoublyLinkedListNode<T> tempNode = head;
		boolean removed = false;
		if (head == null) {
			return false;
		}
		if (head.getItem().equals(itemToRemove)) {
			head = head.getNext();
			removed = true;
		} else {
			while (tempNode.getNext() != null) {
				tempNode = tempNode.getNext();
				if (tempNode.getItem().equals(itemToRemove)) {
					// if it's the last item in the list
					if (tempNode.getNext() == null) {
//                        tempNode.setPrevious(null);
					} else {
//                        tempNode.setPrevious(tempNode.getNext());
						tempNode.getNext().setPrevious(tempNode.getPrevious());
					}
					tempNode.getPrevious().setNext(tempNode.getNext());
					removed = true;
				}
			}
		}
		return removed;
	}

	/**
	 * To calculate the size of the doubly linked list
	 * 
	 * @return int size , the size of the list
	 */
	public int size() {
		DoublyLinkedListNode<T> tempNode = head;
		int size = 1;
		if (head == null) {
			return 0;
		} else {
			while (tempNode.getNext() != null) {
				tempNode = tempNode.getNext();
				size++;
			}
			return size;
		}
	}

	/**
	 * contains check to see of item passed through is in the linked list
	 * 
	 * @param item the item to check if it's in the doubly linked list
	 * @return call to containsHelper
	 */
	@Override
	public boolean contains(Object item) {
		return containsHelper((T) item);
	}

	/**
	 * To see if an item is in the doubly liked list
	 * 
	 * @param item , the item to look for, of type T
	 */
	public boolean containsHelper(T item) {
		boolean contains = false;
		if (head == null) {
			return false;
		} else if (head.getItem().equals(item)) {
			return true;
		} else {
			DoublyLinkedListNode<T> tempNode = head;
			while (tempNode.getNext() != null) {
				if (tempNode.getItem().equals(item)) {
					contains = true;
				}
				tempNode = tempNode.getNext();
			}
			return contains;
		}
	}

	/**
	 * A method to clear the doubly liked list
	 */
	public void clear() {
		head = null;
	}

	@Override
	public int indexOf(Object item) {
		return findIndexOf((T) item);
	}

	/**
	 * To find the index of an item in the doubly linked list
	 * 
	 * @param item , the item to find the index of
	 * @return int the index number
	 */
	public int findIndexOf(T item) {
		int index = 0;
		int indexToReturn = 0;
		boolean itemFound = false;
		DoublyLinkedListNode<T> tempNode = head;
		while (tempNode.getNext() != null) {
			if (tempNode.getItem().equals(item)) {
				itemFound = true;
				indexToReturn = index;
			}
			tempNode = tempNode.getNext();
			index++;
		}
		if (itemFound) {
			return indexToReturn;
		} else {
			return -1;
		}
	}

	/**
	 * get get the linked list node item given an index
	 * 
	 * @param index the integer index value
	 */
	@Override
	public T get(int index) {
		int sizeCounter = 0;
		DoublyLinkedListNode<T> tempNode = head;
		if (head == null) {
			return null;
		}
		while (tempNode != null && sizeCounter <= index) {
			if (sizeCounter == index) {
				return tempNode.getItem();
			} else {
				tempNode = tempNode.getNext();
				sizeCounter++;
			}
		}
		return null;
	}

}
