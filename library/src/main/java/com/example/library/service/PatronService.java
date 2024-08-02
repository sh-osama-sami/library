package com.example.library.service;

import com.example.library.model.Patron;
import com.example.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    @Transactional
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional
    public Patron updatePatron(Long id, Patron updatedPatron) {
        return patronRepository.findById(id).map(patron -> {
            patron.setName(updatedPatron.getName());
            patron.setEmail(updatedPatron.getEmail());
            return patronRepository.save(patron);
        }).orElse(null);
    }

    @Transactional
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
