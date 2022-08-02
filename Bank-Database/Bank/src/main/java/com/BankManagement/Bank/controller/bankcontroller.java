package com.BankManagement.Bank.controller;
import com.BankManagement.Bank.model.account;
import com.BankManagement.Bank.service.bankservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/account")
public class bankcontroller {



    private final bankservice bankservice;
    private String id;
    private double amount;

    public bankcontroller(bankservice bankservice)
    {
        this.bankservice = bankservice ;
    }
    @GetMapping("/all")



    public ResponseEntity<List<account>> getAllEmployees()
    {
        List <account> accounts = bankservice.findallaccount();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<account> addAccount(@Valid @RequestBody account account )
    {
        if (!bankservice.isNumeric(account.getPhoneno()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Enter correct Phone Number");
            if(!bankservice.validatephoneno(account.getPhoneno()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Enter correct Phone Number");


        if(bankservice.findbyemail(account.getEmail())) {
            account newaccount = bankservice.addaAccount(account);
            return new ResponseEntity<>(newaccount, HttpStatus.CREATED);
        }
        else

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account already exist");


    }


   @PutMapping("/update/{id}")

    public ResponseEntity<account> updateEmployee(@PathVariable("id") String id,@Valid @RequestBody account account ) {

       if (!bankservice.isNumeric(account.getPhoneno()))
           throw new ResponseStatusException(
                   HttpStatus.BAD_REQUEST, "Enter correct Phone Number");
       if(!bankservice.validatephoneno(account.getPhoneno()))
           throw new ResponseStatusException(
                   HttpStatus.BAD_REQUEST, "Enter correct Phone Number");
       if (bankservice.findbyemail(account.getEmail(), id)) {
           account updateaccount = bankservice.updateaccount(id, account);
           return new ResponseEntity<>(updateaccount, HttpStatus.OK);
       } else
           throw new ResponseStatusException(
                   HttpStatus.BAD_REQUEST, "Email already exist");
    }

    @DeleteMapping ("/delete/{id}")

    public ResponseEntity<?>deleteEmployee (@PathVariable("id")String id)
    {
        bankservice.deleteaccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/credit/{id}")

    public ResponseEntity<account> credit ( @PathVariable("id")String id,@RequestParam double amount )
    {

        if(amount<=0)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Enter correct amount");

        account updateaccount = bankservice.credit(amount,id);
        return new ResponseEntity<>(updateaccount, HttpStatus.OK);

    }
    @PutMapping("/debit/{id}")

    public ResponseEntity<account> debit( @PathVariable("id")String id,@RequestParam double amount )
    {
        if(amount<=0)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Enter correct amount");

        if(!bankservice.isenoughBalance(amount,id))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Sorry! not enough balance ");
        account updateaccount = bankservice.debit(amount,id);
        return new ResponseEntity<>(updateaccount, HttpStatus.OK);

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
