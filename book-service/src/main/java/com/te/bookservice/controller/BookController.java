package com.te.bookservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.te.bookservice.dto.BookDto;
import com.te.bookservice.entity.Book;
import com.te.bookservice.response.Response;
import com.te.bookservice.services.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PostMapping("/book")
	public ResponseEntity<Response> addBook(@RequestBody BookDto bookDto){
		Book add = bookService.add(bookDto);
		return new ResponseEntity<>(new Response(false, "Book Added", add),HttpStatus.OK);
	}
	
	@GetMapping("/findBook/{id}")
	public ResponseEntity<Response> find(@PathVariable Integer id){
		System.out.println("from controller");
		Book book = bookService.find(id);
		return new ResponseEntity<>(new Response(false, "Here Is Your Book", book),HttpStatus.OK);
	}
}
