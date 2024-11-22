import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket myConnection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message, message2, name, email, password, departmentName, role;
	private int employeeID;

	private int result, numberOfBooks, option;
	private int num1, num2, num3;
	private Library shared;
	
	private String validEmail;
	private String validEmployeeID;
	
	private boolean authenticate;
	
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

						if(attempts >=5) {
							sendMessage("Too many attempts - exiting login");
							break;
						}
					}while(result.equalsIgnoreCase("-1"));
					
				}
				
				
					sendMessage("Press 1 to Repeat Registration/login or 3 to access the reports");
					message = (String)in.readObject();
					//option = Integer.parseInt(message);	
					
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
