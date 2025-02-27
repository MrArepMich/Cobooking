package com.repinsky.cobooking.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CostMinValidator implements ConstraintValidator<CostMin, BigDecimal> {

    private BigDecimal configuredMinCost;

    @Value("${booking.unit.minCost}")
    public void setConfiguredMinCost(String minCost) {
        this.configuredMinCost = new BigDecimal(minCost);
    }

    @Override
    public boolean isValid(BigDecimal cost, ConstraintValidatorContext context) {
        if (cost == null) {
            return true;
        }
        if (cost.compareTo(configuredMinCost) > 0) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Cost must be greater than " + configuredMinCost
            ).addConstraintViolation();
            return false;
        }
    }
}
