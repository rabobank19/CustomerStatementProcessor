package com.rabobank.util;

import com.rabobank.model.CustomerStatement;

import java.math.BigDecimal;

/**
 * This class is to parse the csv file line by line.
 * @author balaji
 *
 */
public final class CsvLineParser {

  private static final int CSV_COLUMN_COUNT = 6;

  /**
   * This method is to parse the customer statements.
   * @param line describe line
   * @return
   */
  public CustomerStatement parse(final String line) {
    String[] columns = line.split(",");
    if (columns.length != CSV_COLUMN_COUNT) {
      throw new IllegalArgumentException("CSV line should contain "
                    + CSV_COLUMN_COUNT + " elements");
    }
      
    if ("Reference".equals(columns[0])) {
      return null;
    }
      
    int c = 0;
      
    return CustomerStatement.builder()
                .referenceNumber(columns[c++])
                .accountNumber(columns[c++])
                .description(columns[c++])
                .startBalance(this.parseBigDec(columns[c++]))
                .mutation(this.parseBigDec(columns[c++]))
                .endBalance(this.parseBigDec(columns[c++]))
                .build();
  }

  protected BigDecimal parseBigDec(final String value) {
    return new BigDecimal(value).setScale(2);
  }
}
