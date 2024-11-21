
public class Book {

	//attributes that make up the book
	private String name, email, password, departmentName, role;
	private int employeeID;
	
	
	//when you make a book to add to the list call a constructor
	public Book(String n, int a, String i, String p, String r, String ro)
	{
		name = n;
		employeeID = a;
		email = i;
		password = p;
		departmentName = r;
		role = ro;
	}
	
	//to allow the user to log into the system
	public String getName()
	{
		return name;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	//ensuring the user has a valid email and employeeID
	public String getEmpID() {
		//casting employeeID to an integer
		String EmpID = Integer.toString(employeeID);				
		return EmpID;
	}
	public String getEmail() {
		return email;
	}
	
	public String toString()
	{
		//find the employee
		String temp = name+"@"+employeeID+"@"+email+"@"+password+"@"+departmentName+"@"+role;
		return temp;
	}
	
}
