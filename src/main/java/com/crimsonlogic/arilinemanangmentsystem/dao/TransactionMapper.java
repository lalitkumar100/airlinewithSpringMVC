package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
@org.springframework.stereotype.Repository
public interface TransactionMapper extends org.springframework.data.repository.Repository<Transaction, String> {
    Transaction save(Transaction entity);


    default int insertTransaction(Transaction transaction) {
        save(transaction);
        return 1;
    }
}
