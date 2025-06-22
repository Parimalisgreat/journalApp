package com.parimal.journalApp.service;

import com.parimal.journalApp.entity.JournalEntry;
import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {


    @Autowired
    private JournalEntryRepo repo;

    @Autowired
    UserService userService;




    @Transactional
    public User createEntry(JournalEntry journalEntry, String userName){

        try{
            User user=userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved=repo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
            return user;
        }
        catch(Exception e){
            throw new RuntimeException("An error occurred while saving the entry ", e);
        }

    }

    public void createEntry(JournalEntry journalEntry){
        repo.save(journalEntry);
    }


    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return repo.findById(id);
    }

    public List<JournalEntry> getAllEntries() {
        return repo.findAll();
    }

    @Transactional
    public boolean deleteById(ObjectId id,String userName) {
        boolean removed=false;
        try{
            User user=userService.findByUserName(userName);
            removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));

            if(removed){
                userService.saveUser(user);
                repo.deleteById(id);
            }
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry ", e);
        }


        return removed;



    }
}
