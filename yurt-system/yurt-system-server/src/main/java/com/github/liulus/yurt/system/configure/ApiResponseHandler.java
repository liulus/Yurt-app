package com.github.liulus.yurt.system.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.liulus.yurt.convention.data.CommonCode;
import com.github.liulus.yurt.convention.data.Result;
import com.github.liulus.yurt.convention.exception.ServiceException;
import com.github.liulus.yurt.convention.exception.ServiceValidException;
import com.github.liulus.yurt.convention.util.Results;
import com.github.liulus.yurt.convention.util.ThrowableUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author liulu
 * @version V1.0
 * @since 2020/9/17
 */
@Slf4j
@RestControllerAdvice
public class ApiResponseHandler implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotationUtils.isAnnotationDeclaredLocally(RestController.class, returnType.getDeclaringClass())
                || returnType.hasMethodAnnotation(ResponseBody.class);
    }


    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof InputStream) {
            return body;
        }
        if (body instanceof Result) {
            return body;
        }
        Result<Object> result = Results.success(body);
        if (body instanceof CharSequence) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return objectMapper.writeValueAsString(result);
        }
        return result;
    }


    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(Exception ex) {

        ServiceException serviceException = ThrowableUtils.findException(ServiceException.class, ex);
        if (serviceException != null) {
            log.warn("\n service exception -> {}", serviceException.getMessage());
            return Results.failure(serviceException.getCode(), serviceException.getMessage());
        }
        Result<Object> result = getInvalidResult(ex);
        if (result != null) {
            return result;
        }
        log.error("系统异常 -> ", ex);
        return Results.error(CommonCode.UNKNOWN_ERROR.code(), ThrowableUtils.buildErrorMessage(ex));
    }

    private Result<Object> getInvalidResult(Exception ex) {
        Result<Object> result = Optional.ofNullable(ex)
                .map(e -> ThrowableUtils.findException(ServiceValidException.class, e))
                .map(invalid -> Results.failure(invalid.getCode(), invalid.getMessage()))
                .orElse(null);
        if (result == null) {
            result = Optional.ofNullable(ex)
                    .map(e -> ThrowableUtils.findException(IllegalArgumentException.class, e))
                    .map(invalid -> Results.failure(CommonCode.INVALID_PARAM.code(), invalid.getMessage()))
                    .orElse(null);
        }
        if (result == null) {
            result = Optional.ofNullable(ex)
                    .map(e -> ThrowableUtils.findException(MethodArgumentNotValidException.class, e))
                    .map(MethodArgumentNotValidException::getBindingResult)
                    .map(BindingResult::getFieldError)
                    .map(FieldError::getDefaultMessage)
                    .map(message -> Results.failure(CommonCode.INVALID_PARAM.code(), message))
                    .orElse(null);
        }
        if (result != null) {
            log.warn("\n Validation failed -> {} {}", result.getCode(), result.getMessage());
        }
        return result;
    }

}
