package com.parimal.journalApp.controller;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.parimal.journalApp.entity.JournalEntry;
import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.service.JournalEntryService;
import com.parimal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService service;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getJournalEntriesByUserName(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        String userName=authentication.getName();
        User user=userService.findByUserName(userName);

        List<JournalEntry> journalEntries=user.getJournalEntries();

        if(journalEntries!=null){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addJournalEntry(@RequestBody JournalEntry journalEntry){
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

            String userName=authentication.getName();
            User user=service.createEntry(journalEntry,userName);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();

        List<JournalEntry> collect=service.getAllEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry=service.getJournalEntryById(id);

            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<?> editJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newJournalEntry){

        try{
            JournalEntry curJournalEntry=service.getJournalEntryById(id).orElse(null);

            if(curJournalEntry!=null){
                curJournalEntry.setTitle((newJournalEntry.getTitle()!=null)? newJournalEntry.getTitle() : curJournalEntry.getTitle());
                curJournalEntry.setContent((newJournalEntry.getContent()!=null)? newJournalEntry.getContent() : curJournalEntry.getContent());
            }
            service.createEntry(curJournalEntry);// Since Id is same as prev, hence the previous value gets override in MongoDB
            return new ResponseEntity<>(curJournalEntry,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        JournalEntry curJournalEntry=service.getJournalEntryById(id).orElse(null);

        if(curJournalEntry!=null){
            boolean removed=service.deleteById(id,userName);

            if(removed){
                return new ResponseEntity<>(HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
