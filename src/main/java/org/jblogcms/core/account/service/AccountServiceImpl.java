/*
 * Copyright 2016 Victor Andreenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jblogcms.core.account.service;

import org.jblogcms.core.account.exception.DuplicateEmailException;
import org.jblogcms.core.account.exception.OldPasswordNotMatchedException;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.model.AccountRole;
import org.jblogcms.core.account.model.ChangePasswordDTO;
import org.jblogcms.core.account.model.NewAccountDTO;
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.common.service.ItemServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Provides the implementation for the service for accessing, adding, changing, deleting {@link Account}.
 *
 * @author Victor Andreenko
 */
@Service
public class AccountServiceImpl
        extends ItemServiceImpl<Account>
        implements AccountService {

    private static final Logger logger =
            LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AccountToolService accountToolService;

    public AccountServiceImpl() {
        super(Account.class);
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAccountToolService(AccountToolService accountToolService) {
        this.accountToolService = accountToolService;
    }

    @Override
    @Transactional
    public void delete(Long accountId) {
        throw new UnsupportedOperationException("Cannot delete the account");
    }

    @Override
    @PreAuthorize("#account.id == principal.id")
    @Transactional
    public void delete(Account account) {
        throw new UnsupportedOperationException("Cannot delete the account");
    }

    @Override
    @Transactional(readOnly = true)
    public int countAllAccountsWithRoleAdmin() {
        return accountRepository.countAllAccountsWithRoleAdmin();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Account> findAccounts(Pageable pageable, Long currentAccountId) {
        Page<Account> accounts = accountRepository.findAccounts(pageable);
        accountToolService.addItemRelationsToItemPage(accounts, currentAccountId);
        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Account> findFavoriteAccounts(Long accountId, Pageable pageable, Long currentAccountId) {
        Page<Account> accounts = accountRepository.findFavoriteAccounts(accountId, pageable);
        accounts = accountToolService.addItemRelationsToItemPage(accounts, currentAccountId);
        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Account> findAccountSubscribers(long accountId, Pageable pageable, Long currentAccountId) {
        Page<Account> accounts = accountRepository.findAccountSubscribers(accountId, pageable);
        accounts = accountToolService.addItemRelationsToItemPage(accounts, currentAccountId);
        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Account> findBlogSubscribers(Long accountId, Pageable pageable, Long currentAccountId) {
        Page<Account> accounts = accountRepository.findBlogSubscribers(accountId, pageable);
        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Account findAccountById(Long accountId, Long currentAccountId) {
        Account account = accountRepository.findAccountById(accountId);
        account = accountToolService.addItemRelationToItem(account, currentAccountId);
        return account;
    }

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("returnObject.id == principal.id")
    public Account findAccountByIdForEdit(Long accountId) {
        return accountRepository.findAccountById(accountId);
    }


    @Override
    @Transactional
    @PreAuthorize("#account.id == principal.id")
    public Account editAccount(Account account)
            throws DuplicateEmailException {

        if (!isAccountEmailUnique(account.getEmail()) &&
                !accountRepository.findOne(account.getId()).getEmail().equals(account.getEmail())) {
            throw new DuplicateEmailException("The email address: " + account.getEmail() + " is already in use.");
        }

        return accountRepository.save(account);
    }


    @Override
    @Transactional
    public boolean changeAccountRole(Long accountId, String newRole) {

        Account account = accountRepository.findAccountById(accountId);
        account.setAccountRole(AccountRole.valueOf(newRole));
        accountRepository.save(account);

        return true;
    }

    @Override
    @Transactional
    public Account addAccount(NewAccountDTO newAccountDTO)
            throws DuplicateEmailException {


        if (!isAccountEmailUnique(newAccountDTO.getEmail())) {
            throw new DuplicateEmailException("DuplicateEmailException email:" + newAccountDTO.getEmail());
        }
        AccountRole accountRole = AccountRole.ROLE_USER;
        if (Objects.equals(newAccountDTO.getRole(), "admin")) {
            accountRole = AccountRole.ROLE_ADMIN;
        }
        Account account = new Account();
        account.setEmail(newAccountDTO.getEmail());
        account.setFirstName(newAccountDTO.getFirstName());
        account.setLastName(newAccountDTO.getLastName());
        account.setAccountRole(accountRole);
        if (!newAccountDTO.isSocialSignIn()) {
            account.setPassword(passwordEncoder.encode(newAccountDTO.getPassword()));
        } else {
            account.setSignInProvider(newAccountDTO.getSignInProvider());
            account.setPassword("social");
        }

        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) throws OldPasswordNotMatchedException {
        String newPasswordEncoded = passwordEncoder.encode(changePasswordDTO.getPassword());

        Account account = accountRepository.findOne(changePasswordDTO.getAccountId());

        if (changePasswordDTO.getOldPassword() != null &&
                passwordEncoder.matches(changePasswordDTO.getOldPassword(), account.getPassword())) {
            account.setPassword(newPasswordEncoded);
            accountRepository.save(account);
            return true;
        } else {
            throw new OldPasswordNotMatchedException(changePasswordDTO.getAccountId(), changePasswordDTO.getOldPassword());
        }
    }


    private boolean isAccountEmailUnique(String email) {
        return (accountRepository.countAccountsByEmail(email)) == 0;
    }


}
