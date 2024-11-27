import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket myConnection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message, message2;
	
	//variables to register
	private String name, email, password, departmentName, role;
	private int employeeID;
	private String validEmail;
	private String validEmployeeID;
	
	//variables for the report 
	private String reportType, date, status;
	private int reportID, reportEmployeeID, assingedEmployeeID;
	private int result, option;
	
	//database is the shared data source 
	private Database shared;	
	
	//authenticate allows the user into the system 
	private boolean authenticate = false;
	
	public ServerThread(Socket s, Database data)
	{
		myConnection = s;
		shared = data;
	}
	
	public void run()
	{
		try  
		{
			out = new ObjectOutputStream(myConnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(myConnection.getInputStream());
		
			//When the server is ready to communicate 
			do
			{
				//repeating until the user enters 1 or 2
				do
				{
					sendMessage("Enter 1 - Register. 2 - Log-in");
					message = (String)in.readObject();
					result = Integer.parseInt(message);	
					
				}while(result!=1 && result!=2);
				
				//EMPLOYEE REGISTRATION
				if(message.equalsIgnoreCase("1"))
				{
					sendMessage("Enter Name");
					name = (String)in.readObject();
					
					//checking if the employeeID is unique 
					do
					{
						sendMessage("Employee ID");
						message = (String)in.readObject();
						employeeID = Integer.parseInt(message);
						validEmployeeID = shared.empolyeeIDExists(message);
					}while(validEmployeeID.equalsIgnoreCase("-1"));
					
					//checking if the email is unique
					do
					{
						sendMessage("Enter Email");
						email = (String)in.readObject();
						validEmail = shared.emailExists(email);
					}while(validEmail.equalsIgnoreCase("-1"));
					
					sendMessage("Enter Password");
					password = (String)in.readObject();
					
					sendMessage("Enter Department Name");
					departmentName = (String)in.readObject();
					
					sendMessage("Enter Role");
					role = (String)in.readObject();
					
					//adding employee to the employeeList 
					shared.addEmployee(name, employeeID, email, password, departmentName, role);
					
					sendMessage("You have successfully registered");
					//allows the user into the system 
					authenticate = true;
				}
				
				//LOGIN TO SYSTEM WITH EMAIL AND PASSWORD 
				else if(message.equalsIgnoreCase("2"))
				{
					String result;
					int attempts = 0;
					do
					{
						sendMessage("Enter your Email");
						String userEmail= (String)in.readObject();
						
						sendMessage("Enter your password ");
						String userPassword = (String)in.readObject();
						
						attempts ++;
						
						//searching if the employee exists 
						result = shared.searchEmployee(userEmail, userPassword);			
						sendMessage(result);
						
						//if they do they are allowed to the system 
						if(result.equalsIgnoreCase("1")){
							authenticate = true;
						}
						//if the user gives the wrong credentials more five times or more it exits the loop 
						if(attempts >=5) {
							sendMessage("Too many attempts - exiting login");
							//they are not allowed in system 
							authenticate = false;
							break;
						}

					}while(result.equalsIgnoreCase("-1"));
					
				}
			//the registration/login process continues until the user is authenticated 
			}while(authenticate == false);
				
			//HEALTH AND SAFTEY REPORT DATABASE
			sendMessage("REPORT DATABASE - HEALTH AND SAFTEY REPORTS");
			
			do
			{
				//looping until the user chooses a valid option (1-5)
				do
				{
					sendMessage("Press 1 to Create a report\nPress 2 to retrieve all registered accident reports\n"
							  + "Press 3 to assign a report\nPress 4 to view all your assigned reports\n"
							  + "Press 5 to update your password");
					
					message = (String)in.readObject();
					option = Integer.parseInt(message);	
					
					if(option < 1 || option > 5) {
						sendMessage("Invalid option please enter a number between 1 - 5");
					}
					
				}while(option!=1 && option!=2 && option!=3 && option!=4 && option!=5);
			
				//CREATE A REPORT
				if(option == 1)
				{	
					do {
						sendMessage("Enter Report type (Accident Report or Health and Safety Risk Report)) ");
						reportType = (String)in.readObject();
						//looping until the user choose a valid report type 
					}while(!reportType.equalsIgnoreCase("Accident Report") && !reportType.equalsIgnoreCase("Health and Safety Risk Report"));
					
				    sendMessage("Enter Date");
					date = (String)in.readObject();
					
					do
					{
						sendMessage("Enter Employee ID of the Report Creation");
						message = (String)in.readObject();
						reportEmployeeID = Integer.parseInt(message);	
						
						//checking if the ID exists 
						validEmployeeID = shared.empolyeeIDExists(message);
						//returning 1 means it is a unique ID is not valid 
					}while(validEmployeeID.equalsIgnoreCase("1"));
	
					do
					{
						sendMessage("Enter Report Status (Open - Assigned - Closed)");
						status = (String)in.readObject();
					}while(!status.equalsIgnoreCase("Open") && !status.equalsIgnoreCase("Assigned") && !status.equalsIgnoreCase("Closed"));
					
					//report ID is random and assigned in library class  
					reportID = shared.reportIDGenerator(reportID);
					//all newly created reports are assigned 0 until they assign an employee through the method
					assingedEmployeeID = 0;
					//assigned employee id blank until assigned 
					
					//adding report to report list 
					shared.addReport(reportType, reportID, date, reportEmployeeID, status, assingedEmployeeID);
					
					sendMessage("Report successfully created");
				}
				
				//OUTPUTTING ALL REPORGTS TO SCREEN 
				if(option == 2)
				{				
//					int length = shared.getLength();
//	
//					//sharing all the reports 
//					sendMessage(""+length);
//					for(int i =0; i<length; i++) {
//						sendMessage(shared.getAccidentReports(i));
//					}
					
					//method checks if you have the inputed ID has reports assigned to them 
					String reportsExists = shared.AccidentReports("Accident Report");
					sendMessage(reportsExists);
				}
				
				//ASSIGNING A REPORT 
				if(option == 3)
				{
					String validAssignment;
					sendMessage("ASSIGN A REPORT ");
					
					sendMessage("Enter the Report ID ");
					String report = (String)in.readObject();
					
					sendMessage("Enter the Employee ID you want to assign the report to");
					String ID = (String)in.readObject();
					
					//if report and id is valid it updates the details of the report in the method 
					validAssignment = shared.reportIDExists(report, ID);
					sendMessage(validAssignment);
				}
				
				//OUTPUTTING REPORTS BASED ON ID
				if(option == 4)
				{	
					sendMessage("Enter your Employee ID");
					String empID = (String)in.readObject();
					
					//method checks if you have the inputed ID has reports assigned to them 
					String reportsExists = shared.yourReports(empID);
					sendMessage(reportsExists);
				}
				
				//UPDATING PASSWORD 
				if(option == 5) {
					String setPassword;
					String newPassword;
					sendMessage("Enter your Email");
					email = (String)in.readObject();
					
					sendMessage("Enter your current password");
					password = (String)in.readObject();
					
					sendMessage("Enter new password");
					newPassword = (String)in.readObject();
					
					//calls the method to check if they are applicable to change their ID 
					setPassword = shared.updatePassword(email, password, newPassword);
					sendMessage(setPassword);

				}
				
				//repeating the menu options 
				sendMessage("Press 1 to go back to the menu");
				message = (String)in.readObject();
				
			}while(message.equalsIgnoreCase("1"));
			
		} //end of the try
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//4: Closing connection
			try
			{
				in.close();
				out.close();
				myConnection.close();
			}
			catch(IOException ioException)
			{
				ioException.printStackTrace();
			}
		}
		
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
}
