package com.example.library.controller;

import com.example.library.entity.BorrowRecord;
import com.example.library.service.BorrowService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping
    public List<BorrowRecord> getAll(@RequestParam(required = false) Long readerId,
                                     @RequestParam(required = false) Long bookId) {
        if (readerId != null) {
            return borrowService.findByReaderId(readerId);
        }
        if (bookId != null) {
            return borrowService.findByBookId(bookId);
        }
        return borrowService.findAll();
    }

    @PostMapping
    public BorrowRecord borrow(@RequestBody Map<String, Long> request) {
        Long bookId = request.get("bookId");
        Long readerId = request.get("readerId");
        return borrowService.borrow(bookId, readerId);
    }

    @PutMapping("/{id}/return")
    public BorrowRecord returnBook(@PathVariable Long id) {
        return borrowService.returnBook(id);
    }
}
