package edu.ncsu.csc216.wolf_tickets.model.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.util.SortedList;

/**
 * writes information about a group to a file
 * @author trung
 *
 */
public class GroupWriter {
	
	/**
	 * write a group to a file
	 * @param groupFile the file you want to write to
	 * @param groupName the name of the group you want to write to a file
	 * @param categories the categories of the group you want to write to a file
	 * @throws IllegalArgumentException if the new file can't be made
	 */
	public static void writeGroupFile(File groupFile, String groupName, SortedList<Category> categories) {
		PrintStream fileWriter;
		try {
			fileWriter = new PrintStream(groupFile);
			fileWriter.print("! " + groupName + "\n");
			for(int i = 0; i < categories.size(); i++) {
				fileWriter.print("# " + categories.get(i).getCategoryName() + "," + categories.get(i).getCompletedCount() + "\n");
				for(int j = 0; j < categories.get(i).getTickets().size(); j++) {
					fileWriter.print(categories.get(i).getTickets().get(j).toString() + "\n");
				}
			}
			fileWriter.close();
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
}
