package com.fintech.eWallet.repository;

import com.fintech.eWallet.model.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {
    Kyc findByUserId(Long userId);

    Optional<Kyc> findById(Long id);

    Kyc save(Kyc kyc);
}
