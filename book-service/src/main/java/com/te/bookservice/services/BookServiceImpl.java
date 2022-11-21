package com.te.bookservice.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.te.bookservice.dto.BookDto;
import com.te.bookservice.entity.Book;
import com.te.bookservice.repo.BookRepo;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepo bookRepo;

	@Override
	public Book add(BookDto bookDto) {
		try {
			Book book = new Book();
			BeanUtils.copyProperties(bookDto, book);
			return bookRepo.save(book);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Cacheable(value = "Response",key = "#id")
	public Book find(Integer id) {
		try {
			System.out.println("DAta from service layer");
			return bookRepo.findById(id).orElseThrow();
		} catch (Exception e) {
		
			throw e;
		}
	}

}
