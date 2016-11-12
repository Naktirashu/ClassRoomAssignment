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

public class ListGui extends JFrame {

	// store the file selected from GUI
	private static File selectedClassRoomFile;
	
	// store the file selected from GUI
	private static File selectedCourseFile;

	// using printWriter for formatted data
	PrintWriter writer = null;

	ClassRoom classroom;
	Course course;

	// Holds all course
	static ArrayList<Course> courseList;

	static ArrayList<ClassRoom> classRoomList;
	
	private JPanel contentPane;
	private JTextField classRoomFileSelectedTextField;
	private JTextField courseFileSelectedTextField;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Initialize the arrays
		courseList = new ArrayList<Course>();
		classRoomList = new ArrayList<ClassRoom>();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListGui frame = new ListGui();
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
	public ListGui() {
		setTitle("Jeff Beaupre Program 3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 300);
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
							break;
						case "C":
							computer = true;
							break;
						case "S":
							science = true;
							break;
						}
						
						
						
						classRoomList.add(new ClassRoom(location, roomNumber, capacity, roomType, science, computer, regular));
					
					} // end else
				} // end outside if (line != null)
			} // end selectedFile for loop

		
			for(int i =0 ; i < classRoomList.size(); i ++){
				System.out.println("Class Room " + i + " Name is: " + classRoomList.get(i).getRoomNumber());
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
						
						String location = tmpArray[4];
						
						//FIXME maybe add an arrayList for each class type, so we don't need to sort later. Do the obj creation within cases
						switch(tmpArray[3]){
						
						case "R":
							regular = true;
							break;
						case "C":
							computer = true;
							break;
						case "S":
							science = true;
							break;
						}
						
						
						
						courseList.add(new Course(name, day, enrolled, roomType, science, computer, regular, location));
					
					} // end else
				} // end outside if (line != null)
			} // end selectedFile for loop

		
			for(int i =0 ; i < courseList.size(); i ++){
				System.out.println("Course " + i + " Name is: " + courseList.get(i).getCourseName());
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
		ListGui.selectedClassRoomFile = selectedClassRoomFile;
	}

	public static File getSelectedCourseFile() {
		return selectedCourseFile;
	}

	public static void setSelectedCourseFile(File selectedCourseFile) {
		ListGui.selectedCourseFile = selectedCourseFile;
	}


}
