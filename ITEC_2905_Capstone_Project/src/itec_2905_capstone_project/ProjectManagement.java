package itec_2905_capstone_project;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import javafx.stage.Stage;


/**
 * <h1>ProjectManagement</h1>
 * <p>This program uses two files to track, add, modify and close issues that arise from a software release.</p>
 * 
 * <p>Created:  11/15/2020</p>
 * 
 * @author Chad Lister
 * 
 */

public class ProjectManagement extends Application {
	
	// Attributes.
	private TableView<ProjectList> table = new TableView<ProjectList>();
	private ObservableList <ProjectList> data = FXCollections.observableArrayList();
	private int index = 0;
	private int lastWorkOrder = 0;
	private String firstName;
	private String empName = "";
	private String com = "";
	private boolean newFlag = false;
	private boolean modFlag = false;
	private GridPane pane2 = new GridPane();
	private Stage currentScreen = new Stage();
	private Stage memberScreen = new Stage();
	private Stage adminScreen = new Stage();
	private Stage detailStage = new Stage();
	private Stage reassignStage = new Stage();
	private Stage adminDetail = new Stage();
		
	// @Override // Override the start method in the Application class.
	public void start(Stage primaryStage) throws Exception, EOFException, NumberFormatException, FileNotFoundException {
		
		// Business name header.
		HBox businessHeader = new HBox();
		businessHeader.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey; -fx-margin-top: 10px; -fx-margin-bottom: 10px");
		businessHeader.setPadding(new Insets(20, 40, 20, 40));
		Text name = new Text("Project Management System");
		name.setFont(new Font("Arial", 40));
		name.setStroke(Color.BLACK);
		name.setFill(Color.RED);
		businessHeader.getChildren().add(name);
		businessHeader.setAlignment(Pos.TOP_CENTER);
		businessHeader.setPrefSize(600, 100);

		// Create a pane and set its properties.
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(8.5, 8.5, 8.5, 8.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		pane.setStyle("-fx-background-color: burlywood");
		pane.setPrefSize(600, 400);

		// Place login nodes in the pane.
		Text employeeText = new Text("Employee Id:  ");
		employeeText.setFont(new Font("Arial", 20));
		pane.add(employeeText, 0, 0);
		TextField employeeId = new TextField();
		employeeId.setPrefWidth(100);;
		pane.add(employeeId, 1, 0);
		Text passwordText = new Text("Password:  ");
		passwordText.setFont(new Font("Arial", 20));
		pane.add(passwordText, 0, 4);
		TextField password = new TextField();
		pane.add(password, 1, 4);
		password.setPrefWidth(100);
		Button loginButton = new Button("Employee Login");
		pane.add(loginButton, 1, 8);
		Text error = new Text();
		error.setFont(new Font("Arial", 20));
		pane.add(error, 0, 12);
		Button exitSystem = new Button("Exit System");
		pane.add(exitSystem, 0, 15);
		
		// Password field enter listener.
		password.setOnAction(e -> {
			
			try {
				
				// Collect text field values and convert to integers.
				String employeeNumber = employeeId.getText().trim();
				String employeePassword = password.getText().trim();
				int employee = Integer.parseInt(employeeNumber);
				
				// Invoke verify method.
				verify(employee, employeePassword);
			}
			catch (Exception ex) {
				
				// Show error message if it doesn't match format.
				employeeId.clear();
				password.clear();
				error.setText("*** Invalid Entry ***");
				error.setStroke(Color.RED);
				employeeId.requestFocus();
			}
		});
		
		// Listener for login button.
		loginButton.setOnAction(e -> {
			
			try {
				
				// Collect text field values and convert to integers.
				String employeeNumber = employeeId.getText().trim();
				String employeePassword = password.getText().trim();
				int employee = Integer.parseInt(employeeNumber);
				
				// Invoke verify method.
				verify(employee, employeePassword);
			}
			catch (Exception ex) {
				
				// Show error if doesn't match format.
				employeeId.clear();
				password.clear();
				error.setText("*** Invalid Entry ***");
				error.setStroke(Color.RED);
				employeeId.requestFocus();
			}
		});
		
		// Listener for exit system button.
		exitSystem.setOnAction(e -> {
			
			// Exit stage.
			currentScreen.close();
		});

		// Setup login box.
		BorderPane loginScreen = new BorderPane();
		loginScreen.setCenter(pane);
		loginScreen.setTop(businessHeader);
		loginScreen.setPrefSize(600, 600);

		// Create a scene and place it in the stage.
		Scene scene = new Scene(loginScreen);
		currentScreen.setTitle("Main Login");
		currentScreen.setScene(scene);
		currentScreen.show();
	}
	
	/**
	 * This method takes the login information and verifies if that information is correct by checking it against the Employee.dat file.
	 * 
	 * @param employeeId (int:  Represents the employee number entered in the "Employee Id" text field.)
	 * @param password (String:  Represents the password entered into the "Password" text field.)
	 * 
	 * @throws Exception
	 */
	
	public void verify(int employeeId, String password) throws Exception {
		
		// Read employee file.
		try {
			
			FileInputStream fileIn = new FileInputStream("Employee.dat");
			ObjectInputStream objIn = new ObjectInputStream(fileIn); 
		
			// While not End of File.
			while (true) {
				Employee employee = (Employee) objIn.readObject();
				int employeeNumber = employee.getEmployeeId();
				String pass = employee.getPassword();
				int level = employee.getAdminLevel();
				
				// If employee id and password matches.
				if (employeeNumber == employeeId && pass.contentEquals(password) == true) {
					
					// Team member level.
					if (level == 1) {
						
						firstName = employee.getFirstName();
						String lastName = employee.getLastName();
						empName = firstName + " " + lastName; 
						teamMemberPage();
						// Close file. Needed for concurrent applications?
						fileIn.close();
						currentScreen.close();
					}
					
					// Team Leader/Administration level.
					if (level == 3) {
						
						firstName = employee.getFirstName();
						String lastName = employee.getLastName();
						empName = firstName + " " + lastName;
							
						teamLeaderPage();
						//  Close file.  Needed for concurrent applications?
						fileIn.close();
						currentScreen.close();
					}
				}
			}
		}
		catch (Exception ex) {
		}
	}
	
	/**
	 * This method builds the team member's table used for the center display.
	 * 
	 * @param firstName (String:  Represents the team member's first name, used in filtering their assigned projects.)
	 * 
	 * @throws Exception
	 */
	
	public void buildMemberTable(String firstName) throws Exception {
		
		// Read projects file.	
		try {
			
			FileInputStream fileIn = new FileInputStream("Project.dat");
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			
			// While not End of File.
			while (true) {
				Project project = (Project) objIn.readObject();
				String assign = project.getAssignedTo();
				
				// If assigned to this team member.
				if (assign.contains(firstName) == true) {
					
					// And open.
					String s = project.getStatus();
					if (s.contentEquals("Open") == true) {
						
						// Add to table.
						int wo = project.getWorkOrder();
						String pack = project.getPackageName();
						String module = project.getModuleName();
						String assigned = project.getAssignedTo();
						String desc = project.getDescription();
						String status = project.getStatus();
						String comments = project.getComments();
						String dateIss = project.getDateIssued() + "";
						String dateCl = project.getDateClosed() + "";
						int priority = project.getPriority();

						data.add(new ProjectList(wo, pack, module, assigned, desc, comments, status, dateIss, dateCl, priority));
					}
				}
			}
		}
		catch (Exception ex) {
		}
		
		// Define table.
		TableColumn priorityCol = new TableColumn("Priority");
		priorityCol.setMinWidth(20);
		priorityCol.setSortType(TableColumn.SortType.ASCENDING);
		priorityCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Integer>("priority"));
		TableColumn issueDateCol = new TableColumn("Date Issued");
		issueDateCol.setMinWidth(50);
		issueDateCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Long>("dateIssued"));
		TableColumn workOrderCol = new TableColumn("Work Order");
		workOrderCol.setMinWidth(30);
		TableColumn packageCol = new TableColumn("Package");
		packageCol.setMinWidth(50);
		packageCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("packageName"));
		workOrderCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Integer>("workOrder"));
		TableColumn moduleNameCol = new TableColumn("Module Name");
		moduleNameCol.setMinWidth(60);
		moduleNameCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("moduleName"));
		TableColumn descCol = new TableColumn("Description");
		descCol.setMinWidth(150);
		descCol.setMaxWidth(155);
		descCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("description"));
		TableColumn statusCol = new TableColumn("Status");
		statusCol.setMinWidth(30);
		statusCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("status"));

		
		table.getColumns().addAll(priorityCol, workOrderCol, packageCol, moduleNameCol, descCol, statusCol, issueDateCol);
		
		table.setItems(data);
		table.getSortOrder().add(priorityCol);
	}
	
	/**
	 * This method builds the table for the center display.  Filters out closed projects.
	 * 
	 * @throws Exception
	 */
	
	public void buildAdminTable() throws Exception {
		
		// Read projects file.
		try {
			
			FileInputStream fileIn = new FileInputStream("Project.dat");
			ObjectInputStream obj = new ObjectInputStream(fileIn);
			
			// While not End of File.
			while (true) {
				Project project = (Project) obj.readObject();
				
				// Get work order.
				int wo = project.getWorkOrder();
				
				// If it is the last work order.  Used in add work order method.
				if (wo > lastWorkOrder) {
					lastWorkOrder = wo;
				}
				
				// Only include open orders..
				String stat = project.getStatus();
				if (stat.contentEquals("Open") == true) {
					
					// Get project information and add to table.
					String pack = project.getPackageName();
					String module = project.getModuleName();
					String assign = project.getAssignedTo();
					String desc = project.getDescription();
					String status = project.getStatus();
					String comments = project.getComments();
					String dateIss = project.getDateIssued() + "";
					String dateCl = project.getDateClosed() + "";
					int priority = project.getPriority();
					data.add(new ProjectList(wo, pack, module, assign, desc, comments, status, dateIss, dateCl, priority));
				}
			}
		}
		catch (Exception ex) {
		}
		
		// Define table.
		TableColumn priorityCol = new TableColumn("Priority");
		priorityCol.setMinWidth(20);
		priorityCol.setSortType(TableColumn.SortType.ASCENDING);
		priorityCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Integer>("priority"));
		TableColumn workOrderCol = new TableColumn("Work Order");
		workOrderCol.setMinWidth(30);
		workOrderCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Integer>("workOrder"));
		TableColumn packageCol = new TableColumn("Package");
		packageCol.setMinWidth(50);
		packageCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("packageName"));
		TableColumn moduleNameCol = new TableColumn("Module Name");
		moduleNameCol.setMinWidth(50);
		moduleNameCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("moduleName"));
		TableColumn assignedToCol = new TableColumn("Assigned To");
		assignedToCol.setMinWidth(50);
		assignedToCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("assignedTo"));
		TableColumn descCol = new TableColumn("Description");
		descCol.setMinWidth(150);
		descCol.setMaxWidth(155);
		descCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("description"));
		TableColumn issuedDateCol = new TableColumn("Issued Date");
		issuedDateCol.setMinWidth(30);
		issuedDateCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("dateIssued"));
		TableColumn statusCol = new TableColumn("Status");
		statusCol.setMinWidth(30);
		statusCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("status"));
		
		table.getColumns().addAll(priorityCol, workOrderCol, packageCol, moduleNameCol, assignedToCol, descCol, issuedDateCol, statusCol);
		
		table.setItems(data);
		table.getSortOrder().add(priorityCol);
	}
	
	/**
	 * This method drives the main team member display.
	 * 
	 * @throws Exception
	 */
	
	public void teamMemberPage() throws Exception {
		
		// Container for table contents.
		Group centerScene = new Group();
		
		// Team member header.
		HBox pageHeader = new HBox();
		pageHeader.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: silver; -fx-margin-top: 10px; -fx-margin-bottom: 10px");
		pageHeader.setPadding(new Insets(10, 20, 20, 10));
		pageHeader.setSpacing(40);
		Text currentDate = new Text(new String(new java.util.Date() + ""));
		currentDate.setFont(new Font("Arial", 20));
		Text name = new Text("Work Orders For " + empName);
		name.setFont(new Font("Arial", 20));
		pageHeader.getChildren().add(currentDate);
		pageHeader.getChildren().add(name);
		pageHeader.setAlignment(Pos.TOP_CENTER);
		pageHeader.setPrefSize(1000, 50);
		
		// Table header.
		HBox tableH = new HBox();
		Text th = new Text("Current Open Orders");
		th.setFont(new Font("Arial", 20));
		th.setStroke(Color.RED);
		tableH.setAlignment(Pos.CENTER);
		tableH.getChildren().add(th);
				
		// Setup table and build.
		try {
			buildMemberTable(firstName);
			table.setEditable(false);
		}
		catch (Exception ex){
		}
		
		// Table contents container.
		VBox vbox = new VBox();
		vbox.setSpacing(20);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.setPrefSize(800, 500);
		vbox.getChildren().add(tableH);
		vbox.getChildren().add(table);
		Text error = new Text();
		error.setFont(new Font("Arial", 20));
		error.setStroke(Color.RED);
		vbox.getChildren().add(error);
		
		// Setup member actions container.
		HBox actions = new HBox();
		actions.setSpacing(20);
		actions.setPadding(new Insets(10, 20, 40, 20));
		actions.setAlignment(Pos.BOTTOM_CENTER);
		Text detail = new Text("Open Detail Panel For:  ");
		detail.setFont(new Font("Arial", 20));
		TextField order = new TextField();
		order.setPrefWidth(100);
		Button refresh = new Button("Refresh Screen");
		actions.getChildren().addAll(detail, order, refresh);
		
		// Listener for detail pane.
		order.setOnAction(e -> {
			try {
				int orderNumber = Integer.parseInt(order.getText().trim());
				
				// Check if in open projects.
				for (ProjectList i: data) {
					
					int or = i.getWorkOrder();
					if (orderNumber == or) {
						order.clear();
						showDetail(orderNumber);
					}
				}
				
				// If it isn't then error.
				error.setText("Order Not Found");
				order.clear();
			}
			catch (NumberFormatException ex) {
				error.setText("Number Required");
				order.clear();
			}
			catch (Exception ex) {
			}
		});
		
		// Team member refresh table.  Differs from administrative table used in rereshTable method. 
		refresh.setOnAction(e -> {
			refreshMemberTable();
		});
				
		centerScene.getChildren().add(vbox);
		
		// Setup main team member page.
		BorderPane projectScreen = new BorderPane();
		projectScreen.setStyle("-fx-background-color: tan");
		projectScreen.setCenter(centerScene);
		projectScreen.setTop(pageHeader);
		projectScreen.setBottom(actions);
		projectScreen.setPrefSize(1000, 900);
		
		Scene scene = new Scene(projectScreen);
		memberScreen.setTitle("Projects Screen");
		memberScreen.setScene(scene);
		memberScreen.show();
	}
	
	/**
	 * This method shows the detail for the work order number entered.
	 * 
	 * @param orderNumber (int:  Represents the work order number to show the detail for.)
	 */
	
	public void showDetail(int orderNumber) throws Exception {
		
		// Display variables.
		String pack = "";
		String mod = "";
		String assigned = "";
		int pri = 0;
		String desc = "";
		
		// Read project File.
		try {
			FileInputStream in = new FileInputStream("Project.dat");
			ObjectInputStream proj = new ObjectInputStream(in);
			
			// Not EOF.
			while (true) {
				Project p = (Project) proj.readObject();
				int workO = p.getWorkOrder();
				
				if (orderNumber == workO) {
					pack = p.getPackageName();
					mod = p.getModuleName();
					assigned = p.getAssignedTo();
					pri = p.getPriority();
					com = p.getComments();
					desc = p.getDescription();
					String status = p.getStatus();

					newFlag = false;
					modFlag = true;
					rebuildFile(orderNumber, pack, mod, assigned, desc, status,pri, com, newFlag);
				}
			}
		}
		catch (Exception ex) {
		}
		
		// Page title.
		HBox orderHeader = new HBox();
		Text order = new Text("Detail for order # " + orderNumber);
		order.setFont(new Font("Arial", 30));
		orderHeader.setPadding(new Insets(40, 10, 10, 10));
		orderHeader.setAlignment(Pos.CENTER);
		orderHeader.getChildren().add(order);
		orderHeader.setPrefSize(1000, 50);
		
		// Center display.
		GridPane pane = new GridPane();
		Text prior = new Text("Priority:  ");
		prior.setFont(new Font("Arial", 20));
		pane.add(prior, 0, 0);
		Text priT = new Text(pri + "");
		priT.setFont(new Font("Arial", 20));
		pane.add(priT, 1, 0);
		Text assign = new Text("Assigned To:  ");
		assign.setFont(new Font("Arial", 20));
		pane.add(assign, 0, 1);
		Text aText = new Text(assigned);
		aText.setFont(new Font("Arial", 20));
		pane.add(aText, 1, 1);
		Text packg = new Text("Package:  ");
		packg.setFont(new Font("Arial", 20));
		pane.add(packg, 0, 2);
		Text packT = new Text(pack);
		packT.setFont(new Font("Arial", 20));
		pane.add(packT, 1, 2);
		Text module = new Text("Module:  ");
		module.setFont(new Font("Arial", 20));
		pane.add(module, 0, 3);
		Text modT = new Text(mod);
		modT.setFont(new Font("Arial", 20));
		pane.add(modT, 1, 3);
		Text des = new Text("Descrption:  ");
		des.setFont(new Font("Arial", 20));
		pane.add(des, 0, 4);
		Text descT = new Text(desc);
		descT.setFont(new Font("Arial", 20));
		pane.add(descT, 1, 4);
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(30);
		pane.setPrefSize(1000, 600);
		
		// Comment area.
		VBox comA = new VBox();
		comA.setPadding(new Insets(10, 40, 10, 40));
		Text comments = new Text("Comments:  ");
		comments.setFont(new Font("Arial", 20));
		TextArea comF = new TextArea(com);
		comF.setPrefSize(200, 200);
		comF.setEditable(false);
		comA.setAlignment(Pos.CENTER);
		comA.getChildren().addAll(comments, comF);
		comA.setPrefSize(250, 250);
		
		// Bottom box.
		HBox actions = new HBox();
		actions.setSpacing(10);
		actions.setPadding(new Insets(10, 20, 40, 20));
		actions.setAlignment(Pos.CENTER);
		Button edit = new Button("Add Comment");
		Button previous = new Button("Previous Screen");
		actions.getChildren().addAll(edit, previous);
		actions.setPrefSize(100, 100);
		
		// Button listeners.
		edit.setOnAction(e -> {
			addComment(orderNumber, com);
			
		});

		previous.setOnAction(e -> {
			detailStage.close();
		});
		
		BorderPane detailScreen = new BorderPane();
		detailScreen.setStyle("-fx-background-color: tan");
		detailScreen.setTop(orderHeader);
		detailScreen.setCenter(pane);
		detailScreen.setRight(comA);
		detailScreen.setBottom(actions);
		detailScreen.setPrefSize(1000, 700);
		
		Scene detail = new Scene(detailScreen);
		detailStage.setTitle("Order Detail");
		detailStage.setScene(detail);
		detailStage.show();
	}
	
	/**
	 * This method get's additional comments from the team member.
	 * 
	 * @param orderNumber (int;  Represents the work order number the comments belong to.)
	 * @param com (String:  Represents the comments already attributed to the work order.)
	 */
	
	public void addComment(int orderNumber, String com) {
		
		// Comment box.
		VBox ac = new VBox();
		ac.setPadding(new Insets(10, 10, 10, 10));
		ac.setSpacing(10);
		Text oCom = new Text(com);
		oCom.setFont(new Font("Arial", 20));
		Text msg = new Text("Comment To Add");
		msg.setFont(new Font("Aarial", 20));
		TextField add = new TextField();
		add.setPrefWidth(200);
		ac.getChildren().addAll(oCom, msg, add);
		ac.setPrefSize(300, 200);
		
		// Additional comment listener.
		add.setOnAction(e -> {
			String additional = add.getText().trim();
			String packN = "";
			String module = "";
			String assign = "";
			String desc = "";
			String status = "";
			int prior = 0;
			String comments = "";
			
			// Get specific project.
			for (ProjectList p: data) {
				int order = p.getWorkOrder();
				
				// If this project.  Add additional comment.
				if (order == orderNumber) {
					String origcom = p.getComments();
					String newCom = origcom + "\n" + additional;
					p.setComments(newCom);
					packN = p.getPackageName();
					module = p.getModuleName();
					assign = p.getAssignedTo();
					desc = p.getDescription();
					status = p.getStatus();
					prior = p.getPriority();
					comments = newCom;
					
					newFlag = false;
					modFlag = true;
					rebuildFile(orderNumber, packN, module, assign, desc, status, prior, comments, newFlag);
				}
			}
			
			// Back to detail screen.
			try {
				showDetail(orderNumber);
			}
			catch (Exception ex) {
			}
			currentScreen.close();
		});
		
		Pane newComment = new Pane();
		newComment.getChildren().add(ac);
		
		Scene n = new Scene(newComment);
		currentScreen.setTitle("Comment Screen");
		currentScreen.setScene(n);
		currentScreen.show();
	}
	
	public void refreshMemberTable() {
		
		// Clear data contents.
		data.clear();
		table.setItems(data);
		
		// Read projects file.
		try (ObjectInputStream file = new ObjectInputStream(new FileInputStream("Project.dat"));
		) {
			
			// While not End of File.
			while (true) {
				Project project = (Project) file.readObject();
				String assign = project.getAssignedTo();
				
				// If assigned to this team member.
				if (assign.contains(firstName) == true) {
					
					// And open.
					String s = project.getStatus();
					if (s.contentEquals("Open") == true) {
						
						// Add to new table contents.
						int wo = project.getWorkOrder();
						String pack = project.getPackageName();
						String module = project.getModuleName();
						String assigned = project.getAssignedTo();
						String desc = project.getDescription();
						String status = project.getStatus();
						String comments = project.getComments();
						String dateIss = project.getDateIssued() + "";
						String dateCl = project.getDateClosed() + "";
						int priority = project.getPriority();
						data.add(new ProjectList(wo, pack, module, assigned, desc, comments, status, dateIss, dateCl, priority));
					}
				}
			}
		}
		catch (Exception ex) {
		}
		table.setItems(data);
		table.sort();
	}
	
	/**
	 * This method drives the main administration/team leader display.
	 * 
	 * @throws Exception
	 */
	
	public void teamLeaderPage() throws Exception {
		
		// Setup administrative table and build.
		try {
			buildAdminTable();
			table.setEditable(false);
		}
		catch (Exception ex) {
		}
		
		// Container for table contents.
		Group centerScene = new Group();
		
		// Team member header.
		HBox pageHeader = new HBox();
		pageHeader.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: silver; -fx-margin-top: 10px; -fx-margin-bottom: 10px");
		pageHeader.setPadding(new Insets(10, 20, 20, 10));
		pageHeader.setSpacing(20);
		Text currentDate = new Text(new String(new java.util.Date() + ""));
		currentDate.setFont(new Font("Arial", 20));
		Text name = new Text("Administration Screen for " + empName);
		name.setFont(new Font("Arial", 20));
		pageHeader.getChildren().add(currentDate);
		pageHeader.getChildren().add(name);
		pageHeader.setAlignment(Pos.TOP_CENTER);
		pageHeader.setPrefSize(1200, 50);
		
		// Table header.
		HBox tableH = new HBox();
		Text th = new Text("Current Open Orders");
		th.setFont(new Font("Arial", 20));
		th.setStroke(Color.RED);
		tableH.setAlignment(Pos.CENTER);
		tableH.getChildren().add(th);
				
		// Table contents container.
		VBox vbox = new VBox();
		vbox.setSpacing(40);
		vbox.setPadding(new Insets(20, 10, 0, 10));
		vbox.setPrefSize(950, 600);
		vbox.getChildren().add(tableH);
		vbox.getChildren().add(table);
		
		// Setup administrative actions container.
		HBox actions = new HBox();
		actions.setSpacing(25);
		actions.setPadding(new Insets(10, 20, 40, 20));
		Button add = new Button("Add Work Order");
		Button close = new Button("Close Work Order");
		Button reassign = new Button("Reassign Work Order");
		Button detail = new Button("Detail Screen");
		Button refresh = new Button("Refresh Screen");
		actions.getChildren().addAll(add, close, reassign, detail, refresh);
		actions.setAlignment(Pos.CENTER);
		actions.setPrefSize(1200, 50);
		
		centerScene.getChildren().addAll(vbox);
		
		// Setup main administrative page.
		BorderPane projectScreen = new BorderPane();
		projectScreen.setStyle("-fx-background-color: lightsalmon");
		projectScreen.setCenter(centerScene);
		projectScreen.setTop(pageHeader);
		projectScreen.setBottom(actions);
		projectScreen.setPrefSize(1200, 900);
		
		// Button listeners.
		add.setOnAction(e -> addWorkOrder());
		close.setOnAction(e-> closeWorkOrder());
		reassign.setOnAction(e-> reassignTo());
		detail.setOnAction(e -> {
			getWorkNumber();
		});
		refresh.setOnAction(e -> {
			refreshTable();
		});
		
		Scene scene = new Scene(projectScreen);
		adminScreen.setTitle("Main Administration Screen");
		adminScreen.setScene(scene);
		adminScreen.show();
	}
	
	/**
	 * This method adds a work order to the Projects.dat file and to the ProjectList table.
	 */
	
	public void addWorkOrder() {
		
		// Page setup.
		HBox title = new HBox();
		Text page = new Text("Add Work Order");
		page.setFont(new Font("Arial", 20));
		page.setStroke(Color.RED);
		title.setPadding(new Insets(80, 20, 20, 20));
		title.getChildren().add(page);
		title.setAlignment(Pos.CENTER);
		title.setPrefSize(1000, 50);
		
		// Center display holder.
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(50);
		pane.setPadding(new Insets(1.5, 1.5, 60, 1.5));
		
		// Place nodes in the pane.
		HBox paneMsg = new HBox();
		Text msg = new Text();
		paneMsg.setAlignment(Pos.CENTER);
		paneMsg.getChildren().add(msg);
		pane.add(paneMsg, 6, 0);
		Text wo = new Text("Work Order:  ");
		wo.setFont(new Font("Arial", 20));
		pane.add(wo,  1,  2);
		Text workOrder = new Text();
		workOrder.setFont(new Font("Arial", 20));
		lastWorkOrder++;
		workOrder.setText(lastWorkOrder + "");
		pane.add(workOrder, 6, 2);
		Text pack = new Text("Package:  ");
		pack.setFont(new Font("Arial", 20));
		pane.add(pack,  1,  3);
		TextField packId = new TextField();
		packId.setPrefWidth(50);
		pane.add(packId, 6, 3);
		packId.setText("Project");
		Text priority = new Text("Priority:  ");
		priority.setFont(new Font("Arial", 20));
		pane.add(priority,  1,  4);
		TextField priorityN = new TextField();
		priorityN.setPrefWidth(50);
		pane.add(priorityN, 6, 4);
		Text assign = new Text("Assign To: ");
		assign.setFont(new Font("Arial", 20));
		pane.add(assign, 1, 5);
		TextField assignTo = new TextField();
		assignTo.setPrefWidth(50);
		pane.add(assignTo, 6, 5);
		Text module = new Text("Module: ");
		module.setFont(new Font("Arial", 20));
		pane.add(module, 1, 6);
		TextField moduleId = new TextField();
		moduleId.setPrefWidth(50);
		pane.add(moduleId, 6, 6);
		Text desc = new Text("Description: ");
		desc.setFont(new Font("Arial", 20));
		pane.add(desc, 1, 7);
		TextField descField = new TextField();
		descField.setPrefWidth(200);
		pane.add(descField, 6, 7);
		
		// Bottom box.
		HBox actions = new HBox();
		actions.setPadding(new Insets(10, 10, 40, 10));
		actions.setSpacing(20);
		Button previous = new Button("Previous Screen");
		Button proceed = new Button("Proceed");
		Button cancel = new Button("Cancel");
		actions.setAlignment(Pos.BOTTOM_CENTER);
		actions.getChildren().addAll(previous, proceed, cancel);
		
		// Comment area.
		VBox area = new VBox();
		area.setPadding(new Insets(20, 20, 60, 20));
		area.setAlignment(Pos.CENTER);
		Text com = new Text("Comments:  ");
		com.setFont(new Font("Arial", 20));
		TextArea comArea = new TextArea();
		comArea.setEditable(true);
		comArea.setPrefSize(200, 200);
		area.getChildren().addAll(com, comArea);
		
		//  Button listeners.
		proceed.setOnAction(e -> {
			
			try {
				
				// Get text field entries.
				int order = Integer.parseInt(workOrder.getText().trim());
				String packN = packId.getText().trim();
				int prior = Integer.parseInt(priorityN.getText().trim());
				String assignField = assignTo.getText().trim();
				String moduleField = moduleId.getText().trim();
				String descF = descField.getText().trim();
				String comments = comArea.getText().trim();
				String dateIss = new java.util.Date() + "";
				String dateCl = "";
				String status = "Open";
				
				// Add to table data and rebuild projects file.
				data.add(new ProjectList(order, packN, moduleField, assignField, descF, comments, "Open", dateIss, dateCl, prior));
				modFlag = false;
				newFlag = true;
				rebuildFile(order, packN, moduleField, assignField, descF, status, prior, comments, newFlag);
				
				msg.setText("Order Added");
				msg.setStroke(Color.RED);
				msg.setFont(new Font("Arial", 20));
				lastWorkOrder++;
				workOrder.setText(lastWorkOrder + "");
				priorityN.clear();
				assignTo.clear();
				moduleId.clear();
				descField.clear();
				comArea.clear();
				
				//  Reset flag.
				newFlag = false;
			}
			catch(NumberFormatException ex) {
				msg.setText("Field Error");
				msg.setFont(new Font("Arial", 20));
				msg.setStroke(Color.RED);
				priority.requestFocus();
			}
		});
		
		previous.setOnAction(e-> {
			currentScreen.close();
			adminScreen.show();
		});
				
		cancel.setOnAction(e-> {
			priorityN.clear();
			assignTo.clear();
			moduleId.clear();
			descField.clear();
			comArea.clear();
		});
		
		BorderPane addScreen = new BorderPane();
		addScreen.setTop(title);
		addScreen.setCenter(pane);
		addScreen.setRight(area);
		addScreen.setBottom(actions);
		addScreen.setStyle("-fx-background-color: lightsalmon");
		addScreen.setPrefSize(1000, 900);
						
		Scene add = new Scene(addScreen);
		currentScreen.setTitle("Add Order Screen");
		currentScreen.setScene(add);
		currentScreen.show();
		
		priorityN.requestFocus();
	}
	
	/**
	 * This method closes a work order.  It removes it from the ProjectList and sets the work order's fields.  Setting it's status to "Closed" and passing the correct flag to rebuild the file.
	 * 
	 */
	
	public void closeWorkOrder() {
		
		// Initial project list setup.
		HBox title = new HBox();
		Text page = new Text("Current Open Orders");
		page.setFont(new Font("Arial", 20));
		page.setStroke(Color.RED);
		title.setPadding(new Insets(80, 10, 10, 10));
		title.getChildren().add(page);
		title.setAlignment(Pos.CENTER);
		title.setPrefSize(1100, 40);
		
		// Center pane container.
		Pane main = new Pane();
		
		// Initial display data.
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(20);
		pane.setPadding(new Insets(1.5, 1.5, 1.5, 1.5));		
		pane.setPrefSize(1100, 500);
		
		// Initial column/row counters and column titles.
		Text orderText = new Text("Work Order");
		orderText.setFont(new Font("Arial", 20));
		pane.add(orderText, 0, 0);
		Text assignText = new Text("Assigned To");
		assignText.setFont(new Font("Arial", 20));
		pane.add(assignText, 1, 0);
		Text descText = new Text("Description");
		descText.setFont(new Font("Arial", 20));
		pane.add(descText, 2, 0);
		Text statusText = new Text("Status");
		statusText.setFont(new Font("Arial", 20));
		pane.add(statusText, 3, 0);
		Text issuedText= new Text("Date Issued");
		issuedText.setFont(new Font("Arial", 20));
		pane.add(issuedText, 4, 0);
		Text error = new Text();
		error.setFont(new Font("Arial", 20));
		error.setStroke(Color.RED);
		pane.add(error, 0, 10);
		int columnCounter = 0;
		int rowCounter = 1;
		
		// Get initial information.
		for (ProjectList i: data) {
			
			Text order = new Text(i.getWorkOrder() + "");
			Text assigned = new Text(i.getAssignedTo());
			Text desc = new Text(i.getDescription());
			Text status = new Text(i.getStatus());
			Text dateIssued = new Text(i.getDateIssued());
			
			// Add contents to center.
			pane.add(order, columnCounter, rowCounter);
			columnCounter++;
			pane.add(assigned, columnCounter, rowCounter);
			columnCounter++;
			pane.add(desc, columnCounter, rowCounter);
			columnCounter++;
			pane.add(status, columnCounter, rowCounter);
			columnCounter++;
			pane.add(dateIssued, columnCounter, rowCounter);
			columnCounter = 0;
			rowCounter++;
			
		}
		
		// Place initial center display.
		main.getChildren().add(pane);
		
		// Bottom box.
		HBox actions = new HBox();
		actions.setPadding(new Insets(10, 10, 20, 10));
		actions.setSpacing(20);
		Button previous = new Button("Previous Screen");
		Text or = new Text("Work Order To Close:");
		or.setFont(new Font("Arial", 20));
		TextField workOrder = new TextField();
		workOrder.setPrefWidth(100);
		Button proceed = new Button("Proceed With Close");
		actions.setAlignment(Pos.BOTTOM_CENTER);
		actions.getChildren().addAll(previous, or, workOrder, proceed);
		
		// Screen definition.
		BorderPane addScreen = new BorderPane();
		addScreen.setTop(title);
		addScreen.setCenter(main);
		addScreen.setBottom(actions);
		addScreen.setStyle("-fx-background-color: lightsalmon");
		addScreen.setPrefSize(1100, 900);
		
		//  Button listeners.
		proceed.setOnAction(e -> {
			
			// Place initial center display and define replacement pane.
			pane2 = new GridPane();
			pane2.setAlignment(Pos.CENTER);
			pane2.setVgap(10);
			pane2.setHgap(20);
			pane2.setPadding(new Insets(1.5, 1.5, 1.5, 1.5));		
			pane2.setPrefSize(1100, 500);
			Text msg = new Text();
			msg.setFont(new Font("Arial", 20));
			msg.setStroke(Color.RED);
			pane2.add(msg, 0, 10);
			
			try{
				
				int order = Integer.parseInt(workOrder.getText().trim());
				
				// Get fields for file rebuild.
				for (ProjectList in: data) {
					
					int workOr = in.getWorkOrder();
					if (order == workOr) {
						String packN = in.getPackageName();
						String module = in.getModuleName();
						String assign = in.getAssignedTo();
						String desc = in.getDescription();
						String status = "Closed";
						int prior = in.getPriority();
						String comments = in.getComments();
						index = data.indexOf(in);
						
						// Set new flag to false and rebuild file.
						newFlag = false;
						modFlag = false;
						rebuildFile(workOr, packN, module, assign, desc, status, prior, comments, newFlag);
					}
				}
				
				// Remove from project list, remove text field and send message to display.
				data.remove(index);
				msg.setText("Order Closed");
				actions.getChildren().removeAll(or, workOrder, proceed);
				
				int mrCounter = 0;
				int mcCounter = 0;
				
				// Modified column/row counters and column titles.
				Text orderHeader = new Text("Work Order");
				orderHeader.setFont(new Font("Arial", 20));
				pane2.add(orderHeader, 0, mrCounter);
				Text assignHeader = new Text("Assigned To");
				assignHeader.setFont(new Font("Arial", 20));
				pane2.add(assignHeader, 1, mrCounter);
				Text descHeader = new Text("Description");
				descHeader.setFont(new Font("Arial", 20));
				pane2.add(descHeader, 2, mrCounter);
				Text statusHeader = new Text("Status");
				statusHeader.setFont(new Font("Arial", 20));
				pane2.add(statusHeader, 3, mrCounter);
				Text issuedHeader= new Text("Date Issued");
				issuedHeader.setFont(new Font("Arial", 20));
				pane2.add(issuedHeader, 4, mrCounter);
				mrCounter++;
				
				for (ProjectList i: data) {
					
					Text orderT = new Text(i.getWorkOrder() + "");
					Text assignT = new Text(i.getAssignedTo());
					Text descT = new Text(i.getDescription());
					Text statusT = new Text(i.getStatus());
					Text dateIssuedT = new Text(i.getDateIssued());
					
					// New modified data.
					pane2.add(orderT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(assignT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(descT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(statusT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(dateIssuedT, mcCounter, mrCounter);
					mcCounter = 0;
					mrCounter++;
					
				}
				
				// Replace center display with new values.
				main.getChildren().remove(pane);
				main.getChildren().add(pane2);
				
			}
			catch (NumberFormatException ex) {
				error.setText("Number Error");
				workOrder.clear();
			}
		});
		
		workOrder.setOnAction(e -> {
			
			// Place initial center display and define replacement pane.
			pane2 = new GridPane();
			pane2.setAlignment(Pos.CENTER);
			pane2.setVgap(10);
			pane2.setHgap(20);
			pane2.setPadding(new Insets(1.5, 1.5, 1.5, 1.5));		
			pane2.setPrefSize(1100, 500);
			Text msg = new Text();
			msg.setFont(new Font("Arial", 20));
			msg.setStroke(Color.RED);
			pane2.add(msg, 0, 10);
			
			try{
				
				int order = Integer.parseInt(workOrder.getText().trim());
				
				// Get fields for file rebuild.
				for (ProjectList in: data) {
					
					int workOr = in.getWorkOrder();
					if (order == workOr) {
						String packN = in.getPackageName();
						String module = in.getModuleName();
						String assign = in.getAssignedTo();
						String desc = in.getDescription();
						String status = "Closed";
						int prior = in.getPriority();
						String comments = in.getComments();
						index = data.indexOf(in);
						
						// Set new flag to false and rebuild file.
						newFlag = false;
						modFlag = false;
						rebuildFile(workOr, packN, module, assign, desc, status, prior, comments, newFlag);
					}
				}
				
				// Remove from project list, remove text field and send message to display.
				data.remove(index);
				msg.setText("Order Closed");
				actions.getChildren().removeAll(or, workOrder, proceed);

				
				int mrCounter = 0;
				int mcCounter = 0;
				
				// Modified column/row counters and column titles.
				Text orderHeader = new Text("Work Order");
				orderHeader.setFont(new Font("Arial", 20));
				pane2.add(orderHeader, 0, mrCounter);
				Text assignHeader = new Text("Assigned To");
				assignHeader.setFont(new Font("Arial", 20));
				pane2.add(assignHeader, 1, mrCounter);
				Text descHeader = new Text("Description");
				descHeader.setFont(new Font("Arial", 20));
				pane2.add(descHeader, 2, mrCounter);
				Text statusHeader = new Text("Status");
				statusHeader.setFont(new Font("Arial", 20));
				pane2.add(statusHeader, 3, mrCounter);
				Text issuedHeader= new Text("Date Issued");
				issuedHeader.setFont(new Font("Arial", 20));
				pane2.add(issuedHeader, 4, mrCounter);
				mrCounter++;
				
				for (ProjectList i: data) {
					
					Text orderT = new Text(i.getWorkOrder() + "");
					Text assignT = new Text(i.getAssignedTo());
					Text descT = new Text(i.getDescription());
					Text statusT = new Text(i.getStatus());
					Text dateIssuedT = new Text(i.getDateIssued());
					
					// New modified data.
					pane2.add(orderT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(assignT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(descT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(statusT, mcCounter, mrCounter);
					mcCounter++;
					pane2.add(dateIssuedT, mcCounter, mrCounter);
					mcCounter = 0;
					mrCounter++;
					
				}
				
				// Replace center display with new values.
				main.getChildren().remove(pane);
				main.getChildren().add(pane2);
				
			}
			catch (NumberFormatException ex) {
				error.setText("Number Error");
				workOrder.clear();
			}
		});
		
		previous.setOnAction(e-> {
			currentScreen.close();
			adminScreen.show();
		});

		Scene add = new Scene(addScreen);
		currentScreen.setScene(add);
		currentScreen.setTitle("Close Work Order");
		currentScreen.show();
	}
	
	/**
	 * This method lists the open orders and retrieves the work order number to reassign.
	 */
	
	public void reassignTo() {
		
		// Initial project list setup.
		HBox title = new HBox();
		Text page = new Text("Current Open Orders");
		page.setFont(new Font("Arial", 20));
		page.setStroke(Color.RED);
		title.setPadding(new Insets(60, 20, 20, 20));
		title.getChildren().add(page);
		title.setAlignment(Pos.CENTER);
		title.setPrefSize(1100, 40);
		
		// Initial center information.
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(20);
		pane.setPadding(new Insets(0, 1.5, 1.5, 1.5));		
		pane.setPrefSize(1100, 600);
		
		// Initial column/row counters and column titles.
		Text message = new Text();
		message.setFont(new Font("Arial", 20));
		message.setStroke(Color.RED);
		pane.add(message, 0, 10);
		Text orderText = new Text("Work Order");
		orderText.setFont(new Font("Arial", 20));
		pane.add(orderText, 0, 0);
		Text assignText = new Text("Assigned To");
		assignText.setFont(new Font("Arial", 20));
		pane.add(assignText, 1, 0);
		Text descText = new Text("Description");
		descText.setFont(new Font("Arial", 20));
		pane.add(descText, 2, 0);
		Text statusText = new Text("Status");
		statusText.setFont(new Font("Arial", 20));
		pane.add(statusText, 3, 0);
		Text issuedText = new Text("Date Issued");
		issuedText.setFont(new Font("Arial", 20));
		pane.add(issuedText, 4, 0);
		
		// Initial center display.
		int columnCounter = 0;
		int rowCounter = 1;
		
		for (ProjectList i: data) {
			
			Text order = new Text(i.getWorkOrder() + "");
			Text assign = new Text(i.getAssignedTo());
			Text desc = new Text(i.getDescription());
			Text status = new Text(i.getStatus());
			Text dateIssued = new Text(i.getDateIssued());
			
			// Add center contents.
			pane.add(order, columnCounter, rowCounter);
			columnCounter++;
			pane.add(assign, columnCounter, rowCounter);
			columnCounter++;
			pane.add(desc, columnCounter, rowCounter);
			columnCounter++;
			pane.add(status, columnCounter, rowCounter);
			columnCounter++;
			pane.add(dateIssued, columnCounter, rowCounter);
			columnCounter = 0;
			rowCounter++;
		}
		
		// Bottom box.
		HBox actions = new HBox();
		actions.setPadding(new Insets(20, 20, 40, 20));
		actions.setSpacing(20);
		actions.setAlignment(Pos.BOTTOM_CENTER);
		Button previous = new Button("Previous");
		Text workOrder = new Text("Work Order To Reassign");
		workOrder.setFont(new Font("Arial", 20));
		TextField wn = new TextField();
		wn.setPrefWidth(100);
		Button proceed = new Button("Proceed To Reassignment Screen");
		actions.getChildren().addAll(previous, workOrder, wn, proceed);
		
		// Button listener.
		proceed.setOnAction(e -> {
			try {
				int order = Integer.parseInt(wn.getText().trim());
				wn.clear();
				reassignDetail(order);
				reassignStage.close();
				refreshTable();
			}
			catch (NumberFormatException ex) {
				message.setText("Number Required");
				wn.clear();
			}
		});
		
		previous.setOnAction(e -> {
			reassignStage.close();
			refreshTable();
		});
		
		// Enter key listener.
		wn.setOnAction(e -> {
			
			try {
				int order = Integer.parseInt(wn.getText().trim());
				wn.clear();
				reassignDetail(order);
				reassignStage.close();
				refreshTable();
			}
			catch (NumberFormatException ex) {
				message.setText("Number Required");
				wn.clear();
			}
		});
				
		BorderPane addScreen = new BorderPane();
		addScreen.setTop(title);
		addScreen.setCenter(pane);
		addScreen.setBottom(actions);
		addScreen.setStyle("-fx-background-color: lightsalmon");
		addScreen.setPrefSize(1100, 900);
						
		Scene add = new Scene(addScreen);
		reassignStage.setScene(add);
		reassignStage.setTitle("Reassignment Screen");
		reassignStage.show();		
	}
	
	/**
	 * This method displays the details of the work order and allows for it to be reassigned from one team member to another.
	 * 
	 * @param orderNumber (int:  Represents the work order being reassigned.)
	 */
	
	public void reassignDetail(int orderNumber) {
		
		// Display variables.
		String pack = "";
		String mod = "";
		String assigned = "";
		int prior = 0;
		String desc = "";
		
		// Read project File.
		try {
			FileInputStream in = new FileInputStream("Project.dat");
			ObjectInputStream proj = new ObjectInputStream(in);
			
			// Not EOF.
			while (true) {
				Project p = (Project) proj.readObject();
				int workO = p.getWorkOrder();
				
				if (orderNumber == workO) {
					pack = p.getPackageName();
					mod = p.getModuleName();
					assigned = p.getAssignedTo();
					prior = p.getPriority();
					desc = p.getDescription();
				}
			}
		}
		catch (Exception ex) {
		}
		
		// Table header.
		HBox orderHeader = new HBox();
		Text order = new Text("Detail for order #  " + orderNumber);
		order.setFont(new Font("Arial", 30));
		orderHeader.setPadding(new Insets(40, 10, 10, 10));
		orderHeader.setAlignment(Pos.CENTER);
		orderHeader.getChildren().add(order);
		orderHeader.setPrefSize(1000, 50);
		
		// Center display contents.
		GridPane pane = new GridPane();
		Text priorText = new Text("Priority:  " + prior);
		priorText.setFont(new Font("Arial", 20));
		pane.add(priorText, 0, 0);
		Text packg = new Text("Package:  " + pack);
		packg.setFont(new Font("Arial", 20));
		pane.add(packg, 0, 1);
		Text module = new Text("Module:  " + mod);
		module.setFont(new Font("Arial", 20));
		pane.add(module, 0, 2);
		Text des = new Text("Descrption:  " + desc);
		des.setFont(new Font("Arial", 20));
		pane.add(des, 0, 3);
		Text assign = new Text("Currently Assigned To:  ");
		assign.setFont(new Font("Arial", 20));
		pane.add(assign, 0, 5);
		Text currentAssign = new Text(assigned);
		currentAssign.setFont(new Font("Arial", 20));
		pane.add(currentAssign, 0, 6);
		Text reassign = new Text("Reassign To:  ");
		reassign.setFont(new Font("Arial", 20));
		pane.add(reassign, 2, 5);
		TextField newAssigned = new TextField();
		newAssigned.setPrefWidth(100);
		pane.add(newAssigned, 2, 6);
		Text message = new Text("");
		message.setFont(new Font("Arial", 20));
		message.setStroke(Color.RED);
		pane.add(message, 0, 10);
		
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(30);
		pane.setPadding(new Insets(1.5, 1.5, 1.5, 1.5));
		pane.setPrefSize(1000, 600);
		
		// Comments area.
		VBox comA = new VBox();
		comA.setPadding(new Insets(10, 40, 10, 40));
		Text comments = new Text("Comments:  ");
		comments.setFont(new Font("Arial", 20));
		TextArea comF = new TextArea(com);
		comF.setPrefSize(200, 200);
		comF.setEditable(false);
		comA.setAlignment(Pos.CENTER);
		comA.getChildren().addAll(comments, comF);
		comA.setPrefSize(250, 250);
		
		// Bottom button box.
		HBox actions = new HBox();
		actions.setSpacing(20);
		actions.setPadding(new Insets(10, 20, 40, 20));
		actions.setAlignment(Pos.BOTTOM_CENTER);
		Button previous = new Button("Previous Screen");
		Button cancel = new Button("Cancel");
		Button main = new Button("Main Administrative Screen");
		actions.getChildren().addAll(previous, cancel, main);
		actions.setPrefSize(100, 100);
		
		// TextField listener.
		newAssigned.setOnAction(e -> {
			
			String assignTo = newAssigned.getText().trim();
			
			for (ProjectList i: data) {
				int workOrder = i.getWorkOrder();
				if (orderNumber == workOrder) {
					String packN = i.getPackageName();
					String moduleN = i.getModuleName();
					String descF = i.getDescription();
					String status = i.getStatus();
					int priority = i.getPriority();
					String commentsF = i.getComments();
					index = data.indexOf(i);
					
					// Set new flag to false and rebuild file.
					newFlag = false;
					modFlag = true;
					rebuildFile(orderNumber, packN, moduleN, assignTo, descF, status, priority, commentsF, newFlag);
				}
			}
			
			// Refresh administrative table and set display to new information.
			refreshTable();
			currentAssign.setText(assignTo);
			
			// Reset flags and clear text field.
			modFlag = false;
			newAssigned.clear();
			message.setText("Reassigned");
		});
		
		cancel.setOnAction(e -> {
			
			// Clear text field and return to reassign screen.
			newAssigned.clear();
			currentScreen.close();
			try {
				reassignTo();
			}
			catch (Exception ex) {
			}
		});
		
		// Back to main administration screen.
		main.setOnAction(e -> {
			currentScreen.close();
			try {
				adminScreen.show();
			}
			catch (Exception ex) {
			}
		});
		
		previous.setOnAction(e -> {
			currentScreen.close();
			refreshTable();
			try {
				reassignTo();
			}
			catch (Exception ex) {
			}
		});
		
		BorderPane detailScreen = new BorderPane();
		detailScreen.setStyle("-fx-background-color: lightsalmon");
		detailScreen.setTop(orderHeader);
		detailScreen.setCenter(pane);
		detailScreen.setRight(comA);
		detailScreen.setBottom(actions);
		detailScreen.setPrefSize(1000, 700);
		
		Scene detail = new Scene(detailScreen);
		currentScreen.setTitle("Reassignment Screen");
		currentScreen.setScene(detail);
		currentScreen.show();
	}
	
	/**
	 * This method gets the number for the work order to detail.
	 */
	
	public void getWorkNumber() {
		
		// Box for order number entry.
		VBox orderBox = new VBox();
		orderBox.setPadding(new Insets(10, 10, 10, 10));
		orderBox.setSpacing(10);
		Text msg = new Text("Detail For Work Order # ");
		msg.setFont(new Font("Aarial", 20));
		TextField workOrder = new TextField();
		workOrder.setPrefWidth(100);
		Text error = new Text();
		error.setFont(new Font("Arial", 20));
		error.setStroke(Color.RED);
		orderBox.getChildren().addAll(msg,workOrder, error);
		orderBox.setPrefSize(300, 200);
		
		// Text field listener.
		workOrder.setOnAction(e -> {
			
			try {
				
				int order = Integer.parseInt(workOrder.getText().trim());
				
				for (ProjectList proj: data) {
					int wo = proj.getWorkOrder();
					
					// Work order is in open projects.
					if (order == wo) {
						detailWorkOrder(order);
						currentScreen.close();
					}
				}
				
				// Work order isn't in open projects.
				error.setText("Work Order Not Found");
				workOrder.clear();
			}
			catch (NumberFormatException ex) {
				error.setText("Number Required");
				workOrder.clear();
			}
		});
		
		Scene orderScene = new Scene(orderBox);
		currentScreen.setTitle("Work Order");
		currentScreen.setScene(orderScene);
		currentScreen.show();
	}
	
	/**
	 * This method displays the details for the work order entered.
	 * 
	 * @param orderNumber (int:  Represents the work order number to be detailed.)
	 */
	
	public void detailWorkOrder(int orderNumber) {
		
		// Display variables.
		String pack = "";
		String mod = "";
		String assigned = "";
		int prior = 0;
		String desc = "";
		
		// Read project File.
		try {
			FileInputStream in = new FileInputStream("Project.dat");
			ObjectInputStream proj = new ObjectInputStream(in);
			
			// Not EOF.
			while (true) {
				Project p = (Project) proj.readObject();
				int workO = p.getWorkOrder();
				
				// Get this work order information.
				if (orderNumber == workO) {
					pack = p.getPackageName();
					mod = p.getModuleName();
					assigned = p.getAssignedTo();
					prior = p.getPriority();
					com = p.getComments();
					desc = p.getDescription();
				}
			}
		}
		catch (Exception ex) {
		}
		
		// Page title.
		HBox orderHeader = new HBox();
		Text order = new Text("Detail for order # " + orderNumber);
		order.setFont(new Font("Arial", 30));
		orderHeader.setPadding(new Insets(20, 10, 40, 10));
		orderHeader.setAlignment(Pos.CENTER);
		orderHeader.getChildren().add(order);
		orderHeader.setPrefSize(1000, 50);
		
		// Center display.
		GridPane pane = new GridPane();
		Text priorText = new Text("Priority:  ");
		priorText.setFont(new Font("Arial", 20));
		pane.add(priorText, 0, 0);
		Text priT = new Text(prior + "");
		priT.setFont(new Font("Arial", 20));
		pane.add(priT, 1, 0);
		Text assign = new Text("Assigned To:  ");
		assign.setFont(new Font("Arial", 20));
		pane.add(assign, 0, 1);
		Text aText = new Text(assigned);
		aText.setFont(new Font("Arial", 20));
		pane.add(aText, 1, 1);
		Text packg = new Text("Package:  ");
		packg.setFont(new Font("Arial", 20));
		pane.add(packg, 0, 2);
		Text packT = new Text(pack);
		packT.setFont(new Font("Arial", 20));
		pane.add(packT, 1, 2);
		Text module = new Text("Module:  ");
		module.setFont(new Font("Arial", 20));
		pane.add(module, 0, 3);
		Text modT = new Text(mod);
		modT.setFont(new Font("Arial", 20));
		pane.add(modT, 1, 3);
		Text des = new Text("Descrption:  ");
		des.setFont(new Font("Arial", 20));
		pane.add(des, 0, 4);
		Text descT = new Text(desc);
		descT.setFont(new Font("Arial", 20));
		pane.add(descT, 1, 4);
		Text message = new Text();
		message.setFont(new Font("Arial", 20));
		pane.add(message, 0, 10);
		Text dateC = new Text();
		dateC.setFont(new Font("Arial", 20));
		pane.add(dateC, 0, 11);
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(30);
		pane.setPrefSize(1000, 600);
		
		// Comment area.
		VBox comA = new VBox();
		comA.setPadding(new Insets(10, 40, 10, 40));
		Text comments = new Text("Comments:  ");
		comments.setFont(new Font("Arial", 20));
		TextArea comF = new TextArea(com);
		comF.setPrefSize(200, 200);
		comF.setEditable(false);
		comA.setAlignment(Pos.CENTER);
		comA.getChildren().addAll(comments, comF);
		comA.setPrefSize(250, 250);
		
		// Bottom box.
		HBox actions = new HBox();
		actions.setSpacing(10);
		actions.setPadding(new Insets(10, 20, 40, 20));
		actions.setAlignment(Pos.CENTER);
		Button edit = new Button("Add Comment");
		Button close = new Button("Close Work Order");
		Button previous = new Button("Previous");
		actions.getChildren().addAll(edit, close, previous);
		actions.setPrefSize(100, 100);
		
		// Button listeners.
		edit.setOnAction(e -> {
			addAdminComment(orderNumber, com);
			
		});
		close.setOnAction(e -> {
			
			// Get fields for file rebuild.
			for (ProjectList in: data) {
				
				int workOr = in.getWorkOrder();
				if (orderNumber == workOr) {
					String packN = in.getPackageName();
					String moduleN = in.getModuleName();
					String assignN = in.getAssignedTo();
					String descN = in.getDescription();
					String status = "Closed";
					int priorN = in.getPriority();
					String commentsN = in.getComments();;
					index = data.indexOf(in);
					
					// Set new flag to false and rebuild file.
					newFlag = false;
					modFlag = false;
					rebuildFile(orderNumber, packN, moduleN, assignN, descN, status, priorN, commentsN, newFlag);
				}
			}
			
			// Send message to display and refresh table.
			message.setText("Order Closed On");
			message.setStroke(Color.RED);
			dateC.setText(new java.util.Date() + "");
			dateC.setStroke(Color.RED);
			refreshTable();
		});
		previous.setOnAction(e -> {
			adminDetail.close();
			refreshTable();
			adminScreen.show();
		});
		
		BorderPane detailScreen = new BorderPane();
		detailScreen.setStyle("-fx-background-color: lightsalmon");
		detailScreen.setTop(orderHeader);
		detailScreen.setCenter(pane);
		detailScreen.setRight(comA);
		detailScreen.setBottom(actions);
		detailScreen.setPrefSize(1000, 700);
		
		Scene detail = new Scene(detailScreen);
		adminDetail.setTitle("Work Order Detail");
		adminDetail.setScene(detail);
		adminDetail.show();		
	}
	
	/**
	 * This methods allows for administrative comments.
	 * 
	 * @param orderNumber (int:  Represents the work order the comment is for.)
	 * @param com (String:  Represents the comments for this work order.)
	 */
	
	public void addAdminComment(int orderNumber, String com) {
		
		// Additional comment box.
		VBox ac = new VBox();
		ac.setPadding(new Insets(10, 10, 10, 10));
		ac.setSpacing(10);
		Text oCom = new Text(com);
		oCom.setFont(new Font("Arial", 20));
		Text msg = new Text("Comment To Add");
		msg.setFont(new Font("Aarial", 20));
		TextField add = new TextField();
		add.setPrefWidth(200);
		ac.getChildren().addAll(oCom, msg, add);
		ac.setPrefSize(300, 200);
		
		// Text field listener.
		add.setOnAction(e -> {
			String additional = add.getText().trim();
			String packN = "";
			String module = "";
			String assign = "";
			String desc = "";
			String status = "";
			int prior = 0;
			String comments = "";
			
			// Get this work order and add comment.
			for (ProjectList p: data) {
				int order = p.getWorkOrder();
				if (order == orderNumber) {
					String origcom = p.getComments();
					String newCom = origcom + "\n" + additional;
					p.setComments(newCom);
					packN = p.getPackageName();
					module = p.getModuleName();
					assign = p.getAssignedTo();
					desc = p.getDescription();
					status = p.getStatus();
					prior = p.getPriority();
					comments = newCom;
				}
			}
			
			// Set flags and rebuild file.
			newFlag = false;
			modFlag = true;
			rebuildFile(orderNumber, packN, module, assign, desc, status, prior, comments, newFlag);
			
			// Work order detail screen.
			try {
				detailWorkOrder(orderNumber);
			}
			catch (Exception ex) {
			}
			currentScreen.close();
		});
		
		Pane newComment = new Pane();
		newComment.getChildren().add(ac);
		
		Scene n = new Scene(newComment);
		currentScreen.setTitle("Administrative Comment Screen");
		currentScreen.setScene(n);
		currentScreen.show();
	}
	
	/**
	 * This method refreshes the administrative table after changes are made.
	 * 
	 * <p>Differs from team member refresh.<\p>
	 */
	
	public void refreshTable() {
		
		// Clear table contents.
		data.clear();
		
		// Read projects file.
		try (ObjectInputStream file = new ObjectInputStream(new FileInputStream("Project.dat"));
		) {
			
			// While not End of File.
			while (true) {
				Project project = (Project) file.readObject();
					
					// Add project information to new table.
				int wo = project.getWorkOrder();
				
				// Filter out closed projects.
				String stat = project.getStatus();
				if (stat.contentEquals("Open") == true) {
				
					String pack = project.getPackageName();
					String module = project.getModuleName();
					String assign = project.getAssignedTo();
					String desc = project.getDescription();
					String status = project.getStatus();
					String comments = project.getComments();
					String dateIss = project.getDateIssued() + "";
					String dateCl = project.getDateClosed() + "";
					int priority = project.getPriority();
					data.add(new ProjectList(wo, pack, module, assign, desc, comments, status, dateIss, dateCl, priority));
				}
			}
		}
		catch (Exception ex) {
			
		}
		
		// Set new table contents.
		table.setItems(data);
		table.sort();	
	}
	
	/**
	 * This method is responsible for rebuilding the Project.dat file.  Eliminates stream corruption errors.
	 * 
	 * @param order (int:  Represents the work order being added, closed, or modified.)
	 * @param packN (String:  Represents the package name associated with the work order.)
	 * @param m (String:  Represents the module name associated with the work number.)
	 * @param a (String:  Represents the team member assigned to the work order.)
	 * @param d (String:  Represents the description for the associated work order.)
	 * @param s (String:  Represents the status of the associated work order.)
	 * @param p (int:  Represents the priority of the associated work order.)
	 * @param c (String:  Represents the comments associated with the work order.)
	 * @param newFlag (boolean:  Represents or not this is a new record being added.)
	 */
	
	public void rebuildFile(int order, String packN, String m, String a, String d, String s, int p, String c, boolean newFlag) {
		
		// Array for projects.
		ArrayList<Project> newProject = new ArrayList<Project>();
		
		// Read projects file and add to array.
		try(ObjectInputStream readIn = new ObjectInputStream(new FileInputStream("Project.dat"));
			) {
				
				// Not EOF.
				while (true) {
					Project proj = (Project) readIn.readObject();
					int o = proj.getWorkOrder();
					
					// If record is being closed.
					if ((order == o) && (newFlag == false) && (modFlag == false)) {
						String pa = proj.getPackageName();
						String mo = proj.getModuleName();
						String as = proj.getAssignedTo();
						String dd = proj.getDescription();
						String ss = "Closed";
						int pp = proj.getPriority();
						String cc = proj.getComments();
						Date i = proj.getDateIssued();
						Date clz = new java.util.Date();
						newProject.add(new Project(o, pa, mo, as, dd, ss, pp, cc, i, clz));
					}
					
					// If record is being modified.
					else if((order == o) && (modFlag == true)) {
						
						String pa = proj.getPackageName();
						String mo = proj.getModuleName();
						String as = a;
						String dd = proj.getDescription();
						String ss = proj.getStatus();
						int pp = proj.getPriority();
						String cc = c;
						Date i = proj.getDateIssued();
						Date clz = proj.getDateClosed();
						newProject.add(new Project(o, pa, mo, as, dd, ss, pp, cc, i, clz));
					}
					
					// If record hasn't changed.
					else {
						
						String pa = proj.getPackageName();
						String mo = proj.getModuleName();
						String as = proj.getAssignedTo();
						String dd = proj.getDescription();
						String ss = proj.getStatus();
						int pp = proj.getPriority();
						String cc = proj.getComments();
						Date i = proj.getDateIssued();
						Date clz = proj.getDateClosed();
						newProject.add(new Project(o, pa, mo, as, dd, ss, pp, cc, i, clz));
					}
				}
		}
		catch (Exception ex) {
		}
		
		// If record is completely new.
		if (newFlag == true) {
			newProject.add(new Project(order, packN, m, a, d, s, p, c));
		}
		
		// Write array out to file.
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Project.dat"));
			) {	
				
				for (Project e: newProject) {
					out.writeObject(e);
				}
		}
		catch (Exception ex) {
		}
	}
	
	/**
	 * This class handles the entries in the table view display.
	 *
	 * @author Chad Lister
	 * @created 11/10/2020
	 */
	
	public static class ProjectList {
		
		//  Attributes.
		private final SimpleIntegerProperty workOrder;
		private final SimpleStringProperty packageName;
		private final SimpleStringProperty moduleName;
		private final SimpleStringProperty assignedTo;
		private final SimpleStringProperty description;
		private final SimpleStringProperty comments;
		private final SimpleStringProperty dateIssued;
		private final SimpleStringProperty dateClosed;
		private final SimpleStringProperty status;
		private final SimpleIntegerProperty priority;
		
		// Constructor.
		private ProjectList(int workOrder, String packageName, String moduleName, String assignedTo, String description, String comments, String status, String dateIssued, String dateClosed, int priority) {
			this.workOrder = new SimpleIntegerProperty(workOrder);
			this.packageName = (new SimpleStringProperty(packageName));
			this.moduleName = new SimpleStringProperty(moduleName);
			this.assignedTo = (new SimpleStringProperty(assignedTo));
			this.description = new SimpleStringProperty(description);
			this.comments = new SimpleStringProperty(comments);
			this.dateIssued = new SimpleStringProperty(dateIssued);
			this.dateClosed = new SimpleStringProperty(dateClosed);
			this.status = new SimpleStringProperty(status);
			this.priority = new SimpleIntegerProperty(priority);
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
			return workOrder.get();
		}
		
		/**
		 * This method sets the number of this work order.
		 * 
		 * @param workOrder the workOrder to set
		 */
		
		public void setWorkOrder(int workOrder) {
			this.workOrder.set(workOrder);
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
			return packageName.get();
		}
		
		/**
		 * This method sets the package name associated with this work order.
		 * 
		 * @param packageName the packageName to set
		 */
		
		public void setPackageName(String packageName) {
			this.packageName.set(packageName);
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
			return moduleName.get();
		}
		
		/**
		 * This method sets the module name associated with this work order.
		 * 
		 * @param moduleName the moduleName to set
		 */
		
		public void setModuleName(String moduleName) {
			this.moduleName.set(moduleName);
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
			return assignedTo.get();
		}
		
		/**
		 * This method sets which team member this work order is assigned to.
		 * 
		 * @param assignedTo the assignedTo to set
		 */
		
		public void setAssignedTo(String assignedTo) {
			this.assignedTo.set(assignedTo);
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
			return description.get();
		}
		
		/**
		 * This method sets the description associated with this work order.
		 * 
		 * @param description
		 */
		
		public void setDescrription(String description) {
			this.description.set(description);
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
			return comments.get();
		}
		
		/**
		 * This method sets the comments for this work order.
		 * 
		 * @param comments the comments to set
		 */
		
		public void setComments(String comments) {
			this.comments.set(comments);
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
		
		public String getDateIssued() {
			return dateIssued.get();
		}
		
		/**
		 * This method sets the date this work order was issued.
		 * 
		 * @param dateIssued the dateIssued to set
		 */
		
		public void setDateIssued(String dateIssued) {
			this.dateIssued.set(dateIssued);
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
		
		public String getDateClosed() {
			return dateClosed.get();
		}
		
		/**
		 * This method sets the date this work order was closed.
		 * 
		 * @param dateClosed the dateClosed to set
		 */
		
		public void setDateClosed(String dateClosed) {
			this.dateClosed.set(dateClosed);
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
			return status.get();
		}
		
		/**
		 * This method sets the status of this work order.
		 * 
		 * @param status the status to set
		 */
		
		public void setStatus(String status) {
			this.status.set(status);
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
			return priority.get();
		}
		
		/**
		 * This method sets the priority for this work order.
		 * 
		 * @param priority the priority to set
		 */
		
		public void setPriority(int priority) {
			this.priority.set(priority);
		}
		
	}
	
	/**
	* The main method is only needed for the IDE with limited
	* JavaFX support. Not needed for running from the command line.
	*/
	
	public static void main(String[] args) {
		launch(args);
	}
}
