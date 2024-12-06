package com.example.enum_validator.validator;

import com.example.enum_validator.annotations.ValueOfEnum;
import com.example.enum_validator.enums.EnumWithCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * Description:
 *
 * @author jiaozhiwang
 * {@code @date} 2024/11/15
 */

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, Integer> {
    private Enum<?>[] enumConstants;

    private int[] excluded;

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        enumConstants = constraintAnnotation.enumClass().getEnumConstants();
        excluded = constraintAnnotation.excluded();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (Arrays.stream(excluded).anyMatch(excluded -> Objects.equals(value, excluded))) {
            return false;
        }
        return Arrays.stream(enumConstants).anyMatch(e -> {
            if (e instanceof EnumWithCode) {
                return Objects.equals(((EnumWithCode) e).getCode(), value);
            } else {
                return e.ordinal() == value;
            }
        });
    }
}
