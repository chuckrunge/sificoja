package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;

import com.cgr.javaGUI.FileCompare;

public class TestA {

	@Test
	public void test01() {
		FileCompare fCompare = new FileCompare();
		int i = fCompare.scanForCount("C://commands//file1.txt");
		assertEquals("nbr input not equal", 20, i);
		//fail("Not yet implemented");
	}

	@Test
	public void test02() {
		FileCompare fCompare = new FileCompare();
		File outFile = new File("./Output.txt");
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(new FileReader(outFile));
		} catch(Exception e) {
			console(e.getMessage());
		}
		String sz = fCompare.readFile(bReader);
		console("*"+sz.substring(5,20)+"*");
		assertEquals("read file not equal", "Five Techniques", sz.substring(5, 20));
		//fail("Not yet implemented");
	}
public void console(String sz) {
	System.out.println(sz);
}
}
