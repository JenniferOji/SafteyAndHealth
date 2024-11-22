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
						System.out.println("Wrong Email or Password - Try Again");
					}
			    	
					//if account exists result = 1
					if(message.equalsIgnoreCase("1"))
					{	
						System.out.println("You have successfully logged in");
					}
					
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