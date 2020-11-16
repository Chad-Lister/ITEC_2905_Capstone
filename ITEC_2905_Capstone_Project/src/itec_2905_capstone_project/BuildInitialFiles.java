package itec_2905_capstone_project;

/**
 * <h1>BuildInitialFiles</h1>
 * 
 * <p>This program builds the Project.dat and Employee.dat files used in ITEC_2905_Capstone_Project.</p>
 * 
 * <p>Created: 11/15/2020</p>
 * 
 * @author Chad Lister
 *
 */

	
import java.io.*;
import java.util.*;

public class BuildInitialFiles {
	
	public static void main(String[] args) throws IOException {
				
		//  Build employee information file.
		try (ObjectOutputStream outMember = new ObjectOutputStream(new FileOutputStream("Employee.dat"));
		) {
			Employee m1 = new Employee(62, "Bob", "Johnson", "4138", 3);
			Employee m2 = new Employee(1001, "Doug", "Banks", "Banks2199", 1);
			Employee m3 = new Employee(162, "Wendy", "Jameson", "IlvU", 1);
			Employee m4 = new Employee(314, "Barbara", "Douglas", "barb", 3);
			Employee m5 = new Employee(413, "Todd", "Tanner", "EseApI", 1);
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
			Project p1 = new Project(1000, "Project", "Verify", "Wendy", "Add code for screen.", "Open", 2, "Test verify method.");
			Project p2 = new Project(1001, "Project", "DetailScreen", "Doug", "Add code.", "Open", 3, "Test detail method.");
			Project p3 = new Project(1002, "Project", "Edit", "Doug", "Add edit method.", "Open", 1, "Test edit method.");
			Project p4 = new Project(1003, "Project Admin", "Assign", "Barbara", "Enable edit.", "Open", 1, "");
			Project p5 = new Project(1004, "Future Project", "Bigger", "Bob", "Future improvements.", "Open", 4, "");
			Project p6 = new Project(1005, "Main", "Field", "Todd", "Make field flags.", "Open", 3, "Make and test flags.");
			Project p7 = new Project(1006, "Main", "Test", "Bob", "Check screens.", "Open", 3, "Check screen transition.");
			Project p8 = new Project(1007, "Final", "Document", "Todd", "Check documents.", "Open", 3, "Check documents.");
			Project p9 = new Project(1008, "Final", "Comments", "Doug", "Check comments.", "Open", 3, "Check commented properly.");
			Project p10 = new Project(1009, "Project", "TeamMember", "Wendy", "Check team member page.", "Open", 3, "Check page.");
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