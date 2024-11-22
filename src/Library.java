import java.util.Iterator;
import java.util.LinkedList;

public class Library {

	private LinkedList<Book> list;
	
	public Library()
	{
		//list holds a linked list of books 
		list = new LinkedList<Book>();
	}
	
	public synchronized void addBook(String name, int employeeID, String email, String password, String departmentName, String role)
	{
		Book temp = new Book(name,employeeID,email,password,departmentName,role);
		
		list.add(temp);
		
		//update the file storage for the books
		
	}
	
	public synchronized String searchBook(String searchValue, String searchValue2)
	{
		//get sent the five parameters to make a book and add the to the list 
		//if you get to the end and the book doesn't exist you send a -1
		String result="-1";//no book 
		Iterator i = list.iterator();
		Book temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Book)i.next();
			
			if(temp.getName().equalsIgnoreCase(searchValue)&& temp.getPassword().equalsIgnoreCase(searchValue2))
			{
				//result = temp.toString();//string of the employee details 
				result = "1";
				break;
			}
		}
		
		return result;
		
	}
	
	public synchronized String empolyeeIDExists(String searchValue)
	{
		//get sent the five parameters to make a book and add the to the list 
		//if you get to the end and the book doesn't exist you send a -1
		String result="1";//no book 
		Iterator i = list.iterator();
		Book temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Book)i.next();
			
			if(temp.getEmpID().equalsIgnoreCase(searchValue))
			{
				result = "-1";//string of the employee details 
				break;
			}
		}
		
		return result;
		
	}
	
	public synchronized String emailExists(String searchValue)
	{
		//get sent the five parameters to make a book and add the to the list 
		//if you get to the end and the book doesn't exist you send a -1
		String result="1";//email already exists  
		Iterator i = list.iterator();
		Book temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Book)i.next();
			
			if(temp.getEmail().equalsIgnoreCase(searchValue))
			{
//				result = temp.toString();//string of the employee details 
				result = "-1";
				break;
			}
		}
		
		return result;
		
	}
	
	public synchronized int getLength()
	{
		return list.size();
	}
	
	public synchronized String getItem(int location)
	{
		Book temp = list.get(location);
		
		return temp.toString();
	}
}