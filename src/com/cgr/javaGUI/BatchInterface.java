package com.cgr.javaGUI;

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
		
		int i = 0;
		String[] szArray = new String[9999];
		FileCompare mCompare = new FileCompare();
		szArray = mCompare.Compare(args[1], args[2]);

		StringBuffer sBuffer = new StringBuffer();
		for(i=0;i<szArray.length;i++) {
			
			if(szArray[i] == null || szArray[i] == "{|}") {
				i = szArray.length;
			} else {
				sBuffer.append(szArray[i] + "\n");
				console(szArray[i]);
				//bWriter.write(szArray[i] + "\n");			
				}
		}
		//console(sBuffer.toString());
		//bWriter.close();

		} catch(Exception ioe) {
			console(ioe.getMessage());
			ioe.printStackTrace();
		}
	}

	public static void console(String sz) {
		System.out.println(sz);
	}

}
