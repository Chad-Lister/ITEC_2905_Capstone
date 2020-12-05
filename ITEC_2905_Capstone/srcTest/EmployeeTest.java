package itec_2905_capstone;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmployeeTest {
	
	Employee emp = new Employee(62, "Bob", "Johanson", "4138", 3, 5, 95);

	@Test
	public void testGetEmployeeId() {
		assertEquals(emp.getEmployeeId(), 62);
	}
	
	@Test
	public void testGetFirstName() {
		assertEquals(emp.getFirstName(), "Bob");
	}
	
	@Test
	public void testGetLastName() {
		assertEquals(emp.getLastName(), "Johanson");
	}
	
	@Test
	public void testGetPassword() {
		assertEquals(emp.getPassword(), "4138");
	}
	
	@Test
	public void testGetAdminLevel() {
		assertEquals(emp.getAdminLevel(), 3);
	}
	
	@Test
	public void testGetOpenProjects() {
		assertEquals(emp.getOpenProjects(), 5);
	}
	
	@Test
	public void testGetClosedProjects() {
		assertEquals(emp.getClosedProjects(), 95);
	}
}
