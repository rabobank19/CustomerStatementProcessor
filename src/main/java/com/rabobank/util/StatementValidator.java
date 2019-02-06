package com.rabobank.util;

import com.rabobank.model.CustomerStatement;
import com.rabobank.model.StatementError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static javax.validation.Validation.buildDefaultValidatorFactory;

/**
 * This class is to validate the customer statement.
 * @author balaji
 *
 */
public final class StatementValidator {

  private Validator beanValidator = buildDefaultValidatorFactory() //
            .getValidator();
  private Set<String> referenceNumbers = new HashSet<>();

  /**
   * This method is to validate the customer statement.
   * @param statement describes statement
   * @return
   */
  public List<StatementError> validate(final CustomerStatement statement) {
    List<StatementError> result = new ArrayList<>();
    if (!referenceNumbers.add(statement.getReferenceNumber())) {
      result.add(new StatementError(statement,
                  "referenceNumber already processed"));
    }
    for (ConstraintViolation<CustomerStatement> violation
               : this.beanValidator.validate(statement)) {
      result.add(buildStatementError(violation));
    }
    return result;
  }

  /**
   * This statement is to log error message during parsing.
   * @param violation describes statement violation
   * @return
   */
  private StatementError buildStatementError(
            final ConstraintViolation<CustomerStatement> violation) {
    StringBuffer errMsg = new StringBuffer();
    errMsg.append(violation.getPropertyPath());
    errMsg.append(' ');
    errMsg.append(violation.getMessage());
    return new StatementError((CustomerStatement) violation.getLeafBean(),
                errMsg.toString());
  }
}
