package itec_2905_capstone_project;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProjectTest {
	
	Project project = new Project(1000, "Project", "Verify.java", "Wendy", "Add code for screen.", "Closed", 2, "");

	@Test
	public void testGetWorkOrder() {
		assertEquals(project.getWorkOrder(), 1000);
	}
	
	@Test
	public void testGetPackageName() {
		assertEquals(project.getPackageName(), "Project");
	}
	
	@Test
	public void testGetModuleName() {
		assertEquals(project.getModuleName(), "Verify.java");
	}
	
	@Test
	public void testGetAssignedTo() {
		assertEquals(project.getAssignedTo(), "Wendy");
	}
	
	@Test
	public void testGetDescription() {
		assertEquals(project.getDescription(), "Add code for screen.");
	}
	
	@Test
	public void testGetComments() {
		assertEquals(project.getComments(), "");
	}
	
	@Test
	public void testGetDateIssued() {
//		assertEquals(project.getDateIssued(), "Sat Nov 14 06:24:58 MST 2020");
	}
	
	@Test
	public void testGetDateClosed() {
		assertEquals(project.getDateClosed(), null);
	}
	
	@Test
	public void testGetStatus() {
		assertEquals(project.getStatus(), "Closed");
	}
	
	@Test
	public void testGetPriority() {
		assertEquals(project.getPriority(), 2);
	}
	
}

