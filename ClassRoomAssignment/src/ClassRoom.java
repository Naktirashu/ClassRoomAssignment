

public class ClassRoom {

	private String location;
	private String roomNumber;
	private int capacity;
	private boolean science = false;
	private boolean computer = false;
	private boolean regular = false;
	
	private String roomType;
	
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
		
		availiabilitySchedule = new Course[4];
		
	}

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


	
}
