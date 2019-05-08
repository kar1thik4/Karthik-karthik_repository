package com.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.model.Transaction;
import com.main.model.User;
import com.main.service.TransactionServIntf;
import com.main.service.UserServIntf;

@RestController
public class UserController {

	@Autowired
	UserServIntf userServ;
	
	@Autowired
	TransactionServIntf transServ;
	
	@PostMapping("/save")
	public String registration(@RequestBody User user) {

		User user1= userServ.saveUser(user);
		if(user1!=null) {
			
			return "Saved successfully";
		}
		else {
			return "Enter a different user name or email";
		}

	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		User user1=userServ.findUserByUnameAndPasswd(user);
		if(user1!=null){
			return "Success";
		}
		else{
			return "Invalid Credentials";
		}
	}

	
	@PostMapping("/Transaction/{id}")
	public String transaction(@PathVariable("id") int userId,@RequestBody Transaction transaction) {
		
		User user=userServ.findUserById(userId);
		String result="";
		
		if(transaction.getPayType().equals("debit")) {
			if(user.getBalance()>transaction.getAmount()) {
				double amount=user.getBalance()-transaction.getAmount();
				user.setBalance(amount);
				result="Amount Debited Successfully";
			}
			else {
				result="Insufficient Balance....";
			}
		}
		else {
			double amount=user.getBalance()+transaction.getAmount();
			user.setBalance(amount);
			result="Amount Credited Successfully";
		}
		transaction.setUser(user);
		transServ.save(transaction);
		return result;
	}
	
	@GetMapping("/history/{id}")
	public List<Transaction> transHistory(@PathVariable("id") int userId){
		
		List<Transaction> list=transServ.findById(userId);
		return list;

	}
	
	
}
