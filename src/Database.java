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

public class Database{
	
	private LinkedList<Employees> employeeList;
	private LinkedList<Reports> reportList;
		
	public Database()
	{
			//holds a linked list of employees  
			employeeList = new LinkedList<Employees>();
			//holds a linked list of reports 
			reportList = new LinkedList<Reports>();
			
			//reading in the employees
			String fileContents;
			Employees temp;
			Reports temp2;
			//POPULATING EMPLOYEE LIST
			try 
			{
				FileReader fr = new FileReader(new File("Employees.txt"));
				BufferedReader br = new BufferedReader(fr);
				
				while((fileContents = br.readLine())!=null)
				{  					
					String[] resultPart = fileContents.split("#");//splitting at the #
					//put each split word into an array of parts 
					temp = new Employees(resultPart[0], Integer.parseInt(resultPart[1]), resultPart[2], resultPart[3],resultPart[4], resultPart[5]);
					employeeList.add(temp);
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
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//POPULATING REPROTS LIST 
			try 
			{
				FileReader fr = new FileReader(new File("Reports.txt"));
				BufferedReader br = new BufferedReader(fr);
				
				while((fileContents = br.readLine())!=null)
				{  					
					String[] resultPart = fileContents.split("#");//splitting at the #
			
					temp2 = new Reports(resultPart[0], Integer.parseInt(resultPart[1]), resultPart[2], Integer.parseInt(resultPart[3]),resultPart[4], Integer.parseInt(resultPart[5]));
					reportList.add(temp2);
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
	
	//method that writes to the employees file when called 
	public synchronized void writeToEmployeesFile()
	{
		Employees temp;
		try 
		{
			FileWriter fw = new FileWriter(new File("Employees.txt"));
			Iterator i = employeeList.iterator();
			while(i.hasNext())
			{
				temp = (Employees)i.next();
				fw.write(temp.toString()+"\n");
				
				System.out.println("Writing "+temp.toString());
			}
			fw.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//method that writes to the reports file when called 
	public synchronized void writeToReportsFile()
	{
		Reports temp;
		try 
		{
			FileWriter fw = new FileWriter(new File("Reports.txt"));
			Iterator i = reportList.iterator();
			while(i.hasNext()){
				temp = (Reports)i.next();
				fw.write(temp.toStringFile()+"\n");
				
				System.out.println("Writing "+temp.toStringFile());
			}	
			fw.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//FOR THE EMPLOYEE REGISTER 	
	public synchronized void addEmployee(String name, int employeeID, String email, String password, String departmentName, String role)
	{
		Employees temp = new Employees(name,employeeID,email,password,departmentName,role);
		
		employeeList.add(temp);
		
		//adding the employee to the file 
		writeToEmployeesFile();
		
	}
	
	
	//when the user logs into the system it checks if there is an email and password matching to one of the employees details in the file 
	public synchronized String searchEmployee(String email, String password)
	{
		String result="-1";//no book 
		Iterator i = employeeList.iterator();
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
			if(temp.getEmail().equalsIgnoreCase(email)&& temp.getPassword().equalsIgnoreCase(password))
			{
				//if result is one it allows the user to enter the system by initializing the authenticate variable true 
				result = "1";
				break;
			}
		}
		
		//otherwise it sends -1 making the user go back to the register/login screen 
		return result;
	}
	
	//when the user wants to update their password it checks if their email and current password matches one in the system
	public synchronized String updatePassword(String email, String currentPassword, String newPassword)
	{
		String result="-1";//no book 
		Iterator i = employeeList.iterator();
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
			//if the password matches 
			if(temp.getEmail().equalsIgnoreCase(email)&& temp.getPassword().equalsIgnoreCase(currentPassword))
			{
				//it calls the method that updates their password 
                temp.updatePassword(newPassword);
                
                //updating the file since there has been a change 
                writeToEmployeesFile();
				result = "1";
				break;
			}
		}
		
		return result;
	}
	
	//when the user is registering it checks if the employee has a unique ID 
	public synchronized String empolyeeIDExists(String employeeID)
	{
		String result="1";//unique employee ID
		Iterator i = employeeList.iterator();
		Employees temp;
		
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
			//if there is a match result is -1 and the user is asked for their ID until it is valid 
			if(temp.getEmpID().equalsIgnoreCase(employeeID))
			{
				//not a unique ID
				result = "-1";
				break;
			}
		}
		return result;	
	}
	
	//when the user is registering it checks if they are giving a unique email 
	public synchronized String emailExists(String searchValue)
	{
		String result="1";//email not in use 
		Iterator i = employeeList.iterator();
		Employees temp;
		
		//item stays in the list but i get a copy of it 
		while(i.hasNext())
		{
			temp = (Employees)i.next();
			
			if(temp.getEmail().equalsIgnoreCase(searchValue))
			{
				result = "-1";//email in use 
				break;
			}
		}
		return result;
		
	}

	//FOR THE REPORTS 
	public synchronized void addReport(String type, int reportID, String date, int reportEmployeeID, String status, int assignedEmployeeID)
	{	
		//when a report is created its put into the list of reports 
		Reports temp = new Reports(type,reportID,date,reportEmployeeID,status,assignedEmployeeID);
		
		reportList.add(temp);
		
		writeToReportsFile();
	}
	
	//when the user tries to assign a file to an employee
	public synchronized String reportIDExists(String searchReport, String searchID) 
	{
        // Default to invalid assignment
        String validAssignment = "-1";
        boolean reportExists = false;
        boolean employeeExists = false;
        Iterator i = reportList.iterator();
        Iterator j = employeeList.iterator();

		Reports temp;
		Employees temp2;
        
        //it checks if the report id exists 
        while(i.hasNext())
        {
			temp = (Reports)i.next();
            if(temp.getReportID().equals(searchReport)) 
            {
                reportExists = true;
                break;
            }
        }
        
        //it then checks if the employee exists 
        while(j.hasNext()) 
        {
			temp2 = (Employees)j.next();
        	if(temp2.getEmpID().equals(searchID)) 
        	{
                employeeExists = true;
                break;
            }
        }
        
        //if they both exist
        if(reportExists && employeeExists) 
        {
            validAssignment = "1";

            //it loops through all the reports until it finds one with a matching ID
            for (Reports temp3 : reportList)
            {
                if (temp3.getReportID().equals(searchReport))
                {
                	//when it finds it it changes the status to assigned and also changes the assigned ID
                    temp3.setAssignedID(Integer.parseInt(searchID));
                    temp3.setStatus("Assigned");
                    break;
                }
            }
            
            //writing to the file since there was an update 
            writeToReportsFile();
        } 
        
        return validAssignment;
    }
	
	//assigns a random number between (1 - 1000) to the report ID 
	public synchronized int reportIDGenerator(int reportID) 
	{
		 
		Random rand = new Random(); 
		int randomNum; 
	    String valid; 
	    
	    //looping until it gets a number with a unique report ID
	    do 
	    { 
	    	randomNum= rand.nextInt(1000) + 1; 
	        String num = Integer.toString(randomNum);     
	        valid = reportExists(num); 

	     }while(valid.equalsIgnoreCase("-1")); 

	    return randomNum; 

	} 

	//method checks if the reportID already exists 
	public synchronized String reportExists(String reportID)
	{ 
	        
		// Default to invalid assignment 
	    String valid = "1"; 
        boolean reportExists = false; 
        Iterator i = reportList.iterator(); 

	    Reports temp; 

        //it checks if the report id exists  
	    while(i.hasNext()) 
	    { 
	    	temp = (Reports)i.next(); 
	    
	        if(temp.getReportID().equals(reportID))  
	        { 
	        	valid = "-1"; 
	            break; 
	        } 
	     } 
	    
        return valid; 
	    
	}
	//getting the number of reports in the reports list
//	public synchronized int getLength()
//	{
//		return reportList.size();
//	}
//	
//	//retrieves all of the accident reports 
//	public synchronized String getAccidentReports(int location)
//	{
//		Reports temp = reportList.get(location);
//		
//		return temp.toString();
//	}
	
	//outputting reports of the users chosen ID
	public synchronized String yourReports(String ID)
	{
		String result="1";//no reports  
		Iterator i = reportList.iterator();
		Reports temp;
		
		//loops through all the reports  
		while(i.hasNext())
		{
			temp = (Reports)i.next();
			
			if(temp.getAssignedID().equalsIgnoreCase(ID))
			{
				result += temp.toString() + "\n";				
			}
		}
		return result;
		
	}
	
	//outputting all the accident report 
	public synchronized String AccidentReports(String type)
	{
		String result="1";//no reports  
		Iterator i = reportList.iterator();
		Reports temp;
		
		//loops through all the reports  
		while(i.hasNext())
		{
			temp = (Reports)i.next();
			
			if(temp.getType().equalsIgnoreCase(type))
			{
				result += temp.toString() + "\n";				
			}
		}
		return result;
		
	}
	
}