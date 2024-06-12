package com.fintech.eWallet.controller;

import com.fintech.eWallet.model.User;
import com.fintech.eWallet.service.UserService;
import com.fintech.eWallet.utils.Crypto.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EncryptionUtil encryptionUtil;



    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/encrypted")
    public List<User> getAllUsersEncrypted() {
        List<User> users= userService.getAllUsers();
        users.forEach(user -> {
            try {
                if (user.getName() != null)
                    user.setName(encryptionUtil.encrypt(user.getName()));
                if (user.getMobile() != null)
                    user.setMobile(encryptionUtil.encrypt(user.getMobile()));
                if (user.getEmail() != null)
                    user.setEmail(encryptionUtil.encrypt(user.getEmail()));
                user.setWallets(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user) throws Exception {
        Optional<User> existingUserOpt = userService.findById(id);
        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User existingUser = existingUserOpt.get();
        if (user.getPassword() != null)
            existingUser.setPassword(encryptionUtil.encrypt(user.getPassword()));
        if (user.getEmail() != null)
            existingUser.setName(user.getName());
        if (user.getMobile() != null)
            existingUser.setMobile(user.getMobile());
        if (user.getName() != null)
            existingUser.setEmail(user.getName());
        User updatedUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<User> updateUserByEmail(@PathVariable String email, @RequestBody User user) throws Exception {
        Optional<User> existingUserOpt = Optional.ofNullable(userService.findUserByEmail(email));
        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User existingUser = existingUserOpt.get();
        if (user.getPassword() != null)
            existingUser.setPassword(encryptionUtil.encrypt(user.getPassword()));
        if (user.getEmail() != null)
            existingUser.setName(user.getName());
        if (user.getMobile() != null)
            existingUser.setMobile(user.getMobile());
        if (user.getName() != null)
            existingUser.setEmail(user.getName());
        User updatedUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
