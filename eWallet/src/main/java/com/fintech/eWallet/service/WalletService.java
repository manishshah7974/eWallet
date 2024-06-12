package com.fintech.eWallet.service;

import com.fintech.eWallet.model.User;
import com.fintech.eWallet.model.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WalletService {
    public List<Wallet> getWalletsByUserId(Long userId);

    User findUserByWalletId(Long id);
    public Optional<Wallet> getWalletById(Long id);
    public Wallet createWallet(Wallet wallet);
    public Wallet updateBalance(Long id, BigDecimal newBalance);
}
