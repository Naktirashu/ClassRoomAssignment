
public class Course {
	
	//name of course
	private String courseName;
	//night course is held
	private String requestedEvening;
	//number of students in course
	private int enrollmentNumber;
	
	//Room Type string used for printing
	private String roomType;
	
	//booleans used to determine which array to move into
	private boolean science = false;
	private boolean computer = false;
	private boolean regular = false;
	
	//does this course require a lowerDivision compatible room?
	private boolean lowerDivision = false;
	
	//day of the week number, used for assigning into week schedule array
	private int meetDayNum;
	//day of the week string, used for printing
	private String meetDayString;
	//this location is used first, unless there is a conflict and it must move
	private String preferredLocation;
	//used to determined if we have found a location for this course
	private boolean roomFound = false;
	//used to check if we check a different campus during conflicts 
	private boolean checkedDifferentCampus = false;
	//used for future use, unimplemented method
	private boolean checkedOccupiedRoom = false;

	
	public Course(String courseName, String requestedEvening, int enrollmentNumber, String roomType, boolean science, boolean computer,
			boolean regular, String preferredLocation, boolean lowerDivision) {
	
		this.courseName = courseName;
		this.requestedEvening = requestedEvening;
		this.enrollmentNumber = enrollmentNumber;
		this.roomType = roomType;
		
		
		this.science = science;
		this.computer = computer;
		this.regular = regular;
		this.preferredLocation = preferredLocation;
		
		//Does this class need to meet at a lower division compatible location?
		this.lowerDivision = lowerDivision;
		
		
		//Sets the week name and number, number used for schedule assignment, name used for printing
		switch (requestedEvening){
		case "M":
			this.meetDayNum = 0;
			this.meetDayString = "Monday";
			break;
		case "T":
			this.meetDayNum = 1;
			this.meetDayString = "Tuesday";
			break;
		case "W":
			this.meetDayNum = 2;
			this.meetDayString = "Wednesday";
			break;
		case "H":
			this.meetDayNum = 3;
			this.meetDayString = "Thursday";
			break;
		}
	}
	
	
	
	
	
	
	
	
/////////////////////////////////////////////////////////////////////	
/////////////////////////Getters and Setters/////////////////////////
/////////////////////////////////////////////////////////////////////
	
	
	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getRequestedEvening() {
		return requestedEvening;
	}

	public void setRequestedEvening(String requestedEvening) {
		this.requestedEvening = requestedEvening;
	}

	public int getEnrollmentNumber() {
		return enrollmentNumber;
	}

	public void setEnrollmentNumber(int enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
	}

	public boolean isScience() {
		return science;
	}

	public void setScience(boolean science) {
		this.science = science;
	}

	public boolean isComputer() {
		return computer;
	}

	public void setComputer(boolean computer) {
		this.computer = computer;
	}

	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getPreferredLocation() {
		return preferredLocation;
	}

	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}

	public boolean isLowerDivision() {
		return lowerDivision;
	}

	public void setLowerDivision(boolean lowerDivision) {
		this.lowerDivision = lowerDivision;
	}

	public int getMeetDayNum() {
		return meetDayNum;
	}

	public void setMeetDayNum(int meetDayNum) {
		this.meetDayNum = meetDayNum;
	}

	public String getMeetDayString() {
		return meetDayString;
	}

	public void setMeetDayString(String meetDayString) {
		this.meetDayString = meetDayString;
	}

	public boolean isRoomFound() {
		return roomFound;
	}








	public void setRoomFound(boolean roomFound) {
		this.roomFound = roomFound;
	}








	public boolean isCheckedDifferentCampus() {
		return checkedDifferentCampus;
	}








	public void setCheckedDifferentCampus(boolean checkedDifferentCampus) {
		this.checkedDifferentCampus = checkedDifferentCampus;
	}








	public boolean isCheckedOccupiedRoom() {
		return checkedOccupiedRoom;
	}








	public void setCheckedOccupiedRoom(boolean checkedOccupiedRoom) {
		this.checkedOccupiedRoom = checkedOccupiedRoom;
	}
	
	
	

}
