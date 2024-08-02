package com.example.library.controller;

import com.example.library.model.BorrowingRecord;
import com.example.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowing")
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

    @PostMapping
    public ResponseEntity<BorrowingRecord> addBorrowingRecord(@RequestBody BorrowingRecord borrowingRecord) {
        BorrowingRecord savedRecord = borrowingRecordService.addBorrowingRecord(borrowingRecord);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long id) {
        BorrowingRecord updatedRecord = borrowingRecordService.returnBook(id);
        return updatedRecord != null ? new ResponseEntity<>(updatedRecord, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowingRecord(@PathVariable Long id) {
        borrowingRecordService.deleteBorrowingRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
