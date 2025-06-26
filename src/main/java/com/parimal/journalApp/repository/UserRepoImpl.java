package com.parimal.journalApp.repository;

import com.parimal.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<User> getUserSA(){
        Query query=new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("email").exists(true),
                        Criteria.where("email").ne(null),
                        Criteria.where("email").ne("")
                )
        );
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users=mongoTemplate.find(query,User.class);
        return users;
    }

}
