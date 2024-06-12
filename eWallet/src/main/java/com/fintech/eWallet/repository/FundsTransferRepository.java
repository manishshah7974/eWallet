package com.fintech.eWallet.repository;

import com.fintech.eWallet.model.FundsTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FundsTransferRepository extends JpaRepository<FundsTransfer, Long> {

    List<FundsTransfer> findAll();

    Optional<FundsTransfer> findById(Long id);

    FundsTransfer save(FundsTransfer fundsTransfer);
}
