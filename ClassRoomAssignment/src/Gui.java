import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Stack;
import java.awt.event.ActionEvent;

public class Gui extends JFrame {

	// store the file selected from GUI
	private static File selectedClassRoomFile;

	// store the file selected from GUI
	private static File selectedCourseFile;

	// using printWriter for formatted data
	PrintWriter writer = null;

	ClassRoom classroom;
	Course course;

	// Holds all course
	// static ArrayList<Course> courseList;
	static ArrayList<Course> scienceCourseList;
	static ArrayList<Course> computerCourseList;
	static ArrayList<Course> regularCourseList;

	// static ArrayList<ClassRoom> classRoomList;

	static ArrayList<ClassRoom> scienceClassRoomList;
	static ArrayList<ClassRoom> computerClassRoomList;
	static ArrayList<ClassRoom> regularClassRoomList;

	private JPanel contentPane;
	private JTextField classRoomFileSelectedTextField;
	private JTextField courseFileSelectedTextField;

	// used to determine if we have our data before calculating our schedule
	private boolean courseData = false;
	private boolean classData = false;
	
	private boolean scienceAllScheduled = false;
	private boolean computerAllScheduled = false;
	private boolean regularAllScheduled = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Initialize the arrays
		regularCourseList = new ArrayList<Course>();
		computerCourseList = new ArrayList<Course>();
		scienceCourseList = new ArrayList<Course>();

		scienceClassRoomList = new ArrayList<ClassRoom>();
		computerClassRoomList = new ArrayList<ClassRoom>();
		regularClassRoomList = new ArrayList<ClassRoom>();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setTitle("Jeff Beaupre Program 3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 258);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSelectFile = new JButton("Select ClassRoom File");
		btnSelectFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					setSelectedClassRoomFile(fileChooser.getSelectedFile());
					classRoomFileSelectedTextField.setText(selectedClassRoomFile.getName());

					parseClassRoomData(selectedClassRoomFile);
				}
			}

		});
		btnSelectFile.setBounds(10, 11, 216, 38);
		contentPane.add(btnSelectFile);

		classRoomFileSelectedTextField = new JTextField();
		classRoomFileSelectedTextField.setEditable(false);
		classRoomFileSelectedTextField.setBounds(243, 29, 237, 20);
		contentPane.add(classRoomFileSelectedTextField);
		classRoomFileSelectedTextField.setColumns(10);

		JLabel lblFileSelected = new JLabel("Class Room File Selected: ");
		lblFileSelected.setBounds(244, 11, 150, 14);
		contentPane.add(lblFileSelected);

		JLabel lblCourseListFile = new JLabel("Course List File Selected:");
		lblCourseListFile.setBounds(243, 74, 151, 14);
		contentPane.add(lblCourseListFile);

		courseFileSelectedTextField = new JTextField();
		courseFileSelectedTextField.setEditable(false);
		courseFileSelectedTextField.setBounds(243, 92, 237, 20);
		contentPane.add(courseFileSelectedTextField);
		courseFileSelectedTextField.setColumns(10);

		JButton btnSelectCourseFile = new JButton("Select Course File");
		btnSelectCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					setSelectedCourseFile(fileChooser.getSelectedFile());
					courseFileSelectedTextField.setText(selectedCourseFile.getName());

					parseCourseData(selectedCourseFile);
				}
			}
		});
		btnSelectCourseFile.setBounds(10, 74, 216, 38);
		contentPane.add(btnSelectCourseFile);

		JButton btnMakeSchedule = new JButton("Make Schedule");
		btnMakeSchedule.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				try {
					writer = new PrintWriter("Schedule.txt");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				if (courseData && classData) {
					System.out.println("Calculating Schedule!!!");
					// FIXME Add calculation logic here
					
					findComputerSchedule();
					//How many computer classes are unassigned? Print them!
					checkUnassignedComputer();
					
					findScienceSchedule();
					//How many science classes are unassigned? Print them!
					checkUnassignedScience();
					
					findRegularSchedule();
					//How many regular classes are unassigned? Print them!
					checkUnassignedRegular();
					
					
					//printUnassignedCourse();
					
					//Close print writer
					writer.close();
					

				} else {
					if (!classData) {
						System.out.println("Please Select Class Room Data File!!!");
					} else if (!courseData) {
						System.out.println("Please Select Course Data File!!!");
					}
				}

			}
		});
		btnMakeSchedule.setBounds(10, 138, 150, 55);
		contentPane.add(btnMakeSchedule);
	}

	private void parseClassRoomData(File selectedFile) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile)));

			System.out.println("***************************");
			System.out.println("* Parsing Class Room Data *");
			System.out.println("***************************");

			for (int i = 0; i < selectedFile.length(); i++) {
				String line = input.readLine();

				if (line != null) {
				
					if (line.length() == 0) {
						// this is the blank line, do nothing!
					}

					// this is for the creation of new classroom obj's
					else {
						String[] tmpArray = line.split("\\s+");

						//checks to see if this is a properly formatted classroom file
						if(tmpArray[0].toString().length() > 3){
							System.out.println("Invalid File Type!!!!");
							System.out.println("Please choose a properly formatted classroom file!");
							
							return;
							
						}
						String location = tmpArray[0];
						String roomNumber = tmpArray[1];
						int capacity = Integer.parseInt(tmpArray[2]);

						String roomType = tmpArray[3];

						boolean science = false;
						boolean computer = false;
						boolean regular = false;

						//create obj's according to class room type
						switch (tmpArray[3]) {

						case "R":
							regular = true;
							regularClassRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science,
									computer, regular));
							break;
						case "C":
							computer = true;
							computerClassRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science,
									computer, regular));
							break;
						case "S":
							science = true;
							scienceClassRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science,
									computer, regular));
							break;
						}
					} // end else
				} // end outside if (line != null)
			} // end selectedFile for loop

			// we have our class data, set the boolean, used for GUI input fail safe!
			classData = true;

			//Print out the data as we have it now, Should be all available classrooms with empty week schedules
			parseClassRoomDataPrint();

		} catch (IOException io) {
			System.out.println("Invalid File Type!!!!");
			System.out.println("Please choose a classroom file!");
			//io.printStackTrace();
		}

	}

	private void parseCourseData(File selectedFile) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile)));

			System.out.println("***********************");
			System.out.println("* Parsing Course Data *");
			System.out.println("***********************");

			for (int i = 0; i < selectedFile.length(); i++) {
				String line = input.readLine();

				if (line != null) {
					
					if (line.length() == 0) {
						// this is the blank line, do nothing!
					}

					// this is for the creation of new Course obj's
					else {
						String[] tmpArray = line.split("\\s+");

						if(tmpArray[0].toString().length() < 4){
							System.out.println("Invalid File Type!!!!");
							System.out.println("Please choose a properly formatted course file!");
							
							return;
							
						}
						String name = tmpArray[0];
						String day = tmpArray[1];
						int enrolled = Integer.parseInt(tmpArray[2]);

						String roomType = tmpArray[3];

						boolean science = false;
						boolean computer = false;
						boolean regular = false;
						boolean lowerDivision = false;

						String location = tmpArray[4];

						String[] tmpDivisonArray = tmpArray[0].split("");

						int tmpDivision = Integer.parseInt(tmpDivisonArray[3]);

						if ((tmpDivision == 1) || (tmpDivision == 2)) {
							lowerDivision = true;
						}

						//create obj's according to class type
						switch (tmpArray[3]) {

						case "R":
							regular = true;
							regularCourseList.add(new Course(name, day, enrolled, roomType, science, computer, regular,
									location, lowerDivision));
							break;
						case "C":
							computer = true;
							computerCourseList.add(new Course(name, day, enrolled, roomType, science, computer, regular,
									location, lowerDivision));
							break;
						case "S":
							science = true;
							scienceCourseList.add(new Course(name, day, enrolled, roomType, science, computer, regular,
									location, lowerDivision));
							break;
						}
					} // end else
				} // end outside if (line != null)
			} // end selectedFile for loop

			// we have our data, set the boolean
			courseData = true;

			parseCourseDataPrint();

		} catch (IOException io) {
			System.out.println("Invalid File Type!!!!");
			System.out.println("Please choose a course file!");
			//io.printStackTrace();
		}
		
		//If we made it here, we have both files. Sort all classroom/course type arrays by class/enrollment size
		sortAll();
			
	}
	
	
	public void findComputerSchedule(){
		//FIXME Prelim calc, change for all kinds of limitations, need to sort by class size too , to limit conflicts
		for (Course course : computerCourseList) {
			for (int i = 0; i < computerClassRoomList.size(); i++) {
				if (course.getPreferredLocation().equals(computerClassRoomList.get(i).getLocation())) {
					if (course.getEnrollmentNumber() <= computerClassRoomList.get(i).getCapacity()
							&& computerClassRoomList.get(i).getAvailiabilitySchedule()[course
									.getMeetDayNum()] == null) {
						computerClassRoomList.get(i).getAvailiabilitySchedule()[course
								.getMeetDayNum()] = course;
						//We found a room!!! YAY!
						course.setRoomFound(true);
						break;
					}
				}
			}
		}
		printComputerSchedule();
	}
	
	public void findScienceSchedule(){
		//FIXME Prelim calc, change for all kinds of limitations, need to sort by class size too , to limit conflicts
		for (Course course : scienceCourseList) {
			for (int i = 0; i < scienceClassRoomList.size(); i++) {
				if (course.getPreferredLocation().equals(scienceClassRoomList.get(i).getLocation())) {
					if (course.getEnrollmentNumber() <= scienceClassRoomList.get(i).getCapacity()
							&& scienceClassRoomList.get(i).getAvailiabilitySchedule()[course
									.getMeetDayNum()] == null) {
						scienceClassRoomList.get(i).getAvailiabilitySchedule()[course
								.getMeetDayNum()] = course;
						//We found a room!!! YAY!
						course.setRoomFound(true);
						break;
					}
				}
			}
		}
		
		printScienceSchedule();
	}
	
	public void findRegularSchedule(){
		//FIXME Prelim calc, change for all kinds of limitations, need to sort by class size too , to limit conflicts
		for (Course course : regularCourseList) {
			for (int i = 0; i < regularClassRoomList.size(); i++) {
				if (course.getPreferredLocation().equals(regularClassRoomList.get(i).getLocation())) {
					if (course.getEnrollmentNumber() <= regularClassRoomList.get(i).getCapacity()
							&& regularClassRoomList.get(i).getAvailiabilitySchedule()[course
									.getMeetDayNum()] == null) {
						regularClassRoomList.get(i).getAvailiabilitySchedule()[course
								.getMeetDayNum()] = course;
						//We found a room!!! YAY!
						course.setRoomFound(true);
						break;
					}
				}
			}
		}
		printRegularSchedule();
	}
	
	
	/**
	 * Used to convert array index integer to day of the week String, used for printing
	 * @param dayNum
	 * @return day
	 */
	public static String dayOfTheWeek(int dayNum){
		String day = "";
		
		switch(dayNum){
		case 0:
			day = "Monday    ";
			break;
		case 1:
			day = "Tuesday   ";
			break;
		case 2:
			day = "Wednesday ";
			break;
		case 3:
			day = "Thursday  ";
			break;
		}
		
		return day;
	}
	
	
//////////////////////////////////////////////////////////////////	
/////////////////////////Printing Methods/////////////////////////
//////////////////////////////////////////////////////////////////
	
	/**
	 * Prints the Parsed Classroom Data to Console
	 */
	private void parseClassRoomDataPrint() {
		System.out.println("Regular Class Rooms:\n");
		for (int i = 0; i < regularClassRoomList.size(); i++) {
			System.out.println("	Class Room " + i + " Name is: " + regularClassRoomList.get(i).getRoomNumber());
			System.out.println("Weekly Schedule:");
			for (int j = 0; j < regularClassRoomList.get(i).getAvailiabilitySchedule().length; j++) {
				System.out.println("Day " + j + "= " + regularClassRoomList.get(i).getAvailiabilitySchedule()[j]);

			}
		}
		System.out.println("\n*********************************** \n");
		System.out.println("Computer Class Rooms:\n");
		for (int i = 0; i < computerClassRoomList.size(); i++) {
			System.out.println("	Class Room " + i + " Name is: " + computerClassRoomList.get(i).getRoomNumber());
			System.out.println("Weekly Schedule:");
			for (int j = 0; j < computerClassRoomList.get(i).getAvailiabilitySchedule().length; j++) {
				System.out.println("Day " + j + "= " + computerClassRoomList.get(i).getAvailiabilitySchedule()[j]);

			}
		}
		System.out.println("\n*********************************** \n");
		System.out.println("Science Class Rooms:\n");
		for (int i = 0; i < scienceClassRoomList.size(); i++) {
			System.out.println("	Class Room " + i + " Name is: " + scienceClassRoomList.get(i).getRoomNumber());
			System.out.println("Weekly Schedule:");
			for (int j = 0; j < scienceClassRoomList.get(i).getAvailiabilitySchedule().length; j++) {
				System.out.println("Day " + j + "= " + scienceClassRoomList.get(i).getAvailiabilitySchedule()[j]);

			}
		}
		System.out.println("\n*********************************** \n");
		
	}
	
	/**
	 * Prints the Parsed Course Data to Console
	 */
	private void parseCourseDataPrint() {
		for (int i = 0; i < regularCourseList.size(); i++) {
			System.out.println(" Regular Course " + i + " Name is: " + regularCourseList.get(i).getCourseName());
			System.out.println("-Meets on " + regularCourseList.get(i).getRequestedEvening() + ", "
					+ regularCourseList.get(i).getMeetDayString());
			System.out.println("-Needs a " + regularCourseList.get(i).getRoomType() + " room");
			System.out.println("Lower Division: " + regularCourseList.get(i).isLowerDivision() + "\n");
		}

		System.out.println("\n*********************************** \n");

		for (int i = 0; i < computerCourseList.size(); i++) {
			System.out.println("Computer Course " + i + " Name is: " + computerCourseList.get(i).getCourseName());
			System.out.println("-Meets on " + computerCourseList.get(i).getRequestedEvening() + ", "
					+ computerCourseList.get(i).getMeetDayString());
			System.out.println("-Needs a " + computerCourseList.get(i).getRoomType() + " room");
			System.out.println("Lower Division: " + computerCourseList.get(i).isLowerDivision() + "\n");
		}

		System.out.println("\n*********************************** \n");

		for (int i = 0; i < scienceCourseList.size(); i++) {
			System.out.println("Science Course " + i + " Name is: " + scienceCourseList.get(i).getCourseName());
			System.out.println("-Meets on " + scienceCourseList.get(i).getRequestedEvening() + ", "
					+ scienceCourseList.get(i).getMeetDayString());
			System.out.println("-Needs a " + scienceCourseList.get(i).getRoomType() + " room");
			System.out.println("Lower Division: " + scienceCourseList.get(i).isLowerDivision() + "\n");
		}

		System.out.println("\n*********************************** \n");
		
	}
	
	/**
	 * Prints the Regular Classroom Schedule to File
	 */
	public void printRegularSchedule(){
		writer.println("");
		writer.println("\n\n*********************************** \n");
		writer.println("Regular Class Rooms:\n");
		writer.println("\n*********************************** \n");
		for (int i = 0; i < regularClassRoomList.size(); i++) {
			writer.println("		Room " + i );
			writer.println("Name is: " + regularClassRoomList.get(i).getRoomNumber());
			writer.println("Capacity: " + regularClassRoomList.get(i).getCapacity());
			writer.println("Location: " + regularClassRoomList.get(i).getLocation());
			writer.println("Weekly Schedule:");
			for (int j = 0; j < regularClassRoomList.get(i).getAvailiabilitySchedule().length; j++) {
				
				String day = dayOfTheWeek(j);
			
				try {
					writer.println("  " + day + ": "
							+ regularClassRoomList.get(i).getAvailiabilitySchedule()[j].getCourseName() + " Enrolled: "
							+ regularClassRoomList.get(i).getAvailiabilitySchedule()[j].getEnrollmentNumber() + ", Req Location: "
							+ regularClassRoomList.get(i).getAvailiabilitySchedule()[j].getPreferredLocation() );

				} catch (Exception e) {
					writer.println(
							"  " + day + ": Room Available");
				}

			}
			writer.println("\n------------------------------------- \n");
		}
	}

	/**
	 * Prints the Science Classroom Schedule to File
	 */
	public void printScienceSchedule(){
		writer.println("");
		writer.println("\n\n*********************************** \n");
		writer.println("Science Class Rooms:\n");
		writer.println("\n*********************************** \n");
		for (int i = 0; i < scienceClassRoomList.size(); i++) {
			writer.println("		Room " + i );
			writer.println("Name is: " + scienceClassRoomList.get(i).getRoomNumber());
			writer.println("Capacity: " + scienceClassRoomList.get(i).getCapacity());
			writer.println("Location: " + scienceClassRoomList.get(i).getLocation());
			writer.println("Weekly Schedule:");
			for (int j = 0; j < scienceClassRoomList.get(i).getAvailiabilitySchedule().length; j++) {
				String day = dayOfTheWeek(j);
				try {
					writer.println("  " + day + ": "
							+ scienceClassRoomList.get(i).getAvailiabilitySchedule()[j].getCourseName() + " Enrolled: "
							+ scienceClassRoomList.get(i).getAvailiabilitySchedule()[j].getEnrollmentNumber()
							+ ", Req Location: " + scienceClassRoomList.get(i).getAvailiabilitySchedule()[j].getPreferredLocation());

				} catch (Exception e) {
					writer.println(
							"  " + day + ": Room Available");
				}

			}
			writer.println("\n------------------------------------- \n");
		}
	}
	
	/**
	 * Prints the Computer Classroom Schedule to File
	 */
	public void printComputerSchedule(){
		writer.println("");
		writer.println("\n\n*********************************** \n");
		writer.println("Computer Class Rooms:\n");
		writer.println("\n*********************************** \n");
		for (int i = 0; i < computerClassRoomList.size(); i++) {
			writer.println("		Room " + i );
					writer.println("Name is: " + computerClassRoomList.get(i).getRoomNumber());
					writer.println("Capacity: " + computerClassRoomList.get(i).getCapacity());
					writer.println("Location: " + computerClassRoomList.get(i).getLocation());
			writer.println("Weekly Schedule:");
			for (int j = 0; j < computerClassRoomList.get(i).getAvailiabilitySchedule().length; j++) {
				
				String day = dayOfTheWeek(j);
				
				try {
					writer.println("  " + day + ": "
							+ computerClassRoomList.get(i).getAvailiabilitySchedule()[j].getCourseName() + " Enrolled: "
							+ computerClassRoomList.get(i).getAvailiabilitySchedule()[j].getEnrollmentNumber() + ", Req Location: "
							+ computerClassRoomList.get(i).getAvailiabilitySchedule()[j].getPreferredLocation());

				} catch (Exception e) {
					writer.println(
							"  " + day + ": Room Available");
				}

			}
			writer.println("\n------------------------------------- \n");
		}
	}
	
	
	public void checkUnassignedComputer(){
		System.out.println("\nUnassigned Computer Courses:");
		System.out.println("***********************************************");
		
		//Unassigned Computer Class Count
		int compCount = 0;
		//Total Computer Class Count
		int compTotal = 0;
		
		//boolean to check if the room was found, used to increment count
		boolean roomFound = false;
		
		//Print all unassigned courses in the computer course list
		for (Course course : computerCourseList) {
			if(!course.isRoomFound()){
				//Room wasn't assigned!!
				
				//increment unassigned count
				compCount ++;
				System.out.println(course.getCourseName() + " Requested location: " + course.getPreferredLocation());		
			}else{
				roomFound = true;
			}
			if(roomFound){
				compTotal ++;
				roomFound = false;			
			}
		}
		System.out.println("\nFound Classrooms for " + compTotal + " out of " + computerCourseList.size() + " courses\n");
		
		if(compCount == 0){
			//We scheduled all Computer classes to rooms
			computerAllScheduled = true;
			System.out.println("All Computer Courses Assigned!\n");
		}
	}
	
	public void checkUnassignedScience(){
		//Unassigned Science Class Count
		int sciCount = 0;
		//Total Science Class Count
		int sciTotal = 0;
		//boolean to check if the room was found, used to increment count
		boolean roomFound = false;
		
		System.out.println("\nUnassigned Science Courses:");
		System.out.println("***********************************************");
		
		//Print all unassigned courses in the science course list
		for (Course course : scienceCourseList) {
			if(!course.isRoomFound()){
				//Room wasn't assigned!!
				
				//increment unassigned count
				sciCount ++;
				System.out.println(course.getCourseName() + " Requested location: " + course.getPreferredLocation());
			}else{
				roomFound = true;
			}
			if(roomFound){
				sciTotal ++;
				roomFound = false;	
			}
		}
		System.out.println("\nFound Classrooms for " + sciTotal + " out of " + scienceCourseList.size() + " courses\n");
		if(sciCount == 0){
			//We scheduled all Science classes to rooms
			scienceAllScheduled = true;
			System.out.println("All Science Courses Assigned!\n");
		}
	}
	
	public void checkUnassignedRegular(){
		//Unassigned Regular Class Count
		int regCount = 0;
		//Total Regular Class Count
		int regTotal = 0; 
		//boolean to check if the room was found, used to increment count
		boolean roomFound = false;
		
		System.out.println("\nUnassigned Regular Courses:");
		System.out.println("***********************************************");
		
		//Print all unassigned courses in the regular course list
		for (Course course : regularCourseList) {
			if(!course.isRoomFound()){
				//Room wasn't assigned!!
				
				//increment unassigned count
				regCount ++ ;
				System.out.println(course.getCourseName() + " Requested location: " + course.getPreferredLocation());
			}else{
				roomFound = true;
			}
			
			if(roomFound){
				regTotal++;	
				roomFound = false;	
			}
		}
		System.out.println("\nFound Classrooms for " + regTotal + " out of " + regularCourseList.size() + " courses\n");
		
		if(regCount == 0){
			//We scheduled all Regular classes to rooms
			regularAllScheduled = true;
			System.out.println("All Regular Courses Assigned!\n");
		}	
	}
	
	
	
/////////////////////////////////////////////////////////////////	
/////////////////////////Sorting Methods/////////////////////////
/////////////////////////////////////////////////////////////////	
	
	/**
	 * Call the Sort Methods
	 */
	private void sortAll() {
		sortComputerRoomList();
		sortScienceRoomList();
		sortRegularRoomList();
		sortComputerClassList();
		sortScienceClassList();
		sortRegularClassList();	
	}
	
	/**
	 * Used to sort the Computer Room type obj's by room size, lowest to highest
	 */
	public static void sortComputerRoomList(){
		
		System.out.println("Sorting the Computer Room objects by class size.......");
		Collections.sort(computerClassRoomList, new Comparator<ClassRoom>() {

		    @Override
		    public int compare(ClassRoom c1, ClassRoom c2) {
		        if (c1.getCapacity() < c2.getCapacity())
		            return -1;
		        else if (c1.getCapacity() > c2.getCapacity())
		            return 1;
		        return 0;
		    }

		});
	}
	
	/**
	 * Used to sort the Science Room type obj's by room size, lowest to highest
	 */
	public static void sortScienceRoomList(){
		
		System.out.println("Sorting the Science Room objects by class size .......");
		Collections.sort(scienceClassRoomList, new Comparator<ClassRoom>() {

		    @Override
		    public int compare(ClassRoom s1, ClassRoom s2) {
		        if (s1.getCapacity() < s2.getCapacity())
		            return -1;
		        else if (s1.getCapacity() > s2.getCapacity())
		            return 1;
		        return 0;
		    }

		});
	}

	/**
	 * Used to sort the Regular Room type obj's by room size, lowest to highest
	 */
	public static void sortRegularRoomList(){
		
		System.out.println("Sorting the Regular Room objects by class size .......");
		Collections.sort(regularClassRoomList, new Comparator<ClassRoom>() {

		    @Override
		    public int compare(ClassRoom r1, ClassRoom r2) {
		        if (r1.getCapacity() < r2.getCapacity())
		            return -1;
		        else if (r1.getCapacity() > r2.getCapacity())
		            return 1;
		        return 0;
		    }

		});
	}	
	
	/**
	 * Used to sort the Computer Course type obj's by enrollment, lowest to highest
	 */
	public static void sortComputerClassList(){
		
		System.out.println("Sorting the Computer Course objects by Enrollment size.......");
		Collections.sort(computerCourseList, new Comparator<Course>() {

		    @Override
		    public int compare(Course c1, Course c2) {
		        if (c1.getEnrollmentNumber() < c2.getEnrollmentNumber())
		            return -1;
		        else if (c1.getEnrollmentNumber() > c2.getEnrollmentNumber())
		            return 1;
		        return 0;
		    }

		});
	}
	
	/**
	 * Used to sort the Science Course type obj's by enrollment, lowest to highest
	 */
	public static void sortScienceClassList(){
			
		System.out.println("Sorting the Science Course objects by Enrollment size .......");
		Collections.sort(scienceCourseList, new Comparator<Course>() {

		    @Override
		    public int compare(Course c1, Course c2) {
		        if (c1.getEnrollmentNumber() < c2.getEnrollmentNumber())
		            return -1;
		        else if (c1.getEnrollmentNumber() > c2.getEnrollmentNumber())
		            return 1;
		        return 0;
		    }

		});
	}
	
	/**
	 * Used to sort the Regular Course type obj's by enrollment, lowest to highest
	 */
	public static void sortRegularClassList(){	
	
		System.out.println("Sorting the Regular Course objects by Enrollment size .......\n");
		Collections.sort(regularCourseList, new Comparator<Course>() {
		    @Override
		    public int compare(Course c1, Course c2) {
		        if (c1.getEnrollmentNumber() < c2.getEnrollmentNumber())
		            return -1;
		        else if (c1.getEnrollmentNumber() > c2.getEnrollmentNumber())
		            return 1;
		        return 0;
		    }

		});
	}	
	
/////////////////////////////////////////////////////////////////////	
/////////////////////////Getters and Setters/////////////////////////
/////////////////////////////////////////////////////////////////////

	public static File getSelectedClassRoomFile() {
		return selectedClassRoomFile;
	}

	public static void setSelectedClassRoomFile(File selectedClassRoomFile) {
		Gui.selectedClassRoomFile = selectedClassRoomFile;
	}

	public static File getSelectedCourseFile() {
		return selectedCourseFile;
	}

	public static void setSelectedCourseFile(File selectedCourseFile) {
		Gui.selectedCourseFile = selectedCourseFile;
	}
}
