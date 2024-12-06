package com.example.enum_validator.controller;

import com.example.enum_validator.annotations.ApiResult;
import com.example.enum_validator.enums.FooEnum;
import com.example.enum_validator.model.FooCmd;
import com.example.enum_validator.model.FooResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description:
 *
 * @author jiaozhiwang
 * {@code @date} 2024/12/6
 */

@ApiResult
@RestController
public class TestController {

    @PostMapping("/foo")
    public FooResult foo(@RequestBody @Valid FooCmd cmd) {
        FooEnum type = FooEnum.get(cmd.getValue());
        return new FooResult() {{
            setCode(type.getCode());
            setName(type.getValue());
        }};
    }
}
