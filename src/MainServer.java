//server 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

	public static void main(String args[])
	{
		ServerSocket provider;
		Socket connection;
		ServerThread handler;
		Database data = new Database();
		
		//populate the library...
		
		try 
		{
			provider = new ServerSocket(2004,10);
			
			while(true)
			{
				//every time connection is received i create a server thread 
				connection = provider.accept();
				handler = new ServerThread(connection, data);
				handler.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
