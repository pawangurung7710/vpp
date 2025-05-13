package com.porshore.vpp.exceptions.handler;

import com.porshore.vpp.constant.ApiConstant;
import com.porshore.vpp.exceptions.VppException;
import com.porshore.vpp.response.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * @author <Pawan Gurung>
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(VppException.class)
    public ResponseEntity<ResponseDto> handleVppException(VppException vppException) {
        return ResponseDto.exceptionResponse(
                vppException.getResult(),
                vppException.getCode(),
                vppException.getMessage(),
                vppException.getHttpStatus(),
                vppException.getDeveloperMsg()
        );
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> unexpectedTypeException(HttpMessageNotReadableException exception) {
        log.error("HttpMessageNotReadableException", exception);
        return ResponseDto.exceptionResponse(
                ApiConstant.VPP_FAILED.getResult(),
                ApiConstant.VPP_FAILED.getCode(),
                "Input data type incorrect",
                HttpStatus.BAD_REQUEST,
                "Error"
        );
    }


    @ExceptionHandler(value = HandlerMethodValidationException.class)
    public ResponseEntity<ResponseDto> handleValidationException(HandlerMethodValidationException exception) {
        log.error("HandlerMethodValidationException: ", exception);

        StringBuilder errorMessages = new StringBuilder();
        exception.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("; "));

        return ResponseDto.exceptionResponse(
                ApiConstant.VPP_FAILED.getResult(),
                ApiConstant.VPP_FAILED.getCode(),
                errorMessages.toString(),
                HttpStatus.BAD_REQUEST,
                "Validation failed."
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDto> genericException(Exception exception) {
        log.error("genericException: ", exception);
        return ResponseDto.exceptionResponse(
                ApiConstant.INTERNAL_SERVER_ERROR.getResult(),
                ApiConstant.INTERNAL_SERVER_ERROR.getCode(),
                "Error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error"
        );
    }

}
