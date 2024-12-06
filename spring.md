---
title: Spring如何对Enum进行validate
date: 2024-12-05 14:12:21
tags: [Java,Spring,Validate,Enum,MethodArgumentNotValidException]

---

### Spring如何对Enum进行validate

#### 前言

Spring的HTTP请求中，一般都需要对接口参数进行校验，如：在对象上使用`@NotNull`等注解；
那么如何对枚举(enum)进行校验呢？

1. 新建一个自定义注解`@ValueOfEnum`：
    ```java
    @Documented
    @Constraint(validatedBy = ValueOfEnumValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ValueOfEnum {
    
        String message() default "value must be one of {values}";
    
        Class<?>[] groups() default {};
    
        Class<? extends Payload>[] payload() default {};
    
        Class<? extends Enum<?>> enumClass();
    
        int[] excluded() default {};
    }
    ```
   
2. 新建一个自定义校验器：
    ```java
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
    ```
   
3. 使用该自定义注解：
    ```java
    @Data
    public class FooCmd implements Serializable {

        @NotNull(message = "enum type must not be empty")
        @ValueOfEnum(enumClass = FooEnum.class, excluded = {100}, message = "enum type must be 200 or 300")
        private Integer value;
    }
    ```

#### 示例代码

完整示例代码：[Enum validator](https://github.com/imwower/enum_validator)