package com.example.enum_validator.model;

import com.example.enum_validator.annotations.ValueOfEnum;
import com.example.enum_validator.enums.FooEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Description:
 *
 * @author jiaozhiwang
 * {@code @date} 2024/12/6
 */

@Data
public class FooCmd implements Serializable {

    @NotNull(message = "enum type must not be empty")
    @ValueOfEnum(enumClass = FooEnum.class, excluded = {100}, message = "enum type must be 200 or 300")
    private Integer value;
}
