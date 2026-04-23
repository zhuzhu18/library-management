package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.BorrowRecord;
import com.example.library.entity.Reader;
import com.example.library.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookService bookService;
    private final ReaderService readerService;

    public BorrowService(BorrowRecordRepository borrowRecordRepository,
                         BookService bookService,
                         ReaderService readerService) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    public List<BorrowRecord> findAll() {
        return borrowRecordRepository.findAll();
    }

    public List<BorrowRecord> findByReaderId(Long readerId) {
        return borrowRecordRepository.findByReaderId(readerId);
    }

    public List<BorrowRecord> findByBookId(Long bookId) {
        return borrowRecordRepository.findByBookId(bookId);
    }

    @Transactional
    public BorrowRecord borrow(Long bookId, Long readerId) {
        Book book = bookService.findById(bookId);
        Reader reader = readerService.findById(readerId);

        if (book.getStock() <= 0) {
            throw new RuntimeException("图书库存不足，无法借出: " + book.getTitle());
        }

        book.setStock(book.getStock() - 1);
        bookService.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setReader(reader);
        record.setBorrowDate(LocalDate.now());
        return borrowRecordRepository.save(record);
    }

    @Transactional
    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("借阅记录不存在，ID: " + recordId));

        if (record.getReturnDate() != null) {
            throw new RuntimeException("该书已归还");
        }

        record.setReturnDate(LocalDate.now());

        Book book = record.getBook();
        book.setStock(book.getStock() + 1);
        bookService.save(book);

        return borrowRecordRepository.save(record);
    }
}
