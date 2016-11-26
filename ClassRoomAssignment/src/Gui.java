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

	// Holds all course by room type
	static ArrayList<Course> scienceCourseList;
	static ArrayList<Course> computerCourseList;
	static ArrayList<Course> regularCourseList;

	// Holds all classrooms by room type
	static ArrayList<ClassRoom> scienceClassRoomList;
	static ArrayList<ClassRoom> computerClassRoomList;
	static ArrayList<ClassRoom> regularClassRoomList;

	// unassigned courses
	static ArrayList<Course> unassignedScienceCourseList;
	static ArrayList<Course> unassignedComputerCourseList;
	static ArrayList<Course> unassignedRegularCourseList;

	private JPanel contentPane;
	private JTextField classRoomFileSelectedTextField;
	private JTextField courseFileSelectedTextField;

	// used to determine if we have our data before calculating our schedule
	private boolean courseData = false;
	private boolean classData = false;

	private boolean scienceAllScheduled = false;
	private boolean computerAllScheduled = false;
	private boolean regularAllScheduled = false;

	// booleans for which reassignment check we have already performed
	private boolean checkedDifferentComputerCampus = false;
	private boolean checkedOccupiedComputerRoom = false;

	// integers used to stop the loop, in case course can not be assigned
	private int runAwayScienceReassignment = 0;
	private int runAwayComputerReassignment = 0;
	private int runAwayRegularReassignment = 0;

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

		unassignedScienceCourseList = new ArrayList<Course>();
		unassignedComputerCourseList = new ArrayList<Course>();
		unassignedRegularCourseList = new ArrayList<Course>();

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
					// How many computer classes are unassigned? Print them!
					checkUnassignedComputer();

					findScienceSchedule();
					// How many science classes are unassigned? Print them!
					checkUnassignedScience();

					findRegularSchedule();
					// How many regular classes are unassigned? Print them!
					checkUnassignedRegular();

					// printUnassignedCourse();

					// print the schedule
					printSchedule();

					// Close print writer
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
	
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////// Data Parsing Methods/////////////////////////
	///////////////////////////////////////////////////////////////////////
	
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
						//temp array to hold the split strings
						String[] tmpArray = line.split("\\s+");

						// checks to see if this is a properly formatted
						// classroom file
						if (tmpArray[0].toString().length() > 3) {
							System.out.println("Invalid File Type!!!!");
							System.out.println("Please choose a properly formatted classroom file!");

							return;

						}
						String location = tmpArray[0];
						String roomNumber = tmpArray[1];
						int capacity = Integer.parseInt(tmpArray[2]);

						String roomType = tmpArray[3];
						
						//defualt the booleans
						boolean science = false;
						boolean computer = false;
						boolean regular = false;

						// create obj's according to class room type
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

			// we have our class data, set the boolean, used for GUI input fail
			// safe!
			classData = true;

			// Print out the data as we have it now, Should be all available
			// classrooms with empty week schedules
			parseClassRoomDataPrint();

		} catch (IOException io) {
			System.out.println("Invalid File Type!!!!");
			System.out.println("Please choose a properly formatted classroom file!");
			// io.printStackTrace();
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

						if (tmpArray[0].toString().length() < 4) {
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

						// create obj's according to class type
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
			System.out.println("Please choose a properly formatted course file!");
			// io.printStackTrace();
		}

		// If we made it here, we have both files. Sort all classroom/course
		// type arrays by class/enrollment size
		sortAll();

	}
	
	/////////////////////////////////////////////////////////////////////////////
	///////////////////////// Initial Scheduling Methods/////////////////////////
	/////////////////////////////////////////////////////////////////////////////

	public void findComputerSchedule() {
		//for all courses in list
		for (Course course : computerCourseList) {
			//look through all classrooms
			for (int i = 0; i < computerClassRoomList.size(); i++) {
				//if preferred campus = classroom campus
				if (course.getPreferredLocation().equals(computerClassRoomList.get(i).getLocation())) {
					//if enrollment of course is lessThanOrEqual to classrooms capacity
					if (course.getEnrollmentNumber() <= computerClassRoomList.get(i).getCapacity()
							&& computerClassRoomList.get(i).getAvailiabilitySchedule()[course
									.getMeetDayNum()] == null) {
						//assign the course to the classroom's weekly schedule if no class is assigned there yet
						computerClassRoomList.get(i).getAvailiabilitySchedule()[course.getMeetDayNum()] = course;
						// We found a room!!! YAY!
						course.setRoomFound(true);
						break;
					}
				}
			}
		}
		// printComputerSchedule();
	}

	public void findScienceSchedule() {
		//for all courses in list
		for (Course course : scienceCourseList) {
			//look through all classrooms
			for (int i = 0; i < scienceClassRoomList.size(); i++) {
				//if preferred campus = classroom campus
				if (course.getPreferredLocation().equals(scienceClassRoomList.get(i).getLocation())) {
					//if enrollment of course is lessThanOrEqual to classrooms capacity
					if (course.getEnrollmentNumber() <= scienceClassRoomList.get(i).getCapacity()
							&& scienceClassRoomList.get(i).getAvailiabilitySchedule()[course.getMeetDayNum()] == null) {
						//assign the course to the classroom's weekly schedule if no class is assigned there yet
						scienceClassRoomList.get(i).getAvailiabilitySchedule()[course.getMeetDayNum()] = course;
						// We found a room!!! YAY!
						course.setRoomFound(true);
						break;
					}
				}
			}
		}

		// printScienceSchedule();
	}

	public void findRegularSchedule() {
		//for all courses in list
		for (Course course : regularCourseList) {
			//look through all classrooms
			for (int i = 0; i < regularClassRoomList.size(); i++) {
				//if preferred campus = classroom campus
				if (course.getPreferredLocation().equals(regularClassRoomList.get(i).getLocation())) {
					//if enrollment of course is lessThanOrEqual to classrooms capacity
					if (course.getEnrollmentNumber() <= regularClassRoomList.get(i).getCapacity()
							&& regularClassRoomList.get(i).getAvailiabilitySchedule()[course.getMeetDayNum()] == null) {
						//assign the course to the classroom's weekly schedule if no class is assigned there yet
						regularClassRoomList.get(i).getAvailiabilitySchedule()[course.getMeetDayNum()] = course;
						// We found a room!!! YAY!
						course.setRoomFound(true);
						break;
					}
				}
			}
		}
		// printRegularSchedule();
	}



	///////////////////////////////////////////////////////////////////////////
	///////////////////////// Schedule Checking Methods/////////////////////////
	///////////////////////////////////////////////////////////////////////////

	public void checkUnassignedComputer() {
		System.out.println("\nUnassigned Computer Courses:");
		System.out.println("***********************************************");

		// clear out the unassigned array, this will be rebuilt without courses
		// that we have newly found rooms for
		try {
			unassignedComputerCourseList.clear();
		} catch (Exception e) {
			// on first run unassignedComputerCourseList has no size;
			System.out.println("Caught unassignedComputerCourseList Exception!!!");
		}

		// Unassigned Computer Class Count
		int compCount = 0;
		// Total Computer Class Count
		int compTotal = 0;

		// boolean to check if the room was found, used to increment count
		boolean roomFound = false;

		// Print all unassigned courses in the computer course list
		for (Course course : computerCourseList) {
			if (!course.isRoomFound()) {
				// Room wasn't assigned!! add it to the unassigned list for
				// reassignment
				unassignedComputerCourseList.add(course);
				// increment unassigned count
				compCount++;
				System.out.println(course.getCourseName() + " Requested location: " + course.getPreferredLocation());
			} else {
				// we found a room for this obj
				compTotal++;
			}

		}

		// print ratio of found classes
		System.out
				.println("\nFound Classrooms for " + compTotal + " out of " + computerCourseList.size() + " courses\n");

		// did we find rooms for all courses?
		if (compCount == 0) {
			// We scheduled all Computer classes to rooms
			computerAllScheduled = true;
			System.out.println("All Computer Courses Assigned!\n");
		} else {
			// We didn't find rooms for all courses
			System.out.println("Rescheduling " + compCount + " course(s)");
			resassignComputerCourse();
		}

		// FIXME delete after testing
		/*for (Course course : unassignedComputerCourseList) {
			System.out.println("Unassigned Science course: " + course.getCourseName());

		}*/
	}

	public void checkUnassignedScience() {
		// Unassigned Science Class Count
		int sciCount = 0;
		// Total Science Class Count
		int sciTotal = 0;
		// boolean to check if the room was found, used to increment count
		boolean roomFound = false;

		System.out.println("\nUnassigned Science Courses:");
		System.out.println("***********************************************");

		// Print all unassigned courses in the science course list
		for (Course course : scienceCourseList) {
			if (!course.isRoomFound()) {
				// Room wasn't assigned!!
				unassignedScienceCourseList.add(course);
				// increment unassigned count
				sciCount++;
				System.out.println(course.getCourseName() + " Requested location: " + course.getPreferredLocation());
			} else {
				roomFound = true;
			}
			if (roomFound) {
				sciTotal++;
				roomFound = false;
			}
		}
		System.out.println("\nFound Classrooms for " + sciTotal + " out of " + scienceCourseList.size() + " courses\n");
		if (sciCount == 0) {
			// We scheduled all Science classes to rooms
			scienceAllScheduled = true;
			System.out.println("All Science Courses Assigned!\n");
		} else {
			System.out.println("Rescheduling " + sciCount + " course(s)");
		}

		// FIXME delete after testing
		/*for (Course course : unassignedScienceCourseList) {
			System.out.println("Unassigned Science course: " + course.getCourseName());

		}*/
	}

	public void checkUnassignedRegular() {
		// Unassigned Regular Class Count
		int regCount = 0;
		// Total Regular Class Count
		int regTotal = 0;
		// boolean to check if the room was found, used to increment count
		boolean roomFound = false;

		System.out.println("\nUnassigned Regular Courses:");
		System.out.println("***********************************************");

		// Print all unassigned courses in the regular course list
		for (Course course : regularCourseList) {
			if (!course.isRoomFound()) {
				// Room wasn't assigned!!
				unassignedRegularCourseList.add(course);
				// increment unassigned count
				regCount++;
				System.out.println(course.getCourseName() + " Requested location: " + course.getPreferredLocation());
			} else {
				roomFound = true;
			}

			if (roomFound) {
				regTotal++;
				roomFound = false;
			}
		}
		System.out.println("\nFound Classrooms for " + regTotal + " out of " + regularCourseList.size() + " courses\n");

		if (regCount == 0) {
			// We scheduled all Regular classes to rooms
			regularAllScheduled = true;
			System.out.println("All Regular Courses Assigned!\n");
		} else {
			System.out.println("Rescheduling " + regCount + " course(s)");
		}

		// FIXME delete after testing
		/*for (Course course : unassignedRegularCourseList) {
			System.out.println("Unassigned Science course: " + course.getCourseName());

		}*/
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////// Re-assignment Methods/////////////////////////
	///////////////////////////////////////////////////////////////////////

	// FIXME works if another available room at other campus
	public void resassignComputerCourse() {

		if (!checkedDifferentComputerCampus) {
			// see if a new campus is a fix
			checkDiffComputerCampus();
		} else if (!checkedOccupiedComputerRoom) {
			//FIXME implement
			checkOccupiedCompRooms();
			return;

		} else {
			
			//FIXME, this will not be good for recursion
			// we have checked both re-assignment methods
			checkedDifferentComputerCampus = false;
			checkedOccupiedComputerRoom = false;
			return;
		}

		checkUnassignedComputer();

	}

	private void checkDiffComputerCampus() {

		for (Course course : unassignedComputerCourseList) {
			if (!course.isRoomFound()) {
				if (!course.isLowerDivision()) {
					// This is for upper division courses, they can go to any campus
					for (int i = 0; i < computerClassRoomList.size(); i++) {
						if (course.getEnrollmentNumber() <= computerClassRoomList.get(i).getCapacity()
								&& computerClassRoomList.get(i).getAvailiabilitySchedule()[course
										.getMeetDayNum()] == null) {
							computerClassRoomList.get(i).getAvailiabilitySchedule()[course.getMeetDayNum()] = course;
							// We found a room!!! YAY!
							course.setRoomFound(true);

							break;
						}
					}
				} else {
					// This is for lower division courses, they can only go to compatible campuses
					for (int i = 0; i < computerClassRoomList.size(); i++) {
						if (computerClassRoomList.get(i).isLowerDivisionCompatible()) {
							if (course.getEnrollmentNumber() <= computerClassRoomList.get(i).getCapacity()
									&& computerClassRoomList.get(i).getAvailiabilitySchedule()[course
											.getMeetDayNum()] == null) {
								computerClassRoomList.get(i).getAvailiabilitySchedule()[course
										.getMeetDayNum()] = course;
								// We found a room!!! YAY!
								course.setRoomFound(true);

								break;
							}//end else inner if
						}//end else outer if
					}//end else for loop
				}//end inner else
			}//end outer if 
		}
		// we checked if the solution is to change campuses
		checkedDifferentComputerCampus = true;
		
		System.out.println("We tried changing campuses: " + checkedDifferentComputerCampus);
	}
	
	//FIXME implement
	public void checkOccupiedCompRooms(){
		
	}

	//////////////////////////////////////////////////////////////////
	///////////////////////// Printing Methods/////////////////////////
	//////////////////////////////////////////////////////////////////

	/**
	 * Prints the finished schedule
	 */
	private void printSchedule() {
		
		writer.println("");
		writer.println("\n*********************************** \n");
		writer.println("Regular Class Rooms:");
		writer.println("\n*********************************** \n");
		
		printScheduleBuilder(regularClassRoomList);
		
		writer.println("");
		writer.println("\n*********************************** \n");
		writer.println("Science Class Rooms:");
		writer.println("\n*********************************** \n");
		
		printScheduleBuilder(scienceClassRoomList);
		
		writer.println("");
		writer.println("\n*********************************** \n");
		writer.println("Computer Class Rooms:");
		writer.println("\n*********************************** \n");
		
		printScheduleBuilder(computerClassRoomList);
		

	}

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
	public void printScheduleBuilder(ArrayList<ClassRoom> classrooms) {
		
		for (int i = 0; i < classrooms.size(); i++) {
			writer.println("		Room " + i);
			writer.println("Name is: " + classrooms.get(i).getRoomNumber());
			writer.println("Capacity: " + classrooms.get(i).getCapacity());
			writer.println("Location: " + classrooms.get(i).getLocation());
			writer.println("Weekly Schedule:");
			for (int j = 0; j < classrooms.get(i).getAvailiabilitySchedule().length; j++) {

				String day = dayOfTheWeek(j);

				try {
					writer.println("  " + day + ": "
							+ classrooms.get(i).getAvailiabilitySchedule()[j].getCourseName() + " Enrolled: "
							+ classrooms.get(i).getAvailiabilitySchedule()[j].getEnrollmentNumber()
							+ ", Req Location: "
							+ classrooms.get(i).getAvailiabilitySchedule()[j].getPreferredLocation());

				} catch (Exception e) {
					writer.println("  " + day + ": Room Available");
				}

			}
			writer.println("\n------------------------------------- \n");
		}
	}
	
	
	/**
	 * Used to convert array index integer to day of the week String, used for
	 * printing
	 * 
	 * @param dayNum
	 * @return day
	 */
	public static String dayOfTheWeek(int dayNum) {
		String day = "";

		switch (dayNum) {
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
	/////////////////////////////////////////////////////////////////
	///////////////////////// Sorting Methods/////////////////////////
	/////////////////////////////////////////////////////////////////

	/**
	 * Call the Sort Methods
	 */
	private void sortAll() {
		
		sortRoomList(computerClassRoomList);
		sortRoomList(scienceClassRoomList);
		sortRoomList(regularClassRoomList);
		
		
		sortClassList(computerCourseList);
		sortClassList(scienceCourseList);
		sortClassList(regularCourseList);
		

	}
	
	/**
	 * Used to sort the class room array lists by lowest capacity to highest
	 * @param classrooms
	 */
	public static void sortRoomList(ArrayList <ClassRoom> classrooms) {

		//System.out.println("Sorting the Computer Room objects by class size.......");
		Collections.sort(classrooms, new Comparator<ClassRoom>() {

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
	 * Used to sort the Course type obj's by enrollment, lowest to
	 * highest
	 */
	public static void sortClassList(ArrayList <Course> courses) {

		//System.out.println("Sorting the Computer Course objects by Enrollment size.......");
		Collections.sort(courses, new Comparator<Course>() {

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
	///////////////////////// Getters and Setters/////////////////////////
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
