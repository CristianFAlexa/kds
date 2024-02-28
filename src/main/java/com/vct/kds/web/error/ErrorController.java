package com.vct.kds.web.error;

import com.vct.kds.converter.exception.NullConversionException;
import com.vct.kds.web.error.exeption.InvalidStateException;
import com.vct.kds.web.error.exeption.OrderNotFoundException;
import com.vct.kds.web.error.exeption.ProductNotFoundException;
import com.vct.kds.web.error.exeption.TableNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({TableNotFoundException.class, ProductNotFoundException.class, OrderNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFound(final RuntimeException runtimeException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, runtimeException.getMessage()));
    }

    @ExceptionHandler({InvalidStateException.class})
    public ResponseEntity<ProblemDetail> handleBadRequest(final RuntimeException runtimeException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, runtimeException.getMessage()));
    }

    @ExceptionHandler({NullConversionException.class})
    public ResponseEntity<ProblemDetail> handleInternalServerError(final RuntimeException runtimeException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, runtimeException.getMessage()));
    }
}
