
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
		//printing employee details
		String temp = "Report Type: " + type+ "\n" +
                  "Report ID: " + reportID+ "\n" +
                  "Date: " + date+ "\n" +
                  "Employee ID of Report Creator: " + employeeID+ "\n" +
                  "Status: " + status + "\n" +
                  "Assigned Employee ID: " + assignedEmployeeID + "\n";
		return temp;
	}
	
	public String getReportID() {
		//casting employeeID to an integer
		String report = Integer.toString(reportID);				
		return report;
	}
	
	public String getAssignedID() {
		String employeeID = Integer.toString(assignedEmployeeID);				
		return employeeID;
	}
	
	public void setAssignedID(int id) {
		this.assignedEmployeeID = id;
	}
	
	public void setStatus(String updatedStatus) {
		this.status = updatedStatus;
	}
}
