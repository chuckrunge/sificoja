package com.cgr.javaGUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.sidacoja.utils.Common;

public class FileCompare {
	
	public FileCompare() {}
	
	public int maxSize = 0;
	public int readAheadNbr = 0;
	public int pctForMatch = 0;
	public boolean sort = false; //true;
	public String[] file1Array = null;
	public String[] file2Array = null;
	public String[] returnArray = null;
	public static String INSERT = "insert";
	public static String DELETE = "delete";
	public static String CHANGE = "change";
	Common common = new Common();
	
	public int getReadAheadNbr() {
		return readAheadNbr;
	}

	public void setReadAheadNbr(int readAheadNbr) {
		this.readAheadNbr = readAheadNbr;
	}

	public int getPctForMatch() {
		return pctForMatch;
	}

	public void setPctForMatch(int pctForMatch) {
		this.pctForMatch = pctForMatch;
	}

	public boolean isSort() {
		return sort;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public String[] Compare(String fName1, String fName2) {

		System.out.println("File Compare: comparing " + fName1 + " and " + fName2);

		try{
		int f1Ctr=0,f2Ctr=0;
		f1Ctr = scanForCount(fName1);
		f2Ctr = scanForCount(fName2);
	    if(f1Ctr>=f2Ctr) maxSize = f1Ctr+1;
	    if(f2Ctr>=f1Ctr) maxSize = f2Ctr+1;
	    //loadParameters();
		if(maxSize>0) {
			console("using maxSize="+maxSize);
			file1Array = new String[maxSize];
			file2Array = new String[maxSize];
			returnArray = new String[maxSize*2];
		}
		
		FileReader fR1 = new FileReader(fName1) ; //("C://commands//file1.txt");
		FileReader fR2 = new FileReader(fName2);  //"C://commands//file2.txt");
		BufferedReader bR1 = new BufferedReader(fR1);
		BufferedReader bR2 = new BufferedReader(fR2);
		
		FileWriter fW = new FileWriter("./Compare.txt");
		BufferedWriter bW = new BufferedWriter(fW);
		
		int ctr1=0, ctr2=0, r=0;
		StringBuffer  szLine1 = new StringBuffer();
		StringBuffer  szLine2 = new StringBuffer();
		boolean file1EOF  = false, file2EOF = false;
		
		szLine1.append(readFile(bR1));
		if(szLine1.toString()=="null") file1EOF = true;
		//console("#"+ctr1+" "+szLine1.toString());
		szLine2.append(readFile(bR2));
		if(szLine2.toString()=="null") file2EOF = true;
		//console("#"+ctr2+" "+szLine2.toString());
		
		while(!file1EOF || !file2EOF) {

			if(!file1EOF) {
//				if(common.isNullOrEmpty(szLine1.toString()))
//					file1Array[ctr1] = "."; //filler for empty  string
//				else
				file1Array[ctr1] = szLine1.toString();
				
				szLine1.delete(0, szLine1.length());
				szLine1.append(readFile(bR1));
				//console("file1" + szLine1.toString());
				if(szLine1.toString().equals("null")) {
					file1EOF = true;
					file1Array[ctr1+1] = "{|}";
				} else ctr1++;
			}	
			
			if(!file2EOF) {
				//if(common.isNullOrEmpty(szLine2.toString()))
				//	file2Array[ctr2] = "."; //filler for empty string
				//else	
				file2Array[ctr2] = szLine2.toString(); 
				
				szLine2.delete(0, szLine2.length());
				szLine2.append(readFile(bR2));
				//console("file2" + szLine2.toString());
				if(szLine2.toString().equals("null")) { 
					file2EOF = true;
					file2Array[ctr2+1] = "{|}";
				} else ctr2++;
			}	
	
		} //end while

		fR1.close();
		fR2.close();
		console("loaded: " + ctr1 +" "+ ctr2);
		
	    if(maxSize>f1Ctr+1) {
	    	int limit = (maxSize-f1Ctr)-1;
	    	console("file1 needs "+limit+" rows");
	    	for(int n=maxSize-1;(n>maxSize-limit-1);n--) {
	    		file1Array[n] = ".";
	    		//console("file1 needed "+n);
	    	}
	    }
	    if(maxSize>f2Ctr+1) {
	    	int limit = (maxSize-f2Ctr)-1;
	    	console("file2 needs "+limit+" rows");
	    	for(int n=maxSize-1;n>maxSize-f2Ctr-1;n--) {
	    		file2Array[n] = ".";
	    		//console("file2 needed "+n);
	    	}
	    }
		
		//displayArray("file array #1: ",file1Array,(maxSize));
		//displayArray("file array #2: ",file2Array,(maxSize));

	    if(sort) {
			console("sorting...");
  	        List<String> list = Arrays.asList(file1Array); 
			Collections.sort(list,String.CASE_INSENSITIVE_ORDER);
			console("sorted first one");
		    list = Arrays.asList(file2Array); 
			Collections.sort(list,String.CASE_INSENSITIVE_ORDER);
		}
	    
		int idx = 0;
	    for(String sz1: file1Array) {
	    	if(".".equals(sz1)) {
	    		file1Array[idx] = "";
	    	}
	    	idx++;	//console("file1 needed "+n);
	    }
		idx = 0;
	    for(String sz2: file2Array) {
	    	if(".".equals(sz2)) {
	    		file2Array[idx] = "";
	    	}
	    	idx++;	//console("file1 needed "+n);
	    }

		String sz = "";
		
		//arrays are loaded 
		int i = 0,j = 0; 

		while( i < maxSize && j < maxSize && ( !"{|}".equals(file1Array[i]) || !"{|}".equals(file2Array[j]))) {
			
			szLine1.delete(0, szLine1.length());
			szLine1.append(file1Array[i]);
			szLine2.delete(0, szLine2.length());
			szLine2.append(file2Array[j]);
			
			if("{|}".equals(file1Array[i]) ) { //EOF file1
				console("file1 at eof");
				//console("i: " + i + " j:" +j+ " r:" +r);
				if(j < (maxSize - 1)) {
					returnArray[r++] = writeToAll(INSERT, bW, j, file2Array[j]);
				}
				j++;
				continue;
			}
			if("{|}".equals(file2Array[j]) ) {  //EOF file2
				console("file2 at eof");
				//console("i: " + i + " j:" +j);
				if(i < (maxSize - 1)) {
					returnArray[r++] = writeToAll(DELETE, bW, i, file1Array[i]);
				}
				i++;
				continue;
			}

			if(szLine1.toString().compareTo(szLine2.toString()) != 0) {
				// did file2 delete or insert a line?  which line number?
				//console("eval#1: "+szLine1.toString() +
				//	" eval#2: "+szLine2.toString() );
				if(readAheadNbr>0) {
					boolean readAheadDone = false;
					int k=0;
					
					//protect read ahead from nulls
					String nextLine = file1Array[(i+1)];
					if(nextLine == null) readAheadDone = false;
					
					//was it a delete?
					k = determineIfSync(file1Array, szLine2.toString(), j);
					if(k!=0 && (k-i)<=readAheadNbr && !readAheadDone) {
						console("reading ahead for delete...");
						for(int m=i;m<k;m++) {
							returnArray[r] = writeToAll(DELETE,bW, i, file1Array[i]);
							r++;
							if("{|}".equals(file1Array[(i+1)])) {
								m = k;
							} else i++; //printed above, moving on 
							
						}
						szLine1.delete(0, szLine1.length());
						szLine1.append(file1Array[i]);
						console("ended read ahead for delete: "+(i+1)+" "+(j+1));
						if(!"{|}".equals(file1Array[(i+1)]))
							i++; //it matches now
						j++;
						readAheadDone = true;
						continue;
					}

					//if delete read ahead was skipped, resume processing
					if(nextLine == null) readAheadDone = true;

					//not a delete - now check for inserts
					//1) szLine1 has changed 2) insert would be file2 only
					k = determineIfSync(file1Array, szLine2.toString(), i);
					if(k==0 && (k-i)<=readAheadNbr && !readAheadDone) {
						boolean match = false;
						if(pctForMatch>0) {
							match = true;
							sz = showBytes(bW, szLine1.toString(), szLine2.toString());
							int pctMatch = countCaps(sz);
							console("ck: "+ pctMatch +  " " + pctForMatch);
							if(pctMatch == 100 || pctMatch < pctForMatch) {
								console(pctMatch+" < "+pctForMatch);
								match = false;
							}
						}
						if(match) {
							returnArray[r] = writeToAll(CHANGE, bW, i, file1Array[i]);
							r++;
							returnArray[r] = writeToAll(CHANGE, bW, j, file2Array[j]);
							r++;
							returnArray[r] = writeToAll(CHANGE,  bW, i, showBytes(bW, szLine1.toString(), szLine2.toString()));
							r++;

							i++;
							j++;
							continue;
						}
						console("!!! reading ahead for insert...");
						returnArray[r] = writeToAll(INSERT, bW, j, file2Array[j]);
						r++;
						j++; //printed above, moving on
						//???if("{|}".equals(file2Array[j])) m = k;
						//}//for(int m=j;m<k;m++) {
						//	returnArray[r] = writeToAll(INSERT, bW, j, file2Array[j]);
						//	r++;
						//	j++; //printed above, moving on
						//	if("{|}".equals(file2Array[j])) m = k;
						//}
						szLine2.delete(0, szLine2.length());
						szLine2.append(file2Array[j]);
						console("ended reading ahead for insert: "+i+" "+(j+1));
						i++; //it matches now
						if(!"{|}".equals(file2Array[j]))
							j++;
						readAheadDone = true;
						continue;
					}
				}	

				if(!szLine1.toString().equals(szLine2.toString())) {
					boolean match = false;
					if(pctForMatch>0) {
						match = true;
						sz = showBytes(bW, szLine1.toString(), szLine2.toString());
						int pctMatch = countCaps(sz); //pc difference
						if(pctMatch < pctForMatch) {
							console(pctMatch+" < "+pctForMatch);
							match = false;
						}
					}
					if(!match && r<maxSize-2) {
						returnArray[r] = String.format("%4d %s", i+1, szLine1);
						console(returnArray[r]);
						//bW.write(returnArray[r]+"\n");
						r++;
						returnArray[r] = String.format("%4d %s", j+1, szLine2);
						console(returnArray[r]);
						//bW.write(returnArray[r]+"\n");
						r++;
						returnArray[r] = String.format("%4d %s", i+1, showBytes(bW, szLine1.toString(), szLine2.toString()));
						console(returnArray[r]);
						//bW.write(returnArray[r]+"\n");
						r++;
					}
					i++;
					j++;
				}
			} else { //match!
				i++;j++;
			} //end else	
		} //end while

		if(r < maxSize) 
			returnArray[r] = "{|}";
		bW.close();
		
		System.out.println("File1 Read:" + f1Ctr); //i
		System.out.println("File2 Read:" + f2Ctr); //j
		System.out.println();
		
		} //end try
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} //end catch

		return returnArray;
	}
	
	public String readFile(BufferedReader bR) {
		String sz = "";
		try{
			sz = bR.readLine();
			if(sz==null) {
				sz = "null";
			}
			return sz;
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		if(sz.isEmpty()) {
			return ".";
		} else {
			return sz;
		}
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

		return szArray3;
	}
	/*
	 * check if line is included farther down
	 */
	public int determineIfSync(String[] szArray, String szLine, int i) {
	
		for(int x=i;x<szArray.length;x++) {
			if(szLine.equals(szArray[x])) {
				return x;
			}
		}
		return 0;
	}
/*
 * utility method to verify array
 */
	public void displayArray(String sz, String[] fileArray, int ctr) {
		console(sz);
		for(int i=0;i<ctr;i++) {
			console(i+fileArray[i]);
		}
		
	}	
	
	public void console(String sz) {
		System.out.println(sz);
	}
	/*
	 *  parm file has been replaced with xml
	 */
	public void loadParameters() {
		String name = "";
		try {
		FileReader sP2 = new FileReader("./sificoja.props");  
		BufferedReader bR2 = new BufferedReader(sP2);
		String line = bR2.readLine();
		while(!(line==null)) {
			int i = line.indexOf('=');
			if(i>0) {
				name = line.substring(0, i);
				if("sort".equals(name)){
					sort = Boolean.parseBoolean(line.substring(i+1));
					console("sort="+sort);
				}
				if("readAheadNbr".equals(name)){
					readAheadNbr = Integer.parseInt(line.substring(i+1));
					console("readAheadNbr="+readAheadNbr);
				}
				if("pctForMatch".equals(name)){
					pctForMatch = Integer.parseInt(line.substring(i+1));
					console("pctForMatch="+pctForMatch);
				}
			}
			line = bR2.readLine();
		}
		bR2.close();
		} catch(Exception e) {
			console(e.getMessage());
			e.printStackTrace();
		}
		
	}
	public int scanForCount(String fName) {
		int ctr = 0;
		StringBuffer scStringBuffer = new StringBuffer();
		try {
			Scanner sc = new Scanner(new File(fName));
			while (sc.hasNextLine()) {
				scStringBuffer.delete(0, scStringBuffer.length()); 
				scStringBuffer.append(sc.nextLine());
				ctr++;
			}
			sc.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ctr;
	}
	/*
	 * determine percent match by counting carots
	 */
	public int countCaps(String szArray) {
		int zCtr=0;
		for(int z=0;z<szArray.length();z++) {
			if(szArray.charAt(z)=='^')
				zCtr++;
		}
		console(zCtr+" caps found in "+szArray.length()+" chars");
		int x = 100 - ( 100 * zCtr / szArray.length() );
		return x;
	}
	/*
	 * utility method for writing to outputs in sync
	 */
	public String writeToAll(String msg, BufferedWriter bw, int i, String lineIn) {
		try {
			console(msg+" "+ (i+1) + " " + lineIn);
			bw.write(msg+" "+ (i+1) + " " + lineIn+"\n");
		} catch (Exception e) {
			console("write failed:"+e.getMessage());
		}
		return String.format(msg + " %4d %s", (i+1), lineIn);
	}

}
