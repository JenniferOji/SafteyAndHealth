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
	
	//variables for the report 
	private String reportType, date, status;
	private int reportID, reportEmployeeID, assingedEmployeeID;
	private int result, numberOfBooks, option;
	private int num1, num2, num3;
	private Library shared;
	
	private String validEmail;
	private String validEmployeeID;
	
	private boolean authenticate = false;
	
	public ServerThread(Socket s, Library lib)
	{
		myConnection = s;
		shared = lib;
	}
	
	public void run()
	{
		try  
		{
			out = new ObjectOutputStream(myConnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(myConnection.getInputStream());
		
			//The server is ready to communicate.....
			do
			{
				//repeating until the user enters 1 or 2
				do
				{
					sendMessage("Enter 1 - Register. 2 - Log-in");
					message = (String)in.readObject();
					result = Integer.parseInt(message);	
					
				}while(result!=1 && result!=2);
				
				if(message.equalsIgnoreCase("1"))
				{
					sendMessage("Enter Name");
					name = (String)in.readObject();
					
					//checking if employeeID is unique 
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
					
					shared.addBook(name, employeeID, email, password, departmentName, role);
					
					sendMessage("You have successfully registered");
					authenticate = true;
				}
				
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
						//String attemptsNum = Integer.toString(attempts);
						
						//Search for the employee ....
						result = shared.searchBook(userEmail, userPassword);			
						sendMessage(result);
						
						if(result.equalsIgnoreCase("1")){
							authenticate = true;
						}
						//if the user gives the wrong credentials more five times or more it exits the loop 
						if(attempts >=5) {
							sendMessage("Too many attempts - exiting login");
							authenticate = false;
							break;
						}

					}while(result.equalsIgnoreCase("-1"));
					
				}
				
			}while(authenticate == false);
				
			//creating report
			sendMessage("REPORT DATABASE - HEALTH AND SAFTEY REPORTS");
			
			do
			{
				do
				{
					sendMessage("Press 1 to Create a report\nPress 2 to retrieve all registered accident reports\nPress 3 to assign report\nPress 4 to view all reports\nPress 5 to update password");
					message = (String)in.readObject();
					option = Integer.parseInt(message);	
					
					if(option < 1 || option > 5) {
						sendMessage("Invalid option please enter a number between 1 - 5");
					}
					
				}while(option!=1 && option!=2 && option!=3 && option!=4 && option!=5);
			
				if(option == 1)
				{			
					sendMessage("Enter Report type (Accident Report or Health and Safety Risk Report) ");
					reportType = (String)in.readObject();
					
				    sendMessage("Enter Date");
					date = (String)in.readObject();
					
					sendMessage("Enter Employee ID of the Report Creation");
					message = (String)in.readObject();
					reportEmployeeID = Integer.parseInt(message);	
	
					sendMessage("Enter Report Status (Open - Assigned - Closed)");
					status = (String)in.readObject();
					
					//report ID is random and assigned in library class  
					reportID = shared.reportIDGenerator(reportID);
					assingedEmployeeID = 0;
					//assigned employee id blank until assigned 
					
					shared.addReport(reportType, reportID, date, reportEmployeeID, status, assingedEmployeeID);
					
					sendMessage("Report successfully created");
				}
				
				if(option == 2) {				
					int length = shared.getLength();
	
					//sharing all the reports 
					sendMessage(""+length);
					for(int i =0; i<length; i++) {
						sendMessage(shared.getAccidentReports(i));
					}
				}
				
				if(option == 3) {
					String validAssignment;
					sendMessage("ASSIGN A REPORT ");
					
					sendMessage("Enter the Report ID ");
					message = (String)in.readObject();
					reportID = Integer.parseInt(message);
					String report = Integer.toString(reportID);//parsing to be able to send to method 			

					sendMessage("Enter the Employee ID you want to assign the report to");
					message = (String)in.readObject();
					assingedEmployeeID = Integer.parseInt(message);
					String ID = Integer.toString(assingedEmployeeID);//parsing to be able to send to method 			

					//if report and id is valid it updates the details of the report in the method 
					validAssignment = shared.reportIDExists(report, ID);
					sendMessage(validAssignment);
				}
				
				if(option == 4) {					
					sendMessage("Enter your Employee ID");
					message = (String)in.readObject();
					employeeID = Integer.parseInt(message);
					String ID = Integer.toString(employeeID);//parsing to be able to send to method 			
					
					int length = shared.getLength();
					
					//sharing all the reports 
					sendMessage(""+length);
					for(int i =0; i<length; i++) {
						sendMessage(shared.getYourReports(i, ID));
					}
					
				}
				
				if(option == 5) {
					String setPassword;
					String newPassword;
					sendMessage("Enter your Email");
					email = (String)in.readObject();
					
					sendMessage("Enter your current password");
					password = (String)in.readObject();
					
					sendMessage("Enter new password");
					newPassword = (String)in.readObject();
					
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
