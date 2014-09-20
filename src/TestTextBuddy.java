


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.Assert.*;

public class TestTextBuddy extends TestCase{    

    public void setUp() throws IOException{
    	TextBuddy.fileName = "TEST.txt";
    	File file = new File(TextBuddy.fileName);
    	file.createNewFile();
    	TextBuddy.readFile(TextBuddy.fileName);
    }
    
    @Test
    public void testCheckFileNameValidation() {
    	String testString1 = "abcd.txt";
    	String testString2 = "abc/asdf.txt";
    	String testString3 = "fff:ddd.txt";
    	String testString4 = "";
    	String testString5 = "okkk";
    	
    	assertTrue(TextBuddy.rightFormatOfName(testString1));
    	assertTrue(TextBuddy.rightFormatOfName(testString5));
    	assertFalse(TextBuddy.rightFormatOfName(testString2));
    	assertFalse(TextBuddy.rightFormatOfName(testString3));
    	assertFalse(TextBuddy.rightFormatOfName(testString4));
    }
    
	@Test
	public void testAdd() {
		String testString1 = "TEST1";
		String testString2 = "TEST2";
		List<String> temp = new ArrayList<String>();
		TextBuddy.add(testString1);
		temp.add(testString1);
		assertTrue(TextBuddy.text.equals(temp));
		TextBuddy.add(testString2);
		temp.add(testString2);
		assertTrue(TextBuddy.text.equals(temp));
		TextBuddy.text.clear();
	}
	
	@Test
	public void testDelete() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String testString1 = "TEST1";
		String testString2 = "TEST2";	
		TextBuddy.text.clear();
		TextBuddy.add(testString1);
		TextBuddy.delete(2);
		assertEquals(TextBuddy.text.size(), 1);
		TextBuddy.add(testString2);
		TextBuddy.delete(1);
		assertEquals(TextBuddy.text.size(), 1);
		TextBuddy.delete(1);
		assertEquals(TextBuddy.text.size(), 0);
		TextBuddy.text.clear();
		System.setOut(null);
	}
	
	@Test
	public void testSort() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String testString1 = "TEST1";
		String testString2 = "TEST2";
		String testString3 = "TEST3";
		String testString4 = "TEST4";
		String result;
		TextBuddy.add(testString4);
		TextBuddy.add(testString3);
		TextBuddy.add(testString2);
		TextBuddy.add(testString1);
		TextBuddy.sort();
		result = TextBuddy.text.get(0);
		assertEquals(result, testString1);
		result = TextBuddy.text.get(1);
		assertEquals(result, testString2);
		result = TextBuddy.text.get(2);
		assertEquals(result, testString3);
		result = TextBuddy.text.get(3);
		assertEquals(result, testString4);
		TextBuddy.text.clear();
	}
	
	@Test
	public void testSearch() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String testString1 = "TEST1";
		String testString2 = "TEST2";
		String testString3 = "TEAST3";
		String testString4 = "TEBST4";	
		String testKey = "TEST";
		String testWrongKey = "WRONG";
		List<String> findList=new ArrayList<String>();
		List<String> expectedList=new ArrayList<String>();
		boolean find=true;
		expectedList.add(testString1);
		expectedList.add(testString2);
		expectedList.add(testString3);
		expectedList.add(testString4);
		TextBuddy.add(testString1);
		TextBuddy.add(testString2);
		TextBuddy.add(testString3);
		TextBuddy.add(testString4);
		findList=TextBuddy.find(testWrongKey);
		assertEquals(findList.size(),0);
		findList=TextBuddy.find(testKey);
		for(String i: findList)
			if(!expectedList.contains(i)) find=false;
		assertTrue(find);
		TextBuddy.text.clear();
	}
	
	@Test
	public void testClear() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String testString1 = "TEST1";
		String testString2 = "TEST2";
		String testString3 = "TEST3";
		String testString4 = "TEST4";
		TextBuddy.add(testString4);
		TextBuddy.add(testString3);
		TextBuddy.add(testString2);
		TextBuddy.add(testString1);	
		TextBuddy.clear();
		assertTrue(TextBuddy.text.isEmpty());
		TextBuddy.text.clear();
	}
		
	@Test
	public void testDisplay() {
		String testString1 = "TEST1";
		String testString2 = "TEST2";
		String testString3 = "TEST3";
		String testString4 = "TEST4";		
		String testOutput = "1.TEST1\n2.TEST2\n3.TEST3\n4.TEST4\n";
		TextBuddy.add(testString1);
		TextBuddy.add(testString2);
		TextBuddy.add(testString3);
		TextBuddy.add(testString4);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		TextBuddy.display(TextBuddy.text);
		assertEquals(testOutput, outContent.toString());
		System.setOut(null);
		TextBuddy.text.clear();
	}
	
}
