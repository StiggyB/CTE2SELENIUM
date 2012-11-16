package cte;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import test.CTETest;
import codeGen.JFileGenerator;

public class GUI {

	private JFrame frmAutomaticTestCase;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmAutomaticTestCase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	private DefaultListModel<String> cte_listModel = new DefaultListModel<String>();
	private DefaultListModel<String> to_listModel = new DefaultListModel<String>();
	private JList<String> cte_list = new JList<String>(cte_listModel);
	private JList<String> to_list = new JList<String>(to_listModel);
	private JTextField txtWebsite;
	private JTextArea txtrJunitoutput = new JTextArea();

	private CTE cte = new CTE();
	private ArrayList<String> cteElements = new ArrayList<>();
	private String strLine = "";
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAutomaticTestCase = new JFrame();
		frmAutomaticTestCase
				.setTitle("Automatic Test Case Generation for Selenium using CTE");
		frmAutomaticTestCase.setBounds(100, 100, 450, 573);
		frmAutomaticTestCase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAutomaticTestCase.getContentPane().setLayout(null);

		JButton btnOpenCtFile = new JButton("Open CT File");
		btnOpenCtFile.setToolTipText("Select a CTE .txt File");
		btnOpenCtFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();

				chooser.setCurrentDirectory(new File("."));
				int choice = chooser.showOpenDialog(chooser);

				if (choice != JFileChooser.APPROVE_OPTION)
					return;
				File chosenFile = chooser.getSelectedFile();

				try {
					cte.setUpFile(chosenFile);
					while ((strLine = cte.readCTEfileByLine()) != null) {
						if (!strLine.isEmpty()) {
							cte_listModel.addElement(strLine);
							cteElements.add(strLine);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		btnOpenCtFile.setBounds(10, 23, 113, 23);
		frmAutomaticTestCase.getContentPane().add(btnOpenCtFile);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 203, 204);
		frmAutomaticTestCase.getContentPane().add(scrollPane);
		cte_list.setToolTipText("");
		cte_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ( arg0.getButton() == MouseEvent.BUTTON1)
					to_listModel.addElement(cte_list.getSelectedValue());
			}
		});

		scrollPane.setViewportView(cte_list);

		JButton btnSearchTestCases = new JButton("Search for Test\r\n Cases");
		btnSearchTestCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cte.parseForTC(cteElements);
			}
		});
		btnSearchTestCases.setBounds(10, 272, 203, 23);
		frmAutomaticTestCase.getContentPane().add(btnSearchTestCases);

		JLabel lblPossibleTestCase = new JLabel("Possible Test Case Objects:");
		lblPossibleTestCase.setBounds(225, 32, 201, 14);
		frmAutomaticTestCase.getContentPane().add(lblPossibleTestCase);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(221, 57, 201, 204);
		frmAutomaticTestCase.getContentPane().add(scrollPane_1);
		to_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( e.getButton() == MouseEvent.BUTTON1)
					to_listModel.removeElement(to_list.getSelectedValue());
			}
		});

		scrollPane_1.setViewportView(to_list);

		txtWebsite = new JTextField();
		txtWebsite.setText("www.google.com");
		txtWebsite.setBounds(10, 301, 412, 20);
		frmAutomaticTestCase.getContentPane().add(txtWebsite);
		txtWebsite.setColumns(10);

		JButton btnParse = new JButton("Parse Website for Matches");
		btnParse.setBounds(223, 272, 203, 23);
		frmAutomaticTestCase.getContentPane().add(btnParse);

		JButton btnGenerateJUnitTest = new JButton("Generate JUnit Test");
		btnGenerateJUnitTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CTETest ct = new CTETest();
				try {
					JFileGenerator jfg = new JFileGenerator();
					jfg.setFileName("test");
					jfg.setPackageName("test");
					jfg.generateFile();
					
					ct.setUp();
					ct.testAdvancedSearch();
					ct.tearDown();
				} catch (AssertionError | Exception e) {
					System.err.println( ": ");
					System.err.println(e);
				}
			}
		});
		btnGenerateJUnitTest.setBounds(114, 332, 195, 23);
		frmAutomaticTestCase.getContentPane().add(btnGenerateJUnitTest);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 362, 412, 141);
		frmAutomaticTestCase.getContentPane().add(scrollPane_2);
		txtrJunitoutput.setEditable(false);
		

		scrollPane_2.setViewportView(txtrJunitoutput);
		txtrJunitoutput.setText("JUnitOutput");
		redirectSystemStreams();

		JMenuBar menuBar = new JMenuBar();
		frmAutomaticTestCase.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
	}
	
	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				txtrJunitoutput.append(text);
			}
		});
	}

	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}
