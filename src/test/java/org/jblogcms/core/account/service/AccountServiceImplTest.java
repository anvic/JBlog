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
import org.jblogcms.core.account.model.NewAccountDTO;
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.account.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Victor Andreenko
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
    private static final Long ACCOUNT_ID = 1L;
    private static final Long CURRENT_ACCOUNT_ID = 2L;

    private static final Long POST_ID = 1L;

    private static final Long EXISTING_ACCOUNT_ID = 111L;
    private static final String EXISTING_ACCOUNT_EMAIL = "existingAccount@mail.com";

    private static final Long NEW_ACCOUNT_POST_ID = 112L;
    private static final String NEW_ACCOUNT_EMAIL = "new account text";
    private static final String ACCOUNT_NOT_DUPLICATE_EMAIL = "notDuplicate@mail.com";

    private static final Long ACCOUNT_1_ID = 1L;
    private static final String ACCOUNT_1_EMAIL = "account1@mail.com";

    private static final Long ACCOUNT_2_ID = 2L;
    private static final String ACCOUNT_2_EMAIL = "account2@mail.com";

    private static final Long ACCOUNT_3_ID = 3L;
    private static final String ACCOUNT_3_EMAIL = "account3@mail.com";

    private static final int ACCOUNTS_SIZE = 3;

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";
    private static final String ACCOUNT_DUPLICATE_EMAIL = "duplicatedemail@mail.com";


    private Account account1 = new Account();
    private Account account2 = new Account();
    private Account account3 = new Account();
    private Account existingAccount = new Account();

    private List<Account> accountList = Arrays.asList(account1, account2, account3);

    private Pageable pageable;
    private Page<Account> accounts;


    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private AccountToolService accountToolServiceMock;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    AccountServiceImpl accountService = new AccountServiceImpl();

    @Before
    public void setUp() throws Exception {
        account1.setId(ACCOUNT_1_ID);
        account1.setEmail(ACCOUNT_1_EMAIL);

        account2.setId(ACCOUNT_2_ID);
        account2.setEmail(ACCOUNT_2_EMAIL);

        account3.setId(ACCOUNT_3_ID);
        account3.setEmail(ACCOUNT_3_EMAIL);

        existingAccount.setId(EXISTING_ACCOUNT_ID);
        existingAccount.setEmail(EXISTING_ACCOUNT_EMAIL);

        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
        accounts = new PageImpl(accountList, pageable, ACCOUNTS_SIZE);
    }


    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findAllAccounts() throws Exception {
        when(accountRepositoryMock.findAccounts(pageable)).thenReturn(accounts);
        when(accountToolServiceMock.addItemRelationsToItemPage(accounts, CURRENT_ACCOUNT_ID)).thenReturn(accounts);

        Page<Account> testAccounts = accountService.findAccounts(pageable, CURRENT_ACCOUNT_ID);

        verify(accountRepositoryMock, times(1)).findAccounts(pageable);
        verify(accountToolServiceMock, times(1)).addItemRelationsToItemPage(accounts, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(ACCOUNTS_SIZE, testAccounts.getTotalElements());

        Assert.assertEquals(ACCOUNT_1_ID, testAccounts.getContent().get(0).getId());
        Assert.assertEquals(ACCOUNT_1_EMAIL, testAccounts.getContent().get(0).getEmail());
        Assert.assertEquals(ACCOUNT_2_ID, testAccounts.getContent().get(1).getId());
        Assert.assertEquals(ACCOUNT_2_EMAIL, testAccounts.getContent().get(1).getEmail());
        Assert.assertEquals(ACCOUNT_3_ID, testAccounts.getContent().get(2).getId());
        Assert.assertEquals(ACCOUNT_3_EMAIL, testAccounts.getContent().get(2).getEmail());
    }

    @Test
    public void findFavoriteAccounts() throws Exception {
        when(accountRepositoryMock.findFavoriteAccounts(ACCOUNT_ID, pageable)).thenReturn(accounts);
        when(accountToolServiceMock.addItemRelationsToItemPage(accounts, CURRENT_ACCOUNT_ID)).thenReturn(accounts);

        Page<Account> testAccounts = accountService.findFavoriteAccounts(ACCOUNT_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(accountRepositoryMock, times(1)).findFavoriteAccounts(ACCOUNT_ID, pageable);
        verify(accountToolServiceMock, times(1)).addItemRelationsToItemPage(accounts, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(ACCOUNTS_SIZE, testAccounts.getTotalElements());

        Assert.assertEquals(ACCOUNT_1_ID, testAccounts.getContent().get(0).getId());
        Assert.assertEquals(ACCOUNT_1_EMAIL, testAccounts.getContent().get(0).getEmail());
        Assert.assertEquals(ACCOUNT_2_ID, testAccounts.getContent().get(1).getId());
        Assert.assertEquals(ACCOUNT_2_EMAIL, testAccounts.getContent().get(1).getEmail());
        Assert.assertEquals(ACCOUNT_3_ID, testAccounts.getContent().get(2).getId());
        Assert.assertEquals(ACCOUNT_3_EMAIL, testAccounts.getContent().get(2).getEmail());
    }

    @Test
    public void findAccountSubscribers() throws Exception {
        when(accountRepositoryMock.findAccountSubscribers(ACCOUNT_ID, pageable)).thenReturn(accounts);
        when(accountToolServiceMock.addItemRelationsToItemPage(accounts, CURRENT_ACCOUNT_ID)).thenReturn(accounts);

        Page<Account> testAccounts = accountService.findAccountSubscribers(ACCOUNT_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(accountRepositoryMock, times(1)).findAccountSubscribers(ACCOUNT_ID, pageable);
        verify(accountToolServiceMock, times(1)).addItemRelationsToItemPage(accounts, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(ACCOUNTS_SIZE, testAccounts.getTotalElements());

        Assert.assertEquals(ACCOUNT_1_ID, testAccounts.getContent().get(0).getId());
        Assert.assertEquals(ACCOUNT_1_EMAIL, testAccounts.getContent().get(0).getEmail());
        Assert.assertEquals(ACCOUNT_2_ID, testAccounts.getContent().get(1).getId());
        Assert.assertEquals(ACCOUNT_2_EMAIL, testAccounts.getContent().get(1).getEmail());
        Assert.assertEquals(ACCOUNT_3_ID, testAccounts.getContent().get(2).getId());
        Assert.assertEquals(ACCOUNT_3_EMAIL, testAccounts.getContent().get(2).getEmail());
    }

    @Test
    public void findAccountById() throws Exception {
        when(accountRepositoryMock.findAccountById(EXISTING_ACCOUNT_ID)).thenReturn(existingAccount);
        when(accountToolServiceMock.addItemRelationToItem(existingAccount, CURRENT_ACCOUNT_ID)).thenReturn(existingAccount);

        Account testAccount = accountService.findAccountById(EXISTING_ACCOUNT_ID, CURRENT_ACCOUNT_ID);

        verify(accountRepositoryMock, times(1)).findAccountById(EXISTING_ACCOUNT_ID);
        verify(accountToolServiceMock, times(1)).addItemRelationToItem(existingAccount, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(EXISTING_ACCOUNT_ID, testAccount.getId());
        Assert.assertEquals(EXISTING_ACCOUNT_EMAIL, testAccount.getEmail());
    }

    @Test(expected = DuplicateEmailException.class)
    public void editAccount_duplicateEmail() throws Exception {
        Account editedExistingAccount = new Account();
        editedExistingAccount.setId(EXISTING_ACCOUNT_ID);
        editedExistingAccount.setEmail(ACCOUNT_DUPLICATE_EMAIL);

        when(accountRepositoryMock.findOne(EXISTING_ACCOUNT_ID)).thenReturn(existingAccount);
        when(accountRepositoryMock.countAccountsByEmail(ACCOUNT_DUPLICATE_EMAIL)).thenReturn(1);
        when(accountRepositoryMock.save(editedExistingAccount)).thenReturn(editedExistingAccount);

        Account savedEditedExistingAccount = accountService.editAccount(editedExistingAccount);
    }

    @Test
    public void editAccount_theSameEmail() throws Exception {
        Account editedExistingAccount = new Account();
        editedExistingAccount.setId(EXISTING_ACCOUNT_ID);
        editedExistingAccount.setEmail(EXISTING_ACCOUNT_EMAIL);

        when(accountRepositoryMock.findOne(EXISTING_ACCOUNT_ID)).thenReturn(existingAccount);
        when(accountRepositoryMock.countAccountsByEmail(EXISTING_ACCOUNT_EMAIL)).thenReturn(1);
        when(accountRepositoryMock.save(editedExistingAccount)).thenReturn(editedExistingAccount);

        Account savedEditedExistingAccount = accountService.editAccount(editedExistingAccount);

        verify(accountRepositoryMock, times(1)).countAccountsByEmail(EXISTING_ACCOUNT_EMAIL);
        verify(accountRepositoryMock, times(1)).save(editedExistingAccount);

        Assert.assertNotNull(savedEditedExistingAccount);
        Assert.assertEquals(EXISTING_ACCOUNT_EMAIL, savedEditedExistingAccount.getEmail());
    }

    @Test
    public void editAccount_changeEmail() throws Exception {
        Account editedExistingAccount = new Account();
        editedExistingAccount.setId(EXISTING_ACCOUNT_ID);
        editedExistingAccount.setEmail(ACCOUNT_NOT_DUPLICATE_EMAIL);

        when(accountRepositoryMock.findOne(EXISTING_ACCOUNT_ID)).thenReturn(existingAccount);
        when(accountRepositoryMock.countAccountsByEmail(ACCOUNT_NOT_DUPLICATE_EMAIL)).thenReturn(0);
        when(accountRepositoryMock.save(editedExistingAccount)).thenReturn(editedExistingAccount);

        Account savedEditedExistingAccount = accountService.editAccount(editedExistingAccount);

        verify(accountRepositoryMock, times(1)).countAccountsByEmail(ACCOUNT_NOT_DUPLICATE_EMAIL);
        verify(accountRepositoryMock, times(1)).save(editedExistingAccount);

        Assert.assertNotNull(savedEditedExistingAccount);
        Assert.assertEquals(ACCOUNT_NOT_DUPLICATE_EMAIL, savedEditedExistingAccount.getEmail());
    }

    @Test
    public void changeAccountRole() throws Exception {

    }

    @Test(expected = DuplicateEmailException.class)
    public void addAccount_duplicateEmail() throws Exception {
        NewAccountDTO newAccountDto = new NewAccountDTO();
        newAccountDto.setEmail(ACCOUNT_DUPLICATE_EMAIL);

        Account newAccount = new Account();
        newAccount.setEmail(newAccountDto.getEmail());

        when(accountRepositoryMock.countAccountsByEmail(ACCOUNT_DUPLICATE_EMAIL)).thenReturn(1);
        when(accountRepositoryMock.save(newAccount)).thenReturn(newAccount);

        Account savedNewAccount = accountService.addAccount(newAccountDto);
    }

    @Test
    public void addAccount() throws Exception {
        NewAccountDTO newAccountDto = new NewAccountDTO();
        newAccountDto.setEmail(NEW_ACCOUNT_EMAIL);

        Account newAccount = new Account();
        newAccount.setEmail(newAccountDto.getEmail());

        when(accountRepositoryMock.countAccountsByEmail(NEW_ACCOUNT_EMAIL)).thenReturn(0);
        when(accountRepositoryMock.save(newAccount)).thenReturn(newAccount);

        Account savedNewAccount = accountService.addAccount(newAccountDto);

        verify(accountRepositoryMock, times(1)).countAccountsByEmail(NEW_ACCOUNT_EMAIL);
        verify(accountRepositoryMock, times(1)).save(newAccount);

        Assert.assertNotNull(savedNewAccount);
        Assert.assertEquals(NEW_ACCOUNT_EMAIL, savedNewAccount.getEmail());
    }

}