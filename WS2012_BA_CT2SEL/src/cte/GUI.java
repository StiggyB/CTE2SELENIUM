package cte;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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

	StringBuffer sb_cte = new StringBuffer();
	DefaultListModel<String> cte_listModel = new DefaultListModel<String>();
	JList<String> cte_list = new JList<String>(cte_listModel);
	private JTextField txtWebsite;
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAutomaticTestCase = new JFrame();
		frmAutomaticTestCase.setTitle("Automatic Test Case Generation for Selenium using CTE");
		frmAutomaticTestCase.setBounds(100, 100, 450, 551);
		frmAutomaticTestCase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAutomaticTestCase.getContentPane().setLayout(null);
		
		JButton btnOpenCtFile = new JButton("Open CT File");
		btnOpenCtFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser= new JFileChooser();


				chooser.setCurrentDirectory(new File("."));
				int choice = chooser.showOpenDialog(chooser);

				if (choice != JFileChooser.APPROVE_OPTION) return;
				File chosenFile = chooser.getSelectedFile();
				
				CTE cte = new CTE();
				String strLine = "";
				try {
					cte.setUpFile(chosenFile);
					while ((strLine = cte.readCTEfileByLine()) != null) {
						if ( strLine != "")
							cte_listModel.addElement(strLine);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				txtrCtetxt.setText(sb_cte.toString());
//				cte_listModel.addElement(sb_cte.toString());
				
			}
		});
		btnOpenCtFile.setBounds(10, 11, 113, 23);
		frmAutomaticTestCase.getContentPane().add(btnOpenCtFile);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 414, 204);
		frmAutomaticTestCase.getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(cte_list);
		
		JButton btnSearchTestCases = new JButton("Search for Test\r\n Cases");
		btnSearchTestCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSearchTestCases.setBounds(10, 260, 203, 23);
		frmAutomaticTestCase.getContentPane().add(btnSearchTestCases);
		
		JLabel lblPossibleTestCase = new JLabel("Possible Test Case Objects:");
		lblPossibleTestCase.setBounds(10, 294, 201, 14);
		frmAutomaticTestCase.getContentPane().add(lblPossibleTestCase);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 319, 201, 129);
		frmAutomaticTestCase.getContentPane().add(scrollPane_1);
		
		JList to_list = new JList();
		scrollPane_1.setViewportView(to_list);
		
		txtWebsite = new JTextField();
		txtWebsite.setText("www.google.com");
		txtWebsite.setBounds(221, 291, 203, 20);
		frmAutomaticTestCase.getContentPane().add(txtWebsite);
		txtWebsite.setColumns(10);
		
		JButton btnParse = new JButton("Parse Website for Matches");
		btnParse.setBounds(221, 260, 203, 23);
		frmAutomaticTestCase.getContentPane().add(btnParse);
		
		JLabel lblCtFile = new JLabel("CT File");
		lblCtFile.setBounds(192, 20, 46, 14);
		frmAutomaticTestCase.getContentPane().add(lblCtFile);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(221, 320, 203, 128);
		frmAutomaticTestCase.getContentPane().add(scrollPane_2);
		
		JList list = new JList();
		scrollPane_2.setViewportView(list);
		
		JButton btnGenerateSeleniumFile = new JButton("Generate Selenium File");
		btnGenerateSeleniumFile.setBounds(124, 459, 195, 23);
		frmAutomaticTestCase.getContentPane().add(btnGenerateSeleniumFile);
		
		JMenuBar menuBar = new JMenuBar();
		frmAutomaticTestCase.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
	}
}
