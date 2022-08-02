package com.BankManagement.Bank.repo;

import com.BankManagement.Bank.model.account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repository extends MongoRepository <account,String>{


     List<account> findByaccountno(String accountno);

     List<account> findByid(String id);

     List<account> findByemail(String email);


}
