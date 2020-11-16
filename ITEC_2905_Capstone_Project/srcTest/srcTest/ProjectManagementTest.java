package itec_2905_capstone_project;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import org.junit.Test;

import itec_2905_capstone_project.ProjectManagement.ProjectList;

public class ProjectManagementTest {
	
//	ArrayList<ProjectList> proj = ArrayListt<ProjectList>(1001, "Main", "Test.java", "Doug", "Test module.", "Testing.", "Open", "dateIssued", "dateClosed", 1);

	@Test
	public void testGetWorkOrder() {
//		assertEquals(proj.getWorkOrder(1001), 1001);
	}

	@Test
	public void testGetPackageName() {
//		assertEquals(proj.getPackageName("Main"), "Main");
	}
	
	@Test
	public void testGetModuleName() {
//		assertEquals(proj.getModuleName("Test.java"), "Test.java");
	}
	
	@Test
	public void testGetAssignedTo() {
//		assertEquals(proj.getAssignedTo("Doug"), "Doug");
	}
	
	@Test
	public void testGetDescription() {
//		assertEquals(proj.getDescription("Test module."), "Test module.");
	}
	
	@Test
	public void testGetComments() {
//		assertEquals(proj.getComments("Testing."), "Testing.");
	}
	
	@Test
	public void testGetDateIssued() {
//		assertEquals(proj.getDateIssued("dateIssued"), "dateIssued");
	}
	
	@Test
	public void testGetDateClosed() {
//		assertEquals(proj.getDateClosed("dateClosed"), "dateClosed");
	}
	
	@Test
	public void testGetStatus() {
//		assertEquals(proj.getStatus("Open"), "Open");
	}
	
	@Test
	public void testGetPriority() {
//		assertEquals(proj.getPriority(1), 1);
	}

}
