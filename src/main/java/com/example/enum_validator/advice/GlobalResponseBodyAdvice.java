package com.example.enum_validator.advice;

import com.example.enum_validator.ResultFactory;
import com.example.enum_validator.annotations.ApiResult;
import com.example.enum_validator.model.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author jiaozhiwang
 * {@code @date} 2024/12/6
 */

@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private ResultFactory resultFactory;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(ApiResult.class)
                || returnType.getContainingClass().isAnnotationPresent(ApiResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  org.springframework.http.server.ServerHttpRequest request,
                                  org.springframework.http.server.ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        }

        return resultFactory.success(body);
    }
}