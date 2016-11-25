

public class ClassRoom {
	
	//Campus where classroom is located
	private String location;
	//room number
	private String roomNumber;
	//amount of students classroom can hold
	private int capacity;
	
	//type of class room
	private boolean science = false;
	private boolean computer = false;
	private boolean regular = false;
	
	//type of classroom, String used for printing
	private String roomType;
	
	//can lower division courses meet here?
	private boolean lowerDivisionCompatible = false;
	
	//weekly schedule
	private Course[] availiabilitySchedule;
	
	

	public ClassRoom(String location, String roomNumber, int capacity, String roomType, boolean science, boolean computer,
			boolean regular) {
		
		this.location = location;
		this.roomNumber = roomNumber;
		this.capacity = capacity;
		this.roomType = roomType;
		this.science = science;
		this.computer = computer;
		this.regular = regular;
		
		//Create the empty week schedule
		availiabilitySchedule = new Course[4];
		
		//check to see if its a lower Division compatible room
		switch(location){
		case "MDW":
		case "STP":
			lowerDivisionCompatible = true;
			break;
		default:
		}	
	}
	
/////////////////////////////////////////////////////////////////////	
/////////////////////////Getters and Setters/////////////////////////
/////////////////////////////////////////////////////////////////////	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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

	public Course[] getAvailiabilitySchedule() {
		return availiabilitySchedule;
	}

	public void setAvailiabilitySchedule(Course[] availiabilitySchedule) {
		this.availiabilitySchedule = availiabilitySchedule;
	}

	public boolean isLowerDivisionCompatible() {
		return lowerDivisionCompatible;
	}

	public void setLowerDivisionCompatible(boolean lowerDivisionCompatible) {
		this.lowerDivisionCompatible = lowerDivisionCompatible;
	}


	
}
