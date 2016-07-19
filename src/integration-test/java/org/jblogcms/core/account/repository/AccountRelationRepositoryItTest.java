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
import org.jblogcms.core.account.model.AccountRelation;
import org.jblogcms.core.config.PersistenceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class AccountRelationRepositoryItTest {

    private static final Long ACCOUNT_1_ID = 1L;
    private static final Long ACCOUNT_2_ID = 2L;
    private static final Long ACCOUNT_3_ID = 3L;
    private static final Long ACCOUNT_RELATION_ID_1 = 1L;

    @Autowired
    AccountRelationRepository accountRelationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Transactional
    public void testGetAccountRelations() throws Exception {
        Account account1 = accountRepository.findOne(ACCOUNT_1_ID);
        Account account2 = accountRepository.findOne(ACCOUNT_2_ID);
        List<Account> accounts = Arrays.asList(account1, account2);

        List<AccountRelation> accountRelations =
                accountRelationRepository.getItemRelations(accounts, ACCOUNT_1_ID);

        Assert.assertEquals(1, accountRelations.size());
        Assert.assertEquals(ACCOUNT_RELATION_ID_1, accountRelations.get(0).getId());
    }

    @Test
    @Transactional
    public void testGetAccountRelation() throws Exception {
        AccountRelation accountRelation =
                accountRelationRepository.getItemRelation(ACCOUNT_3_ID, ACCOUNT_1_ID);

        Assert.assertNotEquals(null, accountRelation);
    }
}