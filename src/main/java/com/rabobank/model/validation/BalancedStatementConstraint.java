package com.rabobank.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This is description of BalancedStatementConstraint annotation.
 * 
 */
@Constraint(validatedBy = BalancedStatementValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BalancedStatementConstraint {

  String message() default "will be overwritten";
  
  Class<?>[] groups() default { };
  
  Class<? extends Payload>[] payload() default { };
}
