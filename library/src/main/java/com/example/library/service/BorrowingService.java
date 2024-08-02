package com.example.library.service;

import com.example.library.model.BorrowingRecord;
import com.example.library.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

    public BorrowingRecord getBorrowingRecordById(Long id) {
        return borrowingRecordRepository.findById(id).orElse(null);
    }

    @Transactional
    public BorrowingRecord addBorrowingRecord(BorrowingRecord borrowingRecord) {
        borrowingRecord.setBorrowDate(LocalDate.now());
        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord returnBook(Long id) {
        return borrowingRecordRepository.findById(id).map(record -> {
            record.setReturnDate(LocalDate.now());
            return borrowingRecordRepository.save(record);
        }).orElse(null);
    }

    public void deleteBorrowingRecord(Long id) {
        borrowingRecordRepository.deleteById(id);
    }
}
