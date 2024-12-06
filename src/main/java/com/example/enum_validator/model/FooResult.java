package com.example.enum_validator.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author jiaozhiwang
 * {@code @date} 2024/12/6
 */

@Data
public class FooResult implements Serializable {

    private String name;

    private Integer code;
}
