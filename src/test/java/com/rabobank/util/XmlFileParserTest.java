package com.rabobank.util;

import java.io.InputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

public class XmlFileParserTest extends TestWithPrintStreamInterception {
	
	 private XmlFileParser sut;
	
	 @Before
	 public void before() {
	   super.before();
	   this.sut = new XmlFileParser(new PrintStream(this.stream));
	 }
	 
	 @Test   
	 public void testEmpty() {
		
	    letSutProcess("empty.xml");
	    assertIsWritten("No. of statements processed: 0");
	    assertIsWritten("No. of violations found: NONE");
	 }
	 
	 private void letSutProcess(final String fileName) {

	        InputStream stream = this.getClass().getClassLoader()
	                .getResourceAsStream(fileName);
	        sut.process(stream);
	 }

}
