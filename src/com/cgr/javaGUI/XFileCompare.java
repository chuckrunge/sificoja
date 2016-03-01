package com.cgr.javaGUI;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class XFileCompare {
	
	public XFileCompare() {}

	String[] returnArray = new String[999];
	
	public String[] Compare(String fName1, String fName2) {

		System.out.println("File Compare: comparing " + fName1 + " and " + fName2);
		for(int i =0; i < 999;i++)
			returnArray[i] = ".";
		
		try{
			
		FileReader fR1 = new FileReader(fName1) ; //("C://commands//file1.txt");
		FileReader fR2 = new FileReader(fName2);  //"C://commands//file2.txt");
		BufferedReader bR1 = new BufferedReader(fR1);
		BufferedReader bR2 = new BufferedReader(fR2);
		
		FileWriter fW = new FileWriter("C://commands//Compare.txt");
		BufferedWriter bW = new BufferedWriter(fW);
		int ctr1=0, ctr2=0, r=0;
		String  szLine1="", szLine2="";
		boolean file1EOF  = false, file2EOF = false;
		
		szLine1 = readFile(bR1);
		if(szLine1==null) file1EOF = true;
		else ctr1++;
		szLine2 = readFile(bR2);
		if(szLine2==null) file2EOF = true;
		else ctr2++;
		
		while(!file1EOF || !file2EOF) {

			if(file1EOF) {
				System.out.println("file1 at eof");
				returnArray[r++] = String.format("%4d %s", ctr2,szLine2);
				System.out.println("file2 "+ctr2 + " " + szLine2);
				szLine2 = readFile(bR2);
				if(szLine2==null) file2EOF = true;
				else ctr2++;
				continue;
			}
			if(file2EOF) {
				System.out.println("file2 at eof");
				returnArray[r++] = String.format("%4d %s", ctr1,szLine1);
				System.out.println("file1 "+ctr1 + " " + szLine1);
				szLine1 = readFile(bR1);
				if(szLine1==null) file1EOF = true;
				else ctr1++;
				continue;
			}

			if(szLine1.compareTo(szLine2) != 0) {

				returnArray[r] = String.format("%4d %s", ctr1,szLine1);
				System.out.println(returnArray[r]);
				r = r + 1;
				returnArray[r] = String.format("%4d %s", ctr2,szLine2);
				System.out.println(returnArray[r]);
				r = r + 1;
				returnArray[r] = String.format("%4d %s", ctr1, showBytes(bW, szLine1, szLine2));
				System.out.println(returnArray[r]);
				r = r + 1;

			} 

			szLine1 = readFile(bR1);
			if(szLine1==null) file1EOF = true;
			else ctr1++;
			szLine2 = readFile(bR2);
			if(szLine2==null) file2EOF = true;
			else ctr2++;

		}
		
		fR1.close();
		fR2.close();
		fW.close();
		
		System.out.println("File1 Read:" + ctr1);
		System.out.println("File2 Read:" + ctr2);
		System.out.println();
		
		} //end try
		catch(Exception e) {
			System.out.println(e.getMessage());
		} //end catch
		return returnArray;
	}
	
	String readFile(BufferedReader bR) {
		String sz = "";
		try{
			sz = bR.readLine();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		return sz;
	}
	
	String showBytes(BufferedWriter bW, String szLine1, String szLine2) {

		String szArray3 = "";
		int j=0;
		if(szLine1.length() >= szLine2.length())
			j = szLine1.length();
		else 
			j = szLine2.length();
		
		for(int i = 0;i<j;i++) {
			
			if((i+1) > szLine1.length()) {
				szArray3 += "^";
			} else {
				if((i+1) > szLine2.length()) {
					szArray3 += "^";
				} else {
					if(szLine1.charAt(i)==szLine2.charAt(i)) { 
						szArray3 += "_";
					} else {
						szArray3 += "^";
					}	
				}
			}	
		}

		try {
			bW.write(szLine1 + "\r\n");
			bW.write(szLine2 + "\r\n");
			bW.write(szArray3 + "\r\n");
			bW.write("\r\n");
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return szArray3;
	}

}
