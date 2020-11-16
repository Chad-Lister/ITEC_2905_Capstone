package itec_2905_capstone_project;

import java.io.*;

/**
 * <h1>PrintProjectFile</h1>
 * 
 * <p>This program prints fields from the Project.dat file for ITEC_2905_Capstone_Project.</p>
 * 
 * <p>Created: 11/15/2020</p>
 * 
 * @author Chad Lister
 *
 */

public class PrintProjectFile {
	public static void main(String[] args) throws Exception, EOFException, NumberFormatException, FileNotFoundException  {
		
		int total = 0;
		
		try (ObjectInputStream file = new ObjectInputStream(new FileInputStream("Project.dat"));
		) {
			
			// While not End of File.
			while (true) {
				
				Project obj = (Project) file.readObject();
				System.out.println(obj.getWorkOrder() + "   Date issued   " + obj.getDateIssued() + "\tDate Closed  " + obj.getDateClosed() + "\tStatus  " + obj.getStatus());
				System.out.println("Assigned To " + obj.getAssignedTo() + "  Comments " + obj.getComments());
				System.out.println();
				total++;
			}
		}
		catch (EOFException ex) {
		}
		System.out.println("Total records at " + total);
	}
}