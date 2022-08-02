package com.BankManagement.Bank.model;


import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Document
public class account implements Serializable {


    @Id
    private String id;

    @Pattern(regexp = "^[\\p{L} .'-]+$")
    @NotEmpty(message = "Name is mandatory")
    @Column(nullable = false,updatable = false)
    private String name;
    @NotEmpty(message = "Phone number is mandatory")

    private String phoneno;
    @NotEmpty(message = "Address number is mandatory")
    private String address;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @Column(nullable = false,updatable = false)
    private String accountno;
    private double balance;

    private List<String> transactions;
    public account(String name, String phoneno, String address, String email) {
        this.name = name;
        this.phoneno = phoneno;
        this.address = address;
        this.email = email;
        this.transactions = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAccountno() {
        return accountno;
    }

    public List<String> getTransactions()
    {
        return transactions;
    }
    public List<String> setTransactions( List<String> transactions)
    {
     return this.transactions = transactions;
    }
    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
