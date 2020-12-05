package itec_2905_capstone;

/**
 * <h1>Print</h1>
 * <p>This program prints selections from the Project.dat and Employee.dat file.  Used only error checking.</p>
 * @author Chad Lister
 *         Date:  12/05/2020
 * @created 12/05/2020
 */
import java.io.*;

public class Print {
	public static void main(String[] args) throws Exception, EOFException, NumberFormatException, FileNotFoundException  {
		
		try (ObjectInputStream file = new ObjectInputStream(new FileInputStream("Employee.dat"));
		) {
			
			// While not End of File.
			while (true) {
				
				Employee obj = (Employee) file.readObject();
				System.out.println("Employee = " + obj.getFirstName() + "\tOpen = " + obj.getOpenProjects() + "\tClosed = " + obj.getClosedProjects());

			}
		}
		catch (EOFException ex) {
		}
		
		int total = 0;
		
		try (ObjectInputStream file = new ObjectInputStream(new FileInputStream("Project.dat"));
		) {
			
			// While not End of File.
			while (true) {
				
				Project obj = (Project) file.readObject();
				System.out.println(obj.getWorkOrder() + "\tfield = " + obj.getField());
//				System.out.println(obj.getWorkOrder() + ",   Date iss   " + obj.getDateIssued() + "\tDate CL  " + obj.getDateClosed() + "\tStatus  " + obj.getStatus());
//				System.out.println("\tassigned " + obj.getAssignedTo() + "\tcom " + obj.getComments() + "\tFIELD" + obj.getField());
				total++;
			}
		}
		catch (EOFException ex) {
		}
		System.out.println("Total records at " + total);
	}
}