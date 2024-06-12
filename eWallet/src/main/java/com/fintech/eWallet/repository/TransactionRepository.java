package com.fintech.eWallet.repository;

import com.fintech.eWallet.model.Transaction;
import com.fintech.eWallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAll();

    @Query("SELECT t FROM Transaction t WHERE t.walletId = :walletId")
    List<Transaction> findByWalletId(@Param("walletId") Long walletId);
}




