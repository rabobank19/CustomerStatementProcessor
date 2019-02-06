package com.rabobank.model.validation;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * This class is to validate the balance.
 */
public final class BalancedStatementValidator
        implements ConstraintValidator
            <BalancedStatementConstraint, BalancedStatement> {

  @Override
  public void initialize(final BalancedStatementConstraint constraint) {
  }

  @Override
  public boolean isValid(final BalancedStatement statement,
                         final ConstraintValidatorContext context) {

    if (statement.getStartBalance() == null
            || statement.getMutation() == null
            || statement.getEndBalance() == null) {
      return true;
    }

    BigDecimal expectedEndBalance = statement.getStartBalance() //
            .add(statement.getMutation()) //
            .setScale(2);

    boolean isValid = expectedEndBalance.equals(statement.getEndBalance() //
            .setScale(2));
      
    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("expected to be "
              + expectedEndBalance) //
              .addPropertyNode("endBalance") //
              .addConstraintViolation();
    }
    return isValid;
  }
}
