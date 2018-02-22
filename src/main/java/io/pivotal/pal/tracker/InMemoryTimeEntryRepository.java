package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    long sequence = 1;
    Map<Long, TimeEntry> entries = new HashMap<>();
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(sequence++);
        entries.put(timeEntry.getId(),timeEntry);
        return  timeEntry;
    }

    public TimeEntry find(long id) {
        return entries.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(entries.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        entries.put(id,timeEntry);
        return timeEntry;
    }

    public TimeEntry delete(long id) {
        return entries.remove(id);
    }

}
