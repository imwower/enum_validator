package com.example.enum_validator.controller;

import com.example.enum_validator.cmd.TestCmd;
import com.example.enum_validator.enums.TestEnum;
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

@RestController
public class TestController {

    @PostMapping("/foo")
    public String foo(@RequestBody @Valid TestCmd cmd) {
        return TestEnum.get(cmd.getValue()).getValue();
    }
}
