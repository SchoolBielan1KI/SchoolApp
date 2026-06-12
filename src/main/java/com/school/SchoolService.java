package com.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository repository;

    public List<School> getAllRecords() {
        return repository.findAll();
    }

    public void saveRecord(School school) {
        repository.save(school);
    }

    public void deleteRecord(String id) {
        repository.deleteById(id);
    }
}