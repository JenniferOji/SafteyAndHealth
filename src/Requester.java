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
 	int result;
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

				//repeating if they don't enter 1 or 2 
				do
				{
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
				}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2")&&!message.equalsIgnoreCase("3"));
			
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
					
					message = (String)in.readObject();
					System.out.println(message);
					
					//successful register
					message = (String)in.readObject();
					System.out.println(message);
					
			    }
			    else if(message.equalsIgnoreCase("2"))
			    {
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
					
					//if the book does not exists (result = -1)
					if(message.equalsIgnoreCase("-1"))
					{
						System.out.println("Wrong Email or Password");
					}
					else
					{
						
						String[] resultPart = message.split("@");
						System.out.println("Name: "+resultPart[0]);
						System.out.println("EmployeeID: "+resultPart[1]);
						System.out.println("Email: "+resultPart[2]);
						System.out.println("Password: "+resultPart[3]);
						System.out.println("DepartmentName: "+resultPart[4]);
						System.out.println("Role: "+resultPart[5]);
						
						//System.out.println("Login Successful. You can now access the system");
					}
			    	
			    	}while(message.equalsIgnoreCase("-1"));
			    	
			    	//successful login
			    	message = (String)in.readObject();
					System.out.println(message);
			    }
			    
			    
			    else if(message.equalsIgnoreCase("3")) {
			    	message = (String)in.readObject();
					result = Integer.parseInt(message);
					
					for(int i=0; i<result; i++) {
						message = (String)in.readObject();
						System.out.println(message);
					}
					
			    }
			    
			    //database message 
			    message = (String)in.readObject();
			    System.out.println(message);
			    
			    //looping until valid option picked
			    do
			    {
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
				}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2")&&!message.equalsIgnoreCase("3")&&!message.equalsIgnoreCase("4")&&!message.equalsIgnoreCase("5"));
			    

			    
		
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