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
public enum ResultCode implements Serializable, EnumWithCode {
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "bad request"),
    ;

    private final int code;
    private final String value;

    ResultCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    static final Map<Integer, ResultCode> enumMap = new HashMap<>();

    static {
        for (ResultCode type : ResultCode.values()) {
            enumMap.put(type.getCode(), type);
        }
    }

    public static ResultCode get(Integer value) {
        return enumMap.get(value);
    }
}
