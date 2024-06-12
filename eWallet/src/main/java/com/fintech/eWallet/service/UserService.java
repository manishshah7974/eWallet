package com.fintech.eWallet.service;

import com.fintech.eWallet.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);
    User findUserByEmail(String email);

    boolean checkIfUserAlreadyExists(String email);
    List<User> findUserByMobile(String mobile);
    
    User save(User user);

    List<User> findAllUsers();


    List<User> getAllUsers();

    User saveUser(User user);

    void deleteUser(Long id);
}
