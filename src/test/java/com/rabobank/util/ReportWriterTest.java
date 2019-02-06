package com.rabobank.util;

import com.rabobank.model.CustomerStatement;
import com.rabobank.model.StatementError;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReportWriterTest extends TestWithPrintStreamInterception {

   private ReportWriter sut;

    @Before
    public void before() {
        super.before();
        this.sut = new ReportWriter(new PrintStream(this.stream));
    }

    
    @Test
    public void assertThatHeaderIsWritten() {
        sut.printViolations(buildErrors("System says no"));
        assertIsWritten("RefNo.   Description");
    }

    
    @Test
    public void assertThatNoViolationsAreReported() {
        sut.printViolations(buildErrors());
        sut.endReport(12);
        assertIsWritten("No. of violations found: NONE");
    }

    @Test
    public void assertThatValidationsAreWritten() {
        sut.printViolations(buildErrors("It does not compute", "This is wrong"));
        assertIsWritten("It does not compute");
        assertIsWritten("This is wrong");
    }

    @Test
    public void assertThatNrStatementsIsWritten() {
        sut.endReport(12);
        assertIsWritten("No. of statements processed: 12");
        assertIsWritten("No. of violations found: NONE");
    }

    @Test
    public void assertThatNrViolationsIsWritten() {
        sut.printViolations(buildErrors("It does not compute"));
        sut.printViolations(buildErrors("This is wrong"));
        sut.endReport(0);
        assertIsWritten("No. of violations found: 2");
    }
    

    private Collection<StatementError> buildErrors(String... messages) {
        List<StatementError> result = new ArrayList<>();
        for (String message: messages) {
            result.add(new StatementError(CustomerStatement.builder()
                    .referenceNumber("1")
                    .description("foobar")
                    .build(), message));
        }
        return result;
    }
    
}