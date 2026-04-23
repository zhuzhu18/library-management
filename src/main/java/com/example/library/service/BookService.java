package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("图书不存在，ID: " + id));
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book bookDetails) {
        Book book = findById(id);
        book.setIsbn(bookDetails.getIsbn());
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setStock(bookDetails.getStock());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }
}
