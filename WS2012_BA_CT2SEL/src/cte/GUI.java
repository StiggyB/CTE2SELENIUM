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
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestResult;

import org.openqa.jetty.util.Password;

import test.web.PasswordTest;
import test.web.WEBTest;
import c2s.TC;

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
	private JTextArea txtrJunitoutput = new JTextArea();

	private CTE cte = new CTE();
	private boolean enableTCsearch = false;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAutomaticTestCase = new JFrame();
		frmAutomaticTestCase
				.setTitle("Automatic Test Case Generation withClassification Trees for Web Testing");
		frmAutomaticTestCase.setBounds(100, 100, 450, 573);
		frmAutomaticTestCase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAutomaticTestCase.getContentPane().setLayout(null);

		JButton btnStartJUnitTest = new JButton("Start JUnit Test");
		btnStartJUnitTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// CTETest ct = new CTETest();
				try {
					// JFileGenerator jfg = new JFileGenerator();
					// jfg.setFileName("test");
					// jfg.setPackageName("test");
					// jfg.generateFile();
					junit.textui.TestRunner.run(PasswordTest.suite());
//					PasswordTest pt = new PasswordTest();
//					pt.testPassword(true, true, "p4ssw0rd");
//					org.junit.runner.JUnitCore.runClasses(CTETest.class);
					// ct.setUp();
//					JUnit4TestAdapter juta = new JUnit4TestAdapter(
//							PasswordTest.class);
//					TestResult result = new TestResult();
//					juta.run(result);
//					for (Iterator<Test> iterator = juta.getTests().iterator(); iterator
//							.hasNext();) {
//						Test type = (Test) iterator.next();
//						iterator.next().
//						
//					}
					
//					System.out.println("JUnit tests Successful: "
//							+ result.wasSuccessful());
					// ct.testLogin();
					// ct.tearDown();
				} catch (AssertionError | Exception e) {
					System.err.println(": ");
					System.err.println(e);
				}
			}
		});
		btnStartJUnitTest.setBounds(120, 272, 195, 23);
		frmAutomaticTestCase.getContentPane().add(btnStartJUnitTest);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 308, 410, 179);
		frmAutomaticTestCase.getContentPane().add(scrollPane_2);
		txtrJunitoutput.setEditable(false);

		scrollPane_2.setViewportView(txtrJunitoutput);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 13, 203, 246);
		frmAutomaticTestCase.getContentPane().add(tabbedPane);

		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Files", null, scrollPane, null);
		cte_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1 && enableTCsearch) {
					to_listModel.addElement(cte_list.getSelectedValue());
					cte_listModel.removeElement(cte_list.getSelectedValue());
					cte.getNodes();
					for (Iterator<TC> iterator = cte.getTestData().iterator(); iterator.hasNext();) {
						to_listModel.addElement(iterator.next().toString());
					}
				}
			}
		});

		scrollPane.setViewportView(cte_list);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(221, 13, 199, 246);
		frmAutomaticTestCase.getContentPane().add(tabbedPane_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab("Testcases", null, scrollPane_1, null);
		to_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {}
					//to_listModel.removeElement(to_list.getSelectedValue());
			}
		});

		scrollPane_1.setViewportView(to_list);
		redirectSystemStreams();

		JMenuBar menuBar = new JMenuBar();
		frmAutomaticTestCase.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("File");
		menuBar.add(mnMenu);

		JMenuItem mntmOpencteFile = new JMenuItem("Open .cte File");
		mntmOpencteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();

				chooser.setCurrentDirectory(new File("."));
				int choice = chooser.showOpenDialog(chooser);

				if (choice != JFileChooser.APPROVE_OPTION)
					return;
				File chosenFile = chooser.getSelectedFile();

				try {
					enableTCsearch = cte.setUpFile(chosenFile);
					cte_listModel.addElement(chosenFile.getName());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mnMenu.add(mntmOpencteFile);
		
		JSeparator separator = new JSeparator();
		mnMenu.add(separator);
		
		JMenuItem mntmPrintTestcases = new JMenuItem("Print Testcases");
		mnMenu.add(mntmPrintTestcases);
		
		JSeparator separator_1 = new JSeparator();
		mnMenu.add(separator_1);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnMenu.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnMenu.add(mntmSaveAs);
		
		JSeparator separator_2 = new JSeparator();
		mnMenu.add(separator_2);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnMenu.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmRename = new JMenuItem("Rename");
		mnEdit.add(mntmRename);
		
		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mnEdit.add(mntmDelete);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelpContents = new JMenuItem("Help Contents");
		mnHelp.add(mntmHelpContents);
		
		JSeparator separator_4 = new JSeparator();
		mnHelp.add(separator_4);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
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
