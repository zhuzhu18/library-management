package com.example.library.controller;

import com.example.library.entity.Reader;
import com.example.library.service.ReaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public List<Reader> getAll() {
        return readerService.findAll();
    }

    @GetMapping("/{id}")
    public Reader getById(@PathVariable Long id) {
        return readerService.findById(id);
    }

    @PostMapping
    public Reader create(@RequestBody Reader reader) {
        return readerService.save(reader);
    }

    @PutMapping("/{id}")
    public Reader update(@PathVariable Long id, @RequestBody Reader reader) {
        return readerService.update(id, reader);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        readerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
