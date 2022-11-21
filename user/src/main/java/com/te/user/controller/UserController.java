package com.te.user.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.user.bookdto.BookDto;
import com.te.user.bookservice.Bookservice;
import com.te.user.response.Response;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.NonNull;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private Bookservice bookConsumer;
	
	private int attempt=1;
	
	/*
	 * Runnable runnable=new Runnable() {
	 * 
	 * @Override public void run() { try { Thread.sleep(100);
	 * System.out.println("Hellow World");
	 * 
	 * } catch (Exception e) { e.getMessage(); }
	 * 
	 * } };
	 */
	
	@PostMapping("/user")
	//@RateLimiter(name = "addBook",fallbackMethod = "ratelimiter")
	public ResponseEntity<Response> addBook(@RequestBody BookDto bookDto){
		ResponseEntity<Response> responseEntity = bookConsumer.addBook(bookDto);
		 Object data =  responseEntity.getBody().getData();
		return new ResponseEntity<>(new Response(false, "Data added",data),HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")

	//@CircuitBreaker(name = "addBook",fallbackMethod = "circuitbreakermessage")
	//@Retry(name = "addBook",fallbackMethod = "circuitbreakermessage")
	@Bulkhead(type = Type.SEMAPHORE,name = "addBook",fallbackMethod = "circuitbreakermessage")
	public ResponseEntity<Response> addBook(@PathVariable Integer id) throws InterruptedException{
		//System.out.println("retry attempt"+ attempt++ );
		Thread.sleep(100);
		return new ResponseEntity<>(new Response(false, "Here Your Book", bookConsumer.find(id).getBody().getData()),HttpStatus.OK);
	}
	
	public ResponseEntity<Response> circuitbreakermessage (Exception e){
		return new ResponseEntity<>(new Response(false, "Try Again", null),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<Response> ratelimiter (Exception e){
		return new ResponseEntity<>(new Response(false, "Rate Limiter Exceed", null),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<Response> timelimiter (Exception e){
		return new ResponseEntity<>(new Response(false, "Time Limiter Exceed For That Request", null),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*
	 * @GetMapping("/users")
	 * 
	 * @TimeLimiter(name = "time",fallbackMethod = "timelimiter") public
	 * CompletableFuture<Void> time (){ return CompletableFuture.runAsync(runnable);
	 * 
	 * }
	 */
	
}
