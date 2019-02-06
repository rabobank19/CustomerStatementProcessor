package com.rabobank.model.validation;

import java.math.BigDecimal;

/**
 * This interface is
 * for validation of the balance of statement.
 */
@BalancedStatementConstraint
public interface BalancedStatement {

  BigDecimal getStartBalance();
  
  BigDecimal getMutation();
  
  BigDecimal getEndBalance();
  
}
