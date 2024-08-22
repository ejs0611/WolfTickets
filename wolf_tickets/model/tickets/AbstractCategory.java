package edu.ncsu.csc216.wolf_tickets.model.tickets;

import edu.ncsu.csc216.wolf_tickets.model.util.SwapList;

/**
 * abstract class at the top of the hierarchy for categories. The AbstractCategory knows its categoryName, the ISwapList of Tickets, 
 * and the number of completed tickets.
 * @author trung
 *
 */
public abstract class AbstractCategory {
	/** the category name */
	private String categoryName;
	/** the number of completed tickets */
	private int completedCount;
	/** list of tickets */
	private SwapList<Ticket> tickets;
	
	/**
	 * constructor of AbstractCategory
	 * @param categoryName the category name
	 * @param completedCount the number of completed tickets
	 * @throws IllegalArgumentException if completedCount is less than zero
	 */
	public AbstractCategory(String categoryName, int completedCount) {
		setCategoryName(categoryName);
		if(completedCount < 0) {
			throw new IllegalArgumentException("Invalid completed count.");
		}
		this.completedCount = completedCount;
		tickets = new SwapList<Ticket>();
	}
	/**
	 * gets the category name
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * sets the category name to the parameter
	 * @param categoryName the categoryName to set
	 * @throws IllegalArgumentException if categoryName is null or an empty string
	 */
	public void setCategoryName(String categoryName) {
		if(categoryName == null || "".equals(categoryName)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.categoryName = categoryName;
	}
	/**
	 * gets the number of completed tickets
	 * @return the completedCount
	 */
	public int getCompletedCount() {
		return completedCount;
	}
	
	/**
	 * gets the list of tickets
	 * @return tickets
	 */
	public SwapList<Ticket> getTickets() {
		return tickets;
	}
	
	/**
	 * adds a ticket to the list of tickets
	 * @param t the ticket to be added
	 */
	public void addTicket(Ticket t) {
		// do later
		t.addCategory(this);
		tickets.add(t);
	}
	
	/**
	 * removes a ticket from the list of tickets and outputs what was removed
	 * @param idx the index of the ticket to be removed
	 * @return the ticket that was removed
	 */
	public Ticket removeTicket(int idx) {
		return tickets.remove(idx);
	}
	
	/**
	 * gets the ticket at the specified index
	 * @param idx the index of the ticket you want to get
	 * @return the ticket at the given index
	 */
	public Ticket getTicket(int idx) {
		return tickets.get(idx);
	}
	
	/**
	 * sets a ticket to complete
	 * @param t the ticket you want to complete
	 */
	public void completeTicket(Ticket t) {
		// do later
		for(int i = 0; i < tickets.size(); i++) {
			if(t == tickets.get(i)) {
				tickets.remove(i);
				completedCount++;
			}
		}
		
	}
	
	/**
	 * abstract method that returns the list of tickets as an array
	 * @return a 2d array of tickets
	 */
	public abstract String[][] getTicketsAsArray();
	
	
}
