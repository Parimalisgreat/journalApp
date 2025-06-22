package com.parimal.journalApp.service;

import com.parimal.journalApp.entity.JournalEntry;
import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepo repo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        repo.save(user);
        return true;
    }
    public void saveUser(User user){
        repo.save(user);
    }


    public Optional<User> getUserEntryById(ObjectId id) {
        return repo.findById(id);
    }

    public List<User> getAllEntries() {
        return repo.findAll();
    }

    public void deleteById(ObjectId id) {

        repo.deleteById(id);

    }

    public User findByUserName(String userName) {
        return repo.findByUserName(userName);
    }

    public User addJournalEntryToUser(JournalEntry journalEntry,String userName){
        User user=this.findByUserName(userName);

        if(user!=null){

            List<JournalEntry> journalEntries=user.getJournalEntries();

            journalEntries.add(journalEntry);

            user.setJournalEntries(journalEntries);

            repo.save(user);

            return user;
        }

        return null;
    }

    public void deleteByUserName(String userName) {
        repo.deleteByUserName(userName);
    }

    public boolean saveAdmin(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Arrays.asList("USER","ADMIN"));
        repo.save(newUser);
        return true;
    }
}
