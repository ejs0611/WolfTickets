package edu.ncsu.csc216.wolf_tickets.model.group;

import java.io.File;


import edu.ncsu.csc216.wolf_tickets.model.io.GroupWriter;
import edu.ncsu.csc216.wolf_tickets.model.tickets.AbstractCategory;
import edu.ncsu.csc216.wolf_tickets.model.tickets.ActiveTicketList;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Ticket;
import edu.ncsu.csc216.wolf_tickets.model.util.SortedList;
import edu.ncsu.csc216.wolf_tickets.model.util.SwapList;

/**
 * The group class manages categories and tickets. The class allows for adding, removing, editing, and getting categories.
 * The class also allows for adding and editing tickets.
 * @author trung
 *
 */
public class Group {
	/** the name of the group */
	private String groupName;
	/** whether or not the group has been changed since the last save */
	private boolean isChanged;
	/** list of active tickets */
	private ActiveTicketList activeTicketList;
	/** the current categories you want to see */
	private AbstractCategory currentCategory;
	/** list of all categories in the group */
	private SortedList<Category> categories;
	
	/**
	 * constructor of Group
	 * @param groupName the name of the group
	 * @throws IllegalArgumentException if groupName is null, empty, or matches ACTIVE_TASKS_NAME
	 */
	public Group(String groupName) {
		setGroupName(groupName);
		categories = new SortedList<Category>();
		activeTicketList = new ActiveTicketList();
		currentCategory = activeTicketList;
		isChanged = true;
	}
	
	/**
	 * saves the current group to the given file
	 * @param groupFile the file you want to save the group to
	 */
	public void saveGroup(File groupFile) {
		GroupWriter.writeGroupFile(groupFile, groupName, categories);
		setChanged(false);
	}
	
	/**
	 * gets the group name
	 * @return the group name
	 */
	public String getGroupName() {
		return groupName;
	}
	
	/**
	 * sets the group name to the parameter
	 * @param groupName the name of the group
	 */
	private void setGroupName(String groupName) {
		if(groupName == null || "".equals(groupName) || groupName.equals(ActiveTicketList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.groupName = groupName;
	}
	
	/**
	 * checks whether or not isChanged is true or false
	 * @return whether or not isChanged is true or false
	 */
	public boolean isChanged() {
		return isChanged;
	}
	
	/**
	 * sets the value of isChanged to the parameter
	 * @param changed the value you want to changed isChanged to
	 */
	public void setChanged(boolean changed) {
		isChanged = changed;
	}
	
	/**
	 * adds the given category to the list of categories, changes the current category to the new category, and isChanged 
	 * updates to true
	 * @param category the category you want to add
	 * @throws IllegalArgumentException if the category name is ACTIVE_TASKS_NAME or a duplicate of an existing category
	 */
	public void addCategory(Category category) {
		if(category.getCategoryName().toUpperCase().equals(ActiveTicketList.ACTIVE_TASKS_NAME.toUpperCase())) {
			throw new IllegalArgumentException("Invalid name.");
		}
		for(int i = 0; i < categories.size(); i++) {
			if(category.getCategoryName().toUpperCase().equals(categories.get(i).getCategoryName().toUpperCase())) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}
		categories.add(category);
		getActiveTicketList();
		currentCategory = category;
		setChanged(true);
	}
	
	/**
	 * returns a list of category names
	 * @return a String array of category names
	 */
	public String[] getCategoriesNames() {
		String[] categoryNames = new String[categories.size() + 1];
		categoryNames[0] = "Active Tickets";
		for(int i = 1; i < categoryNames.length; i++) {
			categoryNames[i] = categories.get(i - 1).getCategoryName();
		}
		
		return categoryNames;
	}
	
	/**
	 * rebuilds the active ticket list by going through every ticket and adding it to active ticket list if it is an active ticket
	 */
	private void getActiveTicketList() {
		activeTicketList.clearTickets();
		for(int i = 0; i < categories.size(); i++) {
			SwapList<Ticket> tickets = categories.get(i).getTickets();
			for(int j = 0; j < tickets.size(); j++) {
				if(tickets.get(j).isActive()) {
					activeTicketList.addTicket(tickets.get(j));
				}
			}
		}
	}
	
	/**
	 * sets the current category to the given category 
	 * @param categoryName the name you want to change the category name to
	 */
	public void setCurrentCategory(String categoryName) {
		boolean found = false;
		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getCategoryName().equals(categoryName)) {
				currentCategory = categories.get(i);
				found = true;
			}
		}
		if(!found) {
			getActiveTicketList();
			currentCategory = activeTicketList;
		}
		
		
	}
	
	/**
	 * gets the current category
	 * @return the current category
	 */
	public AbstractCategory getCurrentCategory() {
		return currentCategory;
	}
	
	/**
	 * removes the given category, edits it, and adds it back to the list of categories. IsChanged is set to true.
	 * @param categoryName the name of the category you want to edit
	 * @throws IllegalArgumentException if the current category is ActiveTicketList, or if the category you are trying to edit
	 * is ActiveTicketList or if the new category name matches a category that already exists.
	 */
	public void editCategory(String categoryName) {
		if(currentCategory.getCategoryName().equals(ActiveTicketList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("The Active Tickets list may not be edited.");
		}
		if(categoryName.toUpperCase().equals(ActiveTicketList.ACTIVE_TASKS_NAME.toUpperCase())) {
			throw new IllegalArgumentException("Invalid name.");
		}
		for(int i = 0; i < categories.size(); i++) {
			if(categoryName.toUpperCase().equals(categories.get(i).getCategoryName().toUpperCase())) 
				throw new IllegalArgumentException("Invalid name.");
		}
		
		for(int j = 0; j < categories.size(); j++) {
			if(currentCategory.getCategoryName().equals(categories.get(j).getCategoryName())) {
				Category newCategory = categories.remove(j);
				newCategory.setCategoryName(categoryName);
				addCategory(newCategory);
			}
		}
		
	}
	
	/**
	 * removes the current category and sets the new current category to active ticket list. IsChanged is set to true. Updates
	 * active ticket list if the ticket being removed is active.
	 * @throws IllegalArgumentException if currentCategory is active ticket list
	 */
	public void removeCategory() {
		if(currentCategory.getCategoryName().equals(ActiveTicketList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("The Active Tickets list may not be deleted.");
		}
		
		for(int i = 0; i < categories.size(); i++) {
			if(currentCategory.getCategoryName().equals(categories.get(i).getCategoryName())) {
				categories.remove(i);
				getActiveTicketList();
			}
		}
		
		currentCategory = activeTicketList;
		setChanged(true);
	}
	
	/**
	 * adds a ticket to a category if current category is not Active Tickets. Also updates active ticket list if the ticket being added is
	 * active. isChanged set to true
	 * @param t the ticket you want to add
	 */
	public void addTicket(Ticket t) {
		if(!currentCategory.getCategoryName().equals(ActiveTicketList.ACTIVE_TASKS_NAME)) {
			currentCategory.addTicket(t);
			if(t.isActive()) {
				getActiveTicketList();
			}
		}
	}
	
	/**
	 * edits a ticket to the given parameters if the current category is not Active Tickets. Also update active ticket list if the ticket
	 * is active. isChanged set to true
	 * @param idx the index of the ticket you want to edit
	 * @param ticketName the name you want to change the ticket name to
	 * @param ticketDescription the description you want to change the description to
	 * @param active the state you want to change active to
	 */
	public void editTicket(int idx, String ticketName, String ticketDescription, boolean active) {
		if(!currentCategory.getCategoryName().equals(ActiveTicketList.ACTIVE_TASKS_NAME)) {
			SwapList<Ticket> ticketList = currentCategory.getTickets();
			ticketList.get(idx).setTicketName(ticketName);
			ticketList.get(idx).setTicketDescription(ticketDescription);
			ticketList.get(idx).setActive(active);
			if(ticketList.get(idx).isActive()) {
				getActiveTicketList();
			}
			setChanged(true);
		}
	}
}
