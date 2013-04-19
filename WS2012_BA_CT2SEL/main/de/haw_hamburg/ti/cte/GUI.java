package de.haw_hamburg.ti.cte;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.runTimeTests.SizingTypeAndMediumSelectionTest;
import de.haw_hamburg.ti.tools.PrintAction;
import de.haw_hamburg.ti.tools.TimeAssist;

public class GUI {

    private JFrame frmAutomaticTestCase;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
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

    private DefaultListModel<String> cte_listModel   = new DefaultListModel<String>();
    private DefaultListModel<String> to_listModel    = new DefaultListModel<String>();
    private JList<String>            cte_list        = new JList<String>(
                                                             cte_listModel);
    private JList<String>            to_list         = new JList<String>(
                                                             to_listModel);
    private JTextArea                txtrJunitoutput = new JTextArea();
    private JPopupMenu               popupMenu       = new JPopupMenu();

    private CTEData                      cte             = new CTEData();
    // private File chosenFile;
    private ArrayList<File>          files           = new ArrayList<>();
    private ArrayList<File>          cttcfiles       = new ArrayList<>();
    private JTextField               txtWwwvalvestarcom;
    private JTextField               txtUsername;
    private JPasswordField           txtPassword;
    protected String                 password;
    protected String                 username;

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmAutomaticTestCase = new JFrame();
        frmAutomaticTestCase
                .setTitle("Automatic Test Case Generation with Classification Trees for Web Testing");
        frmAutomaticTestCase.setBounds(100, 100, 999, 700);
        frmAutomaticTestCase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmAutomaticTestCase.getContentPane().setLayout(null);

        JMenuItem mntmRemove = new JMenuItem("remove");
        mntmRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int i = to_list.getSelectedIndex();
                if (i >= 0) {
                    to_listModel.remove(i);
                    cttcfiles.remove(i);
                }
            }
        });
        popupMenu.add(mntmRemove);

        JButton btnStartJUnitTest = new JButton("Start Unit Test");
        btnStartJUnitTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (txtUsername.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frmAutomaticTestCase,
                            "No username entered.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (txtPassword.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(frmAutomaticTestCase,
                            "No password entered.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (cttcfiles.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAutomaticTestCase,
                            "No test files selected.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    frmAutomaticTestCase.setCursor(Cursor
                            .getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        PrintStream oldoutps = System.out; 
                        try {
                            
                            FileOutputStream outfos = new FileOutputStream("TestOutput.htm"); //create //new output stream
                            PrintStream newoutps = new PrintStream(outfos); //create new output //stream
                            System.setOut(newoutps); //set the output stream
                            
                            System.out.println("Output of Junit Testing");
                            System.out.println();
                            System.out.println(TimeAssist.getDate());
                            
                            long stamp1 = TimeAssist.getTimeStamp();
                            junit.framework.Test t = SizingTypeAndMediumSelectionTest
                                    .suite(cttcfiles, txtUsername.getText(),
                                            String.valueOf(txtPassword.getPassword()));
                            junit.textui.TestRunner.run(t);
                            long stamp2 = TimeAssist.getTimeStamp();
                            
                            System.out.println("################### TIME PASSED:" + TimeAssist.getTimePassed(stamp1, stamp2));
                            System.setOut(oldoutps); //for resetting the output stream
                            
                        } catch (Exception e) {
                            
                            System.out.println("some error");
                            
                        }
                    } catch (Exception e) {
                        if (e.getMessage().equalsIgnoreCase(
                                "Wrong username and/or password")) {
                            JOptionPane.showMessageDialog(
                                    frmAutomaticTestCase,
                                    "Wrong username and/or password.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    frmAutomaticTestCase.setCursor(Cursor.getDefaultCursor());
                }

            }
        });
        btnStartJUnitTest.setBounds(10, 332, 195, 23);
        frmAutomaticTestCase.getContentPane().add(btnStartJUnitTest);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(10, 368, 959, 246);
        frmAutomaticTestCase.getContentPane().add(scrollPane_2);
        txtrJunitoutput.setEditable(false);

        scrollPane_2.setViewportView(txtrJunitoutput);

        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        tabbedPane.setToolTipText("");
        tabbedPane.setBounds(10, 47, 410, 272);
        frmAutomaticTestCase.getContentPane().add(tabbedPane);

        JScrollPane scrollPane = new JScrollPane();
        tabbedPane.addTab("Files", null, scrollPane, null);
        cte_list.setToolTipText("Select a File to see the included Testcases");
        cte_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1
                        && !cte_listModel.isEmpty()) {
                    frmAutomaticTestCase.setCursor(Cursor
                            .getPredefinedCursor(Cursor.WAIT_CURSOR));
                    // to_listModel.addElement(cte_list.getSelectedValue());
                    try {
                        ArrayList<CteTestCase> ctes = cte.getTestData(files
                                .get(cte_list.getSelectedIndex()));

                        // for (Iterator<CteTestCase> iterator =
                        // ctes.iterator(); iterator
                        // .hasNext();) {
                        // to_listModel.addElement("-"
                        // + iterator.next().getName());
                        // }
                    } catch (IOException e) {
                        System.err.println("IO Err");
                        e.printStackTrace();
                    }
                    try {
                        File cttcfile = cte.saveTestCasesToFile(files
                                .get(cte_list.getSelectedIndex()));
                        if (!cttcfiles.contains(cttcfile)) {
                            addPopup(to_list, popupMenu);
                            System.out.println("ADDED: " + cttcfile);
                            cttcfiles.add(cttcfile);
                            to_listModel.addElement(cttcfile.getName());
                            files.remove(cte_list.getSelectedIndex());
                            cte_listModel.removeElement(cte_list
                                    .getSelectedValue());
                        } else {
                            JOptionPane.showMessageDialog(
                                    frmAutomaticTestCase,
                                    "You already added this file.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    frmAutomaticTestCase.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        scrollPane.setViewportView(cte_list);

        JTabbedPane tabbedPane_1 = new JTabbedPane(SwingConstants.TOP);
        tabbedPane_1.setBounds(432, 47, 537, 272);
        frmAutomaticTestCase.getContentPane().add(tabbedPane_1);

        JScrollPane scrollPane_1 = new JScrollPane();
        tabbedPane_1.addTab("Testcases", null, scrollPane_1, null);
        to_list.setToolTipText("Select the Files which should be tested.");
        to_list.addMouseListener(new MouseAdapter() {

            // JPopupMenu jp = new JPopupMenu();
            // jp.add(menuItem)
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                }
                // to_listModel.removeElement(to_list.getSelectedValue());
            }
        });

        scrollPane_1.setViewportView(to_list);

        JLabel lblTestObject = new JLabel("Test Object:");
        lblTestObject.setBounds(12, 13, 78, 16);
        frmAutomaticTestCase.getContentPane().add(lblTestObject);

        txtWwwvalvestarcom = new JTextField();
        txtWwwvalvestarcom.setEditable(false);
        txtWwwvalvestarcom.setText("www.valvestar.com");
        txtWwwvalvestarcom.setBounds(102, 10, 172, 22);
        frmAutomaticTestCase.getContentPane().add(txtWwwvalvestarcom);
        txtWwwvalvestarcom.setColumns(10);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(286, 13, 78, 16);
        frmAutomaticTestCase.getContentPane().add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setText("");
        txtUsername.setBounds(376, 10, 116, 22);
        frmAutomaticTestCase.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(504, 13, 78, 16);
        frmAutomaticTestCase.getContentPane().add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setText("");
        txtPassword.setBounds(594, 10, 116, 22);
        frmAutomaticTestCase.getContentPane().add(txtPassword);
        txtPassword.setColumns(10);

        redirectSystemStreams();

        JMenuBar menuBar = new JMenuBar();
        frmAutomaticTestCase.setJMenuBar(menuBar);

        JMenu mnMenu = new JMenu("File");
        menuBar.add(mnMenu);

        JMenuItem mntmOpencteFile = new JMenuItem("Open .cte File");
        mntmOpencteFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                File chosenFile = chooseFile("cte");
                if (chosenFile != null) {
                    files.add(chosenFile);
                    cte_listModel.addElement(chosenFile.getName());
                }
            }
        });
        mnMenu.add(mntmOpencteFile);

        JSeparator separator = new JSeparator();
        mnMenu.add(separator);

        JMenuItem mntmPrintTestcases = new JMenuItem("Print Testcases");
        mntmPrintTestcases.addActionListener(new PrintAction());
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
        mntmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frmAutomaticTestCase.dispose();
            }
        });
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
            @Override
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

    /**
     * @param extension
     * @return
     * 
     */
    private File chooseFile(String extension) {
        JFileChooser chooser = new JFileChooser();

        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileNameExtensionFilter(null, extension));

        int choice = chooser.showOpenDialog(chooser);

        if (choice != JFileChooser.APPROVE_OPTION)
            return null;
        return chooser.getSelectedFile();
    }

    private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            private void showMenu(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }
}
