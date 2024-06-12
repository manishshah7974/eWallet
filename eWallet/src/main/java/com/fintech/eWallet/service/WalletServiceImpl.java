package com.fintech.eWallet.service;

import com.fintech.eWallet.model.User;
import com.fintech.eWallet.model.Wallet;
import com.fintech.eWallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public List<Wallet> getWalletsByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public User findUserByWalletId(Long id) {
        return walletRepository.findUserByWalletId(id);
    }

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    }

    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet updateBalance(Long id, BigDecimal newBalance) {
        Optional<Wallet> walletOpt = walletRepository.findById(id);
        if (walletOpt.isPresent()) {
            Wallet wallet = walletOpt.get();
            wallet.setBalance(newBalance);
            return walletRepository.save(wallet);
        }
        return null;
    }
}
