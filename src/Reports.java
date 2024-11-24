
public class Reports {

	//attributes that make up the book
	private String type, date, status;
	private int reportID, employeeID,assignedEmployeeID;
	
	
	//when you make a book to add to the list call a constructor
	public Reports(String n, int a, String i, int p, String r, int ro)
	{
		type = n;
		reportID = a;
		date = i;
		employeeID = p;
		status = r;
		assignedEmployeeID = ro;
	}
	

	public String toString()
	{
		//find the employee
		String temp = type+"@"+reportID+"@"+date+"@"+employeeID+"@"+status+"@"+assignedEmployeeID;
		return temp;
	}
	
}
