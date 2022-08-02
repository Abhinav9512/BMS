package com.BankManagement.Bank.service;

import com.BankManagement.Bank.model.account;
import com.BankManagement.Bank.repo.repository;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Random;

@Service
public class bankservice {

private final repository repo;

@Autowired
public bankservice (repository repo)
{
    this.repo = repo;
}

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
public account addaAccount (account acc)
{
    String email = acc.getEmail();

    List<account> list1 = repo.findByemail(email);

    long a;

    while(true) {
        a =  generateRandom(12);

        List <account> list = repo.findByaccountno(String.valueOf(a));
        if(list.size()==0)
            break;
    }
    acc.setAccountno(String.valueOf(a));

    acc.setBalance(0);
    return  this.repo.save(acc);

}
    public List <account> findallaccount ()
    {
        return repo.findAll();
    }



   public account updateaccount(String id, account acc)

    {
        List<account>list = repo.findByid(id);
        account account = list.get(0);
        account.setName(acc.getName());
     account.setAddress(acc.getAddress());
     account.setEmail(acc.getEmail());
     account.setPhoneno(acc.getPhoneno());
     account.setBalance(acc.getBalance());
     account.setTransactions(acc.getTransactions());
     repo.save(account);
     return account;
    }

    public void  deleteaccount (String id)
    {
        List<account>list = repo.findByid(id);

        repo.delete(list.get(0));
    }

    public boolean findbyemail(String email)
    {
        List<account> list1 = repo.findByemail(email);

        if(list1.size()==0)
            return true;
        else
            return false;
    }

    public boolean findbyemail(String email,String id)
    {
        List<account> list1 = repo.findByemail(email);

        if(list1.size()==0)
            return true;
        if(list1.get(0).getId().equals(id))
            return true;

        return false;
    }
    public boolean validatephoneno(String phoneno)
    {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = null;
       try {
           phoneNumber  = phoneNumberUtil.parse(phoneno, "IN");
       }
       catch (NumberParseException e)
        {
            e.printStackTrace();
        }

       return phoneNumberUtil.isValidNumber(phoneNumber);
    }
    public  boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public account credit (double amount,String id)
    {
        List<account>list = repo.findByid(id);

        list.get(0).setBalance(list.get(0).getBalance()+ amount);
       String c = String.valueOf(generateRandom(10));
        String s = "TransactionId: "+c+" -amount credited= "+String.valueOf(amount) ;
        list.get(0).getTransactions().add(s);
        repo.save(list.get(0));
        return list.get(0);
    }

    public boolean isenoughBalance ( double amount,String id)
    {
        List<account>list = repo.findByid(id);

        if(list.get(0).getBalance()>=amount)
            return true;
        else
            return false;
    }

    public account debit (double amount,String id)
    {
        List<account>list = repo.findByid(id);

        list.get(0).setBalance(list.get(0).getBalance() -  amount);


        String c = String.valueOf(generateRandom(10));
        String s = "TransactionId: "+c+" -amount debited= "+String.valueOf(amount) ;
        list.get(0).getTransactions().add(s);
        repo.save(list.get(0));
        return list.get(0);
    }
}
