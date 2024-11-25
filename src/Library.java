import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Library {

	private LinkedList<Book> list;
	private LinkedList<Reports> list2;

	
	public Library()
	{
		//list holds a linked list of books 
		list = new LinkedList<Book>();
		list2 = new LinkedList<Reports>();

	}
	
	//FOR THE EMPLOYEE REGISTER 	
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
		return list2.size();
	}
	
	public synchronized String getItem(int location)
	{
		Book temp = list.get(location);
		
		return temp.toString();
	}
	

	
	//FOR THE REPORTS 
	public synchronized void addReport(String type, int reportID, String date, int reportEmployeeID, String status, int assignedEmployeeID)
	{	
		
		Reports temp = new Reports(type,reportID,date,reportEmployeeID,status,assignedEmployeeID);
		
		list2.add(temp);
		
		//result = temp.toString();//string of the employee details 

		//update the file storage for the books
		
	}
	
	public synchronized String reportIDExists(String searchReport, String searchID) {
        // Default to invalid assignment
        String validAssignment = "-1";
        boolean reportExists = false;
        boolean employeeExists = false;
        Iterator i = list2.iterator();
        Iterator j = list.iterator();

		Reports temp;
		Book temp2;
        
        // Check if report exists
        while(i.hasNext()) {
			temp = (Reports)i.next();
            if(temp.getReportID().equals(searchReport)) {
                reportExists = true;
                break;
            }
        }
        
        // Check if employee exists
        while(j.hasNext()) {
			temp2 = (Book)j.next();
        	if(temp2.getEmpID().equals(searchID)) {
                employeeExists = true;
                break;
            }
        }
        
        
        // Both report and employee must exist for valid assignment
        if(reportExists && employeeExists) {
            validAssignment = "1";

            //looping through all the reports until it finds the correct one
            for (Reports temp3 : list2) {
                if (temp3.getReportID().equals(searchReport)) {
                	//if there is a match - changing the status to assigned and changing the assigned ID
                    temp3.setAssignedID(Integer.parseInt(searchID));
                    temp3.setStatus("Assigned");
                    break;
                }
            }
        } 
        return validAssignment;
    }
	
	public synchronized int reportIDGenerator(int reportID) {
        Random rand = new Random();

        int randomNum = rand.nextInt(1000) + 1;
        return randomNum;

	}
	
	public synchronized String searchAccidentReports(String searchValue, String searchValue2)
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
			
			result = temp.toString();//string of the employee details 
			break;
			
		}
		
		return result;
		
	}
	
	public synchronized String getAccidentReports(int location)
	{
		Reports temp = list2.get(location);
		
		return temp.toString();
	}
	
	public synchronized String getYourHealthReports(String id)
	{
		String result="-1";//email already exists  
		Iterator i = list2.iterator();
		Reports temp;
		
		//looping through all the reports
		while(i.hasNext())
		{
			temp = (Reports)i.next();
			
			if(temp.getAssignedID().equalsIgnoreCase(id))
			{
				result = "1";
				break;
			}
		}
		
		return result;
	}
	
	public synchronized String getYourReports(int location, String id)
	{
		Reports temp = list2.get(location);
		
		if(temp.getAssignedID().equalsIgnoreCase(id))
		{
			return temp.toString();
		}
		
		return null;
	}
	
	
}
