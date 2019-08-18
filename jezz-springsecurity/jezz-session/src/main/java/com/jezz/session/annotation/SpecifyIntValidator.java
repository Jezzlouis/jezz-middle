package com.jezz.session.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpecifyIntValidator implements ConstraintValidator<SpecifyIntValue,Integer> {

    private int[] array;
    private String message;

    @Override
    public void initialize(SpecifyIntValue specifyIntValue) {
        this.array = specifyIntValue.array();
        this.message = specifyIntValue.message();
    }

    @Override
    public boolean isValid(Integer requireValidatedValue, ConstraintValidatorContext constraintValidatorContext) {
        if(requireValidatedValue == null) return false;
        for (int item : array) {
            if (item == requireValidatedValue.intValue()) {
                return true;
            }
        }
        return false;
    }
}
