package com.aTut.blog.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aTut.blog.Payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse =  new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse> duplicateEntry( SQLIntegrityConstraintViolationException ex){

		String message = ex.getMessage() ;
		ApiResponse apiResponse =  new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String,String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) ->
			{
				String fieldName = ((FieldError)error).getField();
				String message = error.getDefaultMessage();
				errors.put(fieldName, message);
			}
		);
		
		return new ResponseEntity<Map<String,String>>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse =  new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.BAD_REQUEST);
	}

}
