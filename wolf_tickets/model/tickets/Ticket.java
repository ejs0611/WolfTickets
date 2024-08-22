package edu.ncsu.csc216.wolf_tickets.model.tickets;

import edu.ncsu.csc216.wolf_tickets.model.util.SwapList;

/**
 * Class contains information about each individual ticket including its name, description, and whether or not the ticket is active.
 * Also allows for adding categories and completing tickets.
 * @author trung
 *
 */
public class Ticket {
	/** the name of the ticket */
	private String ticketName;
	/** the description of the ticket */
	private String ticketDescription;
	/** whether or not the ticket is active */
	private boolean active;
	/** The tickets category */
	private SwapList<AbstractCategory> categories;
	
	/**
	 * constructor of Ticket
	 * @param ticketName the name of the ticket
	 * @param ticketDescription description of the ticket
	 * @param active whether or not the ticket is active
	 */
	public Ticket(String ticketName, String ticketDescription, boolean active) {
		setTicketName(ticketName);
		setTicketDescription(ticketDescription);
		setActive(active);
		categories = new SwapList<AbstractCategory>();
	}
	/**
	 * gets the ticket name
	 * @return the ticketName
	 */
	public String getTicketName() {
		return ticketName;
	}
	/**
	 * sets the ticket name to the parameter
	 * @param ticketName the ticketName to set
	 * @throws IllegalArgumentException if ticketName is null or an empty string
	 */
	public void setTicketName(String ticketName) {
		if(ticketName == null || "".equals(ticketName)) {
			throw new IllegalArgumentException("Incomplete ticket information.");
		}
		this.ticketName = ticketName;
	}
	/**
	 * gets the ticket's description
	 * @return the ticketDescription
	 */
	public String getTicketDescription() {
		return ticketDescription;
	}
	/**
	 * sets the ticket description to the parameter
	 * @param ticketDescription the ticketDescription to set
	 * @throws IllegalArgumentException if ticketDescription is null or an empty string
	 */
	public void setTicketDescription(String ticketDescription) {
		if(ticketDescription == null) {
			throw new IllegalArgumentException("Incomplete ticket information.");
		}
		this.ticketDescription = ticketDescription;
	}
	
	/**
	 * gets whether or not the ticket is active
	 * @return active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * sets ticket active to the parameter
	 * @param active the boolean to set active to
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * gets the category name
	 * @return the category name
	 */
	public String getCategoryName() {
		try {
			return categories.get(0).getCategoryName();
		} catch(IndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * adds a category to the list of categories
	 * @param category the category to add 
	 * @throws IllegalArgumentException if the category you are trying to add is null
	 */
	public void addCategory(AbstractCategory category) {
		if(category == null) {
			throw new IllegalArgumentException("Incomplete ticket information.");
		}
		boolean x = false;
		for(int i = 0; i < category.getTickets().size(); i++) {
			if(category.getTicket(i) == this) {
				x = true;
			}
		}
		if(!x) {
			categories.add(category);
		}
	}
	
	/**
	 * sets a ticket to complete
	 */
	public void completeTicket() {
		for(int i = 0; i < categories.size(); i++) {
			categories.get(i).completeTicket(this);
		}
		
	}
	
	/**
	 * returns a String representation of ticket
	 * @return string representation of ticket
	 */
	public String toString() {
		if(isActive()) {
			return "* " + getTicketName() + "," + "active\n" + getTicketDescription();
		}
		return "* " + getTicketName() + "\n" + getTicketDescription();
	}
}
