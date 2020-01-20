package com.alyssa.Freshqo.data;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Queue 
 * 
 * A File to make the queue
 * 
 * @author Alyssa Gao && Zaid Omer
 * @version 1.0
 * @date June 13, 2019
 */
public class Queue<T> implements Serializable {
	protected QueueNode<T> head;

	/**
	 * enqueue adds node to the queue
	 * 
	 * @param nodeToAdd the node to add to the queue, of type QueueNode
	 * @author Zaid Omer
	 */
	public void enqueue(QueueNode<T> nodeToAdd) {
		if (head.getItem() == null) {
			head = nodeToAdd;
		} else {
			QueueNode<T> tempNode = head;
			while (tempNode.getNext() != null) {
				tempNode = tempNode.getNext();
			}
			tempNode.setNext(nodeToAdd);
		}
	}

	/**
	 * enqueue adds the item passed through to the queue (as a node)
	 * 
	 * @param item , the item to add to the queue, of type T
	 * @author Zaid Omer
	 */
	public void enqueue(T item) {
		QueueNode<T> nodeToAdd = new QueueNode<T>(item);
		if (head == null) {
			head = nodeToAdd;
		} else {
			QueueNode<T> tempNode = head;
			while (tempNode.getNext() != null) {
				tempNode = tempNode.getNext();
			}
			tempNode.setNext(nodeToAdd);
		}
	}

	/**
	 * dequeue removes node from the queue
	 * 
	 * @return T the item in the node being removed
	 * @author Zaid Omer
	 */
	public T dequeue() {
		QueueNode<T> previousRoot = head;
		head = head.getNext();
		return previousRoot.getItem();
	}

	/**
	 * size finds the size of the queue
	 * 
	 * @author Zaid Omer
	 * @return int the size calculated
	 */
	public int size() {
		int count = 1;
		QueueNode<T> tempNode = head;
		if (head == null) {
			return 0;
		} else {
			while (tempNode.getNext() != null) {
				count++;
				tempNode = tempNode.getNext();
			}
			return count;
		}
	}

	/**
	 * creates a queue iterator
	 * 
	 * @author Alyssa Gao
	 * @return Iterator the queue iterator
	 */
	public Iterator iterator() {
		return new QueueIterator();
	}

	/**
	 * QueueIterator
	 * 
	 * The queue iterator that contains methods to help convert a queue to an
	 * arraylist
	 * 
	 * @author Alyssa Gao
	 * @version 1.0
	 * @date June 13, 2019
	 */
	class QueueIterator implements Iterator<T> {

		private QueueNode<T> currentNode = head;

		@Override
		/**
		 * checks if there is still a next value
		 * 
		 * @author Alyssa Gao
		 * @return false if current node is false
		 */
		public boolean hasNext() {
			return currentNode != null;
		}

		@Override
		/**
		 * returns next value in iteration
		 * 
		 * @author Alyssa Gao
		 * @return T the next value
		 */
		public T next() {
			T item = null;
			if (currentNode != null) {
				item = currentNode.getItem();
				currentNode = currentNode.getNext();
			}
			return item;
		}

	}

}