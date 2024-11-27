
public class Reports {

	//attributes that make up the reports 
	private String type, date, status;
	private int reportID, employeeID,assignedEmployeeID;
	
	//when a new report is created it adds to the list and calls the employee constructor 
	public Reports(String n, int a, String i, int p, String r, int ro)
	{
		type = n;
		reportID = a;
		date = i;
		employeeID = p;
		status = r;
		assignedEmployeeID = ro;
	}
	
	//outputting efficiently to file 
	public String toStringFile()
	{
		//printing employee details
		String temp = type+ "#"+reportID+ "#"+date+ "#" +employeeID+ "#"+status + "#"+ assignedEmployeeID;
		return temp;
	}

	//displaying nicely to the screen
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
	
	//getting the report id to ensure its valid when assigning a report 
	public String getReportID() {
		//casting report id to a sting to allow for an easier comparison 
		String report = Integer.toString(reportID);				
		return report;
	}
	
	public String getAssignedID() {
		String employeeID = Integer.toString(assignedEmployeeID);				
		return employeeID;
	}
	
	//making the inputed assigned id the new value 
	public void setAssignedID(int id) {
		this.assignedEmployeeID = id;
	}
	
	//making the inputed status the new status 
	public void setStatus(String updatedStatus) {
		this.status = updatedStatus;
	}
}
