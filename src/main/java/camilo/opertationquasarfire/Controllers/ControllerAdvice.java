package camilo.opertationquasarfire.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import camilo.opertationquasarfire.Exceptions.InformationException;
import camilo.opertationquasarfire.Exceptions.ResquestException;
import camilo.opertationquasarfire.Models.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = ResquestException.class)
  public ResponseEntity<ErrorResponse> ResquestExceptionHandler(ResquestException e) {
    ErrorResponse error = ErrorResponse.builder().message(e.getMessage()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(value = InformationException.class)
  public ResponseEntity<ErrorResponse> InformationExceptionHandler(InformationException e) {
    ErrorResponse error = ErrorResponse.builder().message(e.getMessage()).build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
    ErrorResponse error = ErrorResponse.builder().message("Required request body is wrong or missing.").build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(value = NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> NoResourceFoundExceptionHandler(NoResourceFoundException e) {
    ErrorResponse error = ErrorResponse.builder().message("The request url was not found.").build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> HttpRequestMethodNotSupportedExceptionHandler(
      HttpRequestMethodNotSupportedException e) {
    ErrorResponse error = ErrorResponse.builder().message("The request url was not found.").build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}