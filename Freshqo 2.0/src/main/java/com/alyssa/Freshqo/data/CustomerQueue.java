package com.alyssa.Freshqo.data;

import com.alyssa.Freshqo.domain.Customer;

/**
 * CustomerQueue 
 * 
 * A queue of customers used for waiting list
 * 
 * @author Alyssa Gao
 * @version 1.0
 * @date June 13, 2019
 * @param <T> generic value for the nodes
 */
public class CustomerQueue<T extends Customer> extends Queue<T> {

	/**
	 * dequeue removes node from the queue
	 * 
	 * @param numPeople the nhumber of people
	 * @return T the item in the node being removed
	 */
	public T dequeue(int numPeople) {
		QueueNode<T> tempNode = head;
		QueueNode<T> previousNode = null;
		System.out.println(numPeople);
		while (tempNode != null) {
			if (tempNode.getItem().getNumPeople() <= numPeople) {
				if (previousNode == null) {
					head = tempNode.getNext();
				} else {
					previousNode.setNext(tempNode.getNext());
				}
				return tempNode.getItem();
			}

			previousNode = tempNode;
			tempNode = tempNode.getNext();
		}
		return null;
	}

	/**
	 * dequeue Removes item from to the queue
	 * 
	 * @param customer the customer to be removed from the queue
	 */
	public void dequeue(Customer customer) {
		QueueNode<T> tempNode = head;
		QueueNode<T> previousNode = null;
		while (tempNode != null) {
			if (tempNode.getItem() == customer) {
				if (previousNode == null) {
					head = tempNode.getNext();
				} else {
					previousNode.setNext(tempNode.getNext());
				}
				return;
			}

			previousNode = tempNode;
			tempNode = tempNode.getNext();
		}
	}
}
