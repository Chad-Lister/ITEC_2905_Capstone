package itec_2905_capstone;

import java.util.*;

/**
 * <h1>Project</h1>
 * <p>This program defines the Project.dat file used in the ITEC_2905_Capstone_Project.</p>
 * 
 * @author Chad Lister
 *         Date:  11/15/2020
 * @created 11/15/2020
 * <p>Added field attribute for Project Management.</p>
 * <p>Chad Lister 12/05/2020</p>
 */

public class Project implements java.io.Serializable {
	
	//  Attributes.
	private int workOrder;
	private String packageName;
	private String moduleName;
	private String assignedTo;
	private String description;
	private String comments;
	private Date dateIssued;
	private Date dateClosed;
	private String status;
	private int priority;
	private String field;
	
	// Constructors.
	public Project() {
	}
	
	public Project(int workOrder, String packageName, String moduleName, String assignedTo, String description, String status, int priority, String comments, String field) {
		this.workOrder = workOrder;
		this.packageName = packageName;
		this.moduleName = moduleName;
		this.assignedTo = assignedTo;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.comments = comments;
		this.dateIssued = new java.util.Date();
//		this.dateClosed = new java.util.Date();
		this.field = field;
	}
	
	public Project(int workOrder, String packageName, String moduleName, String assignedTo, String description, String status, int priority, String comments, Date iss, Date cl, String field) {
		this.workOrder = workOrder;
		this.packageName = packageName;
		this.moduleName = moduleName;
		this.assignedTo = assignedTo;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.comments = comments;
		this.dateIssued = iss;
		this.dateClosed = cl;
		this.field = field;
	}
	
	// Methods.
	
	/**
	 * This method returns the work order number associated with this project.
	 * 
	 * <pre>Examples:
	 * {@code returnType (1001) returns 1001.
	 * returnType (1002) returns 1002.
	 * }</pre>
	 * 
	 * @return the workOrder (int:  The work order number.)
	 */
	public int getWorkOrder() {
		return workOrder;
	}
	
	/**
	 * This method returns the package name associated with this work order.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Project") returns "Project".
	 * returnType ("Main") returns "Main".
	 * </pre>
	 * 
	 * @return the packageName (String:  The package name to which this work order belongs.
	 */
	public String getPackageName() {
		return packageName;
	}
	
	/**
	 * This method returns the module name associated with this work order.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Edit.java") returns "Edit.java".
	 * returnType ("Test.java") returns "Test.java".
	 * </pre>
	 * 
	 * @return the moduleName (String:  The module name to which this work order belongs.)
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * This method returns the team member assigned to this work order.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Bob") returns "Bob".
	 * returnType ("Wendy") returns "Wendy".
	 * </pre>
	 * 
	 * @return the assignedTo (String:  The first name of the team member.)
	 */
	public String getAssignedTo() {
		return assignedTo;
	}

	/**
	 * This method returns the assignment description associated with this work order.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Test Edit.java") returns "Test edit.java".
	 * returnType ("Make Test.java") returns "Make Test.java".
	 * </pre>
	 * 
	 * @return the description (String:  The description associated to the work order.)
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method returns the comments associated with this work order.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Testing") returns "Testing".
	 * returnType ("Which method?") returns "Which method?".
	 * </pre>
	 * 
	 * @return the comments (String:  The comments associated with this work order.)
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * This method returns the date this work order was issued.
	 * 
	 * <pre>Examples:
	 * {@code returnType (Wed Nov 11 11:12:00 MST 2020) returns Wed Nov 11 11:12:00 MST 2020.
	 * returnType (Fri Nov 13 11:12:00 MST 2020) returns Fri Nov 13 11:12:00 MST 2020.
	 * </pre>
	 * 
	 * @return the dateIssued (Date:  The date this work order was issued.)
	 */
	public Date getDateIssued() {
		return dateIssued;
	}

	/**
	 * This method returns the date this work order was closed.
	 * 
	 * <pre>Examples:
	 * {@code returnType (Wed Nov 11 11:12:00 MST 2020) returns Wed Nov 11 11:12:00 MST 2020.
	 * returnType (Fri Nov 13 11:12:00 MST 2020) returns Fri Nov 13 11:12:00 MST 2020.
	 * </pre>
	 * 
	 * @return the dateClosed (Date:  TThe date this work order was closed.)
	 */
	public Date getDateClosed() {
		return dateClosed;
	}

	/**
	 * This method returns the status associated with this work order.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("Open") returns "Open".
	 * returnType ("Closed") returns "Closed".
	 * </pre>
	 * 
	 * @return the status (String:  The status of this work order.)
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method returns the priority associated with this work order.
	 * 
	 * <pre>Examples:
	 * {@code returnType (1) returns 1.
	 * returnType (3) returns 3.
	 * </pre>
	 * 
	 * @return the priority (int:  The priority of this work order.)
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * This method returns the modified field if there is a change with this order.
	 * 
	 * <pre>Examples:
	 * {@code returnType ("New") returns "New".
	 * returnType ("Closed") returns "Closed".
	 * </pre>
	 * 
	 * @return the field (String:  The field value associated with this work order.)
	 */
	public String getField() {
		return field;
	}
	
	/**
	 * This method sets the number of this work order.
	 * 
	 * @param workOrder the workOrder to set
	 */
	public void setWorkOrder(int workOrder) {
		this.workOrder = workOrder;
	}
	
	/**
	 * This method sets the package name associated with this work order.
	 * 
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * This method sets which team member this work order is assigned to.
	 * 
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * This method sets the comments for this work order.
	 * 
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * This method sets the date this work order was issued.
	 * 
	 * @param dateIssued the dateIssued to set
	 */
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	
	/**
	 * This method sets the date this work order was closed.
	 * 
	 * @param dateClosed the dateClosed to set
	 */
	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	/**
	 * This method sets the status of this work order.
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * This method sets the priority for this work order.
	 * 
	 * @param priority the priority to set
	 */
	public void setPriorityNumber(int priority) {
		this.priority = priority;
	}
	
	/**
	 * This method sets the field for this order when it is changed.
	 * 
	 * @param field the value to set the field to.
	 */
	public void setField(String field) {
		this.field = field;
	}
}
