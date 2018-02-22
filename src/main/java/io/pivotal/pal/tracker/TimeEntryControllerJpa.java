package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries-jpa")

public class TimeEntryControllerJpa {
    JPATimeEntryRepository jpaTimeEntryRepository;

    public TimeEntryControllerJpa(JPATimeEntryRepository jpaTimeEntryRepository) {
        this.jpaTimeEntryRepository = jpaTimeEntryRepository;
    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        return new ResponseEntity<>(jpaTimeEntryRepository.save(timeEntry), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        if (jpaTimeEntryRepository.findOne(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(jpaTimeEntryRepository.findOne(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<TimeEntry>> list() {
        return ResponseEntity.ok(jpaTimeEntryRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry expected) {
        if (this.read(id) == null || expected.getId() !=id)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        TimeEntry te = jpaTimeEntryRepository.findOne(id);
        te.setUserId(expected.getUserId());
        te.setDate(expected.getDate());
        te.setProjectId(expected.getProjectId());
        te.setHours(expected.getHours());
       return ResponseEntity.ok(jpaTimeEntryRepository.save(expected));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        jpaTimeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
