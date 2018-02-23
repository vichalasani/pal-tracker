package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")

public class TimeEntryController {

    private final CounterService counterService;
    private final GaugeService gaugeService;

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(CounterService counterService, GaugeService gaugeService, TimeEntryRepository timeEntryRepository) {
        this.counterService = counterService;
        this.gaugeService = gaugeService;
        this.timeEntryRepository = timeEntryRepository;
    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry created =  timeEntryRepository.create(timeEntry);
        counterService.increment("TimeEntry.created");
        gaugeService.submit("timeEntries.count",timeEntryRepository.list().size());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timerEntry = timeEntryRepository.find(id);
        if ( timerEntry== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        counterService.increment("TimerEntry.read");
        return ResponseEntity.ok(timerEntry);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(timeEntryRepository.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry expected) {
        if (this.read(id) == null || expected.getId() !=id)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       return ResponseEntity.ok(timeEntryRepository.update(id,expected));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
      /*  if (timeEntryRepository.find(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        timeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
