package com.hoanghai.ecommerce.multivendor.api.services.impl;

import com.hoanghai.ecommerce.multivendor.api.entities.Order;
import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import com.hoanghai.ecommerce.multivendor.api.entities.Transaction;
import com.hoanghai.ecommerce.multivendor.api.repositorys.SellerRepository;
import com.hoanghai.ecommerce.multivendor.api.repositorys.TransactionRepository;
import com.hoanghai.ecommerce.multivendor.api.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final SellerRepository sellerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
