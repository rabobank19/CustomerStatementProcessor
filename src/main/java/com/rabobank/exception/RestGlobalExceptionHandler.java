package com.rabobank.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/*@ExceptionHandler(MultipartException.class)
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {

		HttpStatus status = getStatus(request);
		return new ResponseEntity(ex.getMessage(), status);

	}*/
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {

		List<String> errors = new ArrayList<String>();
		
		errors.add(ex.getMessage());
		
		FileUploadErrors fileError = 
		        new FileUploadErrors(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		/*return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR );*/
		return new ResponseEntity<>(fileError, HttpStatus.BAD_REQUEST );

	}
	
	
	private HttpStatus getStatus(HttpServletRequest request) {
	        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	        if (statusCode == null) {
	            return HttpStatus.INTERNAL_SERVER_ERROR;
	        }
	        return HttpStatus.valueOf(statusCode);
	  }
	  
	  @Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(
	    MethodArgumentNotValidException ex, 
	    HttpHeaders headers, 
	    HttpStatus status, 
	    WebRequest request) {
	      List<String> errors = new ArrayList<String>();
	      for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	          errors.add(error.getField() + ": " + error.getDefaultMessage());
	      }
	      for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	          errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	      }
	       
	      FileUploadErrors fileError = 
	        new FileUploadErrors(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	      return handleExceptionInternal(
	        ex, fileError, headers, fileError.getStatus(), request);
	  }

}
