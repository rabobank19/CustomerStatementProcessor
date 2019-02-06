package com.rabobank.model;

import com.rabobank.model.validation.BalancedStatement;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

/**
 * This class is customer statement.
 * The balance is validated by implementing BalancedStatement.
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public final class CustomerStatement implements BalancedStatement {

  @NotBlank private final String referenceNumber;
  @NotBlank private final String accountNumber;
  @NotBlank private final String description;
  @NotNull private final BigDecimal startBalance;
  @NotNull private final BigDecimal mutation;
  @NotNull private final BigDecimal endBalance;
  
}
