
public class Course {
	
	private String courseName;
	private String requestedEvening;
	private int enrollmentNumber;
	
	private String roomType;
	private boolean science = false;
	private boolean computer = false;
	private boolean regular = false;
	
	private boolean lowerDivision = false;
	
	private int meetDayNum;
	private String meetDayString;

	private String preferredLocation;
	
	private boolean roomFound = false;

	
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
		this.lowerDivision = lowerDivision;
		
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
	
	
	
	
	
	
	
	
//GETTERS AND SETTERS
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
	
	
	

}
