package com.example.enum_validator.enums;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author jiaozhiwang
 * {@code @date} 2024/12/6
 */

@Getter
public enum FooEnum implements Serializable, EnumWithCode {
    A(100, "A"),
    B(200, "B"),
    C(300, "C"),
    ;

    private final int code;
    private final String value;

    FooEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    static final Map<Integer, FooEnum> enumMap = new HashMap<>();

    static {
        for (FooEnum type : FooEnum.values()) {
            enumMap.put(type.getCode(), type);
        }
    }

    public static FooEnum get(Integer value) {
        return enumMap.get(value);
    }
}
