package edu.ncsu.csc216.wolf_tickets.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_tickets.model.group.Group;
import edu.ncsu.csc216.wolf_tickets.model.tickets.AbstractCategory;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Ticket;

/**
 * Reads a file to construct a group object
 * 
 * @author trung
 *
 */
public class GroupReader {

	/**
	 * reads given file and constructs a Group object
	 * 
	 * @param groupFile the file to be read
	 * @return a Group object
	 * @throws IllegalArgumentException if the file cannot be found or if the file doesn't start with an !
	 */
	public static Group readGroupFile(File groupFile) {
		Scanner groupScanner;
		try {
			groupScanner = new Scanner(groupFile);
			String groupName = groupScanner.nextLine();
			if(groupName.charAt(0) != '!') {
				groupScanner.close();
				throw new IllegalArgumentException("Unable to load file.");
			}
			groupName = groupName.substring(2);
			Group group = new Group(groupName);
			groupScanner.useDelimiter("\\r?\\n?[#]");
			while(groupScanner.hasNext()) {
				String categoryText = groupScanner.next();
				Scanner categoryScanner = new Scanner(categoryText);
				Category category = GroupReader.processCategory(categoryScanner.nextLine());
				categoryScanner.close();
				if(category == null) {
					continue;
				}
				Scanner ticketScanner = new Scanner(categoryText);
				ticketScanner.nextLine();
				ticketScanner.useDelimiter("\\r?\\n?[*]");
				while(ticketScanner.hasNext()) {
					GroupReader.processTicket(category, ticketScanner.next());
				}
				ticketScanner.close();
				group.addCategory(category);
			}
			groupScanner.close();
			group.setCurrentCategory("activeTicketList");
			return group;
				
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
	}

	/**
	 * processes a category string and creates a category object
	 * 
	 * @param categoryText a string that contains information about a category
	 * @return a category object or null if the category can't be constructed 
	 */
	private static Category processCategory(String categoryText) {
		String categoryName = "";
		int completedCount = 0;
		Scanner categoryScanner = new Scanner(categoryText);
		categoryScanner.useDelimiter(",");
		try {
			if (categoryScanner.hasNext()) {
				categoryName = categoryScanner.next();
				categoryName = categoryName.substring(1);
			}
			if (categoryScanner.hasNextInt()) {
				completedCount = categoryScanner.nextInt();
			} else {
				categoryScanner.close();
				throw new IllegalArgumentException();
			}
			categoryScanner.close();
			return new Category(categoryName, completedCount);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * process a ticket string and creates a ticket object
	 * 
	 * @param category   the category of the ticket
	 * @param ticketText string containing information about a ticket
	 * @return a ticket object or null if the ticket object can't be constructed
	 */
	private static Ticket processTicket(AbstractCategory category, String ticketText) {
		String ticketName = "";
		String ticketDescription = "";
		boolean active = false;
		Scanner ticketScanner = new Scanner(ticketText);
		try {
			if (ticketScanner.hasNextLine()) {
				ticketName = ticketScanner.nextLine();
				ticketName = ticketName.substring(1);
				if (ticketName.contains("active")) {
					active = true;
					ticketName = ticketName.substring(0, ticketName.indexOf(','));
				}

			}
			while (ticketScanner.hasNextLine()) {
				ticketDescription = ticketDescription + ticketScanner.nextLine();
			}
			ticketScanner.close();
			Ticket ticket = new Ticket(ticketName, ticketDescription, active);
			category.addTicket(ticket);
			return ticket;

		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
