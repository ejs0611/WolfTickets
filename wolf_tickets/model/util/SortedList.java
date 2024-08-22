package edu.ncsu.csc216.wolf_tickets.model.util;


/**
 * Custom list for Categories that keeps objects in sorted order as defined by the
 * Comparable interface.
 * 
 * @author trung
 *
 * @param <E> type for ISortedList
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {

	/** size of list */
	private int size;
	/** front node of linked list */
	private ListNode front;

	/**
	 * missing constructor of SortedList
	 */
	public SortedList() {
		front = null;
		size = 0;
	}

	/**
	 * Adds the element to the list in sorted order.
	 * 
	 * @param element element to add
	 * @throws NullPointerException     if element is null
	 * @throws IllegalArgumentException if element is a duplicate
	 */
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}

		if (front == null) {
			front = new ListNode(element);
		} else {
			ListNode current = front;
			for (int i = 0; i < size; i++) {
				if (current.data == element) {
					throw new IllegalArgumentException("Cannot add duplicate element.");
				}
				current = current.next;
			}

			current = front;
			int index = size;
			for (int i = 0; i < size; i++) {
				if (element.compareTo(current.data) <= 0) {
					index = i;
					break;
				}
				current = current.next;
			}

			if (index == 0) {
				front = new ListNode(element, front);
			} else {
				current = front;

				for (int i = 0; i < index - 1; i++) {
					current = current.next;
				}

				current.next = new ListNode(element, current.next);
			}
		}
		size++;
	}

	/**
	 * Returns the element from the given index. The element is removed from the
	 * list.
	 * 
	 * @param idx index to remove element from
	 * @return element at given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	public E remove(int idx) {
		E value;

		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}

		if (front == null) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		if (idx == 0) {
			value = front.data;
			front = front.next;
		} else {
			ListNode current = front;

			int i = 0;
			while (current.next != null && i < idx - 1) {
				current = current.next;
				i++;
			}
			if (current.next == null) {
				throw new IndexOutOfBoundsException("Invalid index.");
			}
			value = current.next.data;
			current.next = current.next.next;
		}
		size--;
		return value;
	}

	/**
	 * Returns true if the element is in the list.
	 * 
	 * @param element element to search for
	 * @return true if element is found
	 */
	public boolean contains(E element) {
		ListNode current = front;
		for (int i = 0; i < size; i++) {
			if (current.data == element) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Returns the element at the given index.
	 * 
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	public E get(int idx) {
		
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		ListNode current = front;
		for(int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return number of elements in the list
	 */
	public int size() {
		return size;
	}

	private class ListNode {
		/** the data the node contains */
		private E data;
		/** the next node in the list of nodes */
		private ListNode next;

		/**
		 * constructor of ListNode
		 * 
		 * @param data the data the node contains
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * constructor of ListNode
		 * 
		 * @param data the data the node contains
		 * @param next the next node in the list of nodes
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}
