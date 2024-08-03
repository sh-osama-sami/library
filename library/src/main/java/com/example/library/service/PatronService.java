package com.example.library.service;

import com.example.library.model.Patron;
import com.example.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;


    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }



    public Patron getPatronByUsername(String username) {
        return patronRepository.findByUsername(username);
    }

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id).orElse(null);
    }

    @Transactional
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional
    public Patron updatePatron(Long id, Patron updatedPatron) {
        if (updatedPatron == null || !isVaildPatron(updatedPatron)) {
            throw new IllegalArgumentException("Invalid patron data");
        }

        Patron existingPatron = patronRepository.findById(id).orElse(null);
        if (existingPatron == null) {
            return null;
        }

        // Update existing patron with the new data
        existingPatron.setName(updatedPatron.getName());
        existingPatron.setUsername(updatedPatron.getUsername());
        existingPatron.setPassword(updatedPatron.getPassword());
        existingPatron.setEmail(updatedPatron.getEmail());

        return patronRepository.save(existingPatron);
    }

   public boolean isVaildPatron(Patron patron){
        return patron.getName() != null && !patron.getName().isEmpty();
    }

    @Transactional
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
