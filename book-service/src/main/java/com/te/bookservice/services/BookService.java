package com.te.bookservice.services;

import com.te.bookservice.dto.BookDto;
import com.te.bookservice.entity.Book;

public interface BookService {

	Book add(BookDto bookDto);

	Book find(Integer id);

}
