package com.example.processor;

import com.example.domain.Book;
import com.example.model.BookModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

@Log4j2
public class BookProcessor implements ItemProcessor<BookModel, Book> {

  public BookProcessor() {
    log.debug("Initialize Processor");
  }

  @Override
  public Book process(BookModel item) {
    var book = new Book();
    book.setBookId(Long.parseLong(item.getBookId()));
    book.setTitle(item.getTitle());
    book.setAuthors(item.getAuthors());
    book.setIsbn(item.getIsbn());
    return book;
  }
}
