
public class Employees {

	//attributes that make up the employees details 
	private String name, email, password, departmentName, role;
	private int employeeID;
	
	//when a new employee registers and adds to the list it calls the employee constructor 
	public Employees(String n, int a, String i, String p, String r, String ro)
	{
		name = n;
		employeeID = a;
		email = i;
		password = p;
		departmentName = r;
		role = ro;
	}
	
	//to allow the user to log into the system - checks the users email and password 
	public String getEmail() {
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}

	//method is called when a user wants to update their password 
	public void updatePassword(String newPassword) {
		this.password = newPassword;
	}
	
	//ensuring the user has a valid employeeID if they need to register and for assigning reports 
	public String getEmpID() {
		//casting employeeID to an integer
		String EmpID = Integer.toString(employeeID);				
		return EmpID;
	}
	
	//prints this to the file after changes are made 
	public String toString()
	{
		//find the employee
		String temp = name+"#"+employeeID+"#"+ email+"#"+password+"#"+departmentName+"#"+role;
		return temp;
	}
	
}
