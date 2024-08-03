package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book>getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    @Transactional
    public Book updateBook(Long id, Book book) {
        if (book == null || !isValidBook(book)) {
            throw new IllegalArgumentException("Invalid book data");
        }

        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            return null;
        }

        // Update existing book with the new data
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setYear(book.getYear());

        return bookRepository.save(existingBook);
    }

    private boolean isValidBook(Book book) {
        return book.getTitle() != null && !book.getTitle().isEmpty() &&
                book.getAuthor() != null && !book.getAuthor().isEmpty() &&
                book.getIsbn() != null && !book.getIsbn().isEmpty() &&
                book.getYear() > 0;
    }
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> findBooksPublishedAfter(int year) {
        return bookRepository.findByYearGreaterThan(year);
    }
}


