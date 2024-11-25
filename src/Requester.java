//client

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Requester{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
 	Scanner input;
 	int result, option;
	Requester(){
		
		input = new Scanner(System.in);
	}
	void run()
	{
		try
		{
			//1. creating a socket to connect to the server
			
			requestSocket = new Socket("127.0.0.1", 2004);
			System.out.println("Connected to localhost in port 2004");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//3: Communicating with the server
			do
			{
				//repeating if they don't enter 1 or 2 
				do
				{
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
				}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2"));
			
			    if(message.equalsIgnoreCase("1"))
			    {
			    	//NAME
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//ID
					do
					{
						message = (String)in.readObject();
						System.out.println(message);
						message = input.nextLine();
						sendMessage(message);
					}while(message.equalsIgnoreCase("-1"));
					
					//EMAIL
					do
					{
						message = (String)in.readObject();
						System.out.println(message);
						message = input.nextLine();
						sendMessage(message);
					}while(message.equalsIgnoreCase("-1"));
					
					//PASSWORD
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//DEPARTMENT
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//ROLE
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//successful register
					message = (String)in.readObject();
					System.out.println(message);
					
			    }
			    else if(message.equalsIgnoreCase("2"))
			    {
			    	int attempts = 0;
			    	do
			    	{
				    	//Email
				    	message = (String)in.readObject();
						System.out.println(message);
						message = input.nextLine();
						sendMessage(message);
						
						//Password
				    	message = (String)in.readObject();
						System.out.println(message);
						message = input.nextLine();
						sendMessage(message);
						
						//Result
						message = (String)in.readObject();
						System.out.println(message);
						
						attempts ++;
						
						//if the book does not exists (result = -1)
						if(message.equalsIgnoreCase("-1"))
						{
							System.out.println("Wrong Email or Password - Attemps: " + attempts);
						}
				    	
						//if account exists result = 1
						if(message.equalsIgnoreCase("1"))
						{	
							System.out.println("You have successfully logged in");
						}
						
						//if the user exceeds the attempts
						if (attempts >= 5) {
							message = (String)in.readObject();
							System.out.println(message);
	                        break;
	                    }
			    	}while(message.equalsIgnoreCase("-1"));
			    	
			    	
			    }
			     
			    message = (String)in.readObject();
				System.out.println(message);
				message = input.nextLine();
				sendMessage(message);
		
		
			}while(message.equalsIgnoreCase("1"));
			
			//in report database center 
			message = (String)in.readObject();
			System.out.println(message);
				  
			do {
				//looping until user chooses a valid option 
				do
				{
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
	                option = Integer.parseInt(message);
					if(option < 1 || option > 5) {
						message = (String)in.readObject();
						System.out.println(message);
					}
				}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2")&&!message.equalsIgnoreCase("3")&&!message.equalsIgnoreCase("4")&&!message.equalsIgnoreCase("5"));
	
				if(option == 1)
				{	
					//REPORT TYPE 
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//DATE
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//EMPLOYEE ID
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//STATUS
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//SUCCESSFUL CREATION
					message = (String)in.readObject();
					System.out.println(message);
				}
				
				if(option == 2) {				
					message = (String)in.readObject();
					result = Integer.parseInt(message);
							
					for(int i=0; i<result; i++) {
						message = (String)in.readObject();
						System.out.println(message);
					}    
				}
				
				if(option == 3) {
					//ASSIGNING REPORT TO EMPLOYEE
					message = (String)in.readObject();
					System.out.println(message);
					
					//REPORT ID
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//EMPLOYEE ID
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//reading if the assignment is valid 
					message = (String)in.readObject();
					System.out.println(message);
					
					if(message.equalsIgnoreCase("1")) {
						System.out.println("Report assigned successfully");
					}
					
					else {
						System.out.println("Invalid Report ID or Employee ID");
					}
				}
				
				if(option == 4) {
					//ASKING FOR USERS ID
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//LENGHT
					message = (String)in.readObject();
					result = Integer.parseInt(message);
							
					for(int i=0; i<result; i++) {
						message = (String)in.readObject();
						System.out.println(message);
					}  
				}
				
				if(option == 5) {
					//READING USER EMAIL
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//READING USER PASSWORD 
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//READING NEW PASSWORD 
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//READING RESULT OF UPDATEPASSWORD METHOD 
					message = (String)in.readObject();
					System.out.println(message);
					
					if(message.equalsIgnoreCase("1"))
					{
						System.out.println("Password updated successfully");
					}
					
					else
					{
						System.out.println("Incorrect email or password entered");
					}
					
				}
				
				
				//asking the user if they want to repeat
				message = (String)in.readObject();
				System.out.println(message);
				message = input.nextLine();
				sendMessage(message);
				
			}while(message.equalsIgnoreCase("1"));
			
		}
		catch(UnknownHostException unknownHost)
		{
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		Requester client = new Requester();
		client.run();
	}
}