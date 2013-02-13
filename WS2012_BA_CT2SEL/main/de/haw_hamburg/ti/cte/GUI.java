package de.haw_hamburg.ti.cte;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.filechooser.FileNameExtensionFilter;

import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.runTimeTests.SizingTypeAndMediumSelectionTest;
import de.haw_hamburg.ti.tools.PrintAction;

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

    private DefaultListModel<String> cte_listModel   = new DefaultListModel<String>();
    private DefaultListModel<String> to_listModel    = new DefaultListModel<String>();
    private JList<String>            cte_list        = new JList<String>(
                                                             cte_listModel);
    private JList<String>            to_list         = new JList<String>(
                                                             to_listModel);
    private JTextArea                txtrJunitoutput = new JTextArea();

    private CTE                      cte             = new CTE();
//    private File                     chosenFile;
    private ArrayList<File>          files           = new ArrayList<>();

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmAutomaticTestCase = new JFrame();
        frmAutomaticTestCase
                .setTitle("Automatic Test Case Generation withClassification Trees for Web Testing" + getDate());
        frmAutomaticTestCase.setBounds(100, 100, 999, 666);
        frmAutomaticTestCase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmAutomaticTestCase.getContentPane().setLayout(null);

        JButton btnStartJUnitTest = new JButton("Start Unit Test");
        btnStartJUnitTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                File chosenFile = chooseFile("cttc");

                frmAutomaticTestCase.setCursor(Cursor
                        .getPredefinedCursor(Cursor.WAIT_CURSOR));
                junit.framework.Test t = SizingTypeAndMediumSelectionTest
                        .suite(chosenFile);
                junit.textui.TestRunner.run(t);
                frmAutomaticTestCase.setCursor(Cursor.getDefaultCursor());
                // progressBar.setValue(t.getProgress());

            }
        });
        btnStartJUnitTest.setBounds(10, 298, 195, 23);
        frmAutomaticTestCase.getContentPane().add(btnStartJUnitTest);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(10, 334, 959, 246);
        frmAutomaticTestCase.getContentPane().add(scrollPane_2);
        txtrJunitoutput.setEditable(false);

        scrollPane_2.setViewportView(txtrJunitoutput);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 13, 410, 272);
        frmAutomaticTestCase.getContentPane().add(tabbedPane);

        JScrollPane scrollPane = new JScrollPane();
        tabbedPane.addTab("Files", null, scrollPane, null);
        cte_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1
                        && !cte_listModel.isEmpty()) {
                    frmAutomaticTestCase.setCursor(Cursor
                            .getPredefinedCursor(Cursor.WAIT_CURSOR));
                    to_listModel.addElement(cte_list.getSelectedValue());
                    try {
                        ArrayList<CteTestCase> ctes = cte.getTestData(files.get(cte_list.getSelectedIndex()));
                        TestHandler.addList(ctes);
                        
                        for (Iterator<CteTestCase> iterator = ctes
                                .iterator(); iterator.hasNext();) {
                            to_listModel
                                    .addElement(iterator.next().getName());
                        }
                    } catch (IOException e) {
                        System.err.println("IO Err");
                        e.printStackTrace();
                    }
                    cte.saveTestCasesToFile();
                    files.remove(cte_list.getSelectedIndex());
                    cte_listModel.removeElement(cte_list.getSelectedValue());
                    frmAutomaticTestCase.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        scrollPane.setViewportView(cte_list);

        JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_1.setBounds(432, 13, 537, 272);
        frmAutomaticTestCase.getContentPane().add(tabbedPane_1);

        JScrollPane scrollPane_1 = new JScrollPane();
        tabbedPane_1.addTab("Testcases", null, scrollPane_1, null);
        to_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                }
                // to_listModel.removeElement(to_list.getSelectedValue());
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

                File chosenFile = chooseFile("cte");
                files.add(chosenFile);

                cte_listModel.addElement(chosenFile.getName());
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
    
    private String getDate() {
        //
        // Create a DateFormatter object for displaying date information.
        //
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        
        //
        // Get date and time information in milliseconds
        //
        long now = System.currentTimeMillis();

        //
        // Create a calendar object that will convert the date and time value
        // in milliseconds to date. We use the setTimeInMillis() method of the
        // Calendar object.
        //
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        
        System.out.println(formatter.format(calendar.getTime()));
        return formatter.format(calendar.getTime());
    }

    /**
     * @param extension 
     * @return 
     * 
     */
    private File chooseFile(String extension) {
        JFileChooser chooser = new JFileChooser();

        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileNameExtensionFilter(null,
                extension));

        int choice = chooser.showOpenDialog(chooser);

        if (choice != JFileChooser.APPROVE_OPTION)
            return null;
        return chooser.getSelectedFile();
    }
}
