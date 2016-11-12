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
	private static File selectedFile;

	// using printWriter for formatted data
	PrintWriter writer = null;

	Node node;
	SubNode subNode;

	// Holds all nodes
	static ArrayList<Node> nodeList;

	// Search Buffer
	static Stack<Node> stack;

	private JPanel contentPane;
	private JTextField fileSelectedTextField;

	// our starting depth on search
	private int initialDepth = 0;
	// what we wish to increment by
	private int incrementLevel = 0;
	// where we are in the search
	private int currentDepth = 0;
	// stores the edge weight of subnode
	private int subNodeValue = 0;
	// goal found at end of iteration
	private boolean goalReached = false;
	// goal found in iteration
	private boolean tmpGoalReached = false;
	// stores goal names found during search
	private String goalName = "";
	// used for printing search header
	private boolean firstRun = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Initialize the arrays
		nodeList = new ArrayList<Node>();
		stack = new Stack<Node>();

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
		setTitle("Program 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 319, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					setSelectedFile(fileChooser.getSelectedFile());
					fileSelectedTextField.setText(selectedFile.getName());

					parseData(selectedFile);
				}
			}

		});
		btnSelectFile.setBounds(10, 11, 120, 38);
		contentPane.add(btnSelectFile);

		fileSelectedTextField = new JTextField();
		fileSelectedTextField.setBounds(140, 29, 151, 20);
		contentPane.add(fileSelectedTextField);
		fileSelectedTextField.setColumns(10);

		JLabel lblFileSelected = new JLabel("File selected: ");
		lblFileSelected.setBounds(141, 11, 97, 14);
		contentPane.add(lblFileSelected);
	}

	private void parseData(File selectedFile) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile)));


			for (int i = 0; i < selectedFile.length(); i++) {
				String line = input.readLine();

				if (line != null) {
					// System.out.println("line is: " + line);
					// System.out.println("Line length = " + line.length());

					if (line.length() == 0) {
						// this is the blank line, do nothing!
					}
					// this is for non goal nodes
					else if (line.length() == 5) {
						String[] tmpArray = line.split("\\s+");
	

					} // this is for goal nodes
					else if (line.length() == 6) {
						String[] tmpArray = line.split("\\s+");

						
	

					}
					// this is for the creation of new sub nodes
					else {
						String[] tmpArray = line.split("\\s+");
					
					} // end else
				} // end outside if (line != null)
			} // end selectedFile for loop

			

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
	public static void sortListByWeight(Node node) {

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
	}

	/**
	 * Sort Alphabetically by sub node name (Source from:
	 * http://stackoverflow.com/questions/19471005/sorting-an-arraylist-of-objects-alphabetically)
	 * 
	 * @param node
	 */
	public static void sortListByAlphabetic(Node node) {
		Collections.sort(node.getSubNodeArray(), new Comparator<SubNode>() {
			public int compare(SubNode s1, SubNode s2) {
				return s1.getName().compareTo(s2.getName());
			}
		});
	}

	
	/**
	 * Replaces the name with the save file name, made by me
	 */
	public static String fileRename() {
		// File naming replacement by me
		String fileName = selectedFile.getName();
		// strip off the file type from originally selected file
		fileName = fileName.replace(".txt", "");
		fileName = (fileName + "_search.txt");
		// rename file

		return fileName;
		// saveToFile(fileName + "_search.txt", searchSolutionArray);
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}
}
