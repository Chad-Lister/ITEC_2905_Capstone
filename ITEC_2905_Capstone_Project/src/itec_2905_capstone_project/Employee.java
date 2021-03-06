package itec_2905_capstone_project;

/**
 * <h1>Employee</h1>
 * 
 * <p>This program defines the Employee.dat file used in the ITEC_2905_Capstone_Project.</p>
 * 
 * <p>Created:  11/15/2020</p>
 * 
 * @author Chad Lister
 *
 */

public class Employee implements java.io.Serializable {
	
	//  Attributes.
	private int employeeId;
	private String firstName;
	private String lastName;
	private String password;
	private int adminLevel;
	
	//  Constructors.
	public Employee() {
		
	}
	
	public Employee(int employeeId, String firstName, String lastName, String password, int adminLevel) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.adminLevel = adminLevel;
	}

	// Methods.
	
	/**
	 * This method returns the employee number for this employee.
	 * 
	 * <pre>Examples:
	 * {@code returnType (62) returns 62.
	 * returnType (1001) returns 1001.
	 * }</pre>
	 * 
	 * @return the employeeId (int:  This employees Id number.)
	 */
	
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * This method returns the first name for this employee.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Bob") returns "Bob".
	 * returnType ("Wendy") returns "Wendy".
	 * }</pre>
	 * 
	 * @return the firstName (String:  The employee's first name.)
	 */
	
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This method returns the last name for this employee.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Jones") returns "Jones".
	 * returnType ("Edwards") returns "Edwards".
	 * }</pre>
	 * 
	 * @return the lastName (String:  The employee's last name.)
	 */
	
	public String getLastName() {
		return lastName;
	}

	/**
	 * This method returns the employee password for this employee.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Banks2199") returns "Banks2199".
	 * returnType ("4138") returns "4138".
	 * }</pre>
	 * 
	 * @return the password (String:  The employee's password.)
	 */
	
	public String getPassword() {
		return password;
	}

	/**
	 * This method returns the administration level for this employee.
	 * 
	 * <pre>Examples:
	 * {@code returnType (1) returns 1.
	 * returnType (3) returns 3.
	 * }</pre>
	 * 
	 * @return the adminLevel (int:  The administration level for this employee.)
	 */
	
	public int getAdminLevel() {
		return adminLevel;
	}

	/**
	 * This method sets the employee Id number.
	 * 
	 * @param employeeId the employeeId to set
	 */
	
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * This method sets the employee's first name.
	 * 
	 * @param firstName the firstName to set
	 */
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * This method sets the employee's last name.
	 * 
	 * @param lastName the lastName to set
	 */
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * This method sets the employee's password.
	 * 
	 * @param password the password to set
	 */
	
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method sets the administration level for the employee.
	 * 
	 * @param adminLevel the adminLevel to set
	 */
	
	public void setAdminLevel(int adminLevel) {
		this.adminLevel = adminLevel;
	}
}
