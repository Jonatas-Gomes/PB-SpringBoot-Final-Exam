package br.com.compass.pb.msorder.framework.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorValidResponse> listErrors = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(error ->{
            String message =  messageSource.getMessage(error, LocaleContextHolder.getLocale());
            ErrorValidResponse errorResponse = new ErrorValidResponse(error.getField(), message);
            listErrors.add(errorResponse);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listErrors);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> handleGenericException(GenericException exception){

        var genericExeption = new GenericException(exception.getStatus(), exception.getMessageDTO());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericExeption);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception exception){
        var genericException = new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericException);
    }


}
