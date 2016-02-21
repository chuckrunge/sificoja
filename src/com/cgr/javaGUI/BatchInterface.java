package com.cgr.javaGUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BatchInterface {

	public static void main(String[] args) {
		if(args.length==3) {
			console("Sifacoja - Simple File Compare for Java");
			} else {
				console("Command format: java -jar sifacoja.jar BatchInterface file1.txt file2.txt");
				//console(args.length+" args received");
				return;
		}
		try{
		
		File outFile = new File("./Output.txt");
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(outFile));
		int i = 0;
		String[] szArray = new String[9999];
		FileCompare fCompare = new FileCompare();
		szArray = fCompare.Compare(args[1], args[2]);

		StringBuffer sBuffer = new StringBuffer();
		for(i=0;i<szArray.length;i++) {
			if(szArray[i].equals(".")) {
				i = szArray.length;
			} else {
				sBuffer.append(szArray[i] + "\n");
				bWriter.write(szArray[i] + "\n");			}
		}
		//console(sBuffer.toString());
		bWriter.close();

		} catch(IOException ioe) {
			console(ioe.getMessage());
		}
	}

	public static void console(String sz) {
		System.out.println(sz);
	}

}
