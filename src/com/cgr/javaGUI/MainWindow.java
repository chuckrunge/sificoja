package com.cgr.javaGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Insets;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

import com.sidacoja.utils.Cell;
import com.sidacoja.utils.Common;
import com.sidacoja.utils.Row;
import com.sidacoja.utils.RowCache;
import com.sidacoja.utils.Sidacoja;

public class MainWindow implements ActionListener {

	private JFrame frmSimpleFileCompare;
	private JTextField textField;
	private JTextField textField_1;
	private JScrollPane scrollPane = new JScrollPane();
	private JTextArea textArea = new JTextArea();
	private final JButton btnNewButton_3 = new JButton("Compare");
	private final JButton btnNewButton_2 = new JButton("Close");
	private Sidacoja sdcj = new Sidacoja();
	private Common common = new Common();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmSimpleFileCompare.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void actionPerformed (ActionEvent e) {
		//System.out.println(e.getActionCommand());
		if(e.getActionCommand()=="Close" ) {
			frmSimpleFileCompare.dispose();
			frmSimpleFileCompare.setVisible(false);
		}

		if(e.getActionCommand()=="File1" ) {
			FileWindow fWindow1 = new FileWindow();
		    int status = fWindow1.fileWindow.showOpenDialog(null);

		    if (status == JFileChooser.APPROVE_OPTION) {
		      File selectedFile = fWindow1.fileWindow.getSelectedFile();
		      textField.setText(selectedFile.getParent() + "\\" + selectedFile.getName());
		    } else if (status == JFileChooser.CANCEL_OPTION) {
		      System.out.println("cancelled");
		    }
 
		}
		if(e.getActionCommand()=="File2" ) {

			FileWindow fWindow2 = new FileWindow();
		    int status = fWindow2.fileWindow.showOpenDialog(null);

		    if (status == JFileChooser.APPROVE_OPTION) {
		      File selectedFile = fWindow2.fileWindow.getSelectedFile();
		      textField_1.setText(selectedFile.getParent() + "\\" + selectedFile.getName());
		    } else if (status == JFileChooser.CANCEL_OPTION) {
		      System.out.println("cancelled");
		    }
 
		}

		if(e.getActionCommand()=="Compare") {
			int i = 0;
			String[] szArray = new String[9999];
			FileCompare fCompare = new FileCompare();
			//		- option pctForMatch
			Map<String,String> pMap = loadParameters(sdcj);
			if(!common.isNullOrEmpty(pMap.get("sort"))) {
				String doSort = pMap.get("sort");
				if("true".equals(doSort)) {
					fCompare.setSort(true);
					console("sort=true");
				} else {
					console("sort=false");

				}
			};
			if(!common.isNullOrEmpty(pMap.get("readAheadNbr"))) {
				String readAheadNbr = pMap.get("readAheadNbr");
				if(isInteger(readAheadNbr)) {
					int iNbr = Integer.parseInt(readAheadNbr);
					fCompare.setReadAheadNbr(iNbr);
					console("readAheadNbr="+readAheadNbr);
				}
			};
			if(!common.isNullOrEmpty(pMap.get("pctForMatch"))) {
				String pctForMatch = pMap.get("pctForMatch");
				if(isInteger(pctForMatch)) {
					int iNbr = Integer.parseInt(pctForMatch);
					fCompare.setReadAheadNbr(iNbr);
					console("pctForMatch="+pctForMatch);
				}
			};

			szArray = fCompare.Compare(textField.getText(), textField_1.getText());

			StringBuffer sBuffer = new StringBuffer();
			for(i=0;i<szArray.length;i++) {
				if(szArray[i]==null || szArray[i].equals("{|}")) {
					i = szArray.length;
				} else {
					sBuffer.append(szArray[i] + "\n");
				}
			}
			textArea.setText(textArea.getText() + sBuffer);
		}
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSimpleFileCompare = new JFrame();
		frmSimpleFileCompare.setTitle("Simple File Compare for Java");
		frmSimpleFileCompare.setBounds(100, 100, 485, 345);
		frmSimpleFileCompare.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 271, 56};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmSimpleFileCompare.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Sificoja");
		Font font = new Font("Courier New",Font.PLAIN , 12);
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		frmSimpleFileCompare.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnNewButton = new JButton("File1");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		frmSimpleFileCompare.getContentPane().add(btnNewButton, gbc_btnNewButton);
		btnNewButton.addActionListener(this);
		
		textField = new JTextField();
		textField.setText("c:\\commands\\file1.txt");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		frmSimpleFileCompare.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("File2");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 2;
		frmSimpleFileCompare.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		btnNewButton_1.addActionListener(this);
		
		textField_1 = new JTextField();
		textField_1.setText("c:\\commands\\file2.txt");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 4;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		frmSimpleFileCompare.getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
			
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		frmSimpleFileCompare.getContentPane().add(scrollPane, gbc_scrollPane);
		scrollPane.setFont(font);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(textArea);
		
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_3.gridx = 0;
		gbc_btnNewButton_3.gridy = 4;
		frmSimpleFileCompare.getContentPane().add(btnNewButton_3, gbc_btnNewButton_3);
		btnNewButton_3.addActionListener(this);
		
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_2.gridx = 4;
		gbc_btnNewButton_2.gridy = 4;
		frmSimpleFileCompare.getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);
		btnNewButton_2.addActionListener(this);
	
	}

	public static Map<String, String> loadParameters(Sidacoja sdcj) {
		
    	sdcj.input("./resources/properties.xml");
    	sdcj.inputType("xml");
		sdcj.setCacheOnly(true);
    	sdcj.output("N/A");
    	sdcj.outputType("xml");
    	   	
    	RowCache cache = new RowCache();
    	try {
    		cache = sdcj.fire();
    	} catch( Exception e) {
    		console(e.getMessage() );
    		e.printStackTrace();
    	}
    	//console(""+cache.countSelected()+" parameter row read");
		
		//load hashmap
		Map<String,String> hmap = new HashMap<String,String>();
    	
		boolean titlePrint = true;
		List<Row> rowList = cache.getList();
		for(Row row: rowList) {
			List<Cell> cells = row.getList();
			if(titlePrint) {
				titlePrint = false;
				for(Cell cell: cells) {
					hmap.put(cell.getLabel(),cell.getValue());
				}
			}
		}
    	console(""+hmap.size()+" parameters read");
		return hmap;
	}	
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public static void console(String sz) {
		System.out.println(sz);
	}
}
