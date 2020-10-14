package com.br.restApi.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.metamodel.StaticMetamodel;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.restApi.exception.ErrorDetails;
import com.br.restApi.exception.ResourceNotFoundDetails;
import com.br.restApi.exception.ResourceNotFoundException;
import com.br.restApi.exception.ValidationErrorDetails;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {


@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException rnfException){
	ResourceNotFoundDetails rnfdetails = ResourceNotFoundDetails.Builder
	.newBuilder()
	.timestamp(new Date().getTime())
	.status(HttpStatus.NOT_FOUND.value())
	.title("Resource Not Found")
	.detail(rnfException.getMessage())
	.developerMessage(rnfException.getClass().getName())
	.build();
	
	return new ResponseEntity<>(rnfdetails, HttpStatus.NOT_FOUND);
}


   @Override
   public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
       List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
       String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
       String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
       ValidationErrorDetails rnfDetails = ValidationErrorDetails.Builder
               .newBuilder()
               .timestamp(new Date().getTime())
               .status(HttpStatus.BAD_REQUEST.value())
               .title("Field Validation Error")
               .detail("Field Validation Error")
               .developerMessage(manvException.getClass().getName())
               .field(fields)
               .fieldMessage(fieldMessages)
               .build();
       return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
   }

      
   @Override
   protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
		   														HttpHeaders headers,
		   														HttpStatus status,
		   														WebRequest request) {
	   ErrorDetails errorDetails = ResourceNotFoundDetails.Builder
			   .newBuilder()
				.timestamp(new Date().getTime())
				.status(status.value())
				.title("Internal Exception")
				.detail(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build();
		return new ResponseEntity<>(errorDetails, headers, status);
	}

   
   
//   @ExceptionHandler(PropertyExceptions.class)
//   protected ResponseEntity<Object> handlePropertyReference(PropertyReferenceException prException,
//															   HttpHeaders headers,
//													           HttpStatus status,
//													           WebRequest request){
//	   ErrorDetails errorDetails = ResourceNotFoundDetails.Builder
//			   .newBuilder()
//				.timestamp(new Date().getTime())
//				.status(status.value())
//				.title("Internal Exception")
//				.detail("")
//				.developerMessage(prException.getClass().getName())
//				.build();
//		return new ResponseEntity<>(errorDetails, headers, status);
//	   
//   }
   
   
//   @ResponseStatus(HttpStatus.FORBIDDEN)
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<?> handleMethodArgumentNotValid(Exception ex) {
//	   ErrorDetails errorDetails = ErrorDetails.Builder
//			   .newBuilder()
//				.timestamp(new Date().getTime())
//				.detail(ex.getMessage())
//				.developerMessage(ex.getClass().getName())
//				.status(500)
//				.title("Internal Server Error")
//				.build(); 
//	   return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
//	}
   
   
   @ExceptionHandler(Exception.class)
   public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,  WebRequest request)  {
	   ErrorDetails errorDetails = ErrorDetails.Builder
			   .newBuilder()
				.timestamp(new Date().getTime())
				.detail(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.status(500)
				.title("Internal Server Error")
				.build(); 
	   return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
   }
   
   
   
   
   
   
}
