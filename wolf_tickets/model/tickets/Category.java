package edu.ncsu.csc216.wolf_tickets.model.tickets;

/**
 * Extends AbstractCategory and implements the Comparable interface. Category class that
 * allows for getting tickets as a 2d array and comparing between category objects.
 * @author trung
 *
 */
public class Category extends AbstractCategory implements Comparable<Category> {

	/**
	 * constructor of category
	 * @param categoryName the name of the category
	 * @param completedCount the number of completed tickets
	 */
	public Category(String categoryName, int completedCount) {
		super(categoryName, completedCount);
	}
	
	/**
	 * returns a 2d String array where the first column is the priority of the ticket and the name of the ticket
	 * @return a 2d array 
	 */
	public String[][] getTicketsAsArray() {
		String[][] x = new String[super.getTickets().size()][2];
		for(int i = 0; i < super.getTickets().size(); i++) {
			x[i][0] = "" + i;
			x[i][1] = super.getTickets().get(i).getTicketName();
		}
		return x;
	}
	
	/**
	 * compares the names of the categories
	 * @param otherCategory the category you are comparing with
	 * @return an integer representation of how different the categories are
	 */
	public int compareTo(Category otherCategory) {
		return this.getCategoryName().toUpperCase().compareTo(otherCategory.getCategoryName().toUpperCase());
	}

}
