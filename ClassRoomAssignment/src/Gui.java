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
	//static ArrayList<Course> courseList;
	static ArrayList<Course> scienceCourseList;
	static ArrayList<Course> computerCourseList;
	static ArrayList<Course> regularCourseList;
	

	//static ArrayList<ClassRoom> classRoomList;
	
	static ArrayList<ClassRoom> scienceClassRoomList;
	static ArrayList<ClassRoom> computerClassRoomList;
	static ArrayList<ClassRoom> regularClassRoomList;
	
	
	private JPanel contentPane;
	private JTextField classRoomFileSelectedTextField;
	private JTextField courseFileSelectedTextField;

	//used to determine if we have our data before calculating our schedule
	private boolean courseData = false;
	private boolean classData = false;
	
	
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
				if(courseData && classData){
					System.out.println("Calculating Schedule!!!");
					//FIXME Add calculation logic here
					
					
				}else{
					if(!classData){
						System.out.println("Please Select Class Room Data File!!!");
					}
					else if (!courseData){
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
					// System.out.println("line is: " + line);
					// System.out.println("Line length = " + line.length());

					if (line.length() == 0) {
						// this is the blank line, do nothing!
					}
					
					// this is for the creation of new sub nodes
					else {
						String[] tmpArray = line.split("\\s+");
						
					/*System.out.println("tmpArray[0]= " + tmpArray[0]);
						System.out.println("tmpArray[1]= " + tmpArray[1]);
						System.out.println("tmpArray[2]= " + tmpArray[2]);
						System.out.println("tmpArray[3]= " + tmpArray[3]);*/
						
						
						String location = tmpArray[0];
						String roomNumber = tmpArray[1];
						int capacity = Integer.parseInt(tmpArray[2]);
						
						String roomType = tmpArray[3];
						
						boolean science= false;
						boolean computer = false;
						boolean regular = false;
						
						
						//FIXME maybe add an arrayList for each room type, so we don't need to sort later. Do the obj creation within cases
						switch(tmpArray[3]){
						
						case "R":
							regular = true;
							regularClassRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science, computer, regular));
							break;
						case "C":
							computer = true;
							computerClassRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science, computer, regular));
							break;
						case "S":
							science = true;
							scienceClassRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science, computer, regular));
							break;
						}
						
						
						
						//classRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science, computer, regular));
					
					} // end else
				} // end outside if (line != null)
			} // end selectedFile for loop

			//we have our class data, set the boolean
			classData = true;
			
			System.out.println("Regular Class Rooms:\n");
			for(int i =0 ; i < regularClassRoomList.size(); i ++){
				System.out.println("	Class Room " + i + " Name is: " + regularClassRoomList.get(i).getRoomNumber());
				System.out.println("Weekly Schedule:");
				for(int j = 0 ; j < regularClassRoomList.get(i).getAvailiabilitySchedule().length; j ++){
					System.out.println("Day " + j + "= " + regularClassRoomList.get(i).getAvailiabilitySchedule()[j] );
					
				}
			}
			System.out.println("\n*********************************** \n");
			System.out.println("Computer Class Rooms:\n");
			for(int i =0 ; i < computerClassRoomList.size(); i ++){
				System.out.println("	Class Room " + i + " Name is: " + computerClassRoomList.get(i).getRoomNumber());
				System.out.println("Weekly Schedule:");
				for(int j = 0 ; j < computerClassRoomList.get(i).getAvailiabilitySchedule().length; j ++){
					System.out.println("Day " + j + "= " + computerClassRoomList.get(i).getAvailiabilitySchedule()[j] );
					
				}
			}
			System.out.println("\n*********************************** \n");
			System.out.println("Science Class Rooms:\n");
			for(int i =0 ; i < scienceClassRoomList.size(); i ++){
				System.out.println("	Class Room " + i + " Name is: " + scienceClassRoomList.get(i).getRoomNumber());
				System.out.println("Weekly Schedule:");
				for(int j = 0 ; j < scienceClassRoomList.get(i).getAvailiabilitySchedule().length; j ++){
					System.out.println("Day " + j + "= " + scienceClassRoomList.get(i).getAvailiabilitySchedule()[j] );
					
				}
			}
			System.out.println("\n*********************************** \n");
			
		} catch (IOException io) {
			io.printStackTrace();
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
					// System.out.println("line is: " + line);
					// System.out.println("Line length = " + line.length());

					if (line.length() == 0) {
						// this is the blank line, do nothing!
					}
					
					// this is for the creation of new Course obj's
					else {
						String[] tmpArray = line.split("\\s+");
						
						/*System.out.println("tmpArray[0]= " + tmpArray[0]);
						System.out.println("tmpArray[1]= " + tmpArray[1]);
						System.out.println("tmpArray[2]= " + tmpArray[2]);
						System.out.println("tmpArray[3]= " + tmpArray[3]);
						System.out.println("tmpArray[4]= " + tmpArray[4]);*/
						
						String name = tmpArray[0];
						String day = tmpArray[1];
						int enrolled = Integer.parseInt(tmpArray[2]);
						
						String roomType = tmpArray[3];
						
						boolean science= false;
						boolean computer = false;
						boolean regular = false;
						boolean lowerDivision = false;
						
						String location = tmpArray[4];
						
						String[] tmpDivisonArray = tmpArray[0].split("");
						
						int tmpDivision = Integer.parseInt(tmpDivisonArray[3]);
						
						if ((tmpDivision == 1) || (tmpDivision == 2) ){
							lowerDivision = true;
						}
							
						
						//FIXME maybe add an arrayList for each class type, so we don't need to sort later. Do the obj creation within cases
						switch(tmpArray[3]){
						
						case "R":
							regular = true;
							regularCourseList.add(new Course(name, day, enrolled, roomType, science, computer, regular, location, lowerDivision));
							break;
						case "C":
							computer = true;
							computerCourseList.add(new Course(name, day, enrolled, roomType, science, computer, regular, location, lowerDivision));
							break;
						case "S":
							science = true;
							scienceCourseList.add(new Course(name, day, enrolled, roomType, science, computer, regular, location, lowerDivision));
							break;
						}
						
						
						
						//courseList.add(new Course(name, day, enrolled, roomType, science, computer, regular, location));
					
					} // end else
				} // end outside if (line != null)
			} // end selectedFile for loop

			//we have our data, set the boolean
			courseData = true;
			
			
			for(int i =0 ; i < regularCourseList.size(); i ++){
				System.out.println(" Regular Course " + i + " Name is: " + regularCourseList.get(i).getCourseName());
				System.out.println("-Meets on " + regularCourseList.get(i).getRequestedEvening() + ", " + regularCourseList.get(i).getMeetDayString());
				System.out.println("-Needs a " + regularCourseList.get(i).getRoomType() + " room");
				System.out.println("Lower Division: " + regularCourseList.get(i).isLowerDivision() + "\n");
			}

			System.out.println("\n*********************************** \n");
			
			for(int i =0 ; i < computerCourseList.size(); i ++){
				System.out.println("Computer Course " + i + " Name is: " + computerCourseList.get(i).getCourseName());
				System.out.println("-Meets on " + computerCourseList.get(i).getRequestedEvening() + ", " + computerCourseList.get(i).getMeetDayString());
				System.out.println("-Needs a " + computerCourseList.get(i).getRoomType() + " room");
				System.out.println("Lower Division: " + computerCourseList.get(i).isLowerDivision()+ "\n");
			}

			System.out.println("\n*********************************** \n");
			
			for(int i =0 ; i < scienceCourseList.size(); i ++){
				System.out.println("Science Course " + i + " Name is: " + scienceCourseList.get(i).getCourseName());
				System.out.println("-Meets on " + scienceCourseList.get(i).getRequestedEvening() + ", " + scienceCourseList.get(i).getMeetDayString());
				System.out.println("-Needs a " + scienceCourseList.get(i).getRoomType() + " room");
				System.out.println("Lower Division: " + scienceCourseList.get(i).isLowerDivision()+ "\n");
			}

			System.out.println("\n*********************************** \n");

			
		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	/**
	 * Sort by weight of SubNode (Source From: Program 1)
	 * 
	 * @param node
	 */
	/*public static void sortListByWeight(Node node) {

		// Sort the subnodes by weight
		Collections.sort(node.getSubNodeArray(), new Comparator<SubNode>() {

			@Override
			public int compare(SubNode s1, SubNode s2) {
				if (s1.getWeight() < s2.getWeight())
					return -1;
				else if (s1.getWeight() > s2.getWeight())
					return 1;
				return 0;
			}

		});
	}*/

	/**
	 * Sort Alphabetically by sub node name (Source from:
	 * http://stackoverflow.com/questions/19471005/sorting-an-arraylist-of-objects-alphabetically)
	 * 
	 * @param node
	 */
/*	public static void sortListByAlphabetic(Node node) {
		Collections.sort(node.getSubNodeArray(), new Comparator<SubNode>() {
			public int compare(SubNode s1, SubNode s2) {
				return s1.getName().compareTo(s2.getName());
			}
		});
	}
*/
	
	/**
	 * Replaces the name with the save file name, made by me
	 */
	public static String fileRename() {
		// File naming replacement by me
		String fileName = selectedCourseFile.getName();
		// strip off the file type from originally selected file
		fileName = fileName.replace(".txt", "");
		fileName = (fileName + "_search.txt");
		// rename file

		return fileName;
		// saveToFile(fileName + "_search.txt", searchSolutionArray);
	}

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
