package com.fintech.eWallet.utils.JwtTokenUtility;
import org.springframework.stereotype.Service;
import java.util.HashSet;

@Service
public class TokenBlacklistService {

    private HashSet<String> blacklist = new HashSet<>();

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
