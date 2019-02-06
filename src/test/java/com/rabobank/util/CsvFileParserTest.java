package com.rabobank.util;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.PrintStream;

public class CsvFileParserTest extends TestWithPrintStreamInterception {

    private CsvFileParser sut;

    @Before
    public void before() {
        super.before();
        this.sut = new CsvFileParser(new PrintStream(this.stream));
    }

    @Test    
    public void testEmpty() {
        letSutProcess("empty.csv");
        assertIsWritten("No. of statements processed: 0");
        assertIsWritten("No. of violations found: NONE");
    }

    @Test
    public void testValidRecords() {
        letSutProcess("validRecords.csv");
        assertIsWritten("No. of statements processed: 2");
        assertIsWritten("No. of violations found: NONE");
    }

    @Test
    public void testInvalidRecords() {
        letSutProcess("invalidRecords.csv");
        assertIsWritten("No. of statements processed: 2");
        assertIsWritten("No. of violations found: 2");
        assertIsWritten("endBalance expected to be");
        assertIsWritten("referenceNumber already processed");
    }

    @Test(expected = NullPointerException.class)
    public void testNonexistingFile() {
        letSutProcess("doesNotExist");
    }

    private void letSutProcess(final String fileName) {

        InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);
        sut.process(stream);
    }
}