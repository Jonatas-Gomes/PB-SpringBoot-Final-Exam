package br.com.compass.pb.msorder.framework.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {
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

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handle(Exception ex) {
        if (ex instanceof GenericException) {
            return handleGenericException(((GenericException) ex));
        }
        var errorResponse = ErrorResponse.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<Object> handleGenericException(GenericException ex) {
        var errorResponse = ErrorResponse.builder().message(ex.getMessageDTO()).build();
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

}
