package com.univaqproject.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainAppWindow extends JFrame {
	
	private static final long serialVersionUID = 402971745647947246L;
	
	protected JPanel panel;
	protected JTextField linksLine;
	protected JTextArea messageBox;
	protected JScrollPane messageScroller;
	protected JButton submitButton;
	protected SubmitListener submitListener;

	public static void main(String args[]) throws IOException {
		MainAppWindow window = new MainAppWindow();
	}
	
	public MainAppWindow() throws IOException {

		this.setTitle("OOP Project"); 
		this.setLayout(new BorderLayout());
		this.setBounds(200, 200, 600, 350);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		

		/** Menu bar */

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu aboutMenu = new JMenu("About");

		JMenuItem aboutItem = new JMenuItem("OOP Project");
		aboutMenu.add(aboutItem);
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();				
			}
		});

		menuBar.add(aboutMenu);


		/** West panel - chat area */
		panel = new JPanel();

		messageBox = new JTextArea("Waiting for results...\n", 15, 20);

		messageScroller = new JScrollPane();
		messageScroller.setViewportView(messageBox);

		messageScroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {  
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
			}
		});
		
		messageBox.setEditable(false);
		messageBox.setWrapStyleWord(true);
		messageBox.setLineWrap(true);     

		linksLine = new JTextField("Insert the link here...", 40);
		linksLine.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					executeAnalysis();
				}
			}
		});

		submitButton = new JButton("Start");
		submitButton.addActionListener(submitListener = new SubmitListener());

		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(Box.createVerticalGlue());

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(Box.createHorizontalGlue());
		horizontalBox.add(linksLine);
		horizontalBox.add(submitButton);
		horizontalBox.add(Box.createHorizontalGlue());

		verticalBox.add(horizontalBox);
		verticalBox.add(messageScroller);
		verticalBox.add(Box.createVerticalGlue());		
		panel.add(verticalBox);

		add(panel);

		revalidate();
	}
	
	public class SubmitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			executeAnalysis();
			revalidate();
		}
	}
	
	public int showAbout() {
		return JOptionPane.showConfirmDialog(
				this, 
				"This project has been created by:\n\n" +
				"Martin Doychev\n" +
				"Danail Yankov\n" +
				"Milos Darmanovic\n\n" +  
				"Contact: martin.doychev@abv.bg \n", 
				"About", 
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void executeAnalysis() {
		linksLine.setEditable(false);
		//execute analysis

		MySQLAccess dao = new MySQLAccess();
	    try {
			dao.readDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		linksLine.setText("");
		linksLine.setEditable(true);
	}
	
}
