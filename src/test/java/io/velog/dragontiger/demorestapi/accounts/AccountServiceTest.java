package io.velog.dragontiger.demorestapi.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사용자 생성 후 조회")
    public void findByUsername() {

        // Given
        String username = "dragontiger@gmail.com";
        String password = "dragontiger";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        accountService.saveAccount(account);

        // When
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertTrue(passwordEncoder.matches(password, userDetails.getPassword()));
    }

    @Test
    @DisplayName("없는 사용자 조회 시 UsernameNotFoundException")
    public void fintByUsernameFail() {

        // Given
        String username = "fail@gmail.com";

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername(username);
        });

    }
}