package com.example.library.controller;

import com.example.library.TestSecurityConfig;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book("Title", "Author", "ISBN", 2024);
    }

    @Test
    public void getAllBooks() throws Exception {
        given(bookService.getAllBooks()).willReturn(Arrays.asList(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(book.getTitle()));
    }

    @Test
    public void getAllBooks_EmptyList() throws Exception {
        given(bookService.getAllBooks()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getBookById() throws Exception {
        given(bookService.getBookById(1L)).willReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void getBookById_NotFound() throws Exception {
        given(bookService.getBookById(1L)).willReturn(null);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound()); // 404 Not Found
    }

    @Test
    public void addBook() throws Exception {
        Book newBook = new Book("New Title", "New Author", "New ISBN", 2024);
        given(bookService.addBook(Mockito.any(Book.class))).willReturn(newBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Title\", \"author\": \"New Author\", \"isbn\": \"New ISBN\", \"year\": 2024}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(newBook.getTitle()));
    }

    @Test
    public void updateBook() throws Exception {
        Book updatedBook = new Book("Updated Title", "Updated Author", "Updated ISBN", 2024);
        given(bookService.updateBook(Mockito.anyLong(), Mockito.any(Book.class))).willReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"author\": \"Updated Author\", \"isbn\": \"Updated ISBN\", \"year\": 2024}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()));
    }



    @Test
    public void updateBook_NotFound() throws Exception {

        given(bookService.updateBook(Mockito.anyLong(), Mockito.any(Book.class))).willReturn(null);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"author\": \"Updated Author\", \"isbn\": \"Updated ISBN\", \"year\": 2024}"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

}
