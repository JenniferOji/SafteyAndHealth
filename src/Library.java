import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

//for reading in the file 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

	public class Library {
	
	//	private LinkedList<Book> list;
		private LinkedList<Employees> list;
		private LinkedList<Reports> list2;
		
		public Library()
		{
			//list holds a linked list of books 
			list = new LinkedList<Employees>();
			list2 = new LinkedList<Reports>();
			
			//reading in the employees
			String fileContents;
			String[] results = new String[6];
			Employees temp;
			Reports temp2;
			//Populating the employees list
			try 
			{
				FileReader fr = new FileReader(new File("Employees.txt"));
				BufferedReader br = new BufferedReader(fr);
				
				while((fileContents = br.readLine())!=null)
				{  					
					String[] resultPart = fileContents.split("#");//splitting at the #
				
					temp = new Employees(resultPart[0], Integer.parseInt(resultPart[1]), resultPart[2], resultPart[3],resultPart[4], resultPart[5]);
					list.add(temp);
				}
				
			} 
			catch (FileNotFoundException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/////////////////////////////////////////////////////////////////////
			//populating the reports list 
			try 
			{
				FileReader fr = new FileReader(new File("Reports.txt"));
				BufferedReader br = new BufferedReader(fr);
				
				while((fileContents = br.readLine())!=null)
				{  					
					String[] resultPart = fileContents.split("#");//splitting at the #
			
					temp2 = new Reports(resultPart[0], Integer.parseInt(resultPart[1]), resultPart[2], Integer.parseInt(resultPart[3]),resultPart[4], Integer.parseInt(resultPart[5]));
					list2.add(temp2);
				}
				
			} 
			catch (FileNotFoundException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	//FOR THE EMPLOYEE REGISTER 	
	public synchronized void addBook(String name, int employeeID, String email, String password, String departmentName, String role)
	{
		Employees temp = new Employees(name,employeeID,email,password,departmentName,role);
		
		list.add(temp);
		
		//update the file storage for the Employees
		//rewriting the file when an employee registers 
		try 
		{
			FileWriter fw = new FileWriter(new File("Employees.txt"));
			Iterator i = list.iterator();
			
			
			while(i.hasNext())
			{
				temp = (Employees)i.next();
				fw.write(temp.toString()+"\n");
				
				System.out.println("Writing "+temp.toString());
			}
			
			fw.close();
		} 
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized String searchBook(String email, String password)
	{
		//get sent the five parameters to make a book and add the to the list 
		//if you get to the end and the book doesn't exist you send a -1
		String result="-1";//no book 
		Iterator i = list.iterator();
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
			if(temp.getEmail().equalsIgnoreCase(email)&& temp.getPassword().equalsIgnoreCase(password))
			{
				//result = temp.toString();//string of the employee details 
				result = "1";
				break;
			}
		}
		return result;
	}
	
	public synchronized String updatePassword(String email, String currentPassword, String newPassword)
	{
		//get sent the five parameters to make a book and add the to the list 
		//if you get to the end and the book doesn't exist you send a -1
		String result="-1";//no book 
		Iterator i = list.iterator();
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
			if(temp.getEmail().equalsIgnoreCase(email)&& temp.getPassword().equalsIgnoreCase(currentPassword))
			{
                temp.updatePassword(newPassword);
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
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
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
		String result="1";//email already exists  
		Iterator i = list.iterator();
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
			if(temp.getEmail().equalsIgnoreCase(searchValue))
			{
//				result = temp.toString();//string of the employee details 
				result = "-1";
				break;
			}
		}
		
		return result;
		
	}
	
	//TO BE DELETED
	///////////////////////////////////////////////
	public synchronized int getLength2()
	{
		return list.size();
	}
	
	public synchronized String getItem2(int location)
	{
		Employees temp = list.get(location);
		
		return temp.toString();
	}
	
	////////////////////////////////////////////
	public synchronized int getLength()
	{
		return list2.size();
	}
	
	public synchronized String getItem(int location)
	{
		Employees temp = list.get(location);
		
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
		Employees temp2;
        
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
			temp2 = (Employees)j.next();
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
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
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
