package com.example.library.controller;

import com.example.library.model.BorrowingRecord;
import com.example.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingRecordService;

    @GetMapping
    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordService.getAllBorrowingRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecord> getBorrowingRecordById(@PathVariable Long id) {
        BorrowingRecord record = borrowingRecordService.getBorrowingRecordById(id);
        return record != null ? new ResponseEntity<>(record, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> addBorrowingRecord( @PathVariable Long bookId, @PathVariable Long patronId) {

            BorrowingRecord savedRecord = borrowingRecordService.addBorrowingRecord(bookId, patronId);
            return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);

    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId,
                                                      @PathVariable Long patronId) {
        BorrowingRecord updatedRecord = borrowingRecordService.returnBook(bookId, patronId);
        return updatedRecord != null ? new ResponseEntity<>(updatedRecord, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowingRecord(@PathVariable Long id) {
        borrowingRecordService.deleteBorrowingRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
