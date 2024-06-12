package com.fintech.eWallet.repository;

import com.fintech.eWallet.model.User;
import com.fintech.eWallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    List<Wallet> findByUserId(Long userId);

    @Query("SELECT w.user FROM Wallet w WHERE w.id = :walletId")
    User findUserByWalletId(@Param("walletId") Long walletId);

}
