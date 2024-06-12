package com.fintech.eWallet.controller;

import com.fintech.eWallet.model.Wallet;
import com.fintech.eWallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/user/{userId}")
    public List<Wallet> getWalletsByUserId(@PathVariable Long userId) {
        return walletService.getWalletsByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) {
        Optional<Wallet> walletOpt = walletService.getWalletById(id);
        return walletOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        Wallet createdWallet = walletService.createWallet(wallet);
        return ResponseEntity.ok(createdWallet);
    }

    @PutMapping("/{id}/balance")
    public ResponseEntity<Wallet> updateBalance(@PathVariable Long id, @RequestBody BigDecimal newBalance) {
        Wallet updatedWallet = walletService.updateBalance(id, newBalance);
        if (updatedWallet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedWallet);
    }
}
