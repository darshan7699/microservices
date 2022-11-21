package com.te.bookservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.te.bookservice.response.Response;

@RestControllerAdvice
public class BookHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handler (Exception exception){
		return new ResponseEntity<>(new Response(true, "Something Went Wrong", null),HttpStatus.BAD_GATEWAY);
	}
}
