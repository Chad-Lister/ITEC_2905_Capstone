package itec_2905_capstone;

/**
 * <h1>BuildInitialFiles</h1>
 * <p>This program builds the Project.dat and Employee.dat files used in ITEC_2905_Capstone_Project.</p>
 * 
 * @author Chad Lister
 *         Date:  11/15/2020
 * @created 11/15/2020
 */

	
import java.io.*;

public class BuildInitialFiles {
	
	public static void main(String[] args) throws IOException {
				
		//  Build employee information file.
		try (ObjectOutputStream outMember = new ObjectOutputStream(new FileOutputStream("Employee.dat"));
		) {
			Employee m1 = new Employee(62, "Bob", "Johanson", "4138", 3, 2, 95);
			Employee m2 = new Employee(1001, "Doug", "Banks", "Banks2199", 1, 3, 1);
			Employee m3 = new Employee(162, "Wendy", "Jameson", "IlvU", 1, 1, 1);
			Employee m4 = new Employee(314, "Barbara", "Douglas", "barb", 3, 1, 96);
			Employee m5 = new Employee(413, "Todd", "Tanner", "EseApI", 1, 2, 20);
			outMember.writeObject(m1);
			outMember.writeObject(m2);
			outMember.writeObject(m3);
			outMember.writeObject(m4);
			outMember.writeObject(m5);
		}
		catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
		
		//  Build project file.
		try (ObjectOutputStream outProject = new ObjectOutputStream(new FileOutputStream("Project.dat"));
		) {
			Project p1 = new Project(1000, "Project", "Verify.java", "Wendy", "Add code for screen.", "Closed", 2, "", "Closed");
			Project p2 = new Project(1001, "Project", "Detail_Screen.java", "Doug", "Add code.", "Open", 3, "", "");
			Project p3 = new Project(1002, "Project", "Edit.java", "Doug", "Add edit method.", "Open", 1, "", "");
			Project p4 = new Project(1003, "Project Admin", "Assign.java", "Barbara", "Enable edit.", "Open", 1, "", "");
			Project p5 = new Project(1004, "Future Project", "Bigger.java", "Bob", "Future improvements.", "Open", 4, "", "");
			Project p6 = new Project(1005, "Main", "Field.java", "Todd", "Make field flags.", "Open", 3, "Make and test flags.", "");
			Project p7 = new Project(1006, " Main", "Test.java", "Bob", "Check screens.", "Open", 3, "Check screen transition.", "");
			Project p8 = new Project(1007, "Final", "Document.java", "Todd", "Check documents.", "Open", 3, "Check documents.", "");
			Project p9 = new Project(1008, "Final", "Comments.java", "Doug", "Check comments.", "Open", 3, "Check commented properly.", "");
			Project p10 = new Project(1009, "Project", "TeamMember.java", "Wendy", "Check team member page.", "Open", 3, "Check page.", "");
			outProject.writeObject(p1);
			outProject.writeObject(p2);
			outProject.writeObject(p3);
			outProject.writeObject(p4);
			outProject.writeObject(p5);
			outProject.writeObject(p6);
			outProject.writeObject(p7);
			outProject.writeObject(p8);
			outProject.writeObject(p9);
			outProject.writeObject(p10);
		}
		catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
	}
}