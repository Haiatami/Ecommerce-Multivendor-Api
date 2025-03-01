package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.entities.Order;
import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import com.hoanghai.ecommerce.multivendor.api.entities.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);

    List<Transaction> getTransactionsBySellerId(Seller seller);

    List<Transaction> getAllTransactions();
}
