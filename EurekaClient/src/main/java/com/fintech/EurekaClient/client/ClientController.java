package com.fintech.EurekaClient.client;

import com.fintech.EurekaClient.dto.Wallet;
import com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/wallets")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${eureka.client.service-url.eWallet}")
    private String baseUrl;

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable int id) {
        String url = String.format("%s/wallets/%d", baseUrl, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Wallet> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Wallet>() {
                    }
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            } else {
                throw new WalletServiceException("Failed to retrieve wallet, status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Wallet not found with id {}: {}", id, e.getMessage());
            throw new WalletNotFoundException("Wallet not found with id " + id);
        } catch (HttpClientErrorException e) {
            logger.error("Client error occurred while fetching wallet with id {}: {}", id, e.getMessage());
            throw new WalletServiceException("Error fetching wallet with id " + id + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching wallet with id {}: {}", id, e.getMessage());
            throw new WalletServiceException("Unexpected error fetching wallet with id " + id + ": " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wallet>> getWalletsByUserId(@PathVariable Long userId) {
        String url = String.format("%s/wallets/user/%d", baseUrl, userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<Wallet>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Wallet>>() {
                    }
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            } else {
                throw new WalletServiceException("Failed to retrieve wallets for user, status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Wallets not found for user id {}: {}", userId, e.getMessage());
            throw new UserWalletsNotFoundException("Wallets not found for user id " + userId);
        } catch (HttpClientErrorException e) {
            logger.error("Client error occurred while fetching wallets for user id {}: {}", userId, e.getMessage());
            throw new WalletServiceException("Error fetching wallets for user id " + userId + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching wallets for user id {}: {}", userId, e.getMessage());
            throw new WalletServiceException("Unexpected error fetching wallets for user id " + userId + ": " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        String url = String.format("%s/wallets", baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Wallet> entity = new HttpEntity<>(wallet, headers);

        try {
            ResponseEntity<Wallet> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Wallet>() {
                    }
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            } else {
                throw new WalletCreationException("Failed to create wallet, status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            logger.error("Client error occurred while creating wallet: {}", e.getMessage());
            throw new WalletCreationException("Error creating wallet: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating wallet: {}", e.getMessage());
            throw new WalletCreationException("Unexpected error creating wallet: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/balance/{newBalance}")
    public ResponseEntity<Wallet> updateBalance(@PathVariable Long id, @PathVariable BigDecimal newBalance) {
        String url = String.format("%s/wallets/%d/balance", baseUrl, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BigDecimal> entity = new HttpEntity<>(newBalance, headers);

        try {
            ResponseEntity<Wallet> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    new ParameterizedTypeReference<Wallet>() {
                    }
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new WalletNotFoundException("Wallet not found with id " + id);
            } else {
                throw new WalletBalanceUpdateException("Failed to update wallet balance, status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Wallet not found with id {}: {}", id, e.getMessage());
            throw new WalletNotFoundException("Wallet not found with id " + id);
        } catch (HttpClientErrorException e) {
            logger.error("Client error occurred while updating balance for wallet with id {}: {}", id, e.getMessage());
            throw new WalletBalanceUpdateException("Error updating balance for wallet with id " + id + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating balance for wallet with id {}: {}", id, e.getMessage());
            throw new WalletBalanceUpdateException("Unexpected error updating balance for wallet with id " + id + ": " + e.getMessage());
        }
    }
}
