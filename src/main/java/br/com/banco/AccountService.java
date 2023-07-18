package br.com.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public boolean isValidAccountId(Long accountId) {
        Account account = accountRepository.findAccountById(accountId);
        return account != null;
    }
}
