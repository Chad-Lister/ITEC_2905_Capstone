## Final Capstone Project For ITEC 2905 at Southwest Tech, Cedar City, Utah 84720.

## Synopsis
This is the basic design for my Project Management System.  It uses two files to enable administrative functions and team member functions.  Both screens can be run concurrently using the refresh screen buttons.  The administrative screen allows for adding, closing, reassigning and administrative commenting.  The team member screen allows for detail, request close and commenting.

## Motivation
My motivation was to create an "issue" tracker to be used for team projects.  Where administration or team leaders could add new work orders, track bugs and assignments and close them when completed.

## How to Run
The main files are created with BuildInitialFiles.java which builds the Employee.dat and Project.dat files.  After running the build then ProjectManagement.java drives the rest of the program(s).  PrintProjectFile.java prints selected values primarily used for testing.

<img src = "Login and Main.png">
<img src = "Team Leader and Team Member Screens.png">

## Code Example
```
				// Only include open orders.
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
```
## Tests
Located in the srcTest Folder.

## Documentation
Located in the doc folder.
