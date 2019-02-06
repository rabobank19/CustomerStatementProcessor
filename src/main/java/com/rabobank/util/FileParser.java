package com.rabobank.util;

import com.rabobank.model.CustomerStatement;
import com.rabobank.model.StatementError;
import com.rabobank.model.StatementErrorResult;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an abstract FileParser.
 * @author balaji
 *
 */
public abstract class FileParser {

  private ReportWriter reportWriter;
  private int recordCount = 0;
  private StatementValidator validator = new StatementValidator();

  public FileParser(final PrintStream printStream) {
    this.reportWriter = new ReportWriter(printStream);
  }
  
  public abstract void process(final InputStream stream);
  
  public abstract StatementErrorResult fileProcess(final InputStream stream, String fileName);

  protected abstract CustomerStatement parseStatement(Object value);

  /**
   * This method is to process the customer statement.
   * @param value descrobes object value
   */
  protected void processStatement(Object value) {
    CustomerStatement statement = this.parseStatement(value);
    if (statement != null) {
      this.recordCount++;
      
      this.reportWriter.printViolations(
          this.validator.validate(statement));      
      
    }
  }
  
  /**
   * This method is to process the customer statement.
   * @param value descrobes object value
   */
  protected List<StatementError> fetchStatement(Object value) {
    CustomerStatement statement = this.parseStatement(value);
    List<StatementError> statementErrorList = new ArrayList<>();
    if (statement != null) {
      this.recordCount++;
      
      statementErrorList = 
    		  this.validator.validate(statement);
       
    }
    return statementErrorList;
  }


  /**
    * This method is to write the end report.
    */
  protected void endReport() {
    this.reportWriter.endReport(this.recordCount);
  }
  
  /**
   * This method is to write the end report.
   */
  protected int getRecordCount() {
     return this.recordCount;
  }
}
