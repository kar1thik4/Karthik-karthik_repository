package com.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.main.model.Transaction;

public interface TransactionRepoIntf extends CrudRepository<Transaction,Integer> {

	
	
	@Query(value = "select * from transaction where user_id=?1",nativeQuery = true)
	List<Transaction> findTransactionById(int id);

}
