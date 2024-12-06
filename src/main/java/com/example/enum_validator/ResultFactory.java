package com.example.enum_validator;

import com.example.enum_validator.enums.ResultCode;
import com.example.enum_validator.model.Result;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author jiaozhiwang
 * {@code @date} 2024/12/6
 */

@Component
public class ResultFactory {

    public <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    public <T> Result<T> fail(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.BAD_REQUEST.getCode());
        result.setData(data);
        return result;
    }
}
