package com.example.library.service;

import com.example.library.entity.Reader;
import com.example.library.repository.ReaderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader findById(Long id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("读者不存在，ID: " + id));
    }

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader update(Long id, Reader readerDetails) {
        Reader reader = findById(id);
        reader.setName(readerDetails.getName());
        reader.setPhone(readerDetails.getPhone());
        reader.setEmail(readerDetails.getEmail());
        return readerRepository.save(reader);
    }

    public void delete(Long id) {
        Reader reader = findById(id);
        readerRepository.delete(reader);
    }
}
