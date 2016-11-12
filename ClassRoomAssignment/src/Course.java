
public class Course {
	
	private String courseName;
	private String requestedEvening;
	private int enrollmentNumber;
	
	private boolean science = false;
	private boolean computer = false;
	private boolean regular = false;
	
	private String roomType;
	
	private String preferredLocation;

	
	public Course(String courseName, String requestedEvening, int enrollmentNumber, String roomType, boolean science, boolean computer,
			boolean regular, String preferredLocation) {
		super();
		this.courseName = courseName;
		this.requestedEvening = requestedEvening;
		this.enrollmentNumber = enrollmentNumber;
		this.roomType = roomType;
		this.science = science;
		this.computer = computer;
		this.regular = regular;
		this.preferredLocation = preferredLocation;
	}
	
	
	
	
	
	
	
	

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
	
	
	

}
