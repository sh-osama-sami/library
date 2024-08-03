package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.BorrowingRecord;
import com.example.library.model.Patron;
import com.example.library.service.BorrowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BorrowingControllerTests{

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    private MockMvc mockMvc;
    private BorrowingRecord record;
    private Book book;
    private Patron patron;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingController).build();

        book = new Book("Title", "Author", "ISBN", 2024);
        patron = new Patron("John Doe", "john12","john@doe.com","pass");
        record = new BorrowingRecord(book, patron, LocalDate.now(), LocalDate.now().plusDays(14));
    }

    @Test
    public void testGetAllBorrowingRecords() throws Exception {
        BorrowingRecord record = new BorrowingRecord();
        when(borrowingService.getAllBorrowingRecords()).thenReturn(Collections.singletonList(record));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrow"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").isNotEmpty());

        verify(borrowingService, times(1)).getAllBorrowingRecords();
    }

    @Test
    public void testGetBorrowingRecordById_Success() throws Exception {

        given(borrowingService.getBorrowingRecordById(1L)).willReturn(record);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrow/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowDate").exists());

        verify(borrowingService, times(1)).getBorrowingRecordById(anyLong());
    }

    @Test
    public void testGetBorrowingRecordById_NotFound() throws Exception {
        when(borrowingService.getBorrowingRecordById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrow/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(borrowingService, times(1)).getBorrowingRecordById(anyLong());
    }

    @Test
    public void testAddBorrowingRecord() throws Exception {
        BorrowingRecord newRecord = new BorrowingRecord(book, patron, LocalDate.now(), LocalDate.now().plusDays(14));
        when(borrowingService.addBorrowingRecord(
                anyLong(), anyLong()
        )).thenReturn(newRecord);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/1/patron/1")
                        .contentType("application/json")
                        .content("{\"id\":1}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowDate").exists());

    verify(borrowingService, times(1)).addBorrowingRecord(anyLong(), anyLong());
    }


    @Test
    public void testReturnBook_Success() throws Exception {
        BorrowingRecord returnedRecord = new BorrowingRecord(book, patron, LocalDate.now(), LocalDate.now().plusDays(14));

        when(borrowingService.returnBook(anyLong(),anyLong())).thenReturn(returnedRecord);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowDate").exists());

        verify(borrowingService, times(1)).returnBook(anyLong(),anyLong());
    }

    @Test
    public void testReturnBook_NotFound() throws Exception {
        when(borrowingService.returnBook(anyLong(),anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(borrowingService, times(1)).returnBook(anyLong(),anyLong());
    }

    @Test
    public void testDeleteBorrowingRecord() throws Exception {
        doNothing().when(borrowingService).deleteBorrowingRecord(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/borrow/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(borrowingService, times(1)).deleteBorrowingRecord(anyLong());
    }
}
