package br.com.compass.pb.MsHistory.framework.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handle(Exception ex) {
        if (ex instanceof GenericException) {
            return handleGenericException(((GenericException) ex));
        }
        return handleDefault();
    }

    public ResponseEntity<Object> handleDefault() {
        var errorResponse = ErrorResponse.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> handleGenericException(GenericException ex) {
        var errorResponse = ErrorResponse.builder().message(ex.getMessageDTO()).build();
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
