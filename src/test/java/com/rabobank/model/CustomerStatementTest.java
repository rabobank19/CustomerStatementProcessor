package com.rabobank.model;

import com.rabobank.model.validation.AbstractStatementTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the CustomerStatement.
 * Could include MeanBean also, but EqualsVerifier provides enough coverage already.
 */
public class CustomerStatementTest extends AbstractStatementTest {

    @Test
    public void equalsVerifierTest() {
    	 EqualsVerifier
         .forClass(CustomerStatement.class)
         .verify();
    }

    @Test
    public void testToString() {
    	assertThat(CustomerStatement.builder().build().toString())
        .contains("=null, ");
    }

    
    @Test
    public void whenReferenceIsNull_thenNotValid() {
    	assertValidity(CustomerStatement.builder() 
                .referenceNumber(null) 
                .accountNumber("1") 
                .description("foobar") 
                .startBalance(TEN) 
                .mutation(ONE) 
                .endBalance(ELEVEN) 
                .build(), 
        "referenceNumber may not be empty");
    }

    @Test
    public void whenReferenceIsFilled_thenIsValid() {
        assertValidity(CustomerStatement.builder() 
                .referenceNumber("1") 
                .accountNumber("1") 
                .description("foobar") 
                .startBalance(TEN) 
                .mutation(ONE) 
                .endBalance(ELEVEN) 
                .build());
    }

    @Test
    public void whenReferenceIsEmpty_thenNotValid() {
        assertValidity(CustomerStatement.builder() 
                        .referenceNumber("") 
                        .accountNumber("1") 
                        .description("foobar") 
                        .startBalance(TEN) 
                        .mutation(ONE) 
                        .endBalance(ELEVEN) 
                        .build(), 
                "referenceNumber may not be empty");
    }

    @Test
    public void whenReferenceIsBlank_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber(" ") 
                        .accountNumber("1") 
                        .description("foobar") 
                        .startBalance(TEN) 
                        .mutation(ONE) 
                        .endBalance(ELEVEN) 
                        .build(), 
                "referenceNumber may not be empty");
    }

   
    @Test
    public void whenAccountIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") 
                        .accountNumber(null) 
                        .description("foobar") 
                        .startBalance(TEN) 
                        .mutation(ONE) 
                        .endBalance(ELEVEN) 
                        .build(), 
                "accountNumber may not be empty");
    }

    @Test
    public void whenDescriptionIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") 
                        .accountNumber("foobar") 
                        .description(null) 
                        .startBalance(TEN) 
                        .mutation(ONE) 
                        .endBalance(ELEVEN) 
                        .build(), 
                "description may not be empty");
    }

  
    @Test
    public void whenStartBalanceIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") 
                        .accountNumber("1") 
                        .description("foobar") 
                        .startBalance(null) 
                        .mutation(ONE) 
                        .endBalance(ELEVEN) 
                        .build(), 
                "startBalance must not be null");
    }

    @Test
    public void whenMutationIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") 
                        .accountNumber("1") 
                        .description("foobar") 
                        .startBalance(TEN) 
                        .mutation(null) 
                        .endBalance(ELEVEN) 
                        .build(), 
                "mutation must not be null");
    }

    @Test
    public void whenEndBalanceIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") 
                        .accountNumber("1") 
                        .description("foobar") 
                        .startBalance(TEN) 
                        .mutation(ONE) 
                        .endBalance(null) 
                        .build(), 
                "endBalance must not be null");
    }

}