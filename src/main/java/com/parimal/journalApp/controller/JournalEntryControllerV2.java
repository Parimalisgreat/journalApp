package com.parimal.journalApp.controller;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.parimal.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/_journal")
public class JournalEntryControllerV2 {

    Map<ObjectId, JournalEntry> journalEntries=new HashMap<>();

    @PostMapping
    public boolean addJournalEntry(@RequestBody JournalEntry journalEntry){

        journalEntries.put(journalEntry.getId(),journalEntry);

        return true;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId id){
        return journalEntries.get(id);
    }

    @PutMapping("/id/{id}")
    public boolean editJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry){
        journalEntries.put(id,journalEntry);
        return true;
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteEntryById(@PathVariable ObjectId id){
        journalEntries.remove(id);
        return true;
    }

}
