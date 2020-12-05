package itec_2905_capstone;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
 * @author Chad Lister
 *         Date:  11/16/2020
 * @created 11/16/2020
 * 
 * <p>Rebuilt Screens, error checks and added field.</p>
 * <p>Chad Lister 12/05/2020</p>
 */

public class ProjectManagement extends Application {
	
	// Attributes.
	private TableView<ProjectList> table = new TableView<ProjectList>();
	private ObservableList <ProjectList> data = FXCollections.observableArrayList();
	private TextField employeeId = new TextField();
	private TextField password = new TextField();
	private Text loginError = new Text();
	private int index = 0;
	private int lastWorkOrder = 0;
	private String firstName;
	private String empName = "";
	private String com = "";
	private boolean init = true;
	private boolean validate = false;
	private boolean newFlag = false;
	private boolean modFlag = false;
	private boolean addOrder = false;
	private boolean closeOrder = false;
	private boolean changeOrder = false;
	private Stage currentStage = new Stage();
	private Stage memberStage = new Stage();
	private Stage adminStage = new Stage();
	private Stage detailStage = new Stage();
	private Stage reassignStage = new Stage();
	private Stage adminDetail = new Stage();
	private Stage loginStage = new Stage();
		
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
		employeeId.setPrefWidth(100);;
		pane.add(employeeId, 2, 0);
		Text passwordText = new Text("Password:  ");
		passwordText.setFont(new Font("Arial", 20));
		pane.add(passwordText, 0, 4);
		pane.add(password, 2, 4);
		password.setPrefWidth(100);
		Button loginButton = new Button("Employee Login");
		pane.add(loginButton, 2, 8);
		loginError.setFont(new Font("Arial", 20));
		loginError.setStroke(Color.RED);
		pane.add(loginError, 0, 12);
		Button exitSystem = new Button("Exit System");
		pane.add(exitSystem, 0, 15);
		
		// EmployeeId enter listener.  Reverse field entry or blank field error check.
		employeeId.setOnAction(e -> {
			
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
				loginError.setText("*** Invalid Entry ***");
				employeeId.requestFocus();
			}
		});
		
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
				loginError.setText("*** Invalid Entry ***");
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
				loginError.setText("*** Invalid Entry ***");
				employeeId.requestFocus();
			}
		});
		
		// Listener for exit system button.
		exitSystem.setOnAction(e -> {
			
			// Exit stage.
			loginStage.close();
		});

		// Setup login box.
		BorderPane loginScreen = new BorderPane();
		loginScreen.setCenter(pane);
		loginScreen.setTop(businessHeader);
		loginScreen.setPrefSize(600, 600);

		// Create a scene and place it in the stage.
		Scene scene = new Scene(loginScreen);
		loginStage.setTitle("Main Login");
		loginStage.setScene(scene);
		loginStage.show();
	}
	
	/**
	 * This method takes the login information and verifies if that information is correct by checking it against the Employee.dat file.
	 * 
	 * @param employeeIdNumber (int:  Represents the employee Id number entered in the "Employee Id" text field.)
	 * @param employeePassword (String:  Represents the password entered into the "Password" text field.)
	 * 
	 */
	
	public void verify(int employeeIdNumber, String employeePassword) throws Exception {
		
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
				if (employeeNumber == employeeIdNumber && pass.contentEquals(employeePassword) == true) {
					
					// Team member level.
					if (level == 1) {
						
						firstName = employee.getFirstName();
						String lastName = employee.getLastName();
						empName = firstName + " " + lastName;
						
						boolean enableTeam = true;
						boolean enableAdmin = false;
						boolean enableClient = false;
						boolean enableEmployee = false;
						
						mainPage(enableTeam, enableAdmin, enableClient, enableEmployee);
						
						// Close file. Needed for concurrent applications?
						fileIn.close();
						loginStage.close();
					}
					
					// Team Leader/Administration level.
					if (level == 3) {
						
						firstName = employee.getFirstName();
						String lastName = employee.getLastName();
						empName = firstName + " " + lastName;
						
						boolean enableTeam = true;
						boolean enableAdmin = true;
						boolean enableClient = true;
						boolean enableEmployee = true;
						
						mainPage(enableTeam, enableAdmin, enableClient, enableEmployee);
						
						//  Close file.  Needed for concurrent applications?
						fileIn.close();
						loginStage.close();
					}
				}
			}
		}
		catch (Exception ex) {
			loginError.setText("Employee Not Found");
			employeeId.clear();
			password.clear();
		}
	}
	
	/**
	 * This method displays the main screen after employee verification.
	 * 
	 * @param enableTeam (boolean:  Represents whether the employee can access the Team Member screen.  Valid employee default.)
	 * @param enableAdmin (boolean:  Represents whether or not the employee can access the Team Leader screen.  Administrators only.)
	 * @param enableClient (boolean:  Represents whether or not the employee can access the Client (Customer) screen.  Administrators only.)
	 * @param enableEmployee (boolean:  Represents whether or not the employee can access the Employee Detail screen.  Administrators only.)
	 */
	
	public void mainPage(boolean enableTeam, boolean enableAdmin, boolean enableClient, boolean enableEmployee) {
		
		Text message = new Text();
		message.setFont(new Font("Arial", 20));
		message.setStroke(Color.RED);
		
		// Main screen setup.
		GridPane main = new GridPane();
		main.setHgap(50);
		main.setVgap(25);
		main.setPadding(new Insets(40, 25, 40, 25));
		
		// Team member box and listener.
		HBox team = new HBox();
		team.setPadding(new Insets(25, 25, 25, 25));
		team.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white");
		team.setAlignment(Pos.CENTER);
		Text teamScreen = new Text("Team Member Page");
		teamScreen.setFont(new Font("Arial", 20));
		team.getChildren().add(teamScreen);
		main.add(team, 0, 0);
		
		team.setOnMouseClicked(e -> {
			
			if (enableTeam == true) {
				
				//  Default employee screen.
				try {
					teamMemberPage();
					currentStage.close();
				}
				catch (Exception ex) {
				}
			}
		});
		
		// Team leader box and listener.
		HBox admin = new HBox();
		admin.setPadding(new Insets(25, 25, 25, 25));
		admin.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white");
		admin.setAlignment(Pos.CENTER);
		Text adminScreen = new Text("Team Leader Page");
		adminScreen.setFont(new Font("Arial", 20));
		admin.getChildren().add(adminScreen);
		main.add(admin, 1, 0);
		
		admin.setOnMouseClicked(e -> {
			
			if (enableAdmin == true) {
				try {
					teamLeaderPage();
					currentStage.close();
				}
				catch (Exception ex) {
				}
			}
			else {
				message.setText("");
				message.setText("Not Authorized");
			}
		});
		
		// Customer detail box and listener.
		HBox customer = new HBox();
		customer.setPadding(new Insets(25, 25, 25, 25));
		customer.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white");
		customer.setAlignment(Pos.CENTER);
		Text customerScreen = new Text("Client Database");
		customerScreen.setFont(new Font("Arial", 20));
		customer.getChildren().add(customerScreen);
		main.add(customer, 0, 1);
		
		customer.setOnMouseClicked(e -> {
			
			if (enableClient == true) {
				message.setText("");
				message.setText("Not Yet Implemented");
			}
			else {
				message.setText("");
				message.setText("Not Authorized");
			}
		});
		
		// Employee detail box and listener.
		HBox employee = new HBox();
		employee.setPadding(new Insets(25, 25, 25, 25));
		employee.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white");
		employee.setAlignment(Pos.CENTER);
		Text employeeScreen = new Text("Employee Detail");
		employeeScreen.setFont(new Font("Arial", 20));
		employee.getChildren().add(employeeScreen);
		main.add(employee, 1, 1);
		
		employee.setOnMouseClicked(e -> {
			
			if (enableEmployee == true) {
				message.setText("");
				message.setText("Not Yet Implemented");
			}
			else {
				message.setText("");
				message.setText("Not Authorized");
			}
		});
		
		main.add(message, 0, 2);
		
		main.setStyle("-fx-background-color: burlywood");
		main.setPrefSize(300, 300);
		
		Scene mainScene = new Scene(main);
		
		currentStage.setTitle("Main");
		currentStage.setScene(mainScene);
		currentStage.show();
	}
	
	/**
	 * This method builds the team member's table used for the center display.
	 * 
	 * @param firstName (String:  Represents the team member's first name, used in filtering their assigned projects.)
	 * 
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
						String fieldV = project.getField();

						data.add(new ProjectList(wo, pack, module, assigned, desc, comments, status, dateIss, dateCl, priority, fieldV));
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
		packageCol.setMinWidth(60);
		packageCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("packageName"));
		workOrderCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Integer>("workOrder"));
		TableColumn moduleNameCol = new TableColumn("Module Name");
		moduleNameCol.setMinWidth(60);
		moduleNameCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("moduleName"));
		TableColumn descCol = new TableColumn("Description");
		descCol.setMinWidth(150);
		descCol.setMaxWidth(160);
		descCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("description"));
		TableColumn statusCol = new TableColumn("Status");
		statusCol.setMinWidth(50);
		statusCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("status"));
		TableColumn fieldCol = new TableColumn("Message");
		fieldCol.setMinWidth(50);
		fieldCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("field"));
		
		table.getColumns().addAll(priorityCol, workOrderCol, packageCol, moduleNameCol, descCol, statusCol, issueDateCol, fieldCol);
		table.setItems(data);
		table.getSortOrder().add(priorityCol);
		table.setMaxHeight(400);
		
	}
	
	/**
	 * This method builds the table for the center display.  Filters out closed projects.
	 * 
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
					String fieldV = project.getField();
					data.add(new ProjectList(wo, pack, module, assign, desc, comments, status, dateIss, dateCl, priority, fieldV));
				}
			}
		}
		catch (Exception ex) {
		}
		
		lastWorkOrder++;
		
		// Define table.
		TableColumn priorityCol = new TableColumn("Priority");
		priorityCol.setMinWidth(20);
		priorityCol.setSortType(TableColumn.SortType.ASCENDING);
		priorityCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Integer>("priority"));
		TableColumn workOrderCol = new TableColumn("Work Order");
		workOrderCol.setMinWidth(30);
		workOrderCol.setCellValueFactory(new PropertyValueFactory<ProjectList, Integer>("workOrder"));
		TableColumn packageCol = new TableColumn("Package");
		packageCol.setMinWidth(60);
		packageCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("packageName"));
		TableColumn moduleNameCol = new TableColumn("Module Name");
		moduleNameCol.setMinWidth(60);
		moduleNameCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("moduleName"));
		TableColumn assignedToCol = new TableColumn("Assigned To");
		assignedToCol.setMinWidth(50);
		assignedToCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("assignedTo"));
		TableColumn descCol = new TableColumn("Description");
		descCol.setMinWidth(150);
		descCol.setMaxWidth(160);
		descCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("description"));
		TableColumn issuedDateCol = new TableColumn("Issued Date");
		issuedDateCol.setMinWidth(30);
		issuedDateCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("dateIssued"));
		TableColumn statusCol = new TableColumn("Status");
		statusCol.setMinWidth(50);
		statusCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("status"));
		TableColumn fieldCol = new TableColumn("Message");
		fieldCol.setMinWidth(60);
		fieldCol.setCellValueFactory(new PropertyValueFactory<ProjectList, String>("field"));
		
		table.getColumns().addAll(priorityCol, workOrderCol, packageCol, moduleNameCol, assignedToCol, descCol, issuedDateCol, statusCol, fieldCol);
		table.setItems(data);
		table.getSortOrder().add(priorityCol);
		table.setMaxHeight(400);
		
	}
	
	/**
	 * This method drives the main team member display.
	 * 
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
				
		// If initial screen.
		if (init == true) {
			
			// Setup table and build.
			try {
				buildMemberTable(firstName);
				table.setEditable(false);
			}
			catch (Exception ex){
			}
		}
		
		// Table contents container.
		VBox vbox = new VBox();
		vbox.setSpacing(20);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.setPrefSize(835, 500);
		vbox.getChildren().add(tableH);
		vbox.getChildren().add(table);
		Text error = new Text("");
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
				
				// Check if project number is valid.
				validateProject(orderNumber);
				if (validate == true) {
					showDetail(orderNumber);
					order.clear();
				}
				else if (validate == false) {
					error.setText("Invalid Order");
					order.clear();
				}
				validate = false;
			}
			catch (NumberFormatException ex) {
				error.setText("Number Required");
				order.clear();
			}
			catch (Exception ex) {
			}
			order.requestFocus();
		});
		
		// Team member refresh table.  Differs from administrative table used in rereshTable method. 
		refresh.setOnAction(e -> {
			
			refreshMemberTable();
			error.setText("");
			order.clear();
			order.requestFocus();
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
		memberStage.setTitle("Projects Screen");
		memberStage.setScene(scene);
		memberStage.show();
		
		order.requestFocus();
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
					String field = p.getField();
					String newF ="";
					
					// Check if "New" or "Admin Comment" or "Changed" and not "Commented" or "Pending".
					if (field.contentEquals("New") || field.contentEquals("Admin Comment") || field.contentEquals("Changed")) {
						newF = "";
					}
					else {
						newF = field;
					}
					p.setField("");
					newFlag = false;
					modFlag = true;
					
					rebuildFile(orderNumber, pack, mod, assigned, desc, status,pri, com, newFlag, newF);
					refreshMemberTable();
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
		Text message = new Text();
		message.setFont(new Font("Arial", 20));
		message.setStroke(Color.RED);
		pane.add(message, 0, 6);
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(30);
//		pane.setPadding(new Insets(10.5, 1.5, 10.5, 1.5));
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
		Button close = new Button("Request Close");
		Button previous = new Button("Previous Screen");
		actions.getChildren().addAll(edit, close, previous);
		actions.setPrefSize(100, 100);
		
		// Button listeners.
		edit.setOnAction(e -> {
//			detailStage.close();
			addComment(orderNumber, com);
			refreshMemberTable();
			
		});
		close.setOnAction(e -> {
			message.setText("Pending");
			requestClose(orderNumber);
			refreshMemberTable();
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
			String field = "";
			
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
					p.setField("Commented");
					field = "Commented";
					
					newFlag = false;
					modFlag = true;
					rebuildFile(orderNumber, packN, module, assign, desc, status, prior, comments, newFlag, field);
				}
			}
			
			// Back to detail screen.
			try {
				showDetail(orderNumber);
			}
			catch (Exception ex) {
			}
			currentStage.close();
		});
		
		Pane newComment = new Pane();
		newComment.getChildren().add(ac);
		
		Scene n = new Scene(newComment);
		currentStage.setTitle("Comment Screen");
		currentStage.setScene(n);
		currentStage.show();
	}
	
	/**
	 * This method requests that a work order be closed.
	 * Initiated by the Team Member pending Administrative approval.
	 * 
	 * @param orderNumber (int:  Represents the work order number requested to close.)
	 */
	
	public void requestClose(int orderNumber) {
		
		// Read project File.
		try {
			FileInputStream in = new FileInputStream("Project.dat");
			ObjectInputStream proj = new ObjectInputStream(in);
			
			// Not EOF.
			while (true) {
				Project p = (Project) proj.readObject();
				int workO = p.getWorkOrder();
				
				if (orderNumber == workO) {
	//				p.setField("Pending");
					String packN = p.getPackageName();
					String module = p.getModuleName();
					String assign = p.getAssignedTo();
					String desc = p.getDescription();
					String status = p.getStatus();
					int prior = p.getPriority();
					String comments = p.getComments();
					String field = "Pending";
					
					newFlag = false;
					modFlag = true;
					rebuildFile(orderNumber, packN, module, assign, desc, status, prior, comments, newFlag, field);
				}
			}
		}
		catch (Exception ex) {
		}
		
	}
	
	/**
	 * This method refreshes the table for the Team Member screen.
	 */
	
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
						String field = project.getField();
						data.add(new ProjectList(wo, pack, module, assigned, desc, comments, status, dateIss, dateCl, priority, field));
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
	 */
	
	public void teamLeaderPage() throws Exception {
		
		// If initial screen.
		if (init == true) {
			
			// Setup administrative table and build.
			try {
				buildAdminTable();
				table.setEditable(false);
			}
			catch (Exception ex) {
			}
		}
		
		// Container for table contents.
		Group centerScene = new Group();
		
		// Team leader header.
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
		vbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.setPrefSize(975, 600);
		vbox.getChildren().add(tableH);
		vbox.getChildren().add(table);
		
		// Setup administrative actions container.
		HBox actions = new HBox();
		actions.setSpacing(25);
		actions.setPadding(new Insets(10, 20, 10, 40));
		Button add = new Button("Add Work Order");
		Button close = new Button("Close Work Order");
		Button reassign = new Button("Reassign Work Order");
		Button detail = new Button("Detail Screen");
		Button refresh = new Button("Refresh Screen");
		actions.getChildren().addAll(add, close, reassign, detail, refresh);
		actions.setAlignment(Pos.CENTER);
		actions.setPrefSize(1200, 50);
		
		// Button listeners.
		add.setOnAction(e -> addWorkOrder());
		close.setOnAction(e-> {
			adminStage.close();
			closeWorkOrder();
		});
		reassign.setOnAction(e-> {
			adminStage.close();
			reassignTo();
		});
		detail.setOnAction(e -> {
			getWorkNumber();
//			detailWorkOrder(orderNumber);
		});
		refresh.setOnAction(e -> {
			refreshTable();
		});
		
		centerScene.getChildren().addAll(vbox);
		
		// Setup main administrative page.
		BorderPane projectScreen = new BorderPane();
		projectScreen.setStyle("-fx-background-color: lightsalmon");
		projectScreen.setCenter(centerScene);
		projectScreen.setTop(pageHeader);
		projectScreen.setBottom(actions);
		projectScreen.setPrefSize(1200, 900);
		
		Scene scene = new Scene(projectScreen);
		adminStage.setTitle("Main Administration Screen");
		adminStage.setScene(scene);
		adminStage.show();
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
		Text msg = new Text("");
		msg.setStroke(Color.RED);
		msg.setFont(new Font("Arial", 20));
		paneMsg.setAlignment(Pos.CENTER);
		paneMsg.getChildren().add(msg);
		pane.add(paneMsg, 6, 0);
		Text wo = new Text("Work Order:  ");
		wo.setFont(new Font("Arial", 20));
		pane.add(wo,  1,  2);
		Text workOrder = new Text();
		workOrder.setFont(new Font("Arial", 20));
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
				
				String packN = packId.getText().trim();
				int prior = Integer.parseInt(priorityN.getText().trim());
				
				//  Check for valid employee.
				boolean validEmployee = false;
				String assignField = assignTo.getText().trim();
				
				// Read projects file and add to array.
				try(ObjectInputStream readIn = new ObjectInputStream(new FileInputStream("Employee.dat"));
					) {
					
						// Not EOF.
						while (true) {
							
							Employee emp = (Employee) readIn.readObject();
							String name = emp.getFirstName();
							if (assignField.contentEquals(name)) {
								validEmployee = true;
							}
						}
				}
				catch (Exception ex) {
					msg.setText("Employee Error");
					assignTo.clear();
				}
				
				String moduleField = moduleId.getText().trim();
				String descF = descField.getText().trim();
				String comments = comArea.getText().trim();
				String dateIss = new java.util.Date() + "";
				String dateCl = "";
				String status = "Open";
				String field = "New";
				
				if (validEmployee == true) {
					
					// Add to table data and rebuild projects file.
					data.add(new ProjectList(lastWorkOrder, packN, moduleField, assignField, descF, comments, "Open", dateIss, dateCl, prior, field));
					modFlag = false;
					newFlag = true;
					rebuildFile(lastWorkOrder, packN, moduleField, assignField, descF, status, prior, comments, newFlag, field);
					
					// Change employee file.
					String fName = assignTo.getText().trim();
					addOrder = true;
					closeOrder = false;
					changeOrder = false;
					String reassign = "";
					changeEmployeeFile(fName, reassign, addOrder, closeOrder, changeOrder);
					
					msg.setText("Order Added");
					lastWorkOrder++;
					workOrder.setText(lastWorkOrder + "");
					priorityN.clear();
					assignTo.clear();
					moduleId.clear();
					descField.clear();
					comArea.clear();
					
					//  Reset flag.
					newFlag = false;
					addOrder = false;
					closeOrder = false;
					changeOrder = false;
				}
				validEmployee = false;
			}
			catch(NumberFormatException ex) {
				msg.setText("Field Error");
				priority.requestFocus();
			}
		});
		
		previous.setOnAction(e-> {
			currentStage.close();
			adminStage.show();
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
		currentStage.setTitle("Add Order Screen");
		currentStage.setScene(add);
		currentStage.show();
		
		priorityN.requestFocus();
	}
	
	/**
	 * This method closes a work order.  It removes it from the ProjectList and sets the work order's fields.  Setting it's status to "Closed" and passing the correct flag to rebuild the file.
	 * 
	 */
	
	public void closeWorkOrder() {
		
		Group centerScene = new Group();
		
		// Page header.
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
		Text th = new Text("Current Open Orders For Closing");
		th.setFont(new Font("Arial", 20));
		th.setStroke(Color.RED);
		tableH.setAlignment(Pos.CENTER);
		tableH.getChildren().add(th);
				
		// Table contents container.
		VBox vbox = new VBox();
		vbox.setSpacing(40);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.setPrefSize(975, 600);
		vbox.getChildren().add(tableH);
		vbox.getChildren().add(table);
		Text msg = new Text("");
		msg.setFont(new Font("Arial", 20));
		msg.setStroke(Color.RED);
		vbox.getChildren().add(msg);
		
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
		
		centerScene.getChildren().addAll(vbox);
		
		BorderPane addScreen = new BorderPane();
		addScreen.setTop(pageHeader);
		addScreen.setCenter(centerScene);
		addScreen.setBottom(actions);
		addScreen.setStyle("-fx-background-color: lightsalmon");
		addScreen.setPrefSize(1200, 900);
		
		//  Button listeners.
		proceed.setOnAction(e -> {
			
			try{
				
				int order = Integer.parseInt(workOrder.getText().trim());
				
				// Check if valid project.
				validateProject(order);
				if (validate == true) {
					
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
							in.setField("Closed");
							String f = "";
							index = data.indexOf(in);
							
							// Set new flag to false and rebuild file.
							newFlag = false;
							modFlag = false;
							rebuildFile(workOr, packN, module, assign, desc, status, prior, comments, newFlag, f);
							
							// Change employee file.
							addOrder = false;
							closeOrder = true;
							changeOrder = false;
							String reassign = "";
							changeEmployeeFile(assign, reassign, addOrder, closeOrder, changeOrder);
							
							// Reset flags.
							addOrder = false;
							closeOrder = false;
							changeOrder = false;
						}
					}
					
					// Remove from project list, reset flags and send message to display.
					data.remove(index);
					msg.setText("Order Closed");
					workOrder.clear();
				}
				else if (validate == false) {
					msg.setText("Invalid Order");
					workOrder.clear();
				}
				validate = false;
			}
			catch (NumberFormatException ex) {
				msg.setText("Number Required");
			}
			workOrder.requestFocus();
		});
		
		previous.setOnAction(e-> {
			currentStage.close();
			init = false;
			try  {
				teamLeaderPage();
			}
			catch (Exception ex) {
				
			}
		});
		
		// Enter key listener.
		workOrder.setOnAction(e -> {
			
			try {
				
				int order = Integer.parseInt(workOrder.getText().trim());
				
				// Check if valid project.
				validateProject(order);
				if (validate == true) {
					
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
							in.setField("");
							String f = "";
							index = data.indexOf(in);
							
							// Set new flag to false and rebuild file.
							newFlag = false;
							modFlag = false;
							rebuildFile(workOr, packN, module, assign, desc, status, prior, comments, newFlag, f);
							
							// Change employee file.
							addOrder = false;
							closeOrder = true;
							changeOrder = false;
							String reassign = "";
							changeEmployeeFile(assign, reassign, addOrder, closeOrder, changeOrder);
							
							// Reset flags.
							addOrder = false;
							closeOrder = false;
							changeOrder = false;
						}
					}
					
					// Remove from project list, reset flags and send message to display.
					data.remove(index);
					msg.setText("Order Closed");
					workOrder.clear();
					
				}
				else if (validate == false) {
					msg.setText("Invalid Order");
					workOrder.clear();
				}
				validate = false;
			}
			catch (NumberFormatException ex) {
				msg.setText("Number Required");
			}
			workOrder.requestFocus();
		});

		Scene add = new Scene(addScreen);
		currentStage.setScene(add);
		currentStage.setTitle("Close Work Order");
		currentStage.show();
		
		workOrder.requestFocus();
	}
	
	/**
	 * This method lists the open orders and retrieves the work order number to reassign.
	 */
	
	public void reassignTo() {
		
		Group centerScene = new Group();
		
		// Page header.
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
		Text th = new Text("Current Open Orders For Reassignment");
		th.setFont(new Font("Arial", 20));
		th.setStroke(Color.RED);
		tableH.setAlignment(Pos.CENTER);
		tableH.getChildren().add(th);
				
		// Table contents container.
		VBox vbox = new VBox();
		vbox.setSpacing(40);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.setPrefSize(975, 600);
		vbox.getChildren().add(tableH);
		vbox.getChildren().add(table);
		Text msg = new Text("");
		msg.setFont(new Font("Arial", 20));
		msg.setStroke(Color.RED);
		vbox.getChildren().add(msg);
		
		// Bottom box.
		HBox actions = new HBox();
		actions.setPadding(new Insets(10, 10, 20, 10));
		actions.setSpacing(20);
		actions.setAlignment(Pos.BOTTOM_CENTER);
		Button previous = new Button("Previous");
		Text workOrder = new Text("Work Order To Reassign");
		workOrder.setFont(new Font("Arial", 20));
		TextField wn = new TextField();
		wn.setPrefWidth(100);
		Button proceed = new Button("Proceed To Reassignment Screen");
		actions.getChildren().addAll(previous, workOrder, wn, proceed);
		
		centerScene.getChildren().add(vbox);
		
		// Button listener.
		proceed.setOnAction(e -> {
			try {
				int order = Integer.parseInt(wn.getText().trim());
				
				// Check if valid project.
				validateProject(order);
				if (validate == true) {
					wn.clear();
					reassignDetail(order);
					reassignStage.close();
					refreshTable();
				}
				else if (validate == false) {
					msg.setText("Invalid Order");
					wn.clear();
				}
				validate = false;
			}
			catch (NumberFormatException ex) {
				msg.setText("Number Required");
				wn.clear();
			}
			wn.requestFocus();
		});
		
		previous.setOnAction(e -> {
			reassignStage.close();
			init = false;
			try  {
				teamLeaderPage();
				
				init = true;
			}
			catch (Exception ex) {
				
			}
		});
		
		// Enter key listener.
		wn.setOnAction(e -> {
			
			try {
				int order = Integer.parseInt(wn.getText().trim());
				
				// Check if valid project.
				validateProject(order);
				if (validate == true) {
					wn.clear();
					reassignDetail(order);
					reassignStage.close();
					refreshTable();
				}
				else if (validate == false) {
					msg.setText("Invalid Order");
					wn.clear();
				}
				validate = false;
			}
			catch (NumberFormatException ex) {
				msg.setText("Number Required");
				wn.clear();
			}
			wn.requestFocus();
		});
				
		BorderPane addScreen = new BorderPane();
		addScreen.setTop(pageHeader);
		addScreen.setCenter(centerScene);
		addScreen.setBottom(actions);
		addScreen.setStyle("-fx-background-color: lightsalmon");
		addScreen.setPrefSize(1100, 900);
						
		Scene add = new Scene(addScreen);
		reassignStage.setScene(add);
		reassignStage.setTitle("Order Reassignment");
		reassignStage.show();
		
		wn.requestFocus();
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
		
		String prevAssigned = assigned;
		
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
					String f = "Changed";
					i.setField(f);
					index = data.indexOf(i);
					
					// Set new flag to false and rebuild file.
					newFlag = false;
					modFlag = true;
					rebuildFile(orderNumber, packN, moduleN, assignTo, descF, status, priority, commentsF, newFlag, f);
					
					// Change employee file.
					addOrder = false;
					closeOrder = false;
					changeOrder = true;
					
					changeEmployeeFile(assignTo, prevAssigned, addOrder, closeOrder, changeOrder);
					
					// Reset flags.
					addOrder = false;
					closeOrder = false;
					changeOrder = false;
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
			currentStage.close();
			try {
				reassignTo();
			}
			catch (Exception ex) {
			}
		});
		
		// Back to main administration screen.
		main.setOnAction(e -> {
			currentStage.close();
			init = false;
			try {
				teamLeaderPage();
			}
			catch (Exception ex) {
			}
		});
		
		previous.setOnAction(e -> {
			currentStage.close();
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
		currentStage.setTitle("Reassignment Screen");
		currentStage.setScene(detail);
		currentStage.show();
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
				
				// Check if valid project.
				validateProject(order);
				if (validate == true) {
					detailWorkOrder(order);
					currentStage.close();
				}
				else if (validate == false) {
					error.setText("Invalid Order");
					workOrder.clear();
				}
				validate = false;
			}
			catch (NumberFormatException ex) {
				error.setText("Number Required");
				workOrder.clear();
			}
			workOrder.requestFocus();
		});
		
		Scene orderScene = new Scene(orderBox);
		currentStage.setTitle("Work Order");
		currentStage.setScene(orderScene);
		currentStage.show();
		
		workOrder.requestFocus();
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
		String field = "";
		String stat = "";
		
		for(ProjectList i: data) {
			int workO = i.getWorkOrder();
			if (orderNumber == workO) {
				pack = i.getPackageName();
				mod = i.getModuleName();
				assigned = i.getAssignedTo();
				prior = i.getPriority();
				com = i.getComments();
				desc = i.getDescription();
				stat = i.getStatus();
				field = i.getField();
				
				if (field.contentEquals("Commented")) {
					i.setField("");
					String nf = i.getField();
					newFlag = false;
					modFlag = true;
					rebuildFile(orderNumber, pack, mod, assigned, desc, stat, prior, com, newFlag, nf);
					refreshTable();
				}
			}
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
//		pane.setPadding(new Insets(10.5, 1.5, 10.5, 1.5));
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
//			adminDetail.close();
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
					String commentsN = in.getComments();
//					in.setField("Closed");
					in.setField("");
					index = data.indexOf(in);
					String f = "";
					
					// Set new flag to false and rebuild file.
					newFlag = false;
					modFlag = false;
					rebuildFile(orderNumber, packN, moduleN, assignN, descN, status, priorN, commentsN, newFlag, f);
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
//			try {
//				teamLeaderPage();
//			}
//			catch (Exception ex) {
//			}
			adminStage.show();
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
			String f = "";
			
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
					p.setField("Admin Comment");
					f = "Admin Comment";
				}
			}
			
			// Set flags and rebuild file.
			newFlag = false;
			modFlag = true;
			rebuildFile(orderNumber, packN, module, assign, desc, status, prior, comments, newFlag, f);
			
			refreshTable();
			
			// Work order detail screen.
			try {
				detailWorkOrder(orderNumber);
			}
			catch (Exception ex) {
			}
			currentStage.close();
		});
		
		Pane newComment = new Pane();
		newComment.getChildren().add(ac);
		
		Scene n = new Scene(newComment);
		currentStage.setTitle("Administrative Comment Screen");
		currentStage.setScene(n);
		currentStage.show();
	}
	
	/**
	 * This method refreshes the administrative table after changes are made.
	 * 
	 * <p>Differs from team member refresh.</p>
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
					String change = project.getField();
//					String change = "";
					data.add(new ProjectList(wo, pack, module, assign, desc, comments, status, dateIss, dateCl, priority, change));
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
	 * @param f (String:  Represents any changes to this record.)
	 */
	
	public void rebuildFile(int order, String packN, String m, String a, String d, String s, int p, String c, boolean newFlag, String f) {
		
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
						String change = "Closed";
						newProject.add(new Project(o, pa, mo, as, dd, ss, pp, cc, i, clz, change));
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
						String change = f;
						newProject.add(new Project(o, pa, mo, as, dd, ss, pp, cc, i, clz, change));
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
						String change = proj.getField();
						newProject.add(new Project(o, pa, mo, as, dd, ss, pp, cc, i, clz, change));
					}
				}
		}
		catch (Exception ex) {
		}
		
		// If record is completely new.
		if (newFlag == true) {
			newProject.add(new Project(order, packN, m, a, d, s, p, c, f));
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
	 * This method changes the Employee.dat file when an order is added, closed, or reassigned.
	 * 
	 * @param employeeName (String:  Represents the employee's first name for reassignment.)
	 * @param oldAssigned (String:  Represents the previously assigned employee's first name.)
	 * @param add (boolean:  Represents if this is a new order added for the employee.)
	 * @param closed (boolean:  Represents if this is a order being closed for the employee.)
	 * @param change (boolean:  Represents if this order is being reassigned to another employee.)
	 */
	
	public void changeEmployeeFile(String employeeName, String oldAssigned, boolean add, boolean closed, boolean change) {
		
		int openP = 0;
		int closedP = 0;
		
		// Array for projects.
		ArrayList<Employee> newEmployee = new ArrayList<Employee>();
		
		// Read projects file and add to array.
		try(ObjectInputStream readIn = new ObjectInputStream(new FileInputStream("Employee.dat"));
			) {
			
				// Not EOF.
				while (true) {
					
					Employee emp = (Employee) readIn.readObject();
					String name = emp.getFirstName();
					
					// If adding or closing an order.
					if (name.contentEquals(employeeName) && change == false) {
						
						int employeeNumber = emp.getEmployeeId();
						String lName = emp.getLastName();
						String pass = emp.getPassword();
						int adminL = emp.getAdminLevel();
						
						// If project opened.
						if (addOrder == true) {
							openP = emp.getOpenProjects() + 1;
						}
						else {
							openP = emp.getOpenProjects();
						}
						
						// If project closed.
						if (closeOrder == true) {
							closedP = emp.getClosedProjects() + 1;
							openP = emp.getOpenProjects() - 1;
						}
						else {
							closedP = emp.getClosedProjects();
						}
						
						newEmployee.add(new Employee(employeeNumber, employeeName, lName, pass, adminL, openP, closedP));
					}
					
					// If a reassignment, change reassigned employee.
					else if (name.contentEquals(employeeName) && change == true) {
						
						int employeeNumber = emp.getEmployeeId();
						String lName = emp.getLastName();
						String pass = emp.getPassword();
						int adminL = emp.getAdminLevel();
						openP = emp.getOpenProjects() + 1;
						closedP = emp.getClosedProjects();
						
						newEmployee.add(new Employee(employeeNumber, employeeName, lName, pass, adminL, openP, closedP));
					}
					
					// If a reassignment, change previously assigned employee.
					else if (name.contentEquals(oldAssigned) && change == true) {
						
						int employeeNumber = emp.getEmployeeId();
						String lName = emp.getLastName();
						String pass = emp.getPassword();
						int adminL = emp.getAdminLevel();
						openP = emp.getOpenProjects() - 1;
						closedP = emp.getClosedProjects();
						
						newEmployee.add(new Employee(employeeNumber, oldAssigned, lName, pass, adminL, openP, closedP));
					}
					
					// No change.
					else {
						String empName = emp.getFirstName();
						int employeeNumber = emp.getEmployeeId();
						String lName = emp.getLastName();
						String pass = emp.getPassword();
						int adminL = emp.getAdminLevel();
						openP = emp.getOpenProjects();
						closedP = emp.getClosedProjects();
						
						newEmployee.add(new Employee(employeeNumber, empName, lName, pass, adminL, openP, closedP));
					}
				
				}
		}
		catch (Exception ex) {
		}
		
		// Write array out to file.
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Employee.dat"));
			) {	
				
				for (Employee e: newEmployee) {
					out.writeObject(e);
				}
		}
		catch (Exception ex) {
		}
	}
	
	/**
	 * This method checks if the work order is in the current projects.
	 * Either open projects for Team Leader screen or open and assigned to for Team Member screen.
	 * @param workOrder (int:  Represents the work order number to check against ProjectList data.)
	 */
	
	public void validateProject(int workOrder) {
		
		validate = false;
		
		for (ProjectList i: data) {
			
			int workO = i.getWorkOrder();
			if (workOrder == workO) {
				validate = true;
			}
		}
		
	}
	
	/**
	 * This class handles the entries in the table view display.
	 *
	 * @author Chad Lister
	 *         Date:  11/10/2020
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
		private final SimpleStringProperty field;
		
		// Constructor.
		private ProjectList(int workOrder, String packageName, String moduleName, String assignedTo, String description, String comments, String status, String dateIssued, String dateClosed, int priority, String field) {
			this.field = new SimpleStringProperty(field);
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
		public int getWorkOrder() {
			return workOrder.get();
		}
		
		public void setWorkOrder(int workOrder) {
			this.workOrder.set(workOrder);
		}
		
		public String getPackageName() {
			return packageName.get();
		}

		public void setPackageName(String packageName) {
			this.packageName.set(packageName);
		}

		public String getModuleName() {
			return moduleName.get();
		}
		
		public void setModuleName(String moduleName) {
			this.moduleName.set(moduleName);
		}
		
		public String getAssignedTo() {
			return assignedTo.get();
		}

		public void setAssignedTo(String assignedTo) {
			this.assignedTo.set(assignedTo);
		}

		public String getDescription() {
			return description.get();
		}
		
		public void setDescrription(String description) {
			this.description.set(description);
		}
		
		public String getComments() {
			return comments.get();
		}
		
		public void setComments(String comments) {
			this.comments.set(comments);
		}
		
		public String getDateIssued() {
			return dateIssued.get();
		}
		
		public void setDateIssued(String dateIssued) {
			this.dateIssued.set(dateIssued);
		}
		
		public String getDateClosed() {
			return dateClosed.get();
		}
		
		public void setDateClosed(String dateClosed) {
			this.dateClosed.set(dateClosed);
		}
				
		public String getStatus() {
			return status.get();
		}
		
		public void setStatus(String status) {
			this.status.set(status);
		}
				
		public int getPriority() {
			return priority.get();
		}
		
		public void setPriority(int priority) {
			this.priority.set(priority);
		}
		
		public String getField() {
			return field.get();
		}

		public void setField(String field) {
			this.field.set(field);
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
