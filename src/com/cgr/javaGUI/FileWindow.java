package com.cgr.javaGUI;

//import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
//import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileWindow implements ActionListener {

	public JFileChooser fileWindow;
	public JFrame frame;

	/**
	 * Create the application.
	 */
	public FileWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fileWindow = new JFileChooser();
		frame.getContentPane().add(fileWindow, BorderLayout.CENTER);
	}

	public void actionPerformed (ActionEvent e) {
		
		if(e.getActionCommand()=="Cancel" )
			System.exit(0);
				
	}

}
