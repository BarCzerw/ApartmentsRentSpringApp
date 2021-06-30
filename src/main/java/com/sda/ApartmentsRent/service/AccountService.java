package com.sda.ApartmentsRent.service;

import com.sda.ApartmentsRent.exception.InvalidRegisterData;
import com.sda.ApartmentsRent.model.Account;
import com.sda.ApartmentsRent.model.CreateAccountRequest;
import com.sda.ApartmentsRent.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Account> getAccountList() {
        return accountRepository.findAll();
    }

    public boolean register(CreateAccountRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidRegisterData("Passwords do not match!");
        }

        Optional<Account> accountOptional = accountRepository.findByUsername(request.getUsername());
        if(accountOptional.isPresent()){
            throw new InvalidRegisterData("Account with given username already exists!");
        }

        Account account = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        accountRepository.save(account);
        return true;
    }
}
