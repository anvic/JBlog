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

package org.jblogcms.core.account.repository;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.config.PersistenceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Victor Andreenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:blog.xml")
@Transactional
public class AccountRepositoryItTest {

    private static final Long ACCOUNT_1_ID = 1L;
    private static final String ACCOUNT_1_EMAIL = "name1@mail.com";
    private static final Long ACCOUNT_2_ID = 2L;
    private static final String ACCOUNT_2_EMAIL = "name2@mail.com";
    private static final Long ACCOUNT_3_ID = 3L;
    private static final String ACCOUNT_3_EMAIL = "name3@mail.com";

    private static final Long NOT_EXISTING_ACCOUNT_ID = 1565235L;
    private static final String NOT_EXISTING_ACCOUNT_EMAIL = "notexist@mail.com";

    private static final Long ACCOUNT_WITH_NO_FAVORITE_ACCOUNTS = 678234L;
    private static final Long ACCOUNT_WITH_NO_SUBSCRIBERS = 678234L;

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";

    private Pageable pageable;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Transactional
    public void testFindAccountById_returnNull() throws Exception {
        Account notExistingAccount = accountRepository.findAccountById(NOT_EXISTING_ACCOUNT_ID);

        Assert.assertEquals(null, notExistingAccount);
    }

    @Test
    @Transactional
    public void testFindAccountById_returnAccount() throws Exception {
        Account existingAccount = accountRepository.findAccountById(ACCOUNT_1_ID);

        Assert.assertEquals(ACCOUNT_1_ID, existingAccount.getId());
        Assert.assertEquals(ACCOUNT_1_EMAIL, existingAccount.getEmail());
    }

    @Test
    @Transactional
    public void testFindAccountByEmail_returnNull() throws Exception {
        Account notExistingAccount = accountRepository.findAccountByEmail(NOT_EXISTING_ACCOUNT_EMAIL);

        Assert.assertEquals(null, notExistingAccount);
    }

    @Test
    @Transactional
    public void testFindAccountByEmail_returnAccount() throws Exception {
        Account existingAccount = accountRepository.findAccountByEmail(ACCOUNT_1_EMAIL);

        Assert.assertEquals(ACCOUNT_1_ID, existingAccount.getId());
        Assert.assertEquals(ACCOUNT_1_EMAIL, existingAccount.getEmail());
    }

    @Test
    @Transactional
    public void testCountAccountsByEmail_returnZero() throws Exception {
        int countAccountByEmail = accountRepository.countAccountsByEmail(NOT_EXISTING_ACCOUNT_EMAIL);

        Assert.assertEquals(0, countAccountByEmail);
    }

    @Test
    @Transactional
    public void testCountAccountsByEmail_returnOne() throws Exception {
        int countAccountByEmail = accountRepository.countAccountsByEmail(ACCOUNT_1_EMAIL);

        Assert.assertEquals(1, countAccountByEmail);
    }

    @Test
    @Transactional
    public void testFindFavoriteAccounts_returnNoAccounts() throws Exception {
        Page<Account> accounts = accountRepository.findFavoriteAccounts(ACCOUNT_WITH_NO_FAVORITE_ACCOUNTS, pageable);

        Assert.assertEquals(0, accounts.getTotalElements());
    }

    @Test
    @Transactional
    public void testFindAccounts_returnAllAccounts() throws Exception {
        Page<Account> accounts = accountRepository.findAccounts(pageable);

        Assert.assertEquals(3, accounts.getTotalElements());

        Assert.assertEquals(ACCOUNT_1_ID, accounts.getContent().get(0).getId());
        Assert.assertEquals(ACCOUNT_1_EMAIL, accounts.getContent().get(0).getEmail());

        Assert.assertEquals(ACCOUNT_2_ID, accounts.getContent().get(1).getId());
        Assert.assertEquals(ACCOUNT_2_EMAIL, accounts.getContent().get(1).getEmail());

        Assert.assertEquals(ACCOUNT_3_ID, accounts.getContent().get(2).getId());
        Assert.assertEquals(ACCOUNT_3_EMAIL, accounts.getContent().get(2).getEmail());
    }

    @Test
    @Transactional
    public void testFindFavoriteAccounts_returnTwoAccounts() throws Exception {
        Page<Account> accounts = accountRepository.findFavoriteAccounts(ACCOUNT_1_ID, pageable);

        Assert.assertEquals(2, accounts.getTotalElements());

        Assert.assertEquals(ACCOUNT_2_ID, accounts.getContent().get(0).getId());
        Assert.assertEquals(ACCOUNT_2_EMAIL, accounts.getContent().get(0).getEmail());

        Assert.assertEquals(ACCOUNT_3_ID, accounts.getContent().get(1).getId());
        Assert.assertEquals(ACCOUNT_3_EMAIL, accounts.getContent().get(1).getEmail());
    }

    @Test
    @Transactional
    public void testFindAccountSubscribers_returnNoAccounts() throws Exception {
        Page<Account> accounts = accountRepository.findAccountSubscribers(ACCOUNT_WITH_NO_SUBSCRIBERS, pageable);

        Assert.assertEquals(0, accounts.getTotalElements());
    }
}