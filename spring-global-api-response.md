---
title: Spring如何对Controller的返回值进行统一格式化输出
date: 2024-12-09 11:23:21
tags: [Java,Spring,Controller,Response,ResponseBodyAdvice]

---

### Spring如何对Controller的返回值进行统一格式化输出

#### 前言

`Spring`的`RestController`中，经常需要对返回值进行封装，将Java的VO或DTO对象封装为带code和message的响应结果。但是又不想每个方法都进行处理，类似：
`Respose.success(data)`。
可以使用Spring的`ResponseBodyAdvice`的接口对返回结果进行统一格式化输出，简化业务代码。

1. 新建一个自定义注解：
    ```java
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ApiResult {
    }
    ```
2. 新建一个公用的`Result`类：
     ```java
     @Data
     public class Result<T> implements Serializable {
     
         private int code;
     
         private String message;
     
         private T data;
     }
     ```
3. 新建一个工厂方法`ResultFactory`，用来构造成功和失败的响应结果：
    ```java
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
    ```
4. 新建一个实现`ResponseBodyAdvice`的类，用来格式化输出：
    ```java
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
    ```
5. 在Controller的类上或方法上使用该注解：
    ```java
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
    ```
6. 全局异常处理：
    ```java
    @Slf4j
    @RestControllerAdvice
    public class GlobalExceptionHandler {
    @Resource
    private ResultFactory resultFactory;
    
        @ResponseBody
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Result<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : exception.getBindingResult().getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
    
            Result<Map<String, String>> result = resultFactory.fail(errors);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    ```

#### 示例代码

完整示例代码：[Spring format response](https://github.com/imwower/enum_validator)