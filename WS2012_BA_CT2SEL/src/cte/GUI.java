package cte;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
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
	JTextArea txtrCtetxt = new JTextArea();
	DefaultListModel<String> cte_listModel = new DefaultListModel<String>();
	JList<String> cte_list = new JList<String>(cte_listModel);
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnOpenCtFile = new JButton("Open CT File");
		btnOpenCtFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sb_cte = CTE.readCTEfile();
				txtrCtetxt.setText(sb_cte.toString());
				cte_listModel.addElement(sb_cte.toString());
				
			}
		});
		btnOpenCtFile.setBounds(10, 228, 113, 23);
		frame.getContentPane().add(btnOpenCtFile);
		
		JScrollPane cte_txt_scrollPane = new JScrollPane();
		cte_txt_scrollPane.setBounds(10, 11, 206, 206);
		frame.getContentPane().add(cte_txt_scrollPane);
		txtrCtetxt.setEditable(false);
		cte_txt_scrollPane.setViewportView(txtrCtetxt);
		txtrCtetxt.setText("cte_txt");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(223, 11, 201, 204);
		frame.getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(cte_list);
	}
}
