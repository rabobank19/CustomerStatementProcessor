package com.rabobank.util;

import com.rabobank.model.CustomerStatement;
import noNamespace.CustomerStatementType;

/**
 * This class is to implement the parse method.
 * @author balaji
 *
 */
public class XmlStatementParser {

  /**
   * The method parse is to build a Customer Statement object.
   * @param value describes customer statement type
   * @return
   */
  public CustomerStatement parse(final CustomerStatementType value) {
    return CustomerStatement.builder()
                .referenceNumber(value.getReference())
                .accountNumber(value.getAccountNumber())
                .description(value.getDescription())
                .startBalance(value.getStartBalance())
                .mutation(value.getMutation())
                .endBalance(value.getEndBalance())
                .build();
  }
}
