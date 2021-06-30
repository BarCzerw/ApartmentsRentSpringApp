package com.sda.ApartmentsRent.configuration;

import com.sda.ApartmentsRent.model.Account;
import com.sda.ApartmentsRent.model.AccountRole;
import com.sda.ApartmentsRent.repository.AccountRepository;
import com.sda.ApartmentsRent.repository.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_SUPERVISOR = "ROLE_SUPERVISOR";
    private static final String[] AVAILABLE_ROLES = {ROLE_ADMIN, ROLE_USER, ROLE_SUPERVISOR};

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (String availableRole : AVAILABLE_ROLES) {
            addRole(availableRole);
        }
        addUser("admin","admin",new String[]{ROLE_ADMIN});
        addUser("user","user", new String[]{ROLE_USER});
    }

    private void addUser(String username, String password, String[] roles) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (!optionalAccount.isPresent()) {
            Account account = Account.builder()
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build();

            Set<AccountRole> roleSet = new HashSet<>();
            for (String role : roles) {
                Optional<AccountRole> roleOptional = accountRoleRepository.findByName(role);
                if (roleOptional.isPresent()) {
                    roleSet.add(roleOptional.get());
                }
            }
            account.setRoles(roleSet);
            accountRepository.save(account);
        }
    }

    private void addRole(String availableRole) {
        Optional<AccountRole> optionalAccountRole = accountRoleRepository.findByName(availableRole);
        if (!optionalAccountRole.isPresent()) {
            AccountRole accountRole = AccountRole.builder()
                    .name(availableRole)
                    .build();

            accountRoleRepository.save(accountRole);
        }
    }
}
