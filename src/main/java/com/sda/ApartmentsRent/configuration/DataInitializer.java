package com.sda.ApartmentsRent.configuration;

import com.sda.ApartmentsRent.model.Account;
import com.sda.ApartmentsRent.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final static String ADMIN_USERNAME = "admin";
    private final static String ADMIN_PASSWORD = "admin";

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Optional<Account> accountOptional = accountRepository.findByUsername(ADMIN_USERNAME);
        if (accountOptional.isEmpty()) {
            Account account = new Account();

            account.setUsername(ADMIN_USERNAME);
            account.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));

            account.setAccountNonExpired(true);
            account.setAccountNonLocked(true);
            account.setCredentialsNonExpired(true);
            account.setEnabled(true);

            accountRepository.save(account);
        }
    }
}
