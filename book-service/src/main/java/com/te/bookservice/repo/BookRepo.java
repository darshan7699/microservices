package com.te.bookservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookservice.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

}
