/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tickets.model.group.Group;

/**
 * tests GroupReader methods
 * 
 * @author trung
 *
 */
class GroupReaderTest {

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.wolf_tickets.model.io.GroupReader#readGroupFile(java.io.File)}.
	 */
	@Test
	void testReadGroupFile() {
		File test = new File("test-files/group2.txt");
		Group group = GroupReader.readGroupFile(test);
		assertEquals("CSC IT", group.getGroupName());
		assertEquals(4, group.getCategoriesNames().length);
		assertEquals("Active Tickets", group.getCategoriesNames()[0]);
		assertEquals("Classroom Tech", group.getCategoriesNames()[1]);
		assertEquals("Desktop", group.getCategoriesNames()[2]);
		assertEquals("Web", group.getCategoriesNames()[3]);
		assertEquals("Active Tickets", group.getCurrentCategory().getCategoryName());
		assertEquals(4, group.getCurrentCategory().getTickets().size());
	}

}
