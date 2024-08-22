package edu.ncsu.csc216.wolf_tickets.model.tickets;

/**
 * extends AbstractCategory. Category that holds only active tickets. Allows for adding, clearing, and
 * returning 2d array of active tickets.
 * @author trung
 *
 */
public class ActiveTicketList extends AbstractCategory {
	/** constant holding the name of the "ActiveTickets" list */
	public static final String ACTIVE_TASKS_NAME = "Active Tickets";

	/**
	 * constructs the ActiveTicketList with the expected name and no completed tickets
	 */
	public ActiveTicketList() {
		super(ACTIVE_TASKS_NAME, 0);
	}
	
	/**
	 * adds a ticket to the list of tickets but checks that the ticket is active first
	 * @param t the ticket you want to add
	 * @throws IllegalArgumentException if ticket is not active
	 */
	public void addTicket(Ticket t) {
		if(t.isActive()) {
			super.addTicket(t);
		} else {
			throw new IllegalArgumentException("Cannot add ticket to Active Tickets.");
		}
	}
	
	/**
	 * sets the category name to the parameter
	 * @param categoryName the name you want to set the category name to
	 */
	public void setCategoryName(String categoryName) {
		if(categoryName.equals(ACTIVE_TASKS_NAME)) {
			super.setCategoryName(categoryName);
		} else {
			throw new IllegalArgumentException("The Active Tickets list may not be edited.");
		}
		
	}
	
	/**
	 * returns a 2d array where the first column is the name of the category the ticket belongs to and the 2nd is the name of the ticket
	 * @return a 2d array
	 */
	public String[][] getTicketsAsArray() {
		String[][] x = new String[super.getTickets().size()][2];
		for(int i = 0; i < super.getTickets().size(); i++) {
			x[i][0] = super.getTickets().get(i).getCategoryName();
			x[i][1] = super.getTickets().get(i).getTicketName();
		}
		return x;
	}
	
	/**
	 * clears the active ticket list of all tickets
	 */
	public void clearTickets() {
		for(int i = super.getTickets().size() - 1; i >= 0; i--) {
			super.removeTicket(i);
		}
	}
	
	
	
}
